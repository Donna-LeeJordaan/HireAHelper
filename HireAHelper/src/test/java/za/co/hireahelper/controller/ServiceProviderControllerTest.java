/* ServiceProvider.java

   Author: MT Osman (230599125)

   Date: 11 July 2025 */

package za.co.hireahelper.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import za.co.hireahelper.domain.ServiceProvider;
import za.co.hireahelper.domain.ServiceType;
import za.co.hireahelper.factory.ServiceProviderFactory;
import za.co.hireahelper.factory.ServiceTypeFactory;
import java.util.Collections;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.MethodName.class)
public class ServiceProviderControllerTest {

    private static ServiceProvider serviceProvider;

    @Autowired
    private TestRestTemplate restTemplate;

    private String BASE_URL = "http://localhost:8080/HireAHelper/serviceProvider";

    @BeforeAll
    public static void setUp(){

        ServiceType gardener = ServiceTypeFactory.createServiceType("S1", "Gardener");
        assertNotNull(gardener, "ServiceType null");

        serviceProvider = ServiceProviderFactory.createServiceProvider("sp1","Saliegh Haroun", "saliegh@gmail.com", "salieghH1234",
                "0665485568", "saliegh.jpeg", "Experienced gardener with 15 years experience.", 668,
                gardener, Collections.emptyList(),Collections.emptyList());
    }

    @Test
    void a_create() {
        String url = BASE_URL + "/create";
        ResponseEntity<ServiceProvider> postResponse = restTemplate.postForEntity(url, serviceProvider, ServiceProvider.class);
        assertNotNull(postResponse);
        assertEquals(HttpStatus.OK, postResponse.getStatusCode());

        ServiceProvider serviceProviderSaved = postResponse.getBody();
        assertNotNull(serviceProviderSaved);
        assertEquals(serviceProvider.getUserId(), serviceProviderSaved.getUserId());

        System.out.println("Created: " + serviceProviderSaved);
    }

    @Test
    void b_read() {
        String url = BASE_URL + "/read/" + serviceProvider.getUserId();
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

        String url = BASE_URL + "/update";
        restTemplate.put(url, updatedServiceProvider);

        ResponseEntity<ServiceProvider> response = restTemplate.getForEntity(BASE_URL + "/read/" + updatedServiceProvider.getUserId(), ServiceProvider.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        ServiceProvider serviceProviderUpdated = response.getBody();
        assertNotNull(serviceProviderUpdated);
        assertEquals("Moegamat Saliegh Haroun", serviceProviderUpdated.getName());

        System.out.println("Updated:" + serviceProviderUpdated);
    }

    @Disabled
    @Test
    void e_delete() {
        String url = BASE_URL + "/delete/" + serviceProvider.getUserId();
        restTemplate.delete(url);

        ResponseEntity<ServiceProvider> response = restTemplate.getForEntity(BASE_URL + "/read/" + serviceProvider.getUserId(), ServiceProvider.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertNull(response.getBody());
        System.out.println("Deleted");
    }

    @Test
    void d_getAll() {
        String url = BASE_URL + "/all";
        ResponseEntity<ServiceProvider[]> response = restTemplate.getForEntity(url, ServiceProvider[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        ServiceProvider[] serviceProviders = response.getBody();
        assertNotNull(serviceProviders);

        System.out.println("All ServiceProviders:");
        for (ServiceProvider s : serviceProviders) {
            System.out.println(s);
        }
    }
}