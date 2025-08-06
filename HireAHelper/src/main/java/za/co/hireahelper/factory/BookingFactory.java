/* BookingFactory.java
   Author: D.Jordaan (230613152)
   Date: 18 May 2025 / modified on 6 August 2025
*/

package za.co.hireahelper.factory;

import za.co.hireahelper.domain.Booking;
import za.co.hireahelper.domain.Client;
import za.co.hireahelper.domain.ServiceProvider;
import za.co.hireahelper.domain.Review;
import za.co.hireahelper.util.Helper;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class BookingFactory {
    public static Booking createBooking(String bookingId, Date serviceDate, String status,
                                        String notes, Client client, ServiceProvider serviceProvider,
                                        List<Review> reviews) {
        if (Helper.isNullOrEmpty(bookingId) || serviceDate == null || Helper.isNullOrEmpty(status)
                || client == null || serviceProvider == null) {
            return null;
        }

        // Validate date is not in the past
        if (serviceDate.before(new Date())) {
            return null;
        }

        // Validate status is one of allowed values
        if (!Arrays.asList("Confirmed", "Pending", "Cancelled", "Completed").contains(status)) {
            return null;
        }

        return Booking.builder()
                .setBookingId(bookingId)
                .setServiceDate(serviceDate)
                .setStatus(status)
                .setNotes(notes)
                .setClient(client)
                .setServiceProvider(serviceProvider)
                .setReviews(reviews)
                .build();
    }

    // Overloaded method without reviews for convenience
    public static Booking createBooking(String bookingId, Date serviceDate, String status,
                                        String notes, Client client, ServiceProvider serviceProvider) {
        return createBooking(bookingId, serviceDate, status, notes, client, serviceProvider, null);
    }
}