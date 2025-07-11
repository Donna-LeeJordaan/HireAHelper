//Gabriel Kiewietz
// 11 July 2025
//230990703

package za.co.hireahelper.service;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
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

    private static final ServiceType serviceType = ServiceTypeFactory.createServiceType(
            "stype001",
            "Plumbing"
    );

    @Test
    void a_create() {
        ServiceType created = service.create(serviceType);
        assertNotNull(created);
        System.out.println("Created: " + created);
    }

    @Test
    void b_read() {
        ServiceType read = service.read(serviceType.getServiceTypeId());
        assertNotNull(read);
        System.out.println("Read: " + read);
    }

    @Test
    void c_update() {
        ServiceType updatedType = new ServiceType.Builder()
                .copy(serviceType)
                .setDescription()
                .build();

        ServiceType updated = service.update(updatedType);
        assertNotNull(updated);
        System.out.println("Updated: " + updated);
    }

    @Test
    void d_getAll() {
        var allTypes = service.getAll();
        assertNotNull(allTypes);
        System.out.println("All service types: " + allTypes);
    }

    @Test
    void e_delete() {
        boolean deleted = service.delete(serviceType.getServiceTypeId());
        assertTrue(deleted);
        System.out.println("Deleted: " + true);
    }
}
