/* BookingServiceTest.java
   Author: Donna-Lee Jordaan (230613152)
   Date: 25 July 2025 / modified 11 August 2025
*/

package za.co.hireahelper.service;


import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.co.hireahelper.domain.Booking;
import za.co.hireahelper.domain.Client;
import za.co.hireahelper.domain.Review;
import za.co.hireahelper.domain.ServiceProvider;
import za.co.hireahelper.factory.BookingFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
public class BookingServiceTest {
    @Autowired
    private BookingService service;

    @Autowired
    private static ClientService clientService;

    @Autowired
    private static ServiceProviderService serviceProviderService;

    private static Booking booking;

    private static final Client client = new Client.Builder()
            .setUserId("area001")
            .setName("Amina")
            .setEmail("amina@example.com")
            .build();

    private static final ServiceProvider provider = new ServiceProvider.Builder()
            .setUserId("user007")
            .setName("Tauriq")
            .setEmail("tauriq@gmail.com")
            .build();

    private static final List<Review> reviews = new ArrayList<>();

    @BeforeAll
    static void setUp() {
        // Ensure dependencies exist in DB
        if (clientService.read(client.getUserId()) == null) {
            clientService.create(client);
        }
        if (serviceProviderService.read(provider.getUserId()) == null) {
            serviceProviderService.create(provider);
        }

        booking = BookingFactory.createBooking(
                "B001",
                new Date(),
                "Confirmed",
                "Initial booking for service",
                client,
                provider,
                reviews
        );

        assertNotNull(booking, "Booking creation failed");
    }

    @Test
    @Order(1)
    void a_create() {
        Booking created = service.create(booking);
        assertNotNull(created);
        assertEquals(booking.getBookingId(), created.getBookingId());
        System.out.println("Created: " + created);
    }

    @Test
    @Order(2)
    @Transactional
    void b_read() {
        Booking read = service.read(booking.getBookingId());
        assertNotNull(read);
        assertEquals(booking.getBookingId(), read.getBookingId());
        System.out.println("Read: " + read);
    }

    @Test
    @Order(3)
    void c_update() {
        Booking updated = new Booking.Builder()
                .copy(booking)
                .setBookingId(booking.getBookingId())
                .build();

        Booking result = service.update(updated);
        assertNotNull(result);
        assertEquals(booking.getBookingId(), result.getBookingId());
        System.out.println("Updated: " + result);

    }

    @Test
    @Order(4)
    void d_delete() {
        boolean deleted = service.delete(booking.getBookingId());
        assertTrue(deleted);
        System.out.println("Deleted booking with ID: " + booking.getBookingId());
    }
    @Test
    @Order(5)
    @Transactional
    void e_getAll() {
        List<Booking> allBookings = service.getAll();
        assertNotNull(allBookings);
        assertFalse(allBookings.isEmpty());
        System.out.println("All bookings: " + allBookings);
    }

}
