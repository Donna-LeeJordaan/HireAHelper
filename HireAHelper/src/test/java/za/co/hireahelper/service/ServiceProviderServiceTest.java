/* ServiceProvider.java

   Author: MT Osman (230599125)

   Date: 11 July 2025 */

package za.co.hireahelper.service;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.co.hireahelper.domain.ServiceProvider;
import za.co.hireahelper.domain.ServiceType;
import za.co.hireahelper.factory.ServiceProviderFactory;
import za.co.hireahelper.factory.ServiceTypeFactory;
import java.util.Collections;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
public class ServiceProviderServiceTest {

    @Autowired
    private ServiceProviderService service;

    ServiceType gardener = ServiceTypeFactory.createServiceType("S1", "Gardener");

    private ServiceProvider serviceProvider = ServiceProviderFactory.createServiceProvider(
            "sp1","Saliegh Haroun","saliegh@gmail.com","SalieghH1234","0665485568",
            "saliegh.jpeg","Experienced gardener with 15 years experience",668,gardener,Collections.emptyList(),
            Collections.emptyList());

    @Test
    void a_create() {
        ServiceProvider created = service.create(serviceProvider);
        assertNotNull(created);
        System.out.println("Created:" + created);
    }

    @Test
    void b_read() {
        ServiceProvider read = service.read(serviceProvider.getUserId());
        assertNotNull(read);
        System.out.println("Read:" + read);
    }

    @Test
    void d_update() {
        ServiceProvider updatedServiceProvider = new ServiceProvider.Builder()
                .copy(serviceProvider)
                .setName("Moegamat Saliegh Haroun")
                .build();

        ServiceProvider updated = service.update(updatedServiceProvider);
        assertNotNull(updated);
        System.out.println("Updated:" + updated);
    }

    @Test
    void e_getAll() {
        var allServiceProviders= service.getAll();
        assertNotNull(allServiceProviders);
        System.out.println("All Service Providers:" + allServiceProviders);
    }

    @Test
    void f_delete() {
        boolean deleted = service.delete(serviceProvider.getUserId());
        assertTrue(deleted);
        System.out.println("Deleted:" + deleted);
    }
}

