/*
 * ServiceTypeControllerTest.java
 * Author: Gabriel Kiewietz (230990703)
 * Date: 07 August 2025
 */

package za.co.hireahelper.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import za.co.hireahelper.domain.ServiceType;
import za.co.hireahelper.factory.ServiceTypeFactory;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.MethodName.class)
class ServiceTypeControllerTest {

    private static ServiceType serviceType;

    @Autowired
    private TestRestTemplate restTemplate;

    private final String BASE_URL = "/serviceType";

    @BeforeAll
    static void setUp() {
        serviceType = ServiceTypeFactory.createServiceType("type01", "Plumbing");
    }

    @Test
    void a_create() {
        String url = BASE_URL + "/create";
        ResponseEntity<ServiceType> response = restTemplate.postForEntity(url, serviceType, ServiceType.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        System.out.println("Created: " + response.getBody());
    }

    @Test
    void b_read() {
        String url = BASE_URL + "/read/" + serviceType.getTypeId();
        ResponseEntity<ServiceType> response = restTemplate.getForEntity(url, ServiceType.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        System.out.println("Read: " + response.getBody());
    }

    @Test
    void c_update() {
        String url = BASE_URL + "/update";
        ServiceType updated = new ServiceType.Builder()
                .copy(serviceType)
                .setTypeName("Electrical")
                .build();

        HttpEntity<ServiceType> requestEntity = new HttpEntity<>(updated);
        ResponseEntity<ServiceType> response = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, ServiceType.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        System.out.println("Updated: " + response.getBody());
    }

    @Test
    void d_getAll() {
        String url = BASE_URL + "/all";
        ResponseEntity<ServiceType[]> response = restTemplate.getForEntity(url, ServiceType[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        System.out.println("All: ");
        for (ServiceType st : response.getBody()) {
            System.out.println(st);
        }
    }

    @Test
    void e_delete() {
        String url = BASE_URL + "/delete/" + serviceType.getTypeId();
        restTemplate.delete(url);
        System.out.println("Deleted ServiceType with ID: " + serviceType.getTypeId());
    }
}
