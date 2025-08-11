/* ReviewFactoryTest.java
   Author: Donna-Lee Jordaan (230613152)
   Date: 25 July 2025 / modified 11 August 2025
*/

package za.co.hireahelper.factory;

import org.junit.jupiter.api.Test;
import za.co.hireahelper.domain.Client;
import za.co.hireahelper.domain.Review;
import za.co.hireahelper.domain.ServiceProvider;
import za.co.hireahelper.domain.Booking;

import static org.junit.jupiter.api.Assertions.*;

public class ReviewFactoryTest {

    // Helper methods to create minimal valid Client, ServiceProvider, Booking
    private Client createValidClient() {
        return new Client.Builder()
                .setUserId("client001")
                .build();
    }

    private ServiceProvider createValidServiceProvider() {
        return new ServiceProvider.Builder()
                .setUserId("sp001")
                .build();
    }

    private Booking createValidBooking() {
        return new Booking.Builder()
                .setBookingId("booking001")
                .build();
    }

    @Test
    void testCreateValidReview() {
        Client client = createValidClient();
        ServiceProvider sp = createValidServiceProvider();
        Booking booking = createValidBooking();

        Review review = ReviewFactory.createReview(
                "review001",
                5,
                "Excellent service!",
                client,
                sp,
                booking
        );

        System.out.println("Created valid Review: " + review); // for sanity
        assertNotNull(review);
        assertEquals("review001", review.getReviewId());
        assertEquals(5, review.getRating());
        assertEquals("Excellent service!", review.getComment());
        assertEquals(client, review.getClient());
        assertEquals(sp, review.getServiceProvider());
        assertEquals(booking, review.getBooking());
    }

    @Test
    void testCreateReviewWithNullId() {
        Client client = createValidClient();
        ServiceProvider sp = createValidServiceProvider();
        Booking booking = createValidBooking();

        Review review = ReviewFactory.createReview(
                null,
                4,
                "Good service",
                client,
                sp,
                booking
        );

        System.out.println("Review with null ID: " + review); // for sanity
        assertNull(review);
    }

    @Test
    void testCreateReviewWithInvalidRating() {
        Client client = createValidClient();
        ServiceProvider sp = createValidServiceProvider();
        Booking booking = createValidBooking();

        // rating less than 1
        Review reviewLow = ReviewFactory.createReview(
                "review002",
                0,
                "Bad rating",
                client,
                sp,
                booking
        );

        System.out.println("Review with rating 0: " + reviewLow);
        assertNull(reviewLow);

        // rating greater than 5
        Review reviewHigh = ReviewFactory.createReview(
                "review003",
                6,
                "Too high rating",
                client,
                sp,
                booking
        );

        System.out.println("Review with rating 6: " + reviewHigh);
        assertNull(reviewHigh);
    }

    @Test
    void testCreateReviewWithNullComment() {
        Client client = createValidClient();
        ServiceProvider sp = createValidServiceProvider();
        Booking booking = createValidBooking();

        Review review = ReviewFactory.createReview(
                "review004",
                3,
                null,
                client,
                sp,
                booking
        );

        System.out.println("Review with null comment: " + review);
        assertNull(review);
    }

    @Test
    void testCreateReviewWithNullClient() {
        ServiceProvider sp = createValidServiceProvider();
        Booking booking = createValidBooking();

        Review review = ReviewFactory.createReview(
                "review005",
                4,
                "No client",
                null,
                sp,
                booking
        );

        System.out.println("Review with null client: " + review);
        assertNull(review);
    }

    @Test
    void testCreateReviewWithNullServiceProvider() {
        Client client = createValidClient();
        Booking booking = createValidBooking();

        Review review = ReviewFactory.createReview(
                "review006",
                4,
                "No service provider",
                client,
                null,
                booking
        );

        System.out.println("Review with null service provider: " + review);
        assertNull(review);
    }

    @Test
    void testCreateReviewWithNullBooking() {
        Client client = createValidClient();
        ServiceProvider sp = createValidServiceProvider();

        Review review = ReviewFactory.createReview(
                "review007",
                4,
                "No booking",
                client,
                sp,
                null
        );

        System.out.println("Review with null booking: " + review);
        assertNull(review);
    }
}

