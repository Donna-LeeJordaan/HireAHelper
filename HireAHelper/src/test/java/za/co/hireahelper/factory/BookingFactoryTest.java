/* BookingFactoryTest.java
   Author: Donna-Lee Jordaan (230613152)
   Date: 25 July 2025 / modified 11 August 2025
*/

package za.co.hireahelper.factory;

import org.junit.jupiter.api.Test;
import za.co.hireahelper.domain.Booking;
import za.co.hireahelper.domain.Client;
import za.co.hireahelper.domain.ServiceProvider;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class BookingFactoryTest {

    // Helper method to create a minimal valid Client
    private Client createValidClient() {
        return new Client.Builder()
                .setUserId("client001")
                .build();
    }

    // Helper method to create a minimal valid ServiceProvider
    private ServiceProvider createValidServiceProvider() {
        return new ServiceProvider.Builder()
                .setUserId("sp001")
                .build();
    }

    @Test
    void testCreateValidBooking() {
        Client client = createValidClient();
        ServiceProvider serviceProvider = createValidServiceProvider();
        Date serviceDate = new Date();

        Booking booking = BookingFactory.createBooking(
                "booking001",
                serviceDate,
                "Scheduled",
                "Notes for booking",
                client,
                serviceProvider,
                new ArrayList<>()
        );

        System.out.println("Created valid Booking: " + booking); // for sanity
        assertNotNull(booking);
        assertEquals("booking001", booking.getBookingId());
        assertEquals(serviceDate, booking.getServiceDate());
        assertEquals("Scheduled", booking.getStatus());
        assertEquals("Notes for booking", booking.getNotes());
        assertEquals(client, booking.getClient());
        assertEquals(serviceProvider, booking.getServiceProvider());
        assertNotNull(booking.getReviews());
        assertTrue(booking.getReviews().isEmpty());
    }

    @Test
    void testCreateBookingWithNullId() {
        Client client = createValidClient();
        ServiceProvider serviceProvider = createValidServiceProvider();

        Booking booking = BookingFactory.createBooking(
                null,
                new Date(),
                "Scheduled",
                "Notes",
                client,
                serviceProvider,
                new ArrayList<>()
        );

        System.out.println("Booking with null ID: " + booking); // for sanity
        assertNull(booking);
    }

    @Test
    void testCreateBookingWithNullServiceDate() {
        Client client = createValidClient();
        ServiceProvider serviceProvider = createValidServiceProvider();

        Booking booking = BookingFactory.createBooking(
                "booking002",
                null,
                "Scheduled",
                "Notes",
                client,
                serviceProvider,
                new ArrayList<>()
        );

        System.out.println("Booking with null service date: " + booking); // for sanity
        assertNull(booking);
    }

    @Test
    void testCreateBookingWithNullStatus() {
        Client client = createValidClient();
        ServiceProvider serviceProvider = createValidServiceProvider();

        Booking booking = BookingFactory.createBooking(
                "booking003",
                new Date(),
                null,
                "Notes",
                client,
                serviceProvider,
                new ArrayList<>()
        );

        System.out.println("Booking with null status: " + booking); // for sanity
        assertNull(booking);
    }

    @Test
    void testCreateBookingWithNullClient() {
        ServiceProvider serviceProvider = createValidServiceProvider();

        Booking booking = BookingFactory.createBooking(
                "booking004",
                new Date(),
                "Scheduled",
                "Notes",
                null,
                serviceProvider,
                new ArrayList<>()
        );

        System.out.println("Booking with null client: " + booking); // for sanity
        assertNull(booking);
    }

    @Test
    void testCreateBookingWithNullServiceProvider() {
        Client client = createValidClient();

        Booking booking = BookingFactory.createBooking(
                "booking005",
                new Date(),
                "Scheduled",
                "Notes",
                client,
                null,
                new ArrayList<>()
        );

        System.out.println("Booking with null service provider: " + booking); // for sanity
        assertNull(booking);
    }
}

