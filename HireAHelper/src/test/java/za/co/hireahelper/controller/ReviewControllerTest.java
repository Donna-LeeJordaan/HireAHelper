/* ReviewControllerTest.java
   Author: D.Jordaan (230613152)
   Date: 25 July 2025
*/

package za.co.hireahelper.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import za.co.hireahelper.domain.Review;
import za.co.hireahelper.factory.ReviewFactory;
import za.co.hireahelper.domain.Client;
import za.co.hireahelper.domain.ServiceProvider;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.MethodName.class)
class ReviewControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private static Review review;
    private static Client client;
    private static ServiceProvider serviceProvider;

    private String getBaseUrl() {
        return "http://localhost:" + port + "/review";
    }

    @BeforeAll
    public static void setUp() {
        client = new Client.Builder()
                .setUserId("client123")
                .setName("Test Client")
                .build();

        serviceProvider = new ServiceProvider.Builder()
                .setUserId("sp123")
                .setName("Test Provider")
                .build();

        review = ReviewFactory.createReview(
                "review123",
                5,
                "Excellent service!",
                LocalDateTime.now(),
                client,
                serviceProvider
        );
        assertNotNull(review);
    }

    @Test
    void a_create() {
        String url = getBaseUrl() + "/create";
        ResponseEntity<Review> postResponse = restTemplate.postForEntity(url, review, Review.class);

        assertNotNull(postResponse);
        assertEquals(HttpStatus.OK, postResponse.getStatusCode());

        Review createdReview = postResponse.getBody();
        assertNotNull(createdReview);
        assertEquals(review.getReviewId(), createdReview.getReviewId());
        assertEquals(review.getRating(), createdReview.getRating());

        System.out.println("Created review: " + createdReview);
    }

    @Test
    void b_read() {
        String url = getBaseUrl() + "/read/" + review.getReviewId();
        ResponseEntity<Review> response = restTemplate.getForEntity(url, Review.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        Review retrievedReview = response.getBody();
        assertNotNull(retrievedReview);
        assertEquals(review.getReviewId(), retrievedReview.getReviewId());
        assertEquals(review.getComment(), retrievedReview.getComment());

        System.out.println("Retrieved review: " + retrievedReview);
    }

    @Test
    void c_update() {
        Review updatedReview = new Review.Builder()
                .copy(review)
                .setRating(4)
                .setComment("Very good service")
                .build();

        String url = getBaseUrl() + "/update";
        restTemplate.put(url, updatedReview);

        ResponseEntity<Review> response = restTemplate.getForEntity(
                getBaseUrl() + "/read/" + updatedReview.getReviewId(),
                Review.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());

        Review modifiedReview = response.getBody();
        assertNotNull(modifiedReview);
        assertEquals(4, modifiedReview.getRating());
        assertEquals("Very good service", modifiedReview.getComment());

        System.out.println("Updated review: " + modifiedReview);
    }

    @Test
    void d_getAll() {
        String url = getBaseUrl() + "/all";
        ResponseEntity<Review[]> response = restTemplate.getForEntity(url, Review[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        Review[] reviews = response.getBody();
        assertNotNull(reviews);
        assertTrue(reviews.length > 0);

        System.out.println("All reviews:");
        for (Review r : reviews) {
            System.out.println(r);
        }
    }

    @Test
    void e_delete() {
        String url = getBaseUrl() + "/delete/" + review.getReviewId();
        restTemplate.delete(url);

        ResponseEntity<Review> response = restTemplate.getForEntity(
                getBaseUrl() + "/read/" + review.getReviewId(),
                Review.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNull(response.getBody());

        System.out.println("Deleted review with ID: " + review.getReviewId());
    }
}