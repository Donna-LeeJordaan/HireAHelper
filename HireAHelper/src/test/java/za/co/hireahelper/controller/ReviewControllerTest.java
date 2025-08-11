/* ReviewControllerTest.java
   Author: Donna-Lee Jordaan (230613152)
   Date: 25 July 2025 / modified 11 August 2025
*/

package za.co.hireahelper.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import za.co.hireahelper.domain.Booking;
import za.co.hireahelper.domain.Client;
import za.co.hireahelper.domain.Review;
import za.co.hireahelper.domain.ServiceProvider;
import za.co.hireahelper.factory.ReviewFactory;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.MethodName.class)
class ReviewControllerTest {

    private static Review review;

    @Autowired
    private TestRestTemplate restTemplate;

    private final String BASE_URL = "/review";

    // Simple placeholders for dependent objects (replace with factory-created if available)
    private static Client client;
    private static ServiceProvider serviceProvider;
    private static Booking booking;

    @BeforeAll
    static void setUp() {
        // Create minimal stub objects for Client, ServiceProvider, Booking
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
        assertNotNull(review);  // sanity check
    }

    @Test
    void a_create() {
        String url = BASE_URL + "/create";
        ResponseEntity<Review> response = restTemplate.postForEntity(url, review, Review.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        System.out.println("Created: " + response.getBody());
    }

    @Test
    void b_read() {
        String url = BASE_URL + "/read/" + review.getReviewId();
        ResponseEntity<Review> response = restTemplate.getForEntity(url, Review.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        System.out.println("Read: " + response.getBody());
    }

    @Test
    void c_update() {
        String url = BASE_URL + "/update";

        Review updated = new Review.Builder()
                .copy(review)
                .setRating(4)
                .setComment("Good service, but could improve timing.")
                .build();

        HttpEntity<Review> requestEntity = new HttpEntity<>(updated);
        ResponseEntity<Review> response = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Review.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        System.out.println("Updated: " + response.getBody());
    }

    @Test
    void d_getAll() {
        String url = BASE_URL + "/all";
        ResponseEntity<Review[]> response = restTemplate.getForEntity(url, Review[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        System.out.println("All Reviews:");
        for (Review r : response.getBody()) {
            System.out.println(r);
        }
    }

    @Test
    void e_delete() {
        String url = BASE_URL + "/delete/" + review.getReviewId();
        restTemplate.delete(url);
        System.out.println("Deleted Review with ID: " + review.getReviewId());
    }
}
