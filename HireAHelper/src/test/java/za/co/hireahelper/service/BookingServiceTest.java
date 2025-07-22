/* BookingServicTest.java

   Author: D.Jordaan (230613152)

   Date: 13 July 2025 */

package za.co.hireahelper.service;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
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
            .setUserId("client001")
            .setName("Test Client")
            .build();

    private static final ServiceProvider provider = new ServiceProvider.Builder()
            .setUserId("provider001")
            .setName("Test Provider")
            .build();

    private static final Booking booking = BookingFactory.Createbooking(
            "book001",
            new Date(System.currentTimeMillis() + 86400000), // Tomorrow
            "Pending",
            "Test notes",
            client,
            provider
    );

    @Test
    void a_create() {
        Booking created = service.create(booking);
        assertNotNull(created);
        assertEquals("Pending", created.getStatus());
        System.out.println("Created booking: " + created);
    }

    @Test
    void b_read() {
        Booking read = service.read(booking.getBookingId());
        assertNotNull(read);
        assertEquals(booking.getBookingId(), read.getBookingId());
        System.out.println("Read booking: " + read);
    }

    @Test
    void c_update() {
        Booking updatedBooking = new Booking.Builder()
                .copy(booking)
                .setStatus("Confirmed")
                .setNotes("Updated notes")
                .build();

        Booking updated = service.update(updatedBooking);
        assertNotNull(updated);
        assertEquals("Confirmed", updated.getStatus());
        System.out.println("Updated booking: " + updated);
    }

    @Test
    void d_getAll() {
        var allBookings = service.getAll();
        assertNotNull(allBookings);
        assertFalse(allBookings.isEmpty());
        System.out.println("All bookings count: " + allBookings.size());
    }

    @Test
    void e_delete() {
        boolean deleted = service.delete(booking.getBookingId());
        assertTrue(deleted);
        System.out.println("Deleted booking ID " + booking.getBookingId() + ": " + deleted);
    }
}