//gabriel kiewietz
//230990703
package za.co.hireahelper.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import za.co.hireahelper.domain.*;
import za.co.hireahelper.factory.ClientFactory;
import za.co.hireahelper.factory.MessageFactory;
import za.co.hireahelper.factory.ServiceProviderFactory;
import za.co.hireahelper.repository.ServiceTypeRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MessageControllerTest {

    private static Message message;
    private static Client client;
    private static ServiceProvider provider;
    private static Area genericArea;
    private static ServiceType gardener;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ServiceTypeRepository serviceTypeRepository;

    private final String BASE_URL = "http://localhost:8080/HireAHelper/message";

    @BeforeAll
    static void setup(
            @Autowired ServiceTypeRepository serviceTypeRepository,
            @Autowired TestRestTemplate restTemplate) {

        // Create and save ServiceType
        gardener = new ServiceType.Builder()
                .setTypeId("type02")
                .setTypeName("Gardener")
                .build();
        serviceTypeRepository.save(gardener);

        // Create generic area
        genericArea = new Area.Builder()
                .setAreaId("area001")
                .setName("Athlone")
                .build();

        // Create client with empty lists to avoid null issues
        client = ClientFactory.createClient(
                "user001",
                "Amina",
                "amina@example.com",
                "password123",
                "0823456789",
                genericArea,
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>()
        );

        // Create service provider similarly
        provider = ServiceProviderFactory.createServiceProvider(
                "user007",
                "Tauriq Osman",
                "tauriqosman@gmail.com",
                "Tauriq04",
                "0611234567",
                genericArea,
                "tauriq.jpeg",
                "Skilled Gardener with 15 years experience",
                350.0,
                gardener,
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>()
        );

        // Create the message object (not yet saved in DB)
        message = MessageFactory.createMessage(
                "MSG001",
                LocalDateTime.now(),
                "Test message",
                client,
                provider
        );
    }

    @Test
    @Order(1)
    void testCreate() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Message> request = new HttpEntity<>(message, headers);

        ResponseEntity<Message> response = restTemplate.postForEntity(BASE_URL + "/create", request, Message.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(message.getMessageId(), response.getBody().getMessageId());
        System.out.println("Created: " + response.getBody());
    }

    @Test
    @Order(2)
    void testRead() {
        ResponseEntity<Message> response = restTemplate.getForEntity(BASE_URL + "/read/" + message.getMessageId(), Message.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(message.getMessageId(), response.getBody().getMessageId());
        System.out.println("Read: " + response.getBody());
    }

    @Test
    @Order(3)
    void testUpdate() {
        Message updatedMessage = new Message.Builder()
                .copy(message)
                .setContent("Updated test message")
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Message> request = new HttpEntity<>(updatedMessage, headers);

        ResponseEntity<Message> response = restTemplate.exchange(BASE_URL + "/update", HttpMethod.PUT, request, Message.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Updated test message", response.getBody().getContent());
        System.out.println("Updated: " + response.getBody());
    }

    @Test
    @Order(4)
    void testGetAll() {
        ResponseEntity<Message[]> response = restTemplate.getForEntity(BASE_URL + "/all", Message[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().length > 0);
        System.out.println("All messages:");
        for (Message msg : response.getBody()) {
            System.out.println(msg);
        }
    }

    @Test
    @Order(5)
    void testDelete() {
        restTemplate.delete(BASE_URL + "/delete/" + message.getMessageId());

        ResponseEntity<Message> response = restTemplate.getForEntity(BASE_URL + "/read/" + message.getMessageId(), Message.class);
        // After deletion, your service might return null or 404, depending on implementation.
        // So you can check either:
        assertTrue(response.getStatusCode() == HttpStatus.NOT_FOUND || response.getBody() == null);
        System.out.println("Deleted message with ID: " + message.getMessageId());
    }
}