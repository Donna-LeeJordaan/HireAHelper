/* AdminControllerTest.java
   Author: Ameeruddin Arai (230190839)
   Date: 11 August 2025
*/

package za.co.hireahelper.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import za.co.hireahelper.domain.Admin;
import za.co.hireahelper.domain.Area;
import za.co.hireahelper.factory.AdminFactory;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.MethodName.class)
class AdminControllerTest {

    private static Admin admin;

    @Autowired
    private TestRestTemplate restTemplate;

    private static final String BASE_URL = "http://localhost:8080/HireAHelper/admin";

    @BeforeAll
    public static void setUp() {
        Area area = new Area.Builder()
                .setAreaId("area001")
                .setName("Athlone")
                .build();

        admin = AdminFactory.createAdmin(
                "admin001",
                "Santiago Alvarez",
                "s.alvarez@example.com",
                "pass1234",
                "0712345678",
                area
        );
    }

    @Test
    void a_create() {
        String url = BASE_URL + "/create";
        ResponseEntity<Admin> postResponse = this.restTemplate.postForEntity(url, admin, Admin.class);
        assertNotNull(postResponse);
        Admin savedAdmin = postResponse.getBody();
        assertNotNull(savedAdmin);
        assertEquals(admin.getUserId(), savedAdmin.getUserId());
        System.out.println("Created Admin: " + savedAdmin);
    }

    @Test
    void b_read() {
        String url = BASE_URL + "/read/" + admin.getUserId();
        ResponseEntity<Admin> response = this.restTemplate.getForEntity(url, Admin.class);
        assertNotNull(response.getBody());
        assertEquals(admin.getUserId(), response.getBody().getUserId());
        System.out.println("Read Admin: " + response.getBody());
    }

    @Test
    void c_update() {
        Admin updatedAdmin = new Admin.Builder()
                .copy(admin)
                .setName("Santiago A. Updated")
                .build();

        String url = BASE_URL + "/update";
        this.restTemplate.put(url, updatedAdmin);

        ResponseEntity<Admin> response = this.restTemplate.getForEntity(BASE_URL + "/read/" + updatedAdmin.getUserId(), Admin.class);
        assertNotNull(response.getBody());
        assertEquals("Santiago A. Updated", response.getBody().getName());
        System.out.println("Updated Admin: " + response.getBody());
    }

    @Test
    void d_getAll() {
        String url = BASE_URL + "/all";
        ResponseEntity<Admin[]> response = this.restTemplate.getForEntity(url, Admin[].class);
        assertNotNull(response.getBody());
        assertTrue(response.getBody().length > 0);
        System.out.println("All Admins:");
        for (Admin a : response.getBody()) {
            System.out.println(a);
        }
    }

    @Test
    void e_delete() {
        String url = BASE_URL + "/delete/" + admin.getUserId();
        this.restTemplate.delete(url);

        ResponseEntity<Admin> response = this.restTemplate.getForEntity(BASE_URL + "/read/" + admin.getUserId(), Admin.class);
        // After delete, read should return null body
        assertNull(response.getBody());
        System.out.println("Deleted Admin with ID: " + admin.getUserId());
    }
}
