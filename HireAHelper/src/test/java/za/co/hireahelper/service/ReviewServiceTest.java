/* ReviewServiceTest.java
   Author: Donna-Lee Jordaan (230613152)
   Date: 25 July 2025 / modified 11 August 2025
*/

package za.co.hireahelper.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import za.co.hireahelper.domain.Client;
import za.co.hireahelper.domain.Review;
import za.co.hireahelper.domain.ServiceProvider;
import za.co.hireahelper.domain.Booking;
import za.co.hireahelper.factory.ReviewFactory;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ReviewServiceTest {

    @Autowired
    private ReviewService service;

    private static Review review;

    // Helper methods to create minimal dependent objects
    private static Client client;
    private static ServiceProvider serviceProvider;
    private static Booking booking;

    @BeforeAll
    static void setup() {
        client = new Client.Builder()
                .setUserId("client001")
                .build();

        serviceProvider = new ServiceProvider.Builder()
                .setUserId("sp001")
                .build();

        booking = new Booking.Builder()
                .setBookingId("booking001")
                .build();

        review = ReviewFactory.createReview(
                "review001",
                5,
                "Excellent service!",
                client,
                serviceProvider,
                booking
        );

        assertNotNull(review);
    }

    @Test
    @Order(1)
    void a_create() {
        Review created = service.create(review);
        assertNotNull(created);
        assertEquals("review001", created.getReviewId());
        System.out.println("Created: " + created);
    }

    @Test
    @Order(2)
    void b_read() {
        Review found = service.read(review.getReviewId());
        assertNotNull(found);
        System.out.println("Read: " + found);
    }

    @Test
    @Order(3)
    void c_update() {
        Review updated = new Review.Builder()
                .copy(review)
                .setRating(4)
                .setComment("Good service, but room for improvement")
                .build();

        Review result = service.update(updated);
        assertNotNull(result);
        assertEquals(4, result.getRating());
        System.out.println("Updated: " + result);
    }

    @Test
    @Order(4)
    void d_getAll() {
        assertNotNull(service.getAll());
        assertTrue(service.getAll().size() > 0);
        System.out.println("All Reviews: " + service.getAll());
    }

    @Test
    @Order(5)
    void e_delete() {
        boolean deleted = service.delete(review.getReviewId());
        assertTrue(deleted);
        System.out.println("Deleted: " + review.getReviewId());
    }
}
