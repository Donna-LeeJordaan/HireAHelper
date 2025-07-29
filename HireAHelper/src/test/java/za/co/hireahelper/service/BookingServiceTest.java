/* BookingServiceTest.java
   Author: D.Jordaan (230613152)
   Date: 25 July 2025
*/

package za.co.hireahelper.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.co.hireahelper.domain.Booking;
import za.co.hireahelper.domain.Client;
import za.co.hireahelper.domain.ServiceProvider;
import za.co.hireahelper.factory.BookingFactory;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
class BookingServiceTest {

    @Autowired
    private BookingService service;

    private static final Client client = new Client.Builder()
            .setUserId("client123")
            .setName("Test Client")
            .build();

    private static final ServiceProvider serviceProvider = new ServiceProvider.Builder()
            .setUserId("sp123")
            .setName("Test Provider")
            .build();

    private static Booking booking;

    @BeforeAll
    static void setUp() {
        booking = BookingFactory.createBooking(
                "booking123",
                new Date(System.currentTimeMillis() + 86400000), // Tomorrow
                "Pending",
                "Test notes",
                client,
                serviceProvider
        );
        assertNotNull(booking);
    }

    @Test
    @Order(1)
    void a_create() {
        Booking created = service.create(booking);
        assertNotNull(created);
        assertEquals(booking.getBookingId(), created.getBookingId());
        assertEquals(booking.getStatus(), created.getStatus());
        assertEquals(booking.getNotes(), created.getNotes());
        assertEquals(booking.getClient(), created.getClient());
        assertEquals(booking.getServiceProvider(), created.getServiceProvider());
        System.out.println("Created booking: " + created);
    }

    @Test
    @Order(2)
    void b_read() {
        Booking found = service.read(booking.getBookingId());
        assertNotNull(found);
        assertEquals(booking.getBookingId(), found.getBookingId());
        assertEquals(booking.getServiceDate(), found.getServiceDate());
        assertEquals(booking.getStatus(), found.getStatus());
        System.out.println("Read booking: " + found);
    }

    @Test
    @Order(3)
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
        System.out.println("Updated booking: " + result);
    }

    @Test
    @Order(4)
    void d_getAll() {
        var allBookings = service.getAll();
        assertNotNull(allBookings);
        assertFalse(allBookings.isEmpty());
        System.out.println("All bookings count: " + allBookings.size());
    }

    @Test
    @Order(5)
    void e_delete() {
        boolean deleted = service.delete(booking.getBookingId());
        assertTrue(deleted);

        Booking shouldBeNull = service.read(booking.getBookingId());
        assertNull(shouldBeNull);
        System.out.println("Deleted booking with ID: " + booking.getBookingId());
    }

    @Test
    @Order(6)
    void f_invalidBookingTests() {
        // Test past date
        Booking pastDateBooking = new Booking.Builder()
                .copy(booking)
                .setBookingId("booking-invalid-1")
                .setServiceDate(new Date(System.currentTimeMillis() - 86400000)) // Yesterday
                .build();

        assertThrows(IllegalArgumentException.class, () -> service.create(pastDateBooking));

        // Test invalid status
        Booking invalidStatusBooking = new Booking.Builder()
                .copy(booking)
                .setBookingId("booking-invalid-2")
                .setStatus("InvalidStatus")
                .build();

        assertThrows(IllegalArgumentException.class, () -> service.create(invalidStatusBooking));

        System.out.println("Invalid booking tests passed");
    }
}