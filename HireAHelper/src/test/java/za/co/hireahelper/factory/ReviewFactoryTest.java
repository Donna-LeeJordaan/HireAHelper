/* ReviewFactoryTest.java
   Author: Donna-Lee Jordaan (230613152)
   Date: 11 August 2025
*/

package za.co.hireahelper.factory;

import org.junit.jupiter.api.*;
import za.co.hireahelper.domain.*;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ReviewFactoryTest {

    private static Client client = new Client.Builder()
            .setUserId("user001")
            .build();

    private static ServiceProvider serviceProvider = new ServiceProvider.Builder()
            .setUserId("user007")
            .build();

    private static Booking booking = new Booking.Builder()
            .setBookingId("booking001")
            .build();

    private static Review review1 = ReviewFactory.createReview(
            "review001",
            5,
            "Excellent service!",
            client,
            serviceProvider,
            booking);

    private static Review review2 = ReviewFactory.createReview(
            "review002",
            4,
            "Good service, would hire again",
            client,
            serviceProvider,
            booking);

    @Test
    @Order(1)
    public void testCreateReview() {
        assertNotNull(review1);
        assertEquals(5, review1.getRating());
        System.out.println(review1);
    }

    @Test
    @Order(2)
    public void testCreateReviewWithAllAttributes() {
        assertNotNull(review2);
        assertEquals("Good service, would hire again", review2.getComment());
        System.out.println(review2);
    }

    @Test
    @Order(3)
    public void testCreateReviewWithInvalidRating() {
        Review invalidReview = ReviewFactory.createReview(
                "review003",
                6,  // Invalid rating
                "Rating too high",
                client,
                serviceProvider,
                booking);
        assertNull(invalidReview);
        System.out.println(invalidReview);
    }

    @Test
    @Order(4)
    public void testCreateReviewWithNullFields() {
        Review nullReview = ReviewFactory.createReview(
                null,  // Null ID
                3,
                null,  // Null comment
                null,  // Null client
                serviceProvider,
                booking);
        assertNull(nullReview);
        System.out.println(nullReview);
    }

    @Test
    @Order(5)
    public void testCreateReviewWithMissingBooking() {
        Review noBookingReview = ReviewFactory.createReview(
                "review004",
                4,
                "Service was good",
                client,
                serviceProvider,
                null);  // Missing booking
        assertNull(noBookingReview);
        System.out.println(noBookingReview);
    }
}