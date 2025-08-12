/* BookingFactoryTest.java
   Author: Donna-Lee Jordaan (230613152)
   Date: 25 July 2025 / modified 11 August 2025
*/

package za.co.hireahelper.factory;

import org.junit.jupiter.api.*;
import za.co.hireahelper.domain.*;
import java.time.LocalDate;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BookingFactoryTest {

    private static Client client = new Client.Builder()
            .setUserId("client001")
            .build();

    private static ServiceProvider serviceProvider = new ServiceProvider.Builder()
            .setUserId("sp001")
            .build();

    private static LocalDate serviceDate = LocalDate.of(2025, 8, 11);
    private static ArrayList<Review> reviews = new ArrayList<>();

    private static Booking booking1 = BookingFactory.createBooking(
            "B001",
            serviceDate,
            "Pending",
            "Customer requested morning service",
            client,
            serviceProvider,
            reviews
    );

    private static Booking booking2 = BookingFactory.createBooking(
            "B002",
            LocalDate.of(2025, 8, 12),
            "Confirmed",
            "Afternoon service preferred",
            client,
            serviceProvider,
            reviews
    );

    @Test
    @Order(1)
    public void testCreateBooking() {
        assertNotNull(booking1);
        System.out.println(booking1);
    }

    @Test
    @Order(2)
    public void testCreateBookingWithAllAttributes() {
        assertNotNull(booking2);
        assertEquals("B002", booking2.getBookingId());
        assertEquals(LocalDate.of(2025, 8, 12), booking2.getServiceDate());
        assertEquals("Confirmed", booking2.getStatus());
        assertEquals("Afternoon service preferred", booking2.getNotes());
        assertEquals(client, booking2.getClient());
        assertEquals(serviceProvider, booking2.getServiceProvider());
        System.out.println(booking2);
    }

    @Test
    @Order(3)
    public void testCreateBookingWithNullId() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            BookingFactory.createBooking(
                    null,
                    serviceDate,
                    "Scheduled",
                    "Notes",
                    client,
                    serviceProvider,
                    reviews
            );
        });
        assertTrue(exception.getMessage().contains("Booking ID cannot be null"));
        System.out.println("Test passed: Booking with null ID throws exception");
    }

    @Test
    @Order(4)
    public void testCreateBookingWithNullServiceDate() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            BookingFactory.createBooking(
                    "B003",
                    null,
                    "Scheduled",
                    "Notes",
                    client,
                    serviceProvider,
                    reviews
            );
        });
        assertTrue(exception.getMessage().contains("Service date cannot be null"));
        System.out.println("Test passed: Booking with null service date throws exception");
    }

    @Test
    @Order(5)
    public void testCreateBookingWithNullClient() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            BookingFactory.createBooking(
                    "B004",
                    serviceDate,
                    "Scheduled",
                    "Notes",
                    null,
                    serviceProvider,
                    reviews
            );
        });
        assertTrue(exception.getMessage().contains("Client cannot be null"));
        System.out.println("Test passed: Booking with null client throws exception");
    }

    @Test
    @Order(6)
    public void testCreateBookingWithNullServiceProvider() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            BookingFactory.createBooking(
                    "B005",
                    serviceDate,
                    "Scheduled",
                    "Notes",
                    client,
                    null,
                    reviews
            );
        });
        assertTrue(exception.getMessage().contains("Service provider cannot be null"));
        System.out.println("Test passed: Booking with null service provider throws exception");
    }
}