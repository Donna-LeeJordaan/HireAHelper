/* BookingFactoryTest.java
   Author: D.Jordaan (230613152)
   Date: 25 July 2025
*/

package za.co.hireahelper.factory;

import org.junit.jupiter.api.*;
import za.co.hireahelper.domain.Booking;
import za.co.hireahelper.domain.Client;
import za.co.hireahelper.domain.ServiceProvider;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BookingFactoryTest {

    private static Client client;
    private static ServiceProvider serviceProvider;
    private static Date futureDate;
    private static Date pastDate;

    @BeforeAll
    static void setUp() {
        client = new Client.Builder()
                .setUserId("client123")
                .setName("Test Client")
                .build();

        serviceProvider = new ServiceProvider.Builder()
                .setUserId("sp123")
                .setName("Test Provider")
                .build();

        futureDate = new Date(System.currentTimeMillis() + 86400000); // Tomorrow
        pastDate = new Date(System.currentTimeMillis() - 86400000); // Yesterday
    }

    @Test
    @Order(1)
    void a_createValidBooking() {
        Booking booking = BookingFactory.createBooking(
                "booking123",
                futureDate,
                "Confirmed",
                "Test notes",
                client,
                serviceProvider
        );

        assertNotNull(booking);
        assertEquals("booking123", booking.getBookingId());
        assertEquals("Confirmed", booking.getStatus());
        assertEquals(client, booking.getClient());
        assertEquals(serviceProvider, booking.getServiceProvider());
        System.out.println("Created valid booking: " + booking);
    }

    @Test
    @Order(2)
    void b_createBookingWithPastDate() {
        Booking booking = BookingFactory.createBooking(
                "booking124",
                pastDate,
                "Pending",
                "Past date booking",
                client,
                serviceProvider
        );

        assertNull(booking);
        System.out.println("Past date booking test passed - returned null as expected");
    }

    @Test
    @Order(3)
    void c_createBookingWithInvalidStatus() {
        Booking booking = BookingFactory.createBooking(
                "booking125",
                futureDate,
                "InvalidStatus",
                "Invalid status booking",
                client,
                serviceProvider
        );

        assertNull(booking);
        System.out.println("Invalid status booking test passed - returned null as expected");
    }

    @Test
    @Order(4)
    void d_createBookingWithNullFields() {
        // Test null bookingId
        assertNull(BookingFactory.createBooking(null, futureDate, "Confirmed", "Notes", client, serviceProvider));

        // Test null serviceDate
        assertNull(BookingFactory.createBooking("booking126", null, "Confirmed", "Notes", client, serviceProvider));

        // Test null status
        assertNull(BookingFactory.createBooking("booking127", futureDate, null, "Notes", client, serviceProvider));

        // Test null client
        assertNull(BookingFactory.createBooking("booking128", futureDate, "Confirmed", "Notes", null, serviceProvider));

        // Test null serviceProvider
        assertNull(BookingFactory.createBooking("booking129", futureDate, "Confirmed", "Notes", client, null));

        System.out.println("Null field validation tests passed");
    }

    @Test
    @Order(5)
    void e_createBookingWithEmptyStrings() {
        // Test empty bookingId
        assertNull(BookingFactory.createBooking("", futureDate, "Confirmed", "Notes", client, serviceProvider));

        // Test empty status
        assertNull(BookingFactory.createBooking("booking130", futureDate, "", "Notes", client, serviceProvider));

        System.out.println("Empty string validation tests passed");
    }

    @Test
    @Order(6)
    void f_createBookingWithAllStatusValues() {
        // Test all valid status values
        String[] validStatuses = {"Confirmed", "Pending", "Cancelled", "Completed"};

        for (String status : validStatuses) {
            Booking booking = BookingFactory.createBooking(
                    "booking-" + status.toLowerCase(),
                    futureDate,
                    status,
                    "Test " + status + " booking",
                    client,
                    serviceProvider
            );

            assertNotNull(booking);
            assertEquals(status, booking.getStatus());
        }

        System.out.println("All valid status values test passed");
    }
}