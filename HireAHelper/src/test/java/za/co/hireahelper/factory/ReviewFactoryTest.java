/* ReviewFactoryTest.java

   Author: D.Jordaan (230613152)

   Date: 19 May 2025 */

package za.co.hireahelper.factory;

import org.junit.jupiter.api.*;
import za.co.hireahelper.domain.*;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ReviewFactoryTest {

    private static Client client1 = new Client.Builder()
            .setUserId("user001")
            .setName("Jannie")
            .setEmail("jannie@example.com")
            .setPassword("password123")
            .setMobileNumber("0823456789")
            .build();

    private static ServiceProvider serviceProvider1 = new ServiceProvider.Builder()
            .setUserId("SP1")
            .setName("Tauriq Osman")
            .setEmail("moegamattauriqosman@gmail.com")
            .setPassword("Tauriq04")
            .setMobileNumber("0611234567")
            .setProfileImage("tauriq.jpeg")
            .setDescription("Skilled Gardener with 15 years experience")
            .setRate(350)
            .build();

    private static LocalDateTime now = LocalDateTime.now();
    private static LocalDateTime pastTime = now.minusDays(1);
    private static LocalDateTime futureTime = now.plusDays(1);

    private static Review review1 = ReviewFactory.CreateReview(
            "R1", 5, "Excellent service!", now, client1, serviceProvider1);

    private static Review review2 = ReviewFactory.CreateReview(
            "R2", 4, "Good work, but was late", pastTime, client1, serviceProvider1);

    @Test
    @Order(1)
    public void testCreateReview() {
      assertNotNull(review1);
        System.out.println(review1);
    }

    @Test
    @Order(2)
    public void testCreateReviewWithAllAttributes() {
        assertNotNull(review2);
        System.out.println(review2);
    }

    @Test
    @Order(3)
    public void testCreateReviewWithInvalidRating() {
        Review review3 = ReviewFactory.CreateReview(
                "R3", 6, "Amazing service", now, client1, serviceProvider1);
        assertNotNull(review3);
        System.out.println(review3);
    }

    @Test
    @Order(4)
    public void testCreateReviewWithFutureTimestamp() {
        Review review4 = ReviewFactory.CreateReview(
                "R4", 3, "Average service", futureTime, client1, serviceProvider1);
       assertNotNull(review4);
        System.out.println(review4);
    }

    @Test
    @Order(5)
    public void testCreateReviewWithNullFields() {
        Review review5 = ReviewFactory.CreateReview(
                null, 0, null, null, null, null);
        assertNull(review5);
        System.out.println(review5);
    }
}
