//Gabriel Kiewietz
// 11 July 2025
//230990703

package za.co.hireahelper.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.co.hireahelper.domain.ServiceType;
import za.co.hireahelper.factory.ServiceTypeFactory;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
class ServiceTypeServiceTest {
    @Autowired
    private ServiceTypeService service;

    private static ServiceType serviceType;

    @BeforeAll
    static void setup() {
        serviceType = ServiceTypeFactory.createServiceType(
                "ST001",
                "Plumbing"
        );
    }

    @Test
    @Order(1)
    void a_create() {
        ServiceType created = service.create(serviceType);
        assertNotNull(created);
        assertEquals("ST001", created.getTypeId());
    }

    @Test
    @Order(2)
    void b_read() {
        ServiceType found = service.read(serviceType.getTypeId());
        assertNotNull(found);
    }
}