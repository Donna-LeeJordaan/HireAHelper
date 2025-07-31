// Ameeruddin Arai 230190839

// Ameeruddin Arai 230190839

package za.co.hireahelper.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import za.co.hireahelper.domain.Area;
import za.co.hireahelper.factory.AreaFactory;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.MethodName.class)
class AreaControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private static Area area;

    private String getBaseUrl() {
        return "http://localhost:" + port + "/HireAHelper/area";
    }

    @BeforeAll
    static void setUp() {
        area = AreaFactory.createArea("area100", "Cape Town North");
        assertNotNull(area, "Area should be created by the factory");
    }

    @Test
    void a_create() {
        String url = getBaseUrl() + "/create";
        ResponseEntity<Area> response = restTemplate.postForEntity(url, area, Area.class);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        Area created = response.getBody();
        assertNotNull(created);
        assertEquals(area.getAreaId(), created.getAreaId());

        area = created; // Update reference
        System.out.println("Created Area: " + created);
    }

    @Test
    void b_read() {
        String url = getBaseUrl() + "/read/" + area.getAreaId();
        ResponseEntity<Area> response = restTemplate.getForEntity(url, Area.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        Area read = response.getBody();
        assertNotNull(read);
        assertEquals(area.getAreaId(), read.getAreaId());

        System.out.println("Read Area: " + read);
    }

    @Test
    void c_update() {
        Area updated = new Area.Builder()
                .copy(area)
                .setName("Cape Town Central")
                .build();

        String url = getBaseUrl() + "/update";
        restTemplate.put(url, updated);

        ResponseEntity<Area> response = restTemplate.getForEntity(getBaseUrl() + "/read/" + updated.getAreaId(), Area.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Area afterUpdate = response.getBody();
        assertNotNull(afterUpdate);
        assertEquals("Cape Town Central", afterUpdate.getName());

        area = afterUpdate; // Update reference
        System.out.println("Updated Area: " + afterUpdate);
    }

    @Test
    void d_getAll() {
        String url = getBaseUrl() + "/all";
        ResponseEntity<Area[]> response = restTemplate.getForEntity(url, Area[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Area[] areas = response.getBody();
        assertNotNull(areas);
        assertTrue(areas.length > 0);

        System.out.println("All Areas:");
        for (Area a : areas) {
            System.out.println(a);
        }
    }

    @Test
    void e_delete() {
        String url = getBaseUrl() + "/delete/" + area.getAreaId();
        restTemplate.delete(url);

        ResponseEntity<Area> response = restTemplate.getForEntity(getBaseUrl() + "/read/" + area.getAreaId(), Area.class);

        if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
            System.out.println("Area successfully deleted. Not found afterward.");
        } else {
            assertNull(response.getBody(), "Area body should be null after deletion");
        }
    }
}
