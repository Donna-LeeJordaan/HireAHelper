//Gabriel Kiewietz
// 11 July 2025
//230990703

package za.co.hireahelper.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import za.co.hireahelper.domain.ServiceType;
import za.co.hireahelper.factory.ServiceTypeFactory;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ServiceTypeControllerTest {
    @LocalServerPort private int port;
    @Autowired private TestRestTemplate restTemplate;

    private static ServiceType serviceType;
    private String baseUrl;

    @BeforeAll
    static void setup() {
        serviceType = ServiceTypeFactory.createServiceType("ST001", "Plumbing");
    }

    @BeforeEach
    void init() {
        baseUrl = "http://localhost:" + port + "/api/service-types";
    }

    @Test @Order(1)
    void a_create() {
        ResponseEntity<ServiceType> response = restTemplate.postForEntity(
                baseUrl, serviceType, ServiceType.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    // Additional test methods...
}