/* ReviewServiceTest.java
   Author: D.Jordaan (230613152)
   Date: 25 July 2025 /modified on 6 August 2025
*/

package za.co.hireahelper.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import za.co.hireahelper.domain.*;
import za.co.hireahelper.factory.ReviewFactory;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
public class ReviewServiceTest {

    @Autowired
    private ReviewService service;

    @Autowired
    private ClientService clientService;

    @Autowired
    private ServiceProviderService serviceProviderService;

    @Autowired
    private BookingService bookingService;

    private static Review review;

    private static final Client client = new Client.Builder()
            .setUserId("client123")
            .setName("Test Client")
            .build();

    private static final ServiceProvider serviceProvider = new ServiceProvider.Builder()
            .setUserId("sp123")
            .setName("Test Provider")
            .build();

    private static final Booking booking = new Booking.Builder()
            .setBookingId("booking123")
            .setServiceDate(new Date(System.currentTimeMillis() + 86400000))
            .setStatus("Confirmed")
            .build();

    @BeforeAll
    static void setUp() {
        review = ReviewFactory.createReview(
                "review123",
                5,
                "Excellent service!",
                LocalDateTime.now().minusDays(1), // Past date
                client,
                serviceProvider,
                booking
        );
        assertNotNull(review, "Review creation failed");
    }

    @BeforeEach
    public void setupDependencies() {
        if (clientService.read(client.getUserId()) == null) {
            clientService.create(client);
        }
        if (serviceProviderService.read(serviceProvider.getUserId()) == null) {
            serviceProviderService.create(serviceProvider);
        }
        if (bookingService.read(booking.getBookingId()) == null) {
            bookingService.create(booking);
        }
    }

    @Test
    @Transactional
    void a_create() {
        Review created = service.create(review);
        assertNotNull(created);
        assertEquals(review.getReviewId(), created.getReviewId());
        System.out.println("Created: " + created);
    }

    @Test
    @Transactional
    void b_read() {
        Review read = service.read(review.getReviewId());
        assertNotNull(read);
        assertEquals(review.getReviewId(), read.getReviewId());
        System.out.println("Read: " + read);
    }

    @Test
    @Transactional
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
        System.out.println("Updated: " + result);
    }

    @Test
    @Transactional
    void d_getAll() {
        List<Review> allReviews = service.getAll();
        assertNotNull(allReviews);
        assertFalse(allReviews.isEmpty());
        System.out.println("All reviews: " + allReviews);
    }

    @Test
    @Transactional
    void e_delete() {
        boolean deleted = service.delete(review.getReviewId());
        assertTrue(deleted);
        System.out.println("Deleted: " + deleted);
    }
}