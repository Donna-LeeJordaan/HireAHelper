
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

    private static final String BASE_URL = "http://localhost:8080/HireAHelper/admin";

    @BeforeAll
    public static void setUp() {
        admin = AdminFactory.createAdmin(
                "admin001",
                "Fatima Patel",
                "fatima.patel@example.com"
        );
        assertNotNull(admin);
    }

    @Test
    void a_create() {
        String url = BASE_URL + "/create";
        ResponseEntity<Admin> postResponse = restTemplate.postForEntity(url, admin, Admin.class);
        assertNotNull(postResponse);
        assertEquals(HttpStatus.OK, postResponse.getStatusCode());

        Admin adminSaved = postResponse.getBody();
        assertNotNull(adminSaved);
        assertEquals(admin.getAdminId(), adminSaved.getAdminId());

        System.out.println("Created: " + adminSaved);
    }

    @Test
    void b_read() {
        String url = BASE_URL + "/read/" + admin.getAdminId();
        ResponseEntity<Admin> response = restTemplate.getForEntity(url, Admin.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        Admin adminRead = response.getBody();
        assertNotNull(adminRead);
        assertEquals(admin.getAdminId(), adminRead.getAdminId());

        System.out.println("Read: " + adminRead);
    }

    @Test
    void c_update() {
        Admin updatedAdmin = new Admin.Builder()
                .copy(admin)
                .setName("Fatima P. Updated")
                .build();

        String url = BASE_URL + "/update";
        restTemplate.put(url, updatedAdmin);

        ResponseEntity<Admin> response = restTemplate.getForEntity(BASE_URL + "/read/" + updatedAdmin.getAdminId(), Admin.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        Admin adminUpdated = response.getBody();
        assertNotNull(adminUpdated);
        assertEquals("Fatima P. Updated", adminUpdated.getName());

        System.out.println("Updated: " + adminUpdated);
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

        assertNull(response.getBody());

        System.out.println("Deleted: true");
    }
}
