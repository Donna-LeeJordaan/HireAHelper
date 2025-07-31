/* ServiceProvider.java

   Author: MT Osman (230599125)

   Date: 11 July 2025 */

package za.co.hireahelper.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import za.co.hireahelper.domain.*;
import za.co.hireahelper.factory.*;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
public class ServiceProviderServiceTest {

    @Autowired
    private ServiceProviderService service;

    @Autowired
    private AreaService areaService;

    @Autowired
    private ServiceTypeService serviceTypeService;

    private static ServiceProvider serviceProvider;

    private static final Area area = new Area.Builder()
            .setAreaId("area001")
            .setName("Athlone")
            .build();

    private static final ServiceType serviceType = new ServiceType.Builder()
            .setTypeId("S1")
            .setTypeName("Gardener")
            .build();

    private static final List<Booking> bookings = new ArrayList<>();
    private static final List<Message> messages = new ArrayList<>();
    private static final List<Review> reviews = new ArrayList<>();

    @BeforeAll
    public static void setUp() {
        serviceProvider = ServiceProviderFactory.createServiceProvider(
                "sp1", "Tauriq", "tauriq@gmail.com",
                "tauriq01", "0677754479", area,
                "tauriq.jpeg", "Gardener with 15 years experience",
                600.0, serviceType, bookings, messages, reviews);
        assertNotNull(serviceProvider, "Service Provider creation failed");
    }

    @BeforeEach
    public void setupDependencies() {
        if (areaService.read(area.getAreaId()) == null) {
            areaService.create(area);
        }
        if (serviceTypeService.read(serviceType.getTypeId()) == null) {
            serviceTypeService.create(serviceType);
        }
    }

            @Test
            void a_create () {
                ServiceProvider created = service.create(serviceProvider);
                assertNotNull(created);
                assertEquals(serviceProvider.getUserId(), created.getUserId());
                System.out.println("Created:" + created);
            }

            @Test
            @Transactional
            void b_read () {
                ServiceProvider read = service.read(serviceProvider.getUserId());
                assertNotNull(read);
                assertEquals(serviceProvider.getUserId(), read.getUserId());
                System.out.println("Read:" + read);
            }

            @Test
            void c_update () {
                ServiceProvider updated = new ServiceProvider.Builder()
                        .copy(serviceProvider)
                        .setEmail("tauriq01@example.com")
                        .build();

                ServiceProvider updatedProvider = service.update(updated);
                assertNotNull(updatedProvider);
                assertEquals("tauriq01@example.com", updatedProvider.getEmail());
                System.out.println("Updated:" + updated);
            }

            @Test
            @Transactional
            void d_getAll () {
                List<ServiceProvider> all = service.getAll();
                assertNotNull(all);
                assertFalse(all.isEmpty());
                System.out.println("All Service Providers:" + all);
            }

            @Test
            void e_delete () {
                boolean deleted = service.delete(serviceProvider.getUserId());
                assertTrue(deleted);
                System.out.println("Deleted:" + deleted);
            }
        }

