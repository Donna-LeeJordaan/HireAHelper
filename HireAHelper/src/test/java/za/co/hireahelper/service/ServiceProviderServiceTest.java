/* ServiceProvider.java

   Author: MT Osman (230599125)

   Date: 11 July 2025 */

package za.co.hireahelper.service;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.co.hireahelper.domain.*;
import za.co.hireahelper.factory.ServiceProviderFactory;
import za.co.hireahelper.factory.ServiceTypeFactory;
import za.co.hireahelper.repository.AreaRepository;
import za.co.hireahelper.repository.ServiceTypeRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
public class ServiceProviderServiceTest {

    @Autowired
    private ServiceProviderService service;

    @Autowired
    private AreaRepository areaRepository;

    ServiceType serviceType = new ServiceType.Builder()
            .setTypeId("S1")
            .setTypeName("Gardener")
            .build();
    Area area = new Area.Builder()
            .setAreaId("A1")
            .setName("Cape Town")
            .build();

    List<Booking> bookings = new ArrayList<>();
    List<Message> messages = new ArrayList<>();

    private ServiceProvider serviceProvider = ServiceProviderFactory.createServiceProvider(
            "sp1","Saliegh Haroun","saliegh@gmail.com","SalieghH1234","0665485568",
            area,"saliegh.jpeg", "Experienced gardener with 15 years experience",668.0,serviceType ,bookings,
            messages);

    @Test
    void a_create() {
        areaRepository.save(serviceProvider.getArea());

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
        assertEquals("Moegamat Saliegh Haroun", updatedServiceProvider.getName());
        System.out.println("Updated:" + updated);
    }

    @Test
    void e_getAll() {
        List<ServiceProvider> serviceProviders= service.getAll();
        assertNotNull(serviceProviders);
        System.out.println("All Service Providers:" + serviceProviders);
    }

    @Test
    void f_delete() {
        boolean deleted = service.delete(serviceProvider.getUserId());
        assertTrue(deleted);
        System.out.println("Deleted Service Provider with userId:" + serviceProvider.getUserId());
    }
}

