/* BookingServiceTest.java
   Author: D.Jordaan (230613152)
   Date: 25 July 2025 / modified on 6 August 2025
*/

package za.co.hireahelper.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import za.co.hireahelper.domain.*;
import za.co.hireahelper.factory.BookingFactory;
import java.util.Date;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
public class BookingServiceTest {

    @Autowired
    private BookingService service;

    @Autowired
    private ClientService clientService;

    @Autowired
    private ServiceProviderService serviceProviderService;

    private static Booking booking;

    private static final Client client = new Client.Builder()
            .setUserId("client123")
            .setName("Test Client")
            .build();

    private static final ServiceProvider serviceProvider = new ServiceProvider.Builder()
            .setUserId("sp123")
            .setName("Test Provider")
            .build();

    @BeforeAll
    static void setUp() {
        booking = BookingFactory.createBooking(
                "booking123",
                new Date(System.currentTimeMillis() + 86400000), // Tomorrow
                "Pending",
                "Test notes",
                client,
                serviceProvider,
                null // Reviews can be added later
        );
        assertNotNull(booking, "Booking creation failed");
    }

    @BeforeEach
    public void setupDependencies() {
        if (clientService.read(client.getUserId()) == null) {
            clientService.create(client);
        }
        if (serviceProviderService.read(serviceProvider.getUserId()) == null) {
            serviceProviderService.create(serviceProvider);
        }
    }

    @Test
    @Transactional
    void a_create() {
        Booking created = service.create(booking);
        assertNotNull(created);
        assertEquals(booking.getBookingId(), created.getBookingId());
        System.out.println("Created: " + created);
    }

    @Test
    @Transactional
    void b_read() {
        Booking read = service.read(booking.getBookingId());
        assertNotNull(read);
        assertEquals(booking.getBookingId(), read.getBookingId());
        System.out.println("Read: " + read);
    }

    @Test
    @Transactional
    void c_update() {
        Booking updated = new Booking.Builder()
                .copy(booking)
                .setStatus("Confirmed")
                .setNotes("Updated notes")
                .build();

        Booking result = service.update(updated);
        assertNotNull(result);
        assertEquals("Confirmed", result.getStatus());
        assertEquals("Updated notes", result.getNotes());
        System.out.println("Updated: " + result);
    }

    @Test
    @Transactional
    void d_getAll() {
        List<Booking> allBookings = service.getAll();
        assertNotNull(allBookings);
        assertFalse(allBookings.isEmpty());
        System.out.println("All bookings: " + allBookings);
    }

    @Test
    @Transactional
    void e_delete() {
        boolean deleted = service.delete(booking.getBookingId());
        assertTrue(deleted);
        System.out.println("Deleted: " + deleted);
    }
}