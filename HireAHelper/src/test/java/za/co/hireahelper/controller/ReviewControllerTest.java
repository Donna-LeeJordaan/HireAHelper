/* ReviewControllerTest.java

   Author: D.Jordaan (230613152)

   Date: 13 July 2025 */

package za.co.hireahelper.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import za.co.hireahelper.domain.Review;
import za.co.hireahelper.domain.Client;
import za.co.hireahelper.domain.ServiceProvider;
import za.co.hireahelper.factory.ReviewFactory;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.MethodName.class)
class ReviewControllerTest {

    private static Review review;
    private static Client client;
    private static ServiceProvider serviceProvider;
    private static final String BASE_URL = "http://localhost:8080/HireAHelper/review";

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeAll
    static void setUp() {
        client = new Client.Builder()
                .setUserId("client-001")
                .setName("Test Client")
                .build();

        serviceProvider = new ServiceProvider.Builder()
                .setUserId("provider-001")
                .setName("Test Provider")
                .build();

        review = ReviewFactory.CreateReview(
                "REV-001",
                5,
                "Excellent service!",
                LocalDateTime.now().minusDays(1), // Past date
                client,
                serviceProvider
        );
    }

    @Test
    @Order(1)
    void a_createReview() {
        ResponseEntity<Review> response = restTemplate.postForEntity(
                BASE_URL + "/create",
                review,
                Review.class
        );
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("REV-001", response.getBody().getReviewId());
    }

    @Test
    @Order(2)
    void b_readReview() {
        ResponseEntity<Review> response = restTemplate.getForEntity(
                BASE_URL + "/read/" + review.getReviewId(),
                Review.class
        );
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(5, response.getBody().getRating());
    }

    @Test
    @Order(3)
    void c_updateReview() {
        // Create a modified copy of the review
        Review updatedReview = new Review.Builder()
                .copy(review)
                .setComment("Updated review comment")
                .setRating(4)
                .build();

        // Send the update request
        restTemplate.put(BASE_URL + "/update", updatedReview);

        // Verify the update
        ResponseEntity<Review> response = restTemplate.getForEntity(
                BASE_URL + "/read/" + updatedReview.getReviewId(),
                Review.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(4, response.getBody().getRating());
        assertEquals("Updated review comment", response.getBody().getComment());
    }

    @Test
    @Order(4)
    void d_getAllReviews() {
        ResponseEntity<Review[]> response = restTemplate.getForEntity(
                BASE_URL + "/all",
                Review[].class
        );
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().length > 0);
    }

    @Test
    @Order(5)
    void e_deleteReview() {
        restTemplate.delete(BASE_URL + "/delete/" + review.getReviewId());
        ResponseEntity<Review> response = restTemplate.getForEntity(
                BASE_URL + "/read/" + review.getReviewId(),
                Review.class
        );
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @Order(6)
    void createReview_InvalidRating_ShouldFail() {
        Review invalidReview = new Review.Builder()
                .copy(review)
                .setReviewId("REV-002")
                .setRating(6) // Invalid rating
                .build();

        ResponseEntity<String> response = restTemplate.postForEntity(
                BASE_URL + "/create",
                invalidReview,
                String.class
        );
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @Order(7)
    void createReview_FutureDate_ShouldFail() {
        Review invalidReview = new Review.Builder()
                .copy(review)
                .setReviewId("REV-003")
                .setTimeStamp(LocalDateTime.now().plusDays(1)) // Future date
                .build();

        ResponseEntity<String> response = restTemplate.postForEntity(
                BASE_URL + "/create",
                invalidReview,
                String.class
        );
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}