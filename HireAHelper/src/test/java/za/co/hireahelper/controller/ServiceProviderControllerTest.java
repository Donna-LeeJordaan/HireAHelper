/* ServiceProvider.java

   Author: MT Osman (230599125)

   Date: 11 July 2025 */

package za.co.hireahelper.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import za.co.hireahelper.domain.*;
import za.co.hireahelper.factory.AreaFactory;
import za.co.hireahelper.factory.ServiceProviderFactory;
import za.co.hireahelper.factory.ServiceTypeFactory;
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

    private String getBaseUrl() {
        return "http://localhost:" + port + "/serviceProvider";
    }

    @BeforeAll
    public static void setUp(){

        ServiceType gardener = ServiceTypeFactory.createServiceType("S1", "Gardener");
        assertNotNull(gardener, "ServiceType null");

        Area A1 = AreaFactory.createArea("A1","Cape Town");

        List<Booking> bookings = new ArrayList<>();
        List<Message> messages = new ArrayList<>();

        serviceProvider = ServiceProviderFactory.createServiceProvider("sp1","Saliegh Haroun", "saliegh@gmail.com", "salieghH1234",
                "0665485568",A1, "saliegh.jpeg", "Experienced gardener with 15 years experience.", 668.0,
                gardener, bookings,messages);
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