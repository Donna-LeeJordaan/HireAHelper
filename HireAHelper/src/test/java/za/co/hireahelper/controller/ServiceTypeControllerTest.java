//Gabriel Kiewietz
// 11 July 2025
//230990703

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
import za.co.hireahelper.domain.ServiceType;
import za.co.hireahelper.factory.ServiceTypeFactory;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.MethodName.class)
class ServiceTypeControllerTest {

    private static ServiceType serviceType;

    @Autowired
    private TestRestTemplate restTemplate;

    private static final String BASE_URL = "http://localhost:8080/HireAHelper/servicetype";

    @BeforeAll
    public static void setUp() {
        serviceType = ServiceTypeFactory.createServiceType("stype001", "Plumbing");
        assertNotNull(serviceType);
    }

    @Test
    void a_create() {
        ResponseEntity<ServiceType> postResponse = restTemplate.postForEntity(BASE_URL + "/create", serviceType, ServiceType.class);
        assertEquals(HttpStatus.OK, postResponse.getStatusCode());

        ServiceType created = postResponse.getBody();
        assertNotNull(created);
        assertEquals(serviceType.getServiceTypeId(), created.getServiceTypeId());

        System.out.println("Created: " + created);
    }

    @Test
    void b_read() {
        ResponseEntity<ServiceType> response = restTemplate.getForEntity(BASE_URL + "/read/" + serviceType.getServiceTypeId(), ServiceType.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        ServiceType read = response.getBody();
        assertNotNull(read);
        assertEquals(serviceType.getServiceTypeId(), read.getServiceTypeId());

        System.out.println("Read: " + read);
    }

    @Test
    void c_update() {
        ServiceType updated = new ServiceType.Builder()
                .copy(serviceType)
                .setDescription()
                .build();

        restTemplate.put(BASE_URL + "/update", updated);
        ResponseEntity<ServiceType> response = restTemplate.getForEntity(BASE_URL + "/read/" + updated.getServiceTypeId(), ServiceType.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Updated plumbing description", Objects.requireNonNull(response.getBody()).getDescription());

        System.out.println("Updated: " + response.getBody());
    }

    @Test
    void d_getAll() {
        ResponseEntity<ServiceType[]> response = restTemplate.getForEntity(BASE_URL + "/all", ServiceType[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        ServiceType[] types = response.getBody();
        assertNotNull(types);

        System.out.println("All Service Types:");
        for (ServiceType s : types) {
            System.out.println(s);
        }
    }

    @Test
    void e_delete() {
        restTemplate.delete(BASE_URL + "/delete/" + serviceType.getServiceTypeId());
        ResponseEntity<ServiceType> response = restTemplate.getForEntity(BASE_URL + "/read/" + serviceType.getServiceTypeId(), ServiceType.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNull(response.getBody());

        System.out.println("Deleted: true");
    }
}
