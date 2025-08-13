/* ReviewServiceTest.java
   Author: Donna-Lee Jordaan (230613152)
   Date: 12 August 2025
*/
package za.co.hireahelper.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.co.hireahelper.domain.*;
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

    private static Review review;
    private static Client client;
    private static ServiceProvider provider;
    private static Booking booking;

    @BeforeAll
    public static void setUp() {
        Area genericArea = new Area.Builder()
                .setAreaId("area001")
                .setName("Athlone")
                .build();

        client = new Client.Builder()
                .setUserId("user001")
                .setName("Amina")
                .setEmail("amina@example.com")
                .setArea(genericArea)
                .build();

        provider = new ServiceProvider.Builder()
                .setUserId("user007")
                .setName("Tauriq")
                .setEmail("tauriq@gmail.com")
                .setArea(genericArea)
                .build();

        booking = new Booking.Builder()
                .setBookingId("booking001")
                .setClient(client)
                .setServiceProvider(provider)
                .build();

        review = ReviewFactory.createReview(
                "R001",
                5,
                "Excellent service!",
                client,
                provider,
                booking
        );

        assertNotNull(review, "Review creation failed in setup");
    }

    @Test
    @Order(1)
    void a_create() {
        // First ensure dependent entities exist
        clientService.create(client);
        serviceProviderService.create(provider);
        bookingService.create(booking);

        Review created = reviewService.create(review);
        assertNotNull(created);
        assertEquals(review.getReviewId(), created.getReviewId());
        System.out.println("Created Review: " + created);
    }

    @Test
    @Order(2)
    void b_read() {
        Review read = reviewService.read(review.getReviewId());
        assertNotNull(read);
        assertEquals("R001", read.getReviewId());
        assertEquals(5, read.getRating());
        System.out.println("Read Review: " + read);
    }

    @Test
    @Order(3)
    void c_update() {
        Review updatedReview = new Review.Builder()
                .copy(review)
                .setRating(4)
                .setComment("Very good service")
                .build();

        Review updated = reviewService.update(updatedReview);
        assertNotNull(updated);
        assertEquals(4, updated.getRating());
        assertEquals("Very good service", updated.getComment());
        System.out.println("Updated Review: " + updated);
    }

    @Test
    @Order(4)
    void d_getAll() {
        List<Review> allReviews = reviewService.getAll();
        assertNotNull(allReviews);
        assertFalse(allReviews.isEmpty());
        System.out.println("All Reviews: " + allReviews.size());
    }

    @Test
    @Order(5)
    void e_delete() {
        boolean deleted = reviewService.delete(review.getReviewId());
        assertTrue(deleted);
        System.out.println("Deleted Review ID: " + review.getReviewId());
    }
}