/* BookingFactory.java
   Author: Donna-Lee Jordaan (230613152)
   Date: 25 July 2025 / modified 11 August 2025
*/

package za.co.hireahelper.factory;

import za.co.hireahelper.domain.*;
import za.co.hireahelper.util.Helper;
import java.time.LocalDate;
import java.util.List;

public class BookingFactory {

    public static Booking createBooking(String bookingId, LocalDate serviceDate,String timeSlot, String status, String notes,
                                        Client client, ServiceProvider serviceProvider) {

        // Validate inputs
        if (Helper.isNullOrEmpty(bookingId) || serviceDate == null || Helper.isNullOrEmpty(timeSlot) ||
                Helper.isNullOrEmpty(status) || client == null || serviceProvider == null) {
            return null;
        }

        return new Booking.Builder()
                .setBookingId(bookingId)
                .setServiceDate(serviceDate)
                .setTimeSlot(timeSlot)
                .setStatus(status)
                .setNotes(notes) // notes can be null or empty
                .setClient(client)
                .setServiceProvider(serviceProvider)
                .build();
    }
}
