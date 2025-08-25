/*
 * BookingServiceTest.java
 * Author: D Jordaan (230613152)
 * Date: 25 August 2025
 */

package za.co.hireahelper.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import za.co.hireahelper.domain.*;
import za.co.hireahelper.factory.BookingFactory;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Transactional
class BookingServiceTest {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private ServiceProviderService providerService;

    @Autowired
    private AreaService areaService;

    @Autowired
    private ServiceTypeService serviceTypeService;

    private Booking booking;
    private Client client;
    private ServiceProvider provider;
    private Area area;
    private ServiceType serviceType;

    @BeforeEach
    void setUp() {
        // Create and persist Area
        area = new Area.Builder()
                .setAreaId("area001")
                .setName("Athlone")
                .build();
        areaService.create(area);

        // Create and persist ServiceType
        serviceType = new ServiceType.Builder()
                .setTypeId("type02")
                .setTypeName("Gardener")
                .build();
        serviceTypeService.create(serviceType);

        // Create and persist Client
        client = new Client.Builder()
                .setUserId("user001")
                .setName("Amina")
                .setEmail("amina@example.com")
                .setArea(area)
                .build();
        clientService.create(client);

        // Create and persist ServiceProvider
        provider = new ServiceProvider.Builder()
                .setUserId("user007")
                .setName("Tauriq")
                .setEmail("tauriq@gmail.com")
                .setArea(area)
                .setServiceType(serviceType)
                .build();
        providerService.create(provider);

        // Create Booking using Factory
        booking = BookingFactory.createBooking(
                "booking001",
                LocalDate.of(2025, 4, 12),
                "Scheduled",
                "Customer requested morning service",
                client,
                provider
        );

        assertNotNull(booking, "BookingFactory returned null — check input entities");
    }

    @Test
    @Order(1)
    void a_create() {
        Booking savedBooking = bookingService.create(booking);
        assertNotNull(savedBooking);
        assertEquals("booking001", savedBooking.getBookingId());
        System.out.println("Created Booking: " + savedBooking);
    }

    @Test
    @Order(2)
    void b_read() {
        bookingService.create(booking); // ensure booking exists
        Booking readBooking = bookingService.read("booking001");
        assertNotNull(readBooking);
        assertEquals("booking001", readBooking.getBookingId());
        System.out.println("Read Booking: " + readBooking);
    }

    @Test
    @Order(3)
    void c_update() {
        bookingService.create(booking); // ensure booking exists
        Booking updatedBooking = new Booking.Builder()
                .copy(booking)
                .setStatus("Completed")
                .build();
        Booking result = bookingService.update(updatedBooking);
        assertNotNull(result);
        assertEquals("Completed", result.getStatus());
        System.out.println("Updated Booking: " + result);
    }

    @Test
    @Order(4)
    void d_getAll() {
        bookingService.create(booking); // ensure booking exists
        List<Booking> allBookings = bookingService.getAll();
        assertNotNull(allBookings);
        assertFalse(allBookings.isEmpty());
        System.out.println("All Bookings: " + allBookings.size());
    }

    @Test
    @Order(5)
    void e_delete() {
        bookingService.create(booking); // ensure booking exists
        boolean deleted = bookingService.delete("booking001");
        assertTrue(deleted);
        Booking deletedBooking = bookingService.read("booking001");
        assertNull(deletedBooking);
        System.out.println("Deleted Booking ID: booking001");
    }
}
