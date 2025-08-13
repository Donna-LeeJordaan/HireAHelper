// Gabriel Kiewietz
// 230990703
// 18 May 2024

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
    static void setup(@Autowired ServiceTypeRepository serviceTypeRepository,
                      @Autowired TestRestTemplate restTemplate) {

        gardener = new ServiceType.Builder()
                .setTypeId("type02")
                .setTypeName("Gardener")
                .build();
        serviceTypeRepository.save(gardener);

        genericArea = new Area.Builder()
                .setAreaId("area001")
                .setName("Athlone")
                .build();

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
        restTemplate.postForEntity(
                "http://localhost:8080/HireAHelper/client/create",
                client,
                Client.class
        );

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

        restTemplate.postForEntity(
                "http://localhost:8080/HireAHelper/serviceProvider/create",
                provider,
                ServiceProvider.class
        );

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
        ResponseEntity<Message> response = restTemplate.postForEntity(BASE_URL + "/create", message, Message.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(message.getMessageId(), response.getBody().getMessageId());

        message = response.getBody();
        System.out.println("Created: " + message);
    }

    @Test
    @Order(2)
    void testRead() {
        ResponseEntity<Message> response = restTemplate.getForEntity(
                BASE_URL + "/read/" + message.getMessageId(),
                Message.class
        );

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

        restTemplate.put(BASE_URL + "/update", updatedMessage);

        Message fetched = restTemplate.getForObject(BASE_URL + "/read/" + message.getMessageId(), Message.class);
        assertNotNull(fetched);
        assertEquals("Updated test message", fetched.getContent());

        message = fetched;
        System.out.println("Updated: " + message);
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

        Message deleted = restTemplate.getForObject(BASE_URL + "/read/" + message.getMessageId(), Message.class);
        assertNull(deleted); // simpler: accept null response
        System.out.println("Deleted message with ID: " + message.getMessageId());
    }
}
