// Gabriel Kiewietz
// 230990703
// 06 August 2025

package za.co.hireahelper.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import za.co.hireahelper.domain.Message;
import za.co.hireahelper.factory.MessageFactory;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MessageControllerTest {

    private static Message message;

    @Autowired
    private TestRestTemplate restTemplate;

    private final String BASE_URL = "http://localhost:8080/message";

    @BeforeAll
    public static void setUp() {
        message = MessageFactory.buildMessage("msg01", "client01", "provider01", "Hello, I need help with plumbing.");
    }

    @Test
    @Order(1)
    void testCreate() {
        String url = BASE_URL + "/create";
        ResponseEntity<Message> response = restTemplate.postForEntity(url, message, Message.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        System.out.println("Created: " + response.getBody());
    }

    @Test
    @Order(2)
    void testRead() {
        String url = BASE_URL + "/read/" + message.getMessageId();
        ResponseEntity<Message> response = restTemplate.getForEntity(url, Message.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        System.out.println("Read: " + response.getBody());
    }

    @Test
    @Order(3)
    void testUpdate() {
        String url = BASE_URL + "/update";
        Message updatedMessage = new Message.Builder()
                .copy(message)
                .setContent("Updated content: I now need help with electrical as well.")
                .build();
        restTemplate.put(url, updatedMessage);

        ResponseEntity<Message> response = restTemplate.getForEntity(BASE_URL + "/read/" + updatedMessage.getMessageId(), Message.class);
        assertNotNull(response.getBody());
        assertEquals("Updated content: I now need help with electrical as well.", response.getBody().getContent());
        System.out.println("Updated: " + response.getBody());
    }

    @Test
    @Order(4)
    void testGetAll() {
        String url = BASE_URL + "/all";
        ResponseEntity<Message[]> response = restTemplate.getForEntity(url, Message[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().length > 0);
        System.out.println("All Messages:");
        for (Message msg : response.getBody()) {
            System.out.println(msg);
        }
    }

    @Test
    @Order(5)
    void testDelete() {
        String url = BASE_URL + "/delete/" + message.getMessageId();
        restTemplate.delete(url);

        ResponseEntity<Message> response = restTemplate.getForEntity(BASE_URL + "/read/" + message.getMessageId(), Message.class);
        assertNull(response.getBody());
        System.out.println("Deleted: " + message.getMessageId());
    }
}
