// Gabriel Kiewietz
// 230990703
// 18 May 2024

package za.co.hireahelper.factory;

import org.junit.jupiter.api.Test;
import za.co.hireahelper.domain.ServiceType;
import static org.junit.jupiter.api.Assertions.*;

public class ServiceTypeTest {

    @Test
    void testCreateValidServiceType() {
        ServiceType serviceType = ServiceTypeFactory.createServiceType("type01", "Plumbing");
        System.out.println("Created valid ServiceType: " + serviceType); // for Sanity
        assertNotNull(serviceType);
        assertEquals("type01", serviceType.getTypeId());
        assertEquals("Plumbing", serviceType.getTypeName());
    }

    @Test
    void testCreateServiceTypeWithNullId() {
        ServiceType serviceType = ServiceTypeFactory.createServiceType(null, "Electrician");
        System.out.println("ServiceType with null ID: " + serviceType); // for Sanity
        assertNull(serviceType);
    }

    @Test
    void testCreateServiceTypeWithEmptyTypeName() {
        ServiceType serviceType = ServiceTypeFactory.createServiceType("type02", "");
        System.out.println("ServiceType with empty type name: " + serviceType); // for Sanity
        assertNull(serviceType);
    }
}
