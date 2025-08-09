// Ameeruddin Arai 230190839

package za.co.hireahelper.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import za.co.hireahelper.domain.Admin;
import za.co.hireahelper.factory.AdminFactory;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS) // Allows @BeforeAll to be non-static
public class AdminControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private Admin admin;

    private String getBaseUrl() {
        return "http://localhost:" + port + "/HireAHelper/admin";
    }

    @BeforeAll
    void setUp() {
        admin = AdminFactory.createAdmin(
                "admin001",
                "Fatima Patel",
                "fatima.patel@example.com",
                "securePass123",
                "0712345678"
        );
        assertNotNull(admin, "Admin should be created by the factory");
    }

    @Test
    @Order(1)
    void testCreate() {
        String url = getBaseUrl() + "/create";
        ResponseEntity<Admin> response = restTemplate.postForEntity(url, admin, Admin.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(admin.getUserId(), response.getBody().getUserId());

        admin = response.getBody(); // Save for later tests
        System.out.println("Created Admin: " + admin);
    }

    @Test
    @Order(2)
    void testRead() {
        String url = getBaseUrl() + "/read/" + admin.getUserId();
        ResponseEntity<Admin> response = restTemplate.getForEntity(url, Admin.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(admin.getUserId(), response.getBody().getUserId());

        System.out.println("Read Admin: " + response.getBody());
    }

    @Test
    @Order(3)
    void testUpdate() {
        Admin updatedAdmin = new Admin.Builder()
                .copy(admin)
                .setName("Fatima P. Updated")
                .build();

        String url = getBaseUrl() + "/update";
        restTemplate.put(url, updatedAdmin);

        ResponseEntity<Admin> response = restTemplate.getForEntity(
                getBaseUrl() + "/read/" + updatedAdmin.getUserId(),
                Admin.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Fatima P. Updated", response.getBody().getName());

        admin = response.getBody(); // Keep updated reference
        System.out.println("Updated Admin: " + admin);
    }

    @Test
    @Order(4)
    void testGetAll() {
        String url = getBaseUrl() + "/all";
        ResponseEntity<Admin[]> response = restTemplate.getForEntity(url, Admin[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().length > 0, "There should be at least one admin");

        System.out.println("All Admins:");
        for (Admin a : response.getBody()) {
            System.out.println(a);
        }
    }

    @Test
    @Order(5)
    void testDelete() {
        String url = getBaseUrl() + "/delete/" + admin.getUserId();
        restTemplate.delete(url);

        ResponseEntity<String> response = restTemplate.getForEntity(
                getBaseUrl() + "/read/" + admin.getUserId(),
                String.class
        );

        assertTrue(
                response.getStatusCode() == HttpStatus.NOT_FOUND ||
                        (response.getBody() != null && response.getBody().isEmpty()),
                "Admin should not exist after deletion"
        );

        System.out.println("Admin successfully deleted.");
    }
}
