/* ReviewServiceTest.java
   Author: D.Jordaan (230613152)
   Date: 25 July 2025
*/

package za.co.hireahelper.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.co.hireahelper.domain.Review;
import za.co.hireahelper.domain.Client;
import za.co.hireahelper.domain.ServiceProvider;
import za.co.hireahelper.factory.ReviewFactory;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
class ReviewServiceTest {

    @Autowired
    private ReviewService service;

    private static final Client client = new Client.Builder()
            .setUserId("client123")
            .setName("Test Client")
            .build();

    private static final ServiceProvider serviceProvider = new ServiceProvider.Builder()
            .setUserId("sp123")
            .setName("Test Provider")
            .build();

    private static Review review;

    @BeforeAll
    static void setUp() {
        review = ReviewFactory.createReview(
                "review123",
                5,
                "Excellent service!",
                LocalDateTime.now().minusDays(1), // Past date
                client,
                serviceProvider
        );
        assertNotNull(review);
    }

    @Test
    @Order(1)
    void a_create() {
        Review created = service.create(review);
        assertNotNull(created);
        assertEquals(review.getReviewId(), created.getReviewId());
        assertEquals(review.getRating(), created.getRating());
        assertEquals(review.getComment(), created.getComment());
        assertEquals(review.getClient(), created.getClient());
        assertEquals(review.getServiceProvider(), created.getServiceProvider());
        System.out.println("Created review: " + created);
    }

    @Test
    @Order(2)
    void b_read() {
        Review found = service.read(review.getReviewId());
        assertNotNull(found);
        assertEquals(review.getReviewId(), found.getReviewId());
        assertEquals(review.getRating(), found.getRating());
        assertEquals(review.getComment(), found.getComment());
        System.out.println("Read review: " + found);
    }

    @Test
    @Order(3)
    void c_update() {
        Review updated = new Review.Builder()
                .copy(review)
                .setRating(4)
                .setComment("Very good service")
                .build();

        Review result = service.update(updated);
        assertNotNull(result);
        assertEquals(4, result.getRating());
        assertEquals("Very good service", result.getComment());
        System.out.println("Updated review: " + result);
    }

    @Test
    @Order(4)
    void d_getAll() {
        var allReviews = service.getAll();
        assertNotNull(allReviews);
        assertFalse(allReviews.isEmpty());
        System.out.println("All reviews count: " + allReviews.size());
    }

    @Test
    @Order(5)
    void e_delete() {
        boolean deleted = service.delete(review.getReviewId());
        assertTrue(deleted);

        Review shouldBeNull = service.read(review.getReviewId());
        assertNull(shouldBeNull);
        System.out.println("Deleted review with ID: " + review.getReviewId());
    }

    @Test
    @Order(6)
    void f_invalidReviewTests() {
        // Test invalid rating
        Review invalidRating = new Review.Builder()
                .copy(review)
                .setReviewId("review-invalid-1")
                .setRating(6) // Invalid
                .build();

        assertThrows(IllegalArgumentException.class, () -> service.create(invalidRating));

        // Test future timestamp
        Review futureReview = new Review.Builder()
                .copy(review)
                .setReviewId("review-invalid-2")
                .setTimeStamp(LocalDateTime.now().plusDays(1)) // Future date
                .build();

        assertThrows(IllegalArgumentException.class, () -> service.create(futureReview));

        System.out.println("Invalid review tests passed");
    }
}