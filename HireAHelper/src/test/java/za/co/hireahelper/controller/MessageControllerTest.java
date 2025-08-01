/* MessageControllerTest.java
   Gabriel Kiewietz
   31 July 2025
*/

package za.co.hireahelper.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import za.co.hireahelper.domain.Message;
import za.co.hireahelper.factory.MessageFactory;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.MethodName.class)
class MessageControllerTest {

    private static Message message;

    @Autowired
    private TestRestTemplate restTemplate;

    private static final String BASE_URL = "http://localhost:8080/HireAHelper/message";

    @BeforeAll
    public static void setUp() {
        message = MessageFactory.createMessage(
                "msg001",
                LocalDateTime.now(),
                "Hello, I would like to book your service."
        );
    }

    @Test
    void a_create() {
        String url = BASE_URL + "/create";
        ResponseEntity<Message> postResponse = this.restTemplate.postForEntity(url, message, Message.class);
        assertNotNull(postResponse);
        Message savedMessage = postResponse.getBody();
        assertEquals(message.getMessageId(), savedMessage.getMessageId());
        System.out.println("Created: " + savedMessage);
    }

    @Test
    void b_read() {
        String url = BASE_URL + "/read/" + message.getMessageId();
        ResponseEntity<Message> response = this.restTemplate.getForEntity(url, Message.class);
        assertEquals(message.getMessageId(), response.getBody().getMessageId());
        System.out.println("Read: " + response.getBody());
    }

    @Test
    void c_update() {
        Message updatedMessage = new Message.Builder()
                .copy(message)
                .setContent("Updated content: Please confirm availability.")
                .build();

        String url = BASE_URL + "/update";
        this.restTemplate.put(url, updatedMessage);

        ResponseEntity<Message> response = this.restTemplate.getForEntity(BASE_URL + "/read/" + updatedMessage.getMessageId(), Message.class);
        assertNotNull(response.getBody());
        System.out.println("Updated: " + response.getBody());
    }

    @Test
    void d_getAll() {
        String url = BASE_URL + "/all";
        ResponseEntity<Message[]> response = this.restTemplate.getForEntity(url, Message[].class);
        assertNotNull(response.getBody());
        System.out.println("All Messages:");
        for (Message m : response.getBody()) {
            System.out.println(m);
        }
    }

    @Test
    void e_delete() {
        String url = BASE_URL + "/delete/" + message.getMessageId();
        this.restTemplate.delete(url);

        ResponseEntity<Message> response = this.restTemplate.getForEntity(BASE_URL + "/read/" + message.getMessageId(), Message.class);
        assertNull(response.getBody());
        System.out.println("Deleted: true");
    }
}
