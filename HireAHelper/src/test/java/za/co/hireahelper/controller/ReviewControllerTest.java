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
import za.co.hireahelper.domain.Client;
import za.co.hireahelper.domain.Review;
import za.co.hireahelper.domain.ServiceProvider;
import za.co.hireahelper.factory.ReviewFactory;
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
    static void setUp() {
        client = new Client.Builder()
                .setUserId("client-001")
                .setName("Test Client")
                .build();

        serviceProvider = new ServiceProvider.Builder()
                .setUserId("sp-001")
                .setName("Test Provider")
                .build();

        review = ReviewFactory.CreateReview(
                "rev-001",
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

        Review saved = postResponse.getBody();
        assertNotNull(saved);
        assertEquals(review.getReviewId(), saved.getReviewId());

        System.out.println("Created: " + saved);
    }

    @Test
    void b_read() {
        String url = getBaseUrl() + "/read/" + review.getReviewId();
        ResponseEntity<Review> response = restTemplate.getForEntity(url, Review.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        Review read = response.getBody();
        assertNotNull(read);
        assertEquals(review.getReviewId(), read.getReviewId());

        System.out.println("Read: " + read);
    }

    @Test
    void c_update() {
        Review updated = new Review.Builder()
                .copy(review)
                .setRating(4)
                .setComment("Updated comment")
                .build();

        String url = getBaseUrl() + "/update";
        restTemplate.put(url, updated);

        ResponseEntity<Review> response = restTemplate.getForEntity(
                getBaseUrl() + "/read/" + updated.getReviewId(),
                Review.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Review afterUpdate = response.getBody();
        assertNotNull(afterUpdate);
        assertEquals(4, afterUpdate.getRating());
        assertEquals("Updated comment", afterUpdate.getComment());

        // keep latest
        review = afterUpdate;

        System.out.println("Updated: " + afterUpdate);
    }

    @Test
    void d_getAll() {
        String url = getBaseUrl() + "/all";
        ResponseEntity<Review[]> response = restTemplate.getForEntity(url, Review[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        Review[] all = response.getBody();
        assertNotNull(all);
        assertTrue(all.length > 0);

        System.out.println("All Reviews:");
        for (Review r : all) {
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

        System.out.println("Deleted: true");
    }
}
