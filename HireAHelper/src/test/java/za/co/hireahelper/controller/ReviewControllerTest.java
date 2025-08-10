/* ReviewControllerTest.java
   Author: D.Jordaan (230613152)
   Date: 25 July 2025 / modified on 6 August 2025
*/

package za.co.hireahelper.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import za.co.hireahelper.domain.*;
import za.co.hireahelper.factory.ReviewFactory;
import java.time.LocalDateTime;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.MethodName.class)
class ReviewControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private static Review review;
    private static Client client;
    private static ServiceProvider serviceProvider;
    private static Booking booking;

    private static final String BASE_URL = "http://localhost:8080/HireAHelper/review";

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

        booking = new Booking.Builder()
                .setBookingId("booking123")
                .setServiceDate(new Date(System.currentTimeMillis() + 86400000))
                .setStatus("Confirmed")
                .build();

        review = ReviewFactory.createReview(
                "review123",
                5,
                "Excellent service!",
                LocalDateTime.now(),
                client,
                serviceProvider,
                booking
        );
        assertNotNull(review);
    }

    @Test
    void a_create() {
        ResponseEntity<Review> postResponse = restTemplate.postForEntity(BASE_URL, review, Review.class);

        assertAll(
                () -> assertNotNull(postResponse),
                () -> assertEquals(HttpStatus.OK, postResponse.getStatusCode()),
                () -> assertNotNull(postResponse.getBody()),
                () -> assertEquals(review.getReviewId(), postResponse.getBody().getReviewId())
        );
        System.out.println("Created: " + postResponse.getBody());
    }

    @Test
    void b_read() {
        String url = BASE_URL + "/" + review.getReviewId();
        ResponseEntity<Review> response = restTemplate.getForEntity(url, Review.class);

        assertAll(
                () -> assertEquals(HttpStatus.OK, response.getStatusCode()),
                () -> assertNotNull(response.getBody()),
                () -> assertEquals(review.getComment(), response.getBody().getComment())
        );
        System.out.println("Read: " + response.getBody());
    }

    @Test
    void c_update() {
        Review updatedReview = new Review.Builder()
                .copy(review)
                .setRating(4)
                .setComment("Very good service")
                .build();

        restTemplate.put(BASE_URL, updatedReview);

        ResponseEntity<Review> response = restTemplate.getForEntity(
                BASE_URL + "/" + updatedReview.getReviewId(),
                Review.class
        );

        assertAll(
                () -> assertEquals(HttpStatus.OK, response.getStatusCode()),
                () -> assertNotNull(response.getBody()),
                () -> assertEquals(4, response.getBody().getRating())
        );
        System.out.println("Updated: " + response.getBody());
    }

    @Test
    void d_getAll() {
        ResponseEntity<Review[]> response = restTemplate.getForEntity(BASE_URL, Review[].class);

        assertAll(
                () -> assertEquals(HttpStatus.OK, response.getStatusCode()),
                () -> assertNotNull(response.getBody()),
                () -> assertTrue(response.getBody().length > 0)
        );
        System.out.println("All Reviews:");
        for (Review r : response.getBody()) {
            System.out.println(r);
        }
    }

    @Test
    void e_getByBooking() {
        String url = BASE_URL + "/booking/" + booking.getBookingId();
        ResponseEntity<Review[]> response = restTemplate.getForEntity(url, Review[].class);

        assertAll(
                () -> assertEquals(HttpStatus.OK, response.getStatusCode()),
                () -> assertNotNull(response.getBody()),
                () -> assertTrue(response.getBody().length > 0)
        );
        System.out.println("Booking Reviews:");
        for (Review r : response.getBody()) {
            System.out.println(r);
        }
    }

    @Test
    void f_delete() {
        String url = BASE_URL + "/" + review.getReviewId();
        restTemplate.delete(url);

        ResponseEntity<Review> response = restTemplate.getForEntity(url, Review.class);
        assertNull(response.getBody());
        System.out.println("Deleted: " + review.getReviewId());
    }
}