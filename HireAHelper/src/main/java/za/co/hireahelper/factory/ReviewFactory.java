/* ReviewFactory.java
   Author: Donna-Lee Jordaan (230613152)
   Date:25 July 2025 / modified 11 August 2025
*/

package za.co.hireahelper.factory;

import za.co.hireahelper.domain.*;
import za.co.hireahelper.util.Helper;

import java.util.Date;

public class ReviewFactory {

    public static Review createReview(String reviewId, int rating, String comment,
                                      Client client, ServiceProvider serviceProvider, Booking booking) {

        if (Helper.isNullOrEmpty(reviewId) || rating < 1 || rating > 5 ||
                Helper.isNullOrEmpty(String.valueOf(comment)) || client == null ||
                serviceProvider == null || booking == null) {
            return null;
        }

        return new Review.Builder()
                .setReviewId(reviewId)
                .setRating(rating)
                .setComment(comment)
                .setTimeStamp(new Date())  // use java.util.Date here
                .setClient(client)
                .setServiceProvider(serviceProvider)
                .setBooking(booking)
                .build();
    }
}
