/* BookingFactoryTest.java
   Author: Donna-Lee Jordaan (230613152)
   Date: 25 July 2025 / modified 11 August 2025
*/


package za.co.hireahelper.factory;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import za.co.hireahelper.domain.Booking;
import za.co.hireahelper.domain.Client;
import za.co.hireahelper.domain.ServiceProvider;
import za.co.hireahelper.domain.Review;
import za.co.hireahelper.domain.Area;
import za.co.hireahelper.domain.ServiceType;
import java.time.LocalDate;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

public class BookingFactoryTest {

    private Area area = new Area.Builder()
            .setAreaId("area001")
            .setName("Athlone")
            .build();

    private Client createValidClient() {
        return ClientFactory.createClient(
                "user001", "Amina", "amina@example.com", "password123", "0823456789",
                area, new ArrayList<>(), new ArrayList<>(), new ArrayList<>()
        );
    }

    private ServiceProvider createValidServiceProvider() {
        ServiceType gardener = new ServiceType.Builder()
                .setTypeId("type02")
                .setTypeName("Gardener")
                .build();

        return ServiceProviderFactory.createServiceProvider(
                "user007", "Tauriq Osman", "moegamattauriqosman@example.com", "Tauriq04",
                "0611234567", area, "tauriq.jpeg",
                "Skilled Gardener with 15 years experience", 350.0, gardener,
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>()
        );
    }

    @Test
    void testCreateValidBooking() {
        Client client = createValidClient();
        ServiceProvider serviceProvider = createValidServiceProvider();
        LocalDate serviceDate = LocalDate.now();

        Booking booking = BookingFactory.createBooking(
                "booking001",
                serviceDate,
                "Scheduled",
                "Customer requested morning service",
                client,
                serviceProvider,
                new ArrayList<>()
        );

        assertNotNull(booking);

        System.out.println(String.format(
                "Booking[id=%s, date=%s, status=%s, notes=%s, clientId=%s, providerId=%s, reviews=%d]",
                booking.getBookingId(),
                booking.getServiceDate(),
                booking.getStatus(),
                booking.getNotes(),
                booking.getClient() != null ? booking.getClient().getUserId() : "null",
                booking.getServiceProvider() != null ? booking.getServiceProvider().getUserId() : "null",
                booking.getReviews() != null ? booking.getReviews().size() : 0
        ));
    }

    @Test
    void testCreateBookingWithNullId() {
        Client client = createValidClient();
        ServiceProvider serviceProvider = createValidServiceProvider();

        Booking booking = BookingFactory.createBooking(
                null,
                LocalDate.now(),
                "Scheduled",
                "Notes",
                client,
                serviceProvider,
                new ArrayList<>()
        );

        assertNull(booking);

        System.out.println("Booking creation failed as expected with null ID.");
    }

    @Test
    @Order(1)
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

        assertNull(booking);

        System.out.println("Booking creation failed as expected with null service date.");
    }

    @Test
    @Order(2)
    void testCreateBookingWithNullStatus() {
        Client client = createValidClient();
        ServiceProvider serviceProvider = createValidServiceProvider();

        Booking booking = BookingFactory.createBooking(
                "booking003",
                LocalDate.now(),
                null,
                "Notes",
                client,
                serviceProvider,
                new ArrayList<>()
        );

        assertNull(booking);

        System.out.println("Booking creation failed as expected with null status.");
    }

    @Test
    @Order(3)
    void testCreateBookingWithNullClient() {
        ServiceProvider serviceProvider = createValidServiceProvider();

        Booking booking = BookingFactory.createBooking(
                "booking004",
                LocalDate.now(),
                "Scheduled",
                "Notes",
                null,
                serviceProvider,
                new ArrayList<>()
        );

        assertNull(booking);

        System.out.println("Booking creation failed as expected with null client.");
    }

    @Test
    @Order(4)
    void testCreateBookingWithNullServiceProvider() {
        Client client = createValidClient();

        Booking booking = BookingFactory.createBooking(
                "booking005",
                LocalDate.now(),
                "Scheduled",
                "Notes",
                client,
                null,
                new ArrayList<>()
        );

        assertNull(booking);

        System.out.println("Booking creation failed as expected with null service provider.");
    }
}

