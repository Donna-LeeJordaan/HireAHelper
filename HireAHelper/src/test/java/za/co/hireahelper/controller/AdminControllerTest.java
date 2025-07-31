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
@TestMethodOrder(MethodOrderer.MethodName.class)
class AdminControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private static Admin admin;

    private String getBaseUrl() {
        return "http://localhost:" + port + "/HireAHelper/admin";
    }

    @BeforeAll
    static void setUp() {
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
    void a_create() {
        String url = getBaseUrl() + "/create";
        ResponseEntity<Admin> postResponse = restTemplate.postForEntity(url, admin, Admin.class);
        assertNotNull(postResponse);
        assertEquals(HttpStatus.OK, postResponse.getStatusCode());

        Admin savedAdmin = postResponse.getBody();
        assertNotNull(savedAdmin);
        assertEquals(admin.getUserId(), savedAdmin.getUserId());

        admin = savedAdmin;
        System.out.println("Created Admin: " + savedAdmin);
    }

    @Test
    void b_read() {
        String url = getBaseUrl() + "/read/" + admin.getUserId();
        ResponseEntity<Admin> response = restTemplate.getForEntity(url, Admin.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Admin readAdmin = response.getBody();
        assertNotNull(readAdmin);
        assertEquals(admin.getUserId(), readAdmin.getUserId());

        System.out.println("Read Admin: " + readAdmin);
    }

    @Test
    void c_update() {
        Admin updatedAdmin = new Admin.Builder()
                .copy(admin)
                .setName("Fatima P. Updated")
                .build();

        String url = getBaseUrl() + "/update";
        restTemplate.put(url, updatedAdmin);

        ResponseEntity<Admin> response = restTemplate.getForEntity(getBaseUrl() + "/read/" + updatedAdmin.getUserId(), Admin.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Admin adminAfterUpdate = response.getBody();
        assertNotNull(adminAfterUpdate);
        assertEquals("Fatima P. Updated", adminAfterUpdate.getName());

        admin = adminAfterUpdate;
        System.out.println("Updated Admin: " + adminAfterUpdate);
    }

    @Test
    void d_getAll() {
        String url = getBaseUrl() + "/all";
        ResponseEntity<Admin[]> response = restTemplate.getForEntity(url, Admin[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        Admin[] admins = response.getBody();
        assertNotNull(admins);
        assertTrue(admins.length > 0);

        System.out.println("All Admins:");
        for (Admin a : admins) {
            System.out.println(a);
        }
    }

    @Test
    void e_delete() {
        String url = getBaseUrl() + "/delete/" + admin.getUserId();
        restTemplate.delete(url);

        ResponseEntity<Admin> response = restTemplate.getForEntity(getBaseUrl() + "/read/" + admin.getUserId(), Admin.class);

        if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
            System.out.println("Admin successfully deleted. Not found afterward.");
        } else {
            assertNull(response.getBody(), "Admin body should be null after deletion");
        }
    }
}
