/* BookingServiceTest.java
   Author: Donna-Lee Jordaan (230613152)
   Date: 25 July 2025 / modified 11 August 2025
*/

package za.co.hireahelper.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.co.hireahelper.domain.Booking;
import za.co.hireahelper.domain.Client;
import za.co.hireahelper.domain.ServiceProvider;
import za.co.hireahelper.factory.BookingFactory;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BookingServiceTest {

    @Autowired
    private BookingService service;

    private static Booking booking;
    private static Client client;
    private static ServiceProvider serviceProvider;

    @BeforeAll
    static void setup() {
        // Create minimal Client and ServiceProvider for booking
        client = new Client.Builder()
                .setUserId("client001")
                .build();

        serviceProvider = new ServiceProvider.Builder()
                .setUserId("sp001")
                .build();

        booking = BookingFactory.createBooking(
                "BK001",
                new Date(),
                "Scheduled",
                "Initial booking notes",
                client,
                serviceProvider,
                new ArrayList<>()
        );
        assertNotNull(booking);
    }

    @Test
    @Order(1)
    void a_create() {
        Booking created = service.create(booking);
        assertNotNull(created);
        assertEquals("BK001", created.getBookingId());
        System.out.println("Created: " + created);
    }

    @Test
    @Order(2)
    void b_read() {
        Booking found = service.read(booking.getBookingId());
        assertNotNull(found);
        System.out.println("Read: " + found);
    }

    @Test
    @Order(3)
    void c_update() {
        Booking updated = new Booking.Builder()
                .copy(booking)
                .setStatus("Completed")
                .setNotes("Booking completed successfully")
                .build();

        Booking result = service.update(updated);
        assertNotNull(result);
        assertEquals("Completed", result.getStatus());
        System.out.println("Updated: " + result);
    }

    @Test
    @Order(4)
    void d_getAll() {
        assertNotNull(service.getAll());
        assertTrue(service.getAll().size() > 0);
        System.out.println("All Bookings: " + service.getAll());
    }

    @Test
    @Order(5)
    void e_delete() {
        boolean deleted = service.delete(booking.getBookingId());
        assertTrue(deleted);
        System.out.println("Deleted: " + booking.getBookingId());
    }
}

