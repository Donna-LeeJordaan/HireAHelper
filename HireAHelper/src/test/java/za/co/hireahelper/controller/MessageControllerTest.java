//Gabriel Kiewietz
// 11 July 2025
//230990703

package za.co.hireahelper.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import za.co.hireahelper.domain.Message;
import za.co.hireahelper.factory.MessageFactory;

import java.time.LocalDateTime;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.MethodName.class)
class MessageControllerTest {

    private static Message message;

    @Autowired
    private TestRestTemplate restTemplate;

    private static final String BASE_URL = "http://localhost:8080/HireAHelper/message";

    @BeforeAll
    public static void setUp() {
        message = MessageFactory.createMessage("message1", LocalDateTime.now(), "Hello");
        assertNotNull(message);
    }

    @Test
    void a_create() {
        String url = BASE_URL + "/create";
        ResponseEntity<Message> postResponse = restTemplate.postForEntity(url, message, Message.class);
        assertEquals(HttpStatus.OK, postResponse.getStatusCode());

        Message saved = postResponse.getBody();
        assertNotNull(saved);
        assertEquals(message.getMessageId(), saved.getMessageId());

        System.out.println("Created: " + saved);
    }

    @Test
    void b_read() {
        String url = BASE_URL + "/read/" + message.getMessageId();
        ResponseEntity<Message> response = restTemplate.getForEntity(url, Message.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        Message read = response.getBody();
        assertNotNull(read);
        assertEquals(message.getMessageId(), read.getMessageId());

        System.out.println("Read: " + read);
    }

    @Test
    void c_update() {
        Message updated = new Message.Builder()
                .copy(message)
                .setContent("Updated message content")
                .build();

        restTemplate.put(BASE_URL + "/update", updated);
        ResponseEntity<Message> response = restTemplate.getForEntity(BASE_URL + "/read/" + updated.getMessageId(), Message.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Updated message content", Objects.requireNonNull(response.getBody()).getContent());

        System.out.println("Updated: " + response.getBody());
    }

    @Test
    void d_getAll() {
        ResponseEntity<Message[]> response = restTemplate.getForEntity(BASE_URL + "/all", Message[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        Message[] messages = response.getBody();
        assertNotNull(messages);

        System.out.println("All Messages:");
        for (Message m : messages) {
            System.out.println(m);
        }
    }

    @Test
    void e_delete() {
        restTemplate.delete(BASE_URL + "/delete/" + message.getMessageId());
        ResponseEntity<Message> response = restTemplate.getForEntity(BASE_URL + "/read/" + message.getMessageId(), Message.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNull(response.getBody());

        System.out.println("Deleted: true");
    }
}
