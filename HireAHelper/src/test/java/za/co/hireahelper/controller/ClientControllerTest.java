/* ClientController.java

   Author: S Hendricks (221095136)

   Date: 09 July 2025 Updated 24 July 2025
 */

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
import za.co.hireahelper.domain.Area;
import za.co.hireahelper.domain.Booking;
import za.co.hireahelper.domain.Client;
import za.co.hireahelper.domain.Message;
import za.co.hireahelper.factory.ClientFactory;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.MethodName.class)
class ClientControllerTest {

    private static Client client;

    @Autowired
    private TestRestTemplate restTemplate;

    private static final String BASE_URL = "http://localhost:8080/HireAHelper/client";

    @BeforeAll
    public static void setUp() {
        List<Booking> bookings = new ArrayList<>();
        List<Message> messages = new ArrayList<>();

        Area area = new Area.Builder()
                .setAreaId("area001")
                .setName("Cape Town Central")
                .build();

        client = ClientFactory.createClient(
                "user001",
                "Amina",
                "amina@example.com",
                "password123",
                "0823456789",
                area,
                bookings,
                messages
        );

        assertNotNull(client, "Client should be created by the factory");
    }

    @Test
    void a_create() {
        String url = BASE_URL + "/create";
        ResponseEntity<Client> postResponse = restTemplate.postForEntity(url, client, Client.class);
        assertNotNull(postResponse);
        assertEquals(HttpStatus.OK, postResponse.getStatusCode());

        Client savedClient = postResponse.getBody();
        assertNotNull(savedClient);
        assertEquals(client.getUserId(), savedClient.getUserId());
        System.out.println("Created Client: " + savedClient);
    }

    @Test
    void b_read() {
        String url = BASE_URL + "/read/" + client.getUserId();
        ResponseEntity<Client> response = restTemplate.getForEntity(url, Client.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Client readClient = response.getBody();
        assertNotNull(readClient);
        assertEquals(client.getUserId(), readClient.getUserId());
        System.out.println("Read Client: " + readClient);
    }

    @Test
    void c_update() {
        Client updatedClient = new Client.Builder()
                .copy(client)
                .setEmail("amina.updated@example.com")
                .build();

        String url = BASE_URL + "/update";
        restTemplate.put(url, updatedClient);

        ResponseEntity<Client> response = restTemplate.getForEntity(BASE_URL + "/read/" + updatedClient.getUserId(), Client.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Client clientAfterUpdate = response.getBody();
        assertNotNull(clientAfterUpdate);
        assertEquals("amina.updated@example.com", clientAfterUpdate.getEmail());
        System.out.println("Updated Client: " + clientAfterUpdate);
    }

    @Test
    void d_getAll() {
        String url = BASE_URL + "/all";
        ResponseEntity<Client[]> response = restTemplate.getForEntity(url, Client[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        Client[] clients = response.getBody();
        assertNotNull(clients);
        assertTrue(clients.length > 0);

        System.out.println("All Clients:");
        for (Client c : clients) {
            System.out.println(c);
        }
    }

    @Test
    void e_delete() {
        String url = BASE_URL + "/delete/" + client.getUserId();
        restTemplate.delete(url);

        ResponseEntity<Client> response = restTemplate.getForEntity(BASE_URL + "/read/" + client.getUserId(), Client.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNull(response.getBody());
        System.out.println("Deleted Client with userId: " + client.getUserId());
    }
}
