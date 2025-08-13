/*
 ReviewFactoryTest.java
 Author: Donna-Lee Jordaan (230613152)
 Date: 25 July 2025 / modified 11 August 2025
*/

package za.co.hireahelper.factory;

import org.junit.jupiter.api.Test;
import za.co.hireahelper.domain.*;

import java.time.LocalDate;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

public class ReviewFactoryTest {

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

    private Booking createValidBooking(Client client, ServiceProvider serviceProvider) {
        return BookingFactory.createBooking(
                "booking001",
                LocalDate.now(),
                "Scheduled",
                "Customer requested morning service",
                client,
                serviceProvider,
                new ArrayList<>()
        );
    }

    @Test
    void testCreateValidReview() {
        Client client = createValidClient();
        ServiceProvider provider = createValidServiceProvider();
        Booking booking = createValidBooking(client, provider);

        Review review = ReviewFactory.createReview(
                "review001",
                5,
                "Excellent and professional service.",
                client,
                provider,
                booking
        );

        System.out.println("Review = " + review);
        assertNotNull(review);
        assertEquals(5, review.getRating());
        assertEquals("Excellent and professional service.", review.getComment());
    }

    @Test
    void testCreateReviewWithRatingOutOfRange() {
        Client client = createValidClient();
        ServiceProvider provider = createValidServiceProvider();
        Booking booking = createValidBooking(client, provider);

        // Rating higher than allowed (assuming 5 max)
        Review review = ReviewFactory.createReview(
                "review002",
                6,
                "Over the top score",
                client,
                provider,
                booking
        );

        System.out.println("Review with invalid rating = " + review);
        assertNull(review);
    }

    @Test
    void testCreateReviewWithEmptyComment() {
        Client client = createValidClient();
        ServiceProvider provider = createValidServiceProvider();
        Booking booking = createValidBooking(client, provider);

        Review review = ReviewFactory.createReview(
                "review003",
                4,
                "",
                client,
                provider,
                booking
        );

        System.out.println("Review with empty comment = " + review);
        assertNull(review);
    }

    @Test
    void testCreateReviewWithBookingMismatch() {
        Client client = createValidClient();
        ServiceProvider provider = createValidServiceProvider();

        // Create a booking with a DIFFERENT provider
        ServiceProvider otherProvider = ServiceProviderFactory.createServiceProvider(
                "user009", "Yusuf Parker", "yusufparker@example.com", "Yusuf123",
                "0789990001", null, "yusuf.jpeg",
                "Expert Plumber with 10 years experience", 500.0, null,
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>()
        );
        Booking booking = createValidBooking(client, otherProvider);

        Review review = ReviewFactory.createReview(
                "review004",
                3,
                "Provider does not match booking",
                client,
                provider,
                booking
        );

        System.out.println("Review with booking mismatch = " + review);
        assertNull(review);
    }

    @Test
    void testCreateReviewWithNegativeRating() {
        Client client = createValidClient();
        ServiceProvider provider = createValidServiceProvider();
        Booking booking = createValidBooking(client, provider);

        Review review = ReviewFactory.createReview(
                "review005",
                -1,
                "Negative score is invalid",
                client,
                provider,
                booking
        );

        System.out.println("Review with negative rating = " + review);
        assertNull(review);
    }
}
