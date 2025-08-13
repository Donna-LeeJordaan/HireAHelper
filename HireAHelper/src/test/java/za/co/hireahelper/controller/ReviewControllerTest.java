/* ReviewControllerTest.java
   Author: Donna-Lee Jordaan (230613152)
   Date: 12 August 2025
*/
package za.co.hireahelper.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import za.co.hireahelper.domain.*;
import za.co.hireahelper.factory.ReviewFactory;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ReviewControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private static Review review;
    private static Client client;
    private static ServiceProvider provider;
    private static Booking booking;
    private final String BASE_URL = "/hireahelper/reviews";

    @BeforeAll
    static void setup() {
        Area area = new Area.Builder()
                .setAreaId("area001")
                .setName("Athlone")
                .build();

        client = new Client.Builder()
                .setUserId("user001")
                .setName("Amina")
                .setEmail("amina@example.com")
                .setArea(area)
                .build();

        provider = new ServiceProvider.Builder()
                .setUserId("user007")
                .setName("Tauriq")
                .setEmail("tauriq@gmail.com")
                .setArea(area)
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
    }

    @Test
    @Order(1)
    void a_create() {
        ResponseEntity<Review> response = restTemplate.postForEntity(
                BASE_URL + "/create",
                review,
                Review.class
        );

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(review.getReviewId(), response.getBody().getReviewId());

        // Update with persisted version
        review = response.getBody();
        System.out.println("Created Review: " + review);
    }

    @Test
    @Order(2)
    void b_read() {
        ResponseEntity<Review> response = restTemplate.getForEntity(
                BASE_URL + "/" + review.getReviewId(),
                Review.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(5, response.getBody().getRating());
        System.out.println("Retrieved Review: " + response.getBody());
    }

    @Test
    @Order(3)
    void c_update() {
        Review updatedReview = new Review.Builder()
                .copy(review)
                .setRating(4)
                .setComment("Very good service with minor improvements needed")
                .build();

        HttpEntity<Review> request = new HttpEntity<>(updatedReview);
        ResponseEntity<Review> response = restTemplate.exchange(
                BASE_URL + "/update",
                HttpMethod.PUT,
                request,
                Review.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(4, response.getBody().getRating());

        review = response.getBody();
        System.out.println("Updated Review: " + review);
    }

    @Test
    @Order(4)
    void d_getAll() {
        ResponseEntity<Review[]> response = restTemplate.getForEntity(
                BASE_URL + "/all",
                Review[].class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().length > 0);

        System.out.println("All Reviews (" + response.getBody().length + "):");
        for (Review r : response.getBody()) {
            System.out.println(r);
        }
    }

    @Test
    @Order(5)
    void e_delete() {
        restTemplate.delete(BASE_URL + "/delete/" + review.getReviewId());

        // Verify deletion
        ResponseEntity<Review> response = restTemplate.getForEntity(
                BASE_URL + "/" + review.getReviewId(),
                Review.class
        );
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        System.out.println("Deleted Review with ID: " + review.getReviewId());
    }
}