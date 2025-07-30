/* ServiceProvider.java

   Author: MT Osman (230599125)

   Date: 11 July 2025 */

package za.co.hireahelper.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import za.co.hireahelper.domain.*;
import za.co.hireahelper.factory.*;
import za.co.hireahelper.repository.AreaRepository;
import za.co.hireahelper.repository.ServiceTypeRepository;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.MethodName.class)
public class ServiceProviderControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private static ServiceProvider serviceProvider;

    @Autowired
    private AreaRepository areaRepository;

    @Autowired
    private ServiceTypeRepository serviceTypeRepository;

    private String getBaseUrl() {
        return "http://localhost:" + port + "/serviceProvider";
    }

    @BeforeEach
    public void setUp(){
        ServiceType serviceType = new ServiceType.Builder()
                .setTypeId("S1")
                .setTypeName("Gardener")
                .build();
        serviceTypeRepository.save(serviceType);

        Area area = new Area.Builder()
                .setAreaId("A1")
                .setName("Cape Town")
                .build();
        assertNotNull(area, "Area creation failed");
        areaRepository.save(area);

        List<Booking> bookings = new ArrayList<>();
        List<Message> messages = new ArrayList<>();
        List<Review> reviews = new ArrayList<>();

        serviceProvider = ServiceProviderFactory.createServiceProvider(
                "sp1","Tauriq", "tauriq@gmail.com",
                "tauriq01", "0677754479", area,
                "tauriq.jpeg","Gardener with 15 years experience",
                600.0, serviceType, bookings, messages, reviews);
        assertNotNull(serviceProvider, "Service Provider creation failed");
    }

    @Test
    void a_create() {
        String url = getBaseUrl() + "/create";
        ResponseEntity <ServiceProvider> postResponse = restTemplate.postForEntity(url, serviceProvider, ServiceProvider.class);
        assertNotNull(postResponse);
        assertEquals(HttpStatus.OK, postResponse.getStatusCode());

        ServiceProvider serviceProviderSaved = postResponse.getBody();
        assertNotNull(serviceProviderSaved);
        assertEquals(serviceProvider.getUserId(), serviceProviderSaved.getUserId());
        System.out.println("Created: " + serviceProviderSaved);
    }

    @Test
    void b_read() {
        String url = getBaseUrl() + "/read/" + serviceProvider.getUserId();
        ResponseEntity<ServiceProvider> response = restTemplate.getForEntity(url, ServiceProvider.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        ServiceProvider serviceProviderRead = response.getBody();
        assertNotNull(serviceProviderRead);
        assertEquals(serviceProvider.getUserId(), serviceProviderRead.getUserId());
        System.out.println("Read: " + serviceProviderRead);
    }

    @Test
    void c_update() {
        ServiceProvider updatedServiceProvider = new ServiceProvider.Builder()
                .copy(serviceProvider)
                .setName("Moegamat Saliegh Haroun")
                .build();

        String url = getBaseUrl() + "/update";
        restTemplate.put(url, updatedServiceProvider);

        ResponseEntity<ServiceProvider> response = restTemplate.getForEntity(getBaseUrl() + "/read/" + updatedServiceProvider.getUserId(), ServiceProvider.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        ServiceProvider serviceProviderUpdated = response.getBody();
        assertNotNull(serviceProviderUpdated);
        assertEquals("Moegamat Saliegh Haroun", serviceProviderUpdated.getName());
        System.out.println("Updated:" + serviceProviderUpdated);
    }

    @Disabled
    @Test
    void e_delete() {
        String url = getBaseUrl() + "/delete/" + serviceProvider.getUserId();
        restTemplate.delete(url);

        ResponseEntity<ServiceProvider> response = restTemplate.getForEntity(getBaseUrl() + "/read/" + serviceProvider.getUserId(), ServiceProvider.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertNull(response.getBody());
        System.out.println("Deleted");
    }

    @Test
    void d_getAll() {
        String url = getBaseUrl() + "/all";
        ResponseEntity<ServiceProvider[]> response = restTemplate.getForEntity(url, ServiceProvider[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        ServiceProvider[] serviceProviders = response.getBody();
        assertNotNull(serviceProviders);
        assertTrue(serviceProviders.length > 0);

        System.out.println("All ServiceProviders:");
        for (ServiceProvider s : serviceProviders) {
            System.out.println(s);
        }
    }
}