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
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
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

    @Autowired
    private ServiceTypeService serviceTypeService;

    private Review review;
    private Client client;
    private ServiceProvider provider;
    private Booking booking;
    private ServiceType serviceType;
    private Area genericArea;

    @BeforeAll
    public void setUp() {

        genericArea = new Area.Builder()
                .setAreaId("area001")
                .setName("Athlone")
                .build();

        serviceType = new ServiceType.Builder()
                .setTypeId("type01")
                .setTypeName("Plumber")
                .build();
        serviceTypeService.create(serviceType);

        client = new Client.Builder()
                .setUserId("user001")
                .setName("Amina")
                .setEmail("amina@example.com")
                .setArea(genericArea)
                .build();
        clientService.create(client);

        provider = new ServiceProvider.Builder()
                .setUserId("user007")
                .setName("Tauriq")
                .setEmail("tauriq@gmail.com")
                .setArea(genericArea)
                .setServiceType(serviceType)
                .build();
        serviceProviderService.create(provider);

        booking = new Booking.Builder()
                .setBookingId("booking001")
                .setClient(client)
                .setServiceProvider(provider)
                .build();
        bookingService.create(booking);

        review = ReviewFactory.createReview(
                "R001",
                5,
                "Excellent service!",
                client,
                provider,
                booking
        );
    }

    @Test
    @Order(1)
    void a_create() {
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
