/* BookingService.java
   Author: Donna-Lee Jordaan (230613152)
   Date: 25 July 2025 / modified 11 August 2025
*/

package za.co.hireahelper.service;

import za.co.hireahelper.domain.Booking;
import java.util.List;

public interface BookingService extends IService<Booking, String> {
    List<Booking> getAll();

    // New method to get bookings by client ID
    List<Booking> getBookingsByClientId(String clientId);

    // New method to get bookings by service provider ID
    List<Booking> getBookingsByServiceProviderId(String serviceProviderId);
}
