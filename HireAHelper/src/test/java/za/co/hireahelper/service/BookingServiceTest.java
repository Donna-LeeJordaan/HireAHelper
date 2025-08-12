/* BookingServiceTest.java
   Author: Donna-Lee Jordaan (230613152)
   Date: 25 July 2025 / modified 12 August 2025
*/

//package za.co.hireahelper.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.co.hireahelper.domain.Booking;
import za.co.hireahelper.domain.Client;
import za.co.hireahelper.domain.Review;
import za.co.hireahelper.domain.ServiceProvider;
import za.co.hireahelper.factory.BookingFactory;
import za.co.hireahelper.service.ServiceProviderService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//@TestInstance(TestInstance.Lifecycle.PER_CLASS) // Allows non-static @BeforeAll
public class BookingServiceTest {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private ServiceProviderService serviceProviderService;

    static Booking booking;

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
        // Create the test object using a factory or builder
        booking = BookingFactory.createBooking( "B001", 2025-04-12, "Pending","Customer requested morning service", client , provider , new ArrayList<>()
        );

    }

//    @BeforeEach
//    void ensureDependenciesExist() {
//        // If entity is not in DB, insert it
//        if (service.read(entity.getId()) == null) {
//            service.create(entity);
//        }
//    }

    @Test
    @Order(1)
    void a_create() {
        Booking created = bookingService.create(booking);
        assertNotNull(created);
        assertEquals(booking.getBookingId(), created.getBookingId());
        System.out.println("Created: " + created);
    }

    @Test
    @Order(2)
    void b_read() {
        Booking read = bookingService.read(booking.getBookingId());
        assertNotNull(read);
        assertEquals(booking.getBookingId(), read.getBookingId());
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
        System.out.println("Deleted entity with ID: " + booking.getBookingId());
    }

    @Test
    @Order(5)
    void e_getAll() {
        List<Booking> allBookings = bookingService.getAll();
        assertNotNull(allBookings);
        assertFalse(allBookings.isEmpty());
        System.out.println("All bookings: " + allBookings);
    }
}
