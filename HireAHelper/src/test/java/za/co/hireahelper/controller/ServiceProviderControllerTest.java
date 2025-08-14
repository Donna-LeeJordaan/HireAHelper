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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ServiceProviderControllerTest {

    private static ServiceProvider serviceProvider;


    @Autowired
    private TestRestTemplate restTemplate;


    private static final String BASE_URL = "http://localhost:8080/HireAHelper/serviceProvider";

    @BeforeAll
    public static void setUp(){

        List<Booking> bookings = new ArrayList<>();
        List<Message> messages = new ArrayList<>();

        Area area = new Area.Builder()
                .setAreaId("area001")
                .setName("Athlone")
                .build();

        ServiceType serviceType = new ServiceType.Builder()
                .setTypeId("type01")
                .setTypeName("Plumbing")
                .build();

        serviceProvider = ServiceProviderFactory.createServiceProvider(
                "user007",
                "Tauriq",
                "tauriq@example.com",
                "tauriq01",
                "0677754479",
                area,
                "tauriq.jpeg",
                "Plumber with 15 years experience",
                600.0,
                serviceType,
                bookings,
                messages
        );
    }

    @Test
    @Order(1)
    void a_create() {
        String url = BASE_URL + "/create";
        ResponseEntity <ServiceProvider> postResponse = this.restTemplate.postForEntity(url, serviceProvider, ServiceProvider.class);
        assertNotNull(postResponse);
        ServiceProvider savedServiceProvider = postResponse.getBody();
        assertEquals(serviceProvider.getUserId(), savedServiceProvider.getUserId());
        System.out.println("Created: " + savedServiceProvider);
    }

    @Test
    @Order(2)
    void b_read() {
        String url = BASE_URL + "/read/" + serviceProvider.getUserId();
        ResponseEntity<ServiceProvider> response = this.restTemplate.getForEntity(url, ServiceProvider.class);
        assertEquals(serviceProvider.getUserId(), response.getBody().getUserId());
        System.out.println("Read: " + response.getBody());
    }

    @Test
    @Order(3)
    void c_update() {
        ServiceProvider updatedServiceProvider = new ServiceProvider.Builder()
                .copy(serviceProvider)
                .setName("Moegamat Saliegh Haroun")
                .build();

        String url = BASE_URL + "/update";
        this.restTemplate.put(url, updatedServiceProvider);

        ResponseEntity<ServiceProvider> response = this.restTemplate.getForEntity(BASE_URL + "/read/" + updatedServiceProvider.getUserId(), ServiceProvider.class);
        assertNotNull(response.getBody());
        //assertEquals(updatedServiceProvider.getName(), response.getBody().getName());
        System.out.println("Updated: " + response.getBody());


    }

    @Test
    @Order(4)
    void d_getAll() {
        String url = BASE_URL + "/all";
        ResponseEntity<ServiceProvider[]> response = this.restTemplate.getForEntity(url, ServiceProvider[].class);
        assertNotNull(response.getBody());
        // assertTrue(response.getBody().length > 0);
        System.out.println("All ServiceProviders:");
        for (ServiceProvider s : response.getBody()) {
            System.out.println(s);
        }
    }

    @Test
    @Order(5)
    void e_delete() {
        String url = BASE_URL + "/delete/" + serviceProvider.getUserId();
        this.restTemplate.delete(url);

        ResponseEntity<ServiceProvider> response = this.restTemplate.getForEntity(BASE_URL + "/read/" + serviceProvider.getUserId(), ServiceProvider.class);
        assertNull(response.getBody());
        System.out.println("Deleted: true");
    }

}
