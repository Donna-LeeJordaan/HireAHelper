/* ServiceProvider.java
   Author: MT Osman (230599125)
   Date: 18 May 2025
*/

package za.co.hireahelper.domain;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class ServiceProvider extends User {

    private String profileImage;
    private String description;
    private double rate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id", nullable = false)
    private ServiceType serviceType;  //GET BACK TO THIS

    @OneToMany(mappedBy = "serviceProvider", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Booking> bookings;

    @OneToMany(mappedBy = "serviceProvider", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Message> messages;

    @OneToMany(mappedBy = "serviceProvider", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Review> reviews;

    protected ServiceProvider() {
        super();
    }

    private ServiceProvider(Builder builder) {
        super(builder);
        this.profileImage = builder.profileImage;
        this.description = builder.description;
        this.rate = builder.rate;
        this.serviceType = builder.serviceType;
        this.bookings = builder.bookings;
        this.messages = builder.messages;
        this.reviews = builder.reviews;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public String getDescription() {
        return description;
    }

    public double getRate() {
        return rate;
    }


    public ServiceType getServiceType() {
        return serviceType;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    @Override
    public String toString() {
        return "ServiceProvider{" +
                super.toString() +
                "userId='" + getUserId() + '\'' +
                ", name='" + getName() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", password='" + getPassword() + '\'' +
                ", mobileNumber='" + getMobileNumber() + '\'' +
                ", areaId='" + (getArea() != null ? getArea().getAreaId() : "null") + '\'' +
                ", profileImage='" + getProfileImage() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", rate=R" + getRate() +
                ", serviceType=" + (serviceType != null ? serviceType.getTypeId() : "null") +
                ", bookings=" + (bookings != null ? bookings.size() : 0) +
                ", messages=" + (messages != null ? messages.size() : 0) +
                ", reviews=" + (reviews != null ? reviews.size() : 0) +
                '}';
    }

    public static class Builder extends User.Builder<ServiceProvider.Builder> {
        private String profileImage;
        private String description;
        private double rate;
        private ServiceType serviceType;
        private List<Booking> bookings;
        private List<Message> messages;
        private List<Review> reviews;

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

        public Builder setReviews(List<Review> reviews) {
            this.reviews = reviews;
            return this;
        }

        public Builder copy(ServiceProvider serviceProvider) {
            super.copy(serviceProvider);
            this.profileImage = serviceProvider.profileImage;
            this.description = serviceProvider.description;
            this.rate = serviceProvider.rate;
            this.serviceType = serviceProvider.serviceType;
            this.bookings = serviceProvider.bookings;
            this.messages = serviceProvider.messages;
            this.reviews = serviceProvider.reviews;
            return this;
        }

        @Override
        protected Builder self() {
            return this;
        }

        public ServiceProvider build() {
            return new ServiceProvider(this);
        }
    }
}

