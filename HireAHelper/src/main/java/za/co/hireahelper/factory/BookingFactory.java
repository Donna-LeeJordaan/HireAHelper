/* BookingFactory.java

   Author: D.Jordaan (230613152)

   Date: 18 May 2025 */


package za.co.hireahelper.factory;

import za.co.hireahelper.domain.Booking;
import za.co.hireahelper.domain.Client;
import za.co.hireahelper.domain.ServiceProvider;
import za.co.hireahelper.util.Helper;

import java.util.Date;

public class BookingFactory {
    public static Booking Createbooking(String bookingId, Date serviceDate, String status, String notes, Client client, ServiceProvider serviceProvider) {
        if (Helper.isNullOrEmpty(bookingId) || serviceDate == null || Helper.isNullOrEmpty(status)
                || client == null || serviceProvider == null) {
            return null;
        }

        return Booking.builder()
                .setBookingId(bookingId)
                .setServiceDate(serviceDate)
                .setStatus(status)
                .setNotes(notes)
                .setClient(client)
                .setServiceProvider(serviceProvider)
                .build();
    }
}

