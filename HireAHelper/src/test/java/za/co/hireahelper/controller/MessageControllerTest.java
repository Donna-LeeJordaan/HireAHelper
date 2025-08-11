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

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Client> clientRequest = new HttpEntity<>(client, headers);
        restTemplate.postForEntity(
                "http://localhost:8080/HireAHelper/client/create",
                clientRequest,
                Client.class
        );

        // Save service provider via REST
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

        HttpEntity<ServiceProvider> providerRequest = new HttpEntity<>(provider, headers);
        restTemplate.postForEntity(
                "http://localhost:8080/HireAHelper/serviceProvider/create",
                providerRequest,
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
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Message> request = new HttpEntity<>(message, headers);

        ResponseEntity<Message> response = restTemplate.postForEntity(BASE_URL + "/create", request, Message.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(message.getMessageId(), response.getBody().getMessageId());

        // Update the message reference with persisted version
        message = response.getBody();
        System.out.println("Created: " + response.getBody());
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

        Message current = restTemplate.getForObject(
                BASE_URL + "/read/" + message.getMessageId(),
                Message.class
        );

        Message updatedMessage = new Message.Builder()
                .copy(current)
                .setContent("Updated test message")
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Message> request = new HttpEntity<>(updatedMessage, headers);

        ResponseEntity<Message> response = restTemplate.exchange(
                BASE_URL + "/update",
                HttpMethod.PUT,
                request,
                Message.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Updated test message", response.getBody().getContent());


        message = response.getBody();
        System.out.println("Updated: " + response.getBody());
    }

    @Test
    @Order(4)
    void testGetAll() {
        ResponseEntity<Message[]> response = restTemplate.getForEntity(
                BASE_URL + "/all",
                Message[].class
        );

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

        ResponseEntity<Message> preDeleteResponse = restTemplate.getForEntity(
                BASE_URL + "/read/" + message.getMessageId(),
                Message.class
        );
        assertEquals(HttpStatus.OK, preDeleteResponse.getStatusCode());


        restTemplate.delete(BASE_URL + "/delete/" + message.getMessageId());


        ResponseEntity<Message> response = restTemplate.getForEntity(
                BASE_URL + "/read/" + message.getMessageId(),
                Message.class
        );
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        System.out.println("Deleted message with ID: " + message.getMessageId());
    }
}