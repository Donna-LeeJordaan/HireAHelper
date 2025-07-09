/* ClientControllerTest.java

   Author: S Hendricks (221095136)

   Date: 09 July 2025 */

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
import za.co.hireahelper.domain.Client;
import za.co.hireahelper.factory.ClientFactory;
import java.util.Collections;
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
        client = ClientFactory.createClient(
                "client123",
                "Aalia Moosa",
                "aalia.moosa@example.com",
                "securePass123",
                "0712345678",
                Collections.emptyList(),
                Collections.emptyList()
        );
        assertNotNull(client);
    }

    @Test
    void a_create() {
        String url = BASE_URL + "/create";
        ResponseEntity<Client> postResponse = restTemplate.postForEntity(url, client, Client.class);
        assertNotNull(postResponse);
        assertEquals(HttpStatus.OK, postResponse.getStatusCode());

        Client clientSaved = postResponse.getBody();
        assertNotNull(clientSaved);
        assertEquals(client.getUserId(), clientSaved.getUserId());

        System.out.println("Created: " + clientSaved);
    }

    @Test
    void b_read() {
        String url = BASE_URL + "/read/" + client.getUserId();
        ResponseEntity<Client> response = restTemplate.getForEntity(url, Client.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        Client clientRead = response.getBody();
        assertNotNull(clientRead);
        assertEquals(client.getUserId(), clientRead.getUserId());

        System.out.println("Read: " + clientRead);
    }

    @Test
    void c_update() {
        Client updatedClient = new Client.Builder()
                .copy(client)
                .setName("Aalia M. Updated")
                .build();

        String url = BASE_URL + "/update";
        restTemplate.put(url, updatedClient);

        ResponseEntity<Client> response = restTemplate.getForEntity(BASE_URL + "/read/" + updatedClient.getUserId(), Client.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        Client clientUpdated = response.getBody();
        assertNotNull(clientUpdated);
        assertEquals("Aalia M. Updated", clientUpdated.getName());

        System.out.println("Updated: " + clientUpdated);
    }

    @Test
    void e_delete() {
        String url = BASE_URL + "/delete/" + client.getUserId();
        restTemplate.delete(url);

        ResponseEntity<Client> response = restTemplate.getForEntity(BASE_URL + "/read/" + client.getUserId(), Client.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertNull(response.getBody());

        System.out.println("Deleted: true");
    }

    @Test
    void d_getAll() {
        String url = BASE_URL + "/all";
        ResponseEntity<Client[]> response = restTemplate.getForEntity(url, Client[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        Client[] clients = response.getBody();
        assertNotNull(clients);

        System.out.println("All Clients:");
        for (Client c : clients) {
            System.out.println(c);
        }
    }
}


