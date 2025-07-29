/* ReviewFactoryTest.java
   Author: D.Jordaan (230613152)
   Date: 25 July 2025
*/

package za.co.hireahelper.factory;

import org.junit.jupiter.api.*;
import za.co.hireahelper.domain.Client;
import za.co.hireahelper.domain.Review;
import za.co.hireahelper.domain.ServiceProvider;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ReviewFactoryTest {

    private static Client client;
    private static ServiceProvider serviceProvider;
    private static LocalDateTime currentTime;
    private static LocalDateTime pastTime;
    private static LocalDateTime futureTime;

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

        currentTime = LocalDateTime.now();
        pastTime = currentTime.minusDays(1);
        futureTime = currentTime.plusDays(1);
    }

    @Test
    @Order(1)
    void a_createValidReview() {
        Review review = ReviewFactory.createReview(
                "review123",
                5,
                "Excellent service!",
                currentTime,
                client,
                serviceProvider
        );

        assertNotNull(review);
        assertEquals("review123", review.getReviewId());
        assertEquals(5, review.getRating());
        assertEquals("Excellent service!", review.getComment());
        assertEquals(client, review.getClient());
        assertEquals(serviceProvider, review.getServiceProvider());
        System.out.println("Created valid review: " + review);
    }

    @Test
    @Order(2)
    void b_createReviewWithFutureTimestamp() {
        Review review = ReviewFactory.createReview(
                "review124",
                4,
                "Future review",
                futureTime,
                client,
                serviceProvider
        );

        assertNull(review);
        System.out.println("Future timestamp test passed - returned null as expected");
    }

    @Test
    @Order(3)
    void c_createReviewWithInvalidRating() {
        // Test rating below minimum
        assertNull(ReviewFactory.createReview("review125", 0, "Too low", currentTime, client, serviceProvider));

        // Test rating above maximum
        assertNull(ReviewFactory.createReview("review126", 6, "Too high", currentTime, client, serviceProvider));

        System.out.println("Invalid rating tests passed");
    }

    @Test
    @Order(4)
    void d_createReviewWithNullFields() {
        // Test null reviewId
        assertNull(ReviewFactory.createReview(null, 5, "Good", currentTime, client, serviceProvider));

        // Test null timestamp
        assertNull(ReviewFactory.createReview("review127", 5, "Good", null, client, serviceProvider));

        // Test null client
        assertNull(ReviewFactory.createReview("review128", 5, "Good", currentTime, null, serviceProvider));

        // Test null serviceProvider
        assertNull(ReviewFactory.createReview("review129", 5, "Good", currentTime, client, null));

        System.out.println("Null field validation tests passed");
    }

    @Test
    @Order(5)
    void e_createReviewWithEmptyStrings() {
        // Test empty reviewId
        assertNull(ReviewFactory.createReview("", 5, "Good", currentTime, client, serviceProvider));

        // Test empty comment (should be allowed)
        Review review = ReviewFactory.createReview(
                "review130",
                3,
                "",
                currentTime,
                client,
                serviceProvider
        );
        assertNotNull(review);
        assertEquals("", review.getComment());

        System.out.println("Empty string validation tests passed");
    }

    @Test
    @Order(6)
    void f_createReviewWithAllValidRatings() {
        // Test all valid rating values (1-5)
        for (int i = 1; i <= 5; i++) {
            Review review = ReviewFactory.createReview(
                    "review-rating-" + i,
                    i,
                    "Rating test " + i,
                    currentTime,
                    client,
                    serviceProvider
            );

            assertNotNull(review);
            assertEquals(i, review.getRating());
        }

        System.out.println("All valid rating values test passed");
    }
}