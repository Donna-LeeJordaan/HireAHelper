package za.co.hireahelper.factory;

import za.co.hireahelper.domain.ServiceProvider;
import za.co.hireahelper.domain.ServiceType;
import za.co.hireahelper.domain.Booking;
import za.co.hireahelper.domain.Message;
import za.co.hireahelper.util.Helper;

import java.util.List;

public class ServiceProviderFactory {

    public static ServiceProvider createServiceProvider(String profileImage, String description, double rate, ServiceType serviceType,
                                                        List<Booking> bookings, List<Message> messages) {

        if (Helper.isNullOrEmpty(profileImage) || Helper.isNullOrEmpty(description) || !Helper.isValidRate(rate) || serviceType == null) {
            return null;
        }

        return ServiceProvider.builder()
                .setProfileImage(profileImage)
                .setDescription(description)
                .setRate(rate)
                .setServiceType(serviceType)
                .setBookings(bookings)
                .setMessages(messages)
                .build();
    }
}