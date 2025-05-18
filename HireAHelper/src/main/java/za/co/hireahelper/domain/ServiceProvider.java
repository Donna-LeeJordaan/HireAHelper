/* ServiceProvider.java

   Author: MT Osman (230599125)

   Date: 18 May 2025 */

package za.co.hireahelper.domain;

import java.util.List;

public class ServiceProvider extends User{
    private String profileImage;
    private String description;
    private double rate;
    private ServiceType serviceType;
    private List<Booking> bookings;
    private List<Message> messages;

    private ServiceProvider() {super();}

    private ServiceProvider(Builder builder){
        super(builder);
        this.profileImage = builder.profileImage;
        this.description = builder.description;
        this.rate = builder.rate;
        this.serviceType = builder.serviceType;
        this.bookings = builder.bookings;
        this.messages = builder.messages;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getProfileImage() {return profileImage;}

    public String getDescription() {return description;}

    public double getRate() {return rate;}

    public ServiceType getServiceType() {return serviceType;}

    public List<Booking> getBookings() {return bookings;}

    public List<Message> getMessages() {return messages;}

    @Override
    public String toString() {
        return "ServiceProvider{" +
                "profileImage='" + profileImage + '\'' +
                ", description='" + description + '\'' +
                ", rate=" + rate +
                ", serviceType=" + serviceType +
                ", bookings=" + bookings +
                ", messages=" + messages +
                '}';
    }

    public static class Builder extends User.Builder<ServiceProvider.Builder>{
        private String profileImage;
        private String description;
        private double rate;
        private ServiceType serviceType;
        private List<Booking> bookings;
        private List<Message> messages;

        public Builder setProfileImage(String profileImage) {
            this.profileImage = profileImage;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setRate(double rate) {
            this.rate = rate;
            return this;
        }

        public Builder setServiceType(ServiceType serviceType) {
            this.serviceType = serviceType;
            return this;
        }

        public Builder setBookings(List<Booking> bookings) {
            this.bookings = bookings;
            return this;
        }

        public Builder setMessages(List<Message> messages) {
            this.messages = messages;
            return this;
        }

        public Builder copy(ServiceProvider serviceProvider){
            super.copy(serviceProvider);
            this.profileImage = serviceProvider.profileImage;
            this.description = serviceProvider.description;
            this.rate = serviceProvider.rate;
            this.serviceType = serviceProvider.serviceType;
            this.bookings = serviceProvider.bookings;
            this.messages = serviceProvider.messages;
            return this;
        }

        @Override
        protected Builder self() {
            return null;
        }

        public ServiceProvider build() {return new ServiceProvider(this);}

    }
}