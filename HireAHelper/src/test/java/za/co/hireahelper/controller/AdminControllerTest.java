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
        admin = AdminFactory.createAdmin(
                "admin001",
                "Fatima Patel",
                "fatima.patel@example.com",
                "securePass123",
                "0712345678"
        );
        assertNotNull(admin);
    }

    @Test
    void a_create() {
        String url = BASE_URL + "/create";
        ResponseEntity<Admin> postResponse = restTemplate.postForEntity(url, admin, Admin.class);
        assertNotNull(postResponse);
        assertEquals(HttpStatus.OK, postResponse.getStatusCode());

        Admin saved = postResponse.getBody();
        assertNotNull(saved);
        assertEquals(admin.getAdminId(), saved.getAdminId());

        admin = saved; // update reference with saved data from DB
        System.out.println("Created: " + saved);
    }

    @Test
    void b_read() {
        String url = BASE_URL + "/read/" + admin.getAdminId();
        ResponseEntity<Admin> response = restTemplate.getForEntity(url, Admin.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        Admin read = response.getBody();
        assertNotNull(read);
        assertEquals(admin.getAdminId(), read.getAdminId());

        System.out.println("Read: " + read);
    }

    @Test
    void c_update() {
        Admin updated = new Admin.Builder()
                .copy(admin)
                .setName("Fatima P. Updated")
                .build();

        String url = BASE_URL + "/update";
        restTemplate.put(url, updated);

        ResponseEntity<Admin> response = restTemplate.getForEntity(BASE_URL + "/read/" + updated.getAdminId(), Admin.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        Admin updatedResponse = response.getBody();
        assertNotNull(updatedResponse);
        assertEquals("Fatima P. Updated", updatedResponse.getName());

        admin = updatedResponse; // update reference
        System.out.println("Updated: " + updatedResponse);
    }

    @Test
    void d_getAll() {
        String url = BASE_URL + "/all";
        ResponseEntity<Admin[]> response = restTemplate.getForEntity(url, Admin[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        Admin[] admins = response.getBody();
        assertNotNull(admins);
        System.out.println("All Admins:");
        for (Admin a : admins) {
            System.out.println(a);
        }
    }

    @Test
    void e_delete() {
        String url = BASE_URL + "/delete/" + admin.getAdminId();
        restTemplate.delete(url);

        ResponseEntity<Admin> response = restTemplate.getForEntity(BASE_URL + "/read/" + admin.getAdminId(), Admin.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNull(response.getBody()); // Ensure null after deletion

        System.out.println("Deleted Admin ID: " + admin.getAdminId());
    }
}
