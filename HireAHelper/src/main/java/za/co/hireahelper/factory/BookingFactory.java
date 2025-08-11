/* BookingFactory.java
   Author: Donna-Lee Jordaan (230613152)
   Date: 25 July 2025 / modified 11 August 2025
*/

package za.co.hireahelper.factory;

import za.co.hireahelper.domain.Booking;
import za.co.hireahelper.domain.Client;
import za.co.hireahelper.domain.Review;
import za.co.hireahelper.domain.ServiceProvider;
import za.co.hireahelper.util.Helper;

import java.util.Date;
import java.util.List;

public class BookingFactory {

    public static Booking createBooking(String bookingId, Date serviceDate, String status, String notes,
                                        Client client, ServiceProvider serviceProvider, List<Review> reviews) {

        // Validate inputs
        if (Helper.isNullOrEmpty(bookingId) || serviceDate == null ||
                Helper.isNullOrEmpty(status) || client == null || serviceProvider == null) {
            return null;
        }

        return new Booking.Builder()
                .setBookingId(bookingId)
                .setServiceDate(serviceDate)
                .setStatus(status)
                .setNotes(notes) // notes can be null or empty
                .setClient(client)
                .setServiceProvider(serviceProvider)
                .setReviews(reviews)
                .build();
    }
}
