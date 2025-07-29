//Gabriel Kiewietz
// 11 July 2025
//230990703

package za.co.hireahelper.controller;


import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import za.co.hireahelper.domain.Client;
import za.co.hireahelper.domain.Message;
import za.co.hireahelper.domain.ServiceProvider;
import za.co.hireahelper.factory.MessageFactory;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MessageControllerTest {
    @LocalServerPort
    private int port;  // Injected test server port

    @Autowired
    private TestRestTemplate restTemplate;

    private static Message message;
    private String baseUrl;

    @BeforeAll
    static void setup() {
        // Create test Client and ServiceProvider
        Client client = new Client.Builder()
                .setUserId("CL001")
                .setName("Test Client")
                .build();

        ServiceProvider provider = new ServiceProvider.Builder()
                .setUserId("SP001")
                .setName("Test Provider")
                .build();

        // Create test message
        message = MessageFactory.createMessage(
                "MSG001",
                LocalDateTime.now(),
                "Test message content",
                client,
                provider
        );
    }

    @BeforeEach
    void init() {
        baseUrl = "http://localhost:" + port + "/api/messages";  // Dynamic URL
    }

    @Test
    @Order(1)
    void a_create() {
        ResponseEntity<Message> response = restTemplate.postForEntity(
                baseUrl,
                message,
                Message.class
        );

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(message.getMessageId(), response.getBody().getMessageId());
    }

    @Test
    @Order(2)
    void b_read() {
        ResponseEntity<Message> response = restTemplate.getForEntity(
                baseUrl + "/" + message.getMessageId(),
                Message.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    // Additional test methods (update, delete, getAll)...
}