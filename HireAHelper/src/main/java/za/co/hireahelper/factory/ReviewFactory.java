/* ReviewFactory.java

   Author: D.Jordaan (230613152)

   Date: 18 May 2025 */

package za.co.hireahelper.factory;

import za.co.hireahelper.domain.Client;
import za.co.hireahelper.domain.Review;
import za.co.hireahelper.domain.ServiceProvider;
import za.co.hireahelper.util.Helper;
import java.time.LocalDateTime;


public class ReviewFactory {
    public static Review CreateReview(String reviewId, int rating, String comment, LocalDateTime timeStamp, Client client, ServiceProvider serviceProvider){
        if (Helper.isNullOrEmpty(reviewId) || !Helper.isValidRate(rating)
                || client == null || serviceProvider == null || timeStamp == null) {
            return null;
        }

        // Add future timestamp validation
        if (timeStamp.isAfter(LocalDateTime.now())) {
            return null;
        }

        return Review.builder()
                .setReviewId(reviewId)
                .setRating(rating)
                .setComment(comment)
                .setClient(client)
                .setServiceProvider(serviceProvider)
                .setTimeStamp(timeStamp)
                .build();
    }
}


