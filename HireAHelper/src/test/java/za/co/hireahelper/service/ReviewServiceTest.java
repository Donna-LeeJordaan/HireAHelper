/* ReviewServiceTest.java
   Author: Donna-Lee Jordaan (230613152)
   Date: 25 July 2025 / modified 12 August 2025
*/
package za.co.hireahelper.service;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.co.hireahelper.domain.Booking;
import za.co.hireahelper.domain.Client;
import za.co.hireahelper.domain.Review;
import za.co.hireahelper.domain.ServiceProvider;
import za.co.hireahelper.factory.ReviewFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ReviewServiceTest {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private ServiceProviderService serviceProviderService;

    @Autowired
    private BookingService bookingService;

    private static final Client client = new Client.Builder()
            .setUserId("area001")
            .setName("Amina")
            .setEmail("amina@example.com")
            .build();

    private static final ServiceProvider provider = new ServiceProvider.Builder()
            .setUserId("user007")
            .setName("Tauriq")
            .setEmail("tauriq@gmail.com")
            .build();

    private static Review review;
    private Booking booking;


    @BeforeAll
    void setUp() {
        review = ReviewFactory.createReview(
                "R001",
                5,
                "Excellent service!",
                client,
                provider,
                booking

        );
    }

//    @BeforeEach
//    void setupDependencies() {
//        if (clientService.read(client.getUserId()) == null) {
//            clientService.create(client);
//        }
//        if (serviceProviderService.read(provider.getUserId()) == null) {
//            serviceProviderService.create(provider);
//        }
//        if (bookingService.read(booking.getBookingId()) == null) {
//            bookingService.create(booking);
//        }
//    }

    @Test
    @Order(1)
    void a_create() {
        Review created = reviewService.create(review);
        assertNotNull(created);
        assertEquals(review.getReviewId(), created.getReviewId());
        System.out.println("Created: " + created);
    }

    @Test
    @Order(2)
    @Transactional
    void b_read() {
        Review read = reviewService.read(review.getReviewId());
        assertNotNull(read);
        assertEquals(review.getReviewId(), read.getReviewId());
        System.out.println("Read: " + read);
    }

    @Test
    @Order(3)
    void c_update() {
        Review updated = new Review.Builder()
                .copy(review)
                .setRating(4) // Example update
                .build();

        Review result = reviewService.update(updated);
        assertNotNull(result);
        assertEquals(4, result.getRating());
        System.out.println("Updated: " + result);
    }

    @Test
    @Order(4)
    void d_delete() {
        boolean deleted = reviewService.delete(review.getReviewId());
        assertTrue(deleted);
        System.out.println("Deleted review with ID: " + review.getReviewId());
    }

    @Test
    @Order(5)
    @Transactional
    void e_getAll() {
        List<Review> allReviews = reviewService.getAll();
        assertNotNull(allReviews);
        assertFalse(allReviews.isEmpty());
        System.out.println("All reviews: " + allReviews);
    }
}
