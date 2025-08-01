/* ServiceTypeControllerTest.java
    Gabriel Kiewietz
    31 July 2025
*/

package za.co.hireahelper.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import za.co.hireahelper.domain.ServiceType;
import za.co.hireahelper.factory.ServiceTypeFactory;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.MethodName.class)
class ServiceTypeControllerTest {

    private static ServiceType serviceType;

    @Autowired
    private TestRestTemplate restTemplate;

    private static final String BASE_URL = "http://localhost:8080/HireAHelper/serviceType";

    @BeforeAll
    public static void setUp() {
        serviceType = ServiceTypeFactory.createServiceType("ST01", "Electrician");
    }

    @Test
    void a_create() {
        String url = BASE_URL + "/create";
        ResponseEntity<ServiceType> postResponse = this.restTemplate.postForEntity(url, serviceType, ServiceType.class);
        assertNotNull(postResponse);
        ServiceType saved = postResponse.getBody();
        assertEquals(serviceType.getTypeId(), saved.getTypeId());
        System.out.println("Created: " + saved);
    }

    @Test
    void b_read() {
        String url = BASE_URL + "/read/" + serviceType.getTypeId();
        ResponseEntity<ServiceType> response = this.restTemplate.getForEntity(url, ServiceType.class);
        assertEquals(serviceType.getTypeId(), response.getBody().getTypeId());
        System.out.println("Read: " + response.getBody());
    }

    @Test
    void c_update() {
        ServiceType updated = new ServiceType.Builder()
                .copy(serviceType)
                .setTypeName("Updated Electrician")
                .build();

        String url = BASE_URL + "/update";
        this.restTemplate.put(url, updated);

        ResponseEntity<ServiceType> response = this.restTemplate.getForEntity(BASE_URL + "/read/" + updated.getTypeId(), ServiceType.class);
        assertNotNull(response.getBody());
        System.out.println("Updated: " + response.getBody());
    }

    @Test
    void d_getAll() {
        String url = BASE_URL + "/all";
        ResponseEntity<ServiceType[]> response = this.restTemplate.getForEntity(url, ServiceType[].class);
        assertNotNull(response.getBody());
        System.out.println("All ServiceTypes:");
        for (ServiceType s : response.getBody()) {
            System.out.println(s);
        }
    }

    @Test
    void e_delete() {
        String url = BASE_URL + "/delete/" + serviceType.getTypeId();
        this.restTemplate.delete(url);

        ResponseEntity<ServiceType> response = this.restTemplate.getForEntity(BASE_URL + "/read/" + serviceType.getTypeId(), ServiceType.class);
        assertNull(response.getBody());
        System.out.println("Deleted: true");
    }
}
