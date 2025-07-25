/* ServiceProviderFactory.java

   Author: MT Osman (230599125)

   Date: 18 May 2025 */

package za.co.hireahelper.factory;

import za.co.hireahelper.domain.*;
import za.co.hireahelper.util.Helper;
import java.util.List;

public class ServiceProviderFactory {

    public static ServiceProvider createServiceProvider(String userId, String name, String email, String password, String mobileNumber, Area area, String profileImage,
                                                        String description, double rate, ServiceType serviceType, List<Booking> bookings, List<Message> messages) {

        if (Helper.isNullOrEmpty(userId) || Helper.isNullOrEmpty(name) || Helper.isNullOrEmpty(email) ||Helper.isNullOrEmpty(password) ||
                Helper.isNullOrEmpty(mobileNumber) ||Helper.isNullOrEmpty(profileImage) || Helper.isNullOrEmpty(description) ||
                rate <= 0 || area ==null || serviceType == null || bookings == null || messages == null) {
            return null;
        }

        if (!Helper.isValidEmail(email)) {
            return null;
        }

        if (!Helper.isValidMobileNumber(mobileNumber)) {
            return null;
        }

        return new ServiceProvider.Builder()
                .setUserId(userId)
                .setName(name)
                .setEmail(email)
                .setPassword(password)
                .setMobileNumber(mobileNumber)
                .setArea(area)
                .setProfileImage(profileImage)
                .setDescription(description)
                .setRate(rate)
                .setServiceType(serviceType)
                .setBookings(bookings)
                .setMessages(messages)
                .build();
    }

}