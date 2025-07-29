/* ReviewServiceTest.java

   Author: D.Jordaan (230613152)

   Date: 13 July 2025 */

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
            .setUserId("client-001")
            .setName("Test Client")
            .build();

    private static final ServiceProvider provider = new ServiceProvider.Builder()
            .setUserId("provider-001")
            .setName("Test Provider")
            .build();

    private static final Review review = ReviewFactory.CreateReview(
            "REV-001",
            5,
            "Excellent service!",
            LocalDateTime.now().minusDays(1), // Past date
            client,
            provider
    );

    @Test
    @Order(1)
    void a_create() {
        Review created = service.create(review);
        assertNotNull(created);
        assertEquals("REV-001", created.getReviewId());
        assertEquals(5, created.getRating());
        assertEquals("Excellent service!", created.getComment());
        assertEquals(client, created.getClient());
        assertEquals(provider, created.getServiceProvider());
        System.out.println("Created: " + created);
    }

    @Test
    @Order(2)
    void b_read() {
        Review read = service.read(review.getReviewId());
        assertNotNull(read);
        assertEquals("REV-001", read.getReviewId());
        assertEquals("Excellent service!", read.getComment());
        System.out.println("Read: " + read);
    }

    @Test
    @Order(3)
    void c_update() {
        Review updatedReview = new Review.Builder()
                .copy(review)
                .setRating(4)
                .setComment("Very good service")
                .build();

        Review updated = service.update(updatedReview);
        assertNotNull(updated);
        assertEquals(4, updated.getRating());
        assertEquals("Very good service", updated.getComment());
        System.out.println("Updated: " + updated);
    }

    @Test
    @Order(4)
    void d_getAll() {
        var allReviews = service.getAll();
        assertNotNull(allReviews);
        assertTrue(allReviews.size() >= 1);
        System.out.println("All reviews: " + allReviews.size());
    }

    @Test
    @Order(5)
    void e_delete() {
        boolean deleted = service.delete(review.getReviewId());
        assertTrue(deleted);

        // Verify the review is actually deleted
        Review deletedReview = service.read(review.getReviewId());
        assertNull(deletedReview);
        System.out.println("Deleted: " + true);
    }

    @Test
    @Order(6)
    void f_createInvalidRating() {
        Review invalidReview = new Review.Builder()
                .copy(review)
                .setReviewId("REV-002")
                .setRating(6) // Invalid rating
                .build();

        assertThrows(IllegalArgumentException.class, () -> {
            service.create(invalidReview);
        });
        System.out.println("Invalid rating test passed");
    }
}
