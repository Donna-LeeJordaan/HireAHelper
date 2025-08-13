/*
 * BookingServiceTest.java
 * Author: S Hendricks (221095136)
 * Date: 12 April 2025
 */

package za.co.hireahelper.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
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
    private ServiceTypeService serviceTypeService;  // <-- added

    private Booking booking;
    private Client client;
    private ServiceProvider provider;
    private Area area;

    @BeforeEach
    public void setUp() {
        area = new Area.Builder()
                .setAreaId("area001")
                .setName("Athlone")
                .build();
        areaService.create(area);

        ServiceType gardener = new ServiceType.Builder()
                .setTypeId("type02")
                .setTypeName("Gardener")
                .build();
        serviceTypeService.create(gardener);

        client = new Client.Builder()
                .setUserId("user001")
                .setName("Amina")
                .setEmail("amina@example.com")
                .setArea(area)
                .build();
        clientService.create(client);

        provider = new ServiceProvider.Builder()
                .setUserId("user007")
                .setName("Tauriq")
                .setEmail("tauriq@gmail.com")
                .setArea(area)
                .setServiceType(gardener)
                .build();
        providerService.create(provider);

        booking = BookingFactory.createBooking(
                "booking001",
                LocalDate.of(2025, 4, 12),
                "Scheduled",
                "Customer requested morning service",
                client,
                provider,
                new ArrayList<>()
        );

        assertNotNull(booking, "BookingFactory returned null — check input entities");
    }

    @Test
    @Order(1)
    void testCreateBooking() {
        Booking savedBooking = bookingService.create(booking);
        assertNotNull(savedBooking);
        assertEquals("booking001", savedBooking.getBookingId());
        System.out.println("Created Booking: " + savedBooking);
    }

    @Test
    @Order(2)
    void testReadBooking() {
        Booking readBooking = bookingService.read("booking001");
        assertNotNull(readBooking);
        assertEquals("booking001", readBooking.getBookingId());
        System.out.println("Read Booking: " + readBooking);
    }

    @Test
    @Order(3)
    void testUpdateBooking() {
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
    void testGetAllBookings() {
        List<Booking> allBookings = bookingService.getAll();
        assertNotNull(allBookings);
        assertFalse(allBookings.isEmpty());
        System.out.println("All Bookings: " + allBookings.size());
    }

    @Test
    @Order(5)
    void testDeleteBooking() {
        boolean deleted = bookingService.delete("booking001");
        assertTrue(deleted);
        Booking deletedBooking = bookingService.read("booking001");
        assertNull(deletedBooking);
        System.out.println("Deleted Booking ID: booking001");
    }
}
