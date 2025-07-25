// Ameeruddin Arai 230190839

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
import za.co.hireahelper.domain.Admin;
import za.co.hireahelper.factory.AdminFactory;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.MethodName.class)
class AdminControllerTest {

    private static Admin admin;

    @Autowired
    private TestRestTemplate restTemplate;

    private final String BASE_URL = "/HireAHelper/admin";

    @BeforeAll
    public static void setUp() {
        System.out.println("Setting up Admin object for test...");
        admin = AdminFactory.createAdmin(
                "admin001",
                "Fatima Patel",
                "fatima.patel@example.com",
                "securePass123",
                "0712345678"
        );
        assertNotNull(admin);
        System.out.println("Setup complete: " + admin);
    }

    @Test
    void a_create() {
        System.out.println("Starting CREATE test...");
        String url = BASE_URL + "/create";
        ResponseEntity<Admin> postResponse = restTemplate.postForEntity(url, admin, Admin.class);

        System.out.println("POST Response: " + postResponse);
        assertNotNull(postResponse);
        assertEquals(HttpStatus.OK, postResponse.getStatusCode());

        Admin saved = postResponse.getBody();
        assertNotNull(saved);
        assertEquals(admin.getAdminId(), saved.getAdminId());

        admin = saved; // update reference with saved data from DB
        System.out.println("CREATE successful: " + saved);
    }

    @Test
    void b_read() {
        System.out.println("Starting READ test...");
        String url = BASE_URL + "/read/" + admin.getAdminId();
        ResponseEntity<Admin> response = restTemplate.getForEntity(url, Admin.class);

        System.out.println("GET Response: " + response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        Admin read = response.getBody();
        assertNotNull(read);
        assertEquals(admin.getAdminId(), read.getAdminId());

        System.out.println("READ successful: " + read);
    }

    @Test
    void c_update() {
        System.out.println("Starting UPDATE test...");
        Admin updated = new Admin.Builder()
                .copy(admin)
                .setName("Fatima P. Updated")
                .build();

        String url = BASE_URL + "/update";
        restTemplate.put(url, updated);
        System.out.println("PUT request sent for update.");

        ResponseEntity<Admin> response = restTemplate.getForEntity(BASE_URL + "/read/" + updated.getAdminId(), Admin.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        Admin updatedResponse = response.getBody();
        assertNotNull(updatedResponse);
        assertEquals("Fatima P. Updated", updatedResponse.getName());

        admin = updatedResponse; // update reference
        System.out.println("UPDATE successful: " + updatedResponse);
    }

    @Test
    void d_getAll() {
        System.out.println("Starting GET ALL test...");
        String url = BASE_URL + "/all";
        ResponseEntity<Admin[]> response = restTemplate.getForEntity(url, Admin[].class);

        System.out.println("GET ALL Response Status: " + response.getStatusCode());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        Admin[] admins = response.getBody();
        assertNotNull(admins);
        System.out.println("Total Admins Retrieved: " + admins.length);
        for (Admin a : admins) {
            System.out.println(a);
        }
    }

    @Test
    void e_delete() {
        System.out.println("Starting DELETE test...");
        String url = BASE_URL + "/delete/" + admin.getAdminId();
        restTemplate.delete(url);
        System.out.println("DELETE request sent for ID: " + admin.getAdminId());

        ResponseEntity<Admin> response = restTemplate.getForEntity(BASE_URL + "/read/" + admin.getAdminId(), Admin.class);
        System.out.println("READ after DELETE Response: " + response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNull(response.getBody()); // Ensure null after deletion

        System.out.println("DELETE successful for Admin ID: " + admin.getAdminId());
    }
}
