// Gabriel Kiewietz
// 11 July 2025
// 230990703

package za.co.hireahelper.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.co.hireahelper.domain.ServiceType;
import za.co.hireahelper.factory.ServiceTypeFactory;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ServiceTypeServiceTest {

    @Autowired
    private ServiceTypeService service;

    private static ServiceType serviceType;

    @BeforeAll
    static void setup() {
        serviceType = ServiceTypeFactory.createServiceType(
                "type01",
                "Plumbing"
        );
    }

    @Test
    @Order(1)
    void a_create() {
        ServiceType created = service.create(serviceType);
        assertNotNull(created);
        assertEquals("type01", created.getTypeId());
        System.out.println("Created: " + created);
    }

    @Test
    @Order(2)
    void b_read() {
        ServiceType found = service.read(serviceType.getTypeId());
        assertNotNull(found);
        System.out.println("Read: " + found);
    }

    @Test
    @Order(3)
    void c_update() {
        ServiceType updated = new ServiceType.Builder()
                .copy(serviceType)
                .setTypeName("Electrical")
                .build();
        ServiceType result = service.update(updated);
        assertNotNull(result);
        assertEquals("Electrical", result.getTypeName());
        System.out.println("Updated: " + result);
    }

    @Test
    @Order(4)
    void d_getAll() {
        assertNotNull(service.getAll());
        assertTrue(service.getAll().size() > 0);
        System.out.println("All ServiceTypes: " + service.getAll());
    }

    @Test
    @Order(5)
    void e_delete() {
        boolean deleted = service.delete(serviceType.getTypeId());
        assertTrue(deleted);
        System.out.println("Deleted: " + serviceType.getTypeId());
    }
}
