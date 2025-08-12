/* BookingServiceTest.java
   Author: Donna-Lee Jordaan (230613152)
   Date: 25 July 2025 / modified 11 August 2025
*/
package za.co.hireahelper.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.co.hireahelper.domain.*;
import za.co.hireahelper.factory.BookingFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BookingServiceTest {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private ServiceProviderService serviceProviderService;

    private static Booking booking;
    private static Client client;
    private static ServiceProvider provider;
    private static LocalDate serviceDate; // Changed from Date to LocalDate

    @BeforeAll
    static void setUp() {
        // Use LocalDate
        serviceDate = LocalDate.of(2025, 4, 12); // Year, Month, Day

        // Create test client and provider
        client = new Client.Builder()
                .setUserId("area001")
                .setName("Amina")
                .setEmail("amina@example.com")
                .build();

        provider = new ServiceProvider.Builder()
                .setUserId("user007")
                .setName("Tauriq")
                .setEmail("tauriq@gmail.com")
                .build();

        // Create booking using the factory (updated to LocalDate)
        booking = BookingFactory.createBooking(
                "B001",
                serviceDate, // Now LocalDate
                "Pending",
                "Customer requested morning service",
                client,
                provider,
                new ArrayList<>()
        );
    }

    @Test
    @Order(1)
    void a_create() {
        clientService.create(client);
        serviceProviderService.create(provider);

        Booking created = bookingService.create(booking);
        assertNotNull(created);
        assertEquals("B001", created.getBookingId());
        System.out.println("Created: " + created);
    }

    @Test
    @Order(2)
    void b_read() {
        Booking read = bookingService.read(booking.getBookingId());
        assertNotNull(read);
        assertEquals("B001", read.getBookingId());
        System.out.println("Read: " + read);
    }

    @Test
    @Order(3)
    void c_update() {
        Booking updatedBooking = new Booking.Builder()
                .copy(booking)
                .setStatus("Confirmed")
                .build();

        Booking updated = bookingService.update(updatedBooking);
        assertNotNull(updated);
        assertEquals("Confirmed", updated.getStatus());
        System.out.println("Updated: " + updated);
    }

    @Test
    @Order(4)
    void d_delete() {
        boolean deleted = bookingService.delete(booking.getBookingId());
        assertTrue(deleted);
        System.out.println("Deleted: " + booking.getBookingId());
    }

    @Test
    @Order(5)
    void e_getAll() {
        List<Booking> allBookings = bookingService.getAll();
        assertNotNull(allBookings);
        System.out.println("All bookings: " + allBookings.size());
    }
}