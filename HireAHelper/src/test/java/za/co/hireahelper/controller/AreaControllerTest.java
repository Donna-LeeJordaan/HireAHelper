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
        area = AreaFactory.createArea("area100", "Cape Town North");
        assertNotNull(area);
    }

    @Test
    void a_create() {
        String url = BASE_URL + "/create";
        ResponseEntity<Area> postResponse = restTemplate.postForEntity(url, area, Area.class);
        assertEquals(HttpStatus.OK, postResponse.getStatusCode());

        Area saved = postResponse.getBody();
        assertNotNull(saved);
        assertEquals(area.getAreaId(), saved.getAreaId());

        System.out.println("Created: " + saved);
    }

    @Test
    void b_read() {
        String url = BASE_URL + "/read/" + area.getAreaId();
        ResponseEntity<Area> response = restTemplate.getForEntity(url, Area.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        Area read = response.getBody();
        assertNotNull(read);
        assertEquals(area.getAreaId(), read.getAreaId());

        System.out.println("Read: " + read);
    }

    @Test
    void c_update() {
        Area updated = new Area.Builder()
                .copy(area)
                .setName("Cape Town Central")
                .build();

        restTemplate.put(BASE_URL + "/update", updated);
        ResponseEntity<Area> response = restTemplate.getForEntity(BASE_URL + "/read/" + updated.getAreaId(), Area.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Cape Town Central", Objects.requireNonNull(response.getBody()).getName());

        System.out.println("Updated: " + response.getBody());
    }

    @Test
    void d_getAll() {
        ResponseEntity<Area[]> response = restTemplate.getForEntity(BASE_URL + "/all", Area[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        Area[] areas = response.getBody();
        assertNotNull(areas);

        System.out.println("All Areas:");
        for (Area a : areas) {
            System.out.println(a);
        }
    }

    @Test
    void e_delete() {
        restTemplate.delete(BASE_URL + "/delete/" + area.getAreaId());
        ResponseEntity<Area> response = restTemplate.getForEntity(BASE_URL + "/read/" + area.getAreaId(), Area.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNull(response.getBody());

        System.out.println("Deleted: true");
    }
}
