/* ServiceProvider.java

   Author: MT Osman (230599125)

   Date: 11 July 2025 */

package za.co.hireahelper.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import za.co.hireahelper.domain.*;
import za.co.hireahelper.factory.ServiceProviderFactory;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import za.co.hireahelper.repository.ServiceTypeRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ServiceProviderControllerTest {

    private ServiceProvider serviceProvider;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ServiceTypeRepository serviceTypeRepository;

    private Area area;
    private ServiceType serviceType;

    private final String BASE_URL = "http://localhost:8080/HireAHelper/serviceProvider";

    @BeforeEach
    void setUp() {

        List<Booking> bookings = new ArrayList<>();
        List<Message> messages = new ArrayList<>();
        List<Review> reviews = new ArrayList<>();

        area = new Area.Builder()
                .setAreaId("area001")
                .setName("Athlone")
                .build();

        serviceType = new ServiceType.Builder()
                .setTypeId("type01")
                .setTypeName("Plumber")
                .build();
        serviceTypeRepository.save(serviceType);

        serviceProvider = ServiceProviderFactory.createServiceProvider(
                "user007",
                "Tauriq",
                "tauriq@gmail.com",
                "tauriq01",
                "0677754479",
                area,
                "tauriq.jpeg",
                "Plumber with 15 years experience",
                600.0,
                serviceType,
                bookings,
                messages,
                reviews
        );
    }

    @Test
    @Order(1)
    void a_create() {
        ResponseEntity<ServiceProvider> response = restTemplate.postForEntity(BASE_URL + "/create", serviceProvider, ServiceProvider.class);
        assertEquals(serviceProvider.getUserId(), response.getBody().getUserId());
        serviceProvider = response.getBody();
        System.out.println("Created: " + serviceProvider);
    }

    @Test
    @Order(2)
    void b_read() {
        ResponseEntity<ServiceProvider> response = restTemplate.getForEntity(BASE_URL + "/read/" + serviceProvider.getUserId(), ServiceProvider.class);
        assertEquals(serviceProvider.getUserId(), response.getBody().getUserId());
        System.out.println("Read: " + response.getBody());
    }

    @Test
    @Order(3)
    void c_update() {
        ServiceProvider updated = new ServiceProvider.Builder()
                .copy(serviceProvider)
                .setName("Moegamat Saliegh Haroun")
                .build();

        restTemplate.put(BASE_URL + "/update", updated);

        ResponseEntity<ServiceProvider> response = restTemplate.getForEntity(BASE_URL + "/read/" + updated.getUserId(), ServiceProvider.class);
        assertEquals("Moegamat Saliegh Haroun", response.getBody().getName());
        serviceProvider = response.getBody();
        System.out.println("Updated: " + serviceProvider);
    }

    @Test
    @Order(4)
    void d_getAll() {
        ResponseEntity<ServiceProvider[]> response = restTemplate.getForEntity(BASE_URL + "/all", ServiceProvider[].class);
        assertTrue(response.getBody().length > 0);
        System.out.println("All ServiceProviders:");
        for (ServiceProvider s : response.getBody()) {
            System.out.println(s);
        }
    }

    @Test
    @Order(5)
    void e_delete() {
        restTemplate.delete(BASE_URL + "/delete/" + serviceProvider.getUserId());
        ResponseEntity<ServiceProvider> response = restTemplate.getForEntity(BASE_URL + "/read/" + serviceProvider.getUserId(), ServiceProvider.class);
        assertNull(response.getBody());
        System.out.println("Deleted: true");
    }
}
