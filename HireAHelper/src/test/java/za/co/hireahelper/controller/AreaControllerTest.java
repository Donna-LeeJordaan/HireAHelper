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
import za.co.hireahelper.domain.Area;
import za.co.hireahelper.factory.AreaFactory;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.MethodName.class)
class AreaControllerTest {

    private static Area area;

    @Autowired
    private TestRestTemplate restTemplate;

    private static final String BASE_URL = "http://localhost:8080/HireAHelper/area";

    @BeforeAll
    public static void setUp() {
        System.out.println("Setting up Area object for tests...");
        area = AreaFactory.createArea("area100", "Cape Town North");
        assertNotNull(area);
        System.out.println("Setup complete: " + area);
    }

    @Test
    void a_create() {
        System.out.println("\n--- Running CREATE Test ---");
        String url = BASE_URL + "/create";
        System.out.println("Sending POST request to: " + url);
        ResponseEntity<Area> postResponse = restTemplate.postForEntity(url, area, Area.class);

        System.out.println("Response received: " + postResponse);
        assertEquals(HttpStatus.OK, postResponse.getStatusCode());

        Area saved = postResponse.getBody();
        assertNotNull(saved);
        assertEquals(area.getAreaId(), saved.getAreaId());

        System.out.println("CREATE successful: " + saved);
    }

    @Test
    void b_read() {
        System.out.println("\n--- Running READ Test ---");
        String url = BASE_URL + "/read/" + area.getAreaId();
        System.out.println("Sending GET request to: " + url);
        ResponseEntity<Area> response = restTemplate.getForEntity(url, Area.class);

        System.out.println("Response received: " + response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        Area read = response.getBody();
        assertNotNull(read);
        assertEquals(area.getAreaId(), read.getAreaId());

        System.out.println("READ successful: " + read);
    }

    @Test
    void c_update() {
        System.out.println("\n--- Running UPDATE Test ---");
        Area updated = new Area.Builder()
                .copy(area)
                .setName("Cape Town Central")
                .build();

        String url = BASE_URL + "/update";
        System.out.println("Sending PUT request to: " + url + " with data: " + updated);
        restTemplate.put(url, updated);

        String readUrl = BASE_URL + "/read/" + updated.getAreaId();
        ResponseEntity<Area> response = restTemplate.getForEntity(readUrl, Area.class);

        System.out.println("Response received after update: " + response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Cape Town Central", Objects.requireNonNull(response.getBody()).getName());

        System.out.println("UPDATE successful: " + response.getBody());
    }

    @Test
    void d_getAll() {
        System.out.println("\n--- Running GET ALL Test ---");
        String url = BASE_URL + "/all";
        System.out.println("Sending GET request to: " + url);
        ResponseEntity<Area[]> response = restTemplate.getForEntity(url, Area[].class);

        System.out.println("Response received: " + response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        Area[] areas = response.getBody();
        assertNotNull(areas);
        System.out.println("GET ALL successful - Total Areas: " + areas.length);

        for (Area a : areas) {
            System.out.println(a);
        }
    }

    @Test
    void e_delete() {
        System.out.println("\n--- Running DELETE Test ---");
        String deleteUrl = BASE_URL + "/delete/" + area.getAreaId();
        System.out.println("Sending DELETE request to: " + deleteUrl);
        restTemplate.delete(deleteUrl);

        String readUrl = BASE_URL + "/read/" + area.getAreaId();
        System.out.println("Checking if area still exists via GET: " + readUrl);
        ResponseEntity<Area> response = restTemplate.getForEntity(readUrl, Area.class);

        System.out.println("Response after delete: " + response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNull(response.getBody());

        System.out.println("DELETE successful: Area with ID " + area.getAreaId() + " no longer exists.");
    }
}

