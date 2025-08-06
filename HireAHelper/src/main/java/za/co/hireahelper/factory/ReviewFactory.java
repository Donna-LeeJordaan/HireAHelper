/* ReviewFactory.java
   Author: D.Jordaan (230613152)
   Date: 25 July 2025 / modified on 6 August 2025
*/

package za.co.hireahelper.factory;

import za.co.hireahelper.domain.Booking;
import za.co.hireahelper.domain.Client;
import za.co.hireahelper.domain.Review;
import za.co.hireahelper.domain.ServiceProvider;
import za.co.hireahelper.util.Helper;
import java.time.LocalDateTime;

public class ReviewFactory {
    public static Review createReview(String reviewId, int rating, String comment,
                                      LocalDateTime timeStamp, Client client,
                                      ServiceProvider serviceProvider, Booking booking) {
        if (Helper.isNullOrEmpty(reviewId) || !Helper.isValidRate(rating)
                || client == null || serviceProvider == null
                || timeStamp == null || booking == null) {
            return null;
        }

        // Validate timestamp is not in the future
        if (timeStamp.isAfter(LocalDateTime.now())) {
            return null;
        }

        // Validate rating is within allowed range (1-5)
        if (rating < 1 || rating > 5) {
            return null;
        }

        return Review.builder()
                .setReviewId(reviewId)
                .setRating(rating)
                .setComment(comment)
                .setTimeStamp(timeStamp)
                .setClient(client)
                .setServiceProvider(serviceProvider)
                .setBooking(booking)
                .build();
    }

    // Overloaded method for backward compatibility
    public static Review createReview(String reviewId, int rating, String comment,
                                      LocalDateTime timeStamp, Client client,
                                      ServiceProvider serviceProvider) {
        return createReview(reviewId, rating, comment, timeStamp, client, serviceProvider, null);
    }
}