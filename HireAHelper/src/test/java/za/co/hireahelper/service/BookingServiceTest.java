/* BookingFactory.java
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
@TestMethodOrder(MethodOrderer.MethodName.class)
public class BookingServiceTest {

    @Autowired
    private BookingService bookingService;

    private static Booking booking;

    @BeforeAll
    public static void setUp() {
        Area genericArea = new Area.Builder()
                .setAreaId("area001")
                .setName("Athlone")
                .build();

        Client client = new Client.Builder()
                .setUserId("user001")
                .setName("Amina")
                .setEmail("amina@example.com")
                .setArea(genericArea)
                .build();

        ServiceProvider provider = new ServiceProvider.Builder()
                .setUserId("user007")
                .setName("Tauriq")
                .setEmail("tauriq@gmail.com")
                .setArea(genericArea)
                .build();

        booking = BookingFactory.createBooking(
                "booking001",
                LocalDate.of(2025, 4, 12),
                "Scheduled",
                "Customer requested morning service",
                client,
                provider,
                new ArrayList<>()
        );

        assertNotNull(booking, "Booking creation failed in setup");
    }

    @Test
    public void a_create() {
        Booking created = bookingService.create(booking);
        assertNotNull(created);
        System.out.println("Created Booking: " + created);
    }

    @Test
    public void b_read() {
        Booking read = bookingService.read(booking.getBookingId());
        assertNotNull(read);
        assertEquals("booking001", read.getBookingId());
        System.out.println("Read Booking: " + read);
    }

    @Test
    public void c_update() {
        Booking updatedBooking = new Booking.Builder()
                .copy(booking)
                .setStatus("Confirmed")
                .build();

        Booking updated = bookingService.update(updatedBooking);
        assertNotNull(updated);
        assertEquals("Confirmed", updated.getStatus());
        System.out.println("Updated Booking: " + updated);
    }

    @Test
    public void d_getAll() {
        List<Booking> allBookings = bookingService.getAll();
        assertNotNull(allBookings);
        assertFalse(allBookings.isEmpty());
        System.out.println("All Bookings: " + allBookings.size());
    }

    @Test
    public void e_delete() {
        boolean deleted = bookingService.delete(booking.getBookingId());
        assertTrue(deleted);
        System.out.println("Deleted Booking ID: " + booking.getBookingId());
    }
}



