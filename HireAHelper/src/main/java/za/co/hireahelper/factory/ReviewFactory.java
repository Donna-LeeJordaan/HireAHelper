/* ReviewFactory.java
   Author: D.Jordaan (230613152)
   Date: 25 July 2025
*/

package za.co.hireahelper.factory;

import za.co.hireahelper.domain.Client;
import za.co.hireahelper.domain.Review;
import za.co.hireahelper.domain.ServiceProvider;
import za.co.hireahelper.util.Helper;
import java.time.LocalDateTime;

public class ReviewFactory {
    public static Review createReview(String reviewId, int rating, String comment,
                                      LocalDateTime timeStamp, Client client, ServiceProvider serviceProvider) {
        if (Helper.isNullOrEmpty(reviewId) || !Helper.isValidRate(rating)
                || client == null || serviceProvider == null || timeStamp == null) {
            return null;
        }

        // Validate timestamp is not in the future
        if (timeStamp.isAfter(LocalDateTime.now())) {
            return null;
        }

        return Review.builder()
                .setReviewId(reviewId)
                .setRating(rating)
                .setComment(comment)
                .setTimeStamp(timeStamp)
                .setClient(client)
                .setServiceProvider(serviceProvider)
                .build();
    }
}