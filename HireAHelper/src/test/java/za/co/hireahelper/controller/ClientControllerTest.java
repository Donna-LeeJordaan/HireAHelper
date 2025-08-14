/* ClientControllerTest.java
   Author: S Hendricks (221095136)
   Date: 09 July 2025
 */

package za.co.hireahelper.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import za.co.hireahelper.domain.*;
import za.co.hireahelper.factory.ClientFactory;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
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
                .setName("Athlone")
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
    }

    @Test
    void a_create() {
        String url = BASE_URL + "/create";
        ResponseEntity<Client> postResponse = this.restTemplate.postForEntity(url, client, Client.class);
        assertNotNull(postResponse);
        Client savedClient = postResponse.getBody();
        assertEquals(client.getUserId(), savedClient.getUserId());
        System.out.println("Created: " + savedClient);
    }

    @Test
    void b_read() {
        String url = BASE_URL + "/read/" + client.getUserId();
        ResponseEntity<Client> response = this.restTemplate.getForEntity(url, Client.class);
        assertEquals(client.getUserId(), response.getBody().getUserId());
        System.out.println("Read: " + response.getBody());
    }

    @Test
    void c_update() {
        Client updatedClient = new Client.Builder()
                .copy(client)
                .setEmail("amina.updated@example.com")
                .build();

        String url = BASE_URL + "/update";
        this.restTemplate.put(url, updatedClient);

        ResponseEntity<Client> response = this.restTemplate.getForEntity(BASE_URL + "/read/" + updatedClient.getUserId(), Client.class);
        assertNotNull(response.getBody());
        System.out.println("Updated: " + response.getBody());
    }

    @Test
    void d_getAll() {
        String url = BASE_URL + "/all";
        ResponseEntity<Client[]> response = this.restTemplate.getForEntity(url, Client[].class);
        assertNotNull(response.getBody());
        System.out.println("All Clients:");
        for (Client c : response.getBody()) {
            System.out.println(c);
        }
    }

    @Test
    void e_delete() {
        String url = BASE_URL + "/delete/" + client.getUserId();
        this.restTemplate.delete(url);

        ResponseEntity<Client> response = this.restTemplate.getForEntity(BASE_URL + "/read/" + client.getUserId(), Client.class);
        assertNull(response.getBody());
        System.out.println("Deleted: true");
    }
}


