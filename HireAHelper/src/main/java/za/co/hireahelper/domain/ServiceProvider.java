/* ServiceProvider.java

   Author: MT Osman (230599125)

   Date: 18 May 2025 */

package za.co.hireahelper.domain;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class ServiceProvider extends User{

    private String profileImage;
    private String description;
    private double rate;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private ServiceType serviceType;

    @OneToMany(mappedBy = "serviceProvider", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Booking> bookings;

    @OneToMany(mappedBy = "serviceProvider", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Message> messages;

    protected ServiceProvider() {super();}

    private ServiceProvider(Builder builder){
        super(builder);
        this.profileImage = builder.profileImage;
        this.description = builder.description;
        this.rate = builder.rate;
        this.serviceType = builder.serviceType;
        this.bookings = builder.bookings;
        this.messages = builder.messages;
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
                ", serviceType=" + getServiceType() +
                ", bookings=" + getBookings() +
                ", messages=" + getMessages() +
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
            return this;
        }

        public ServiceProvider build() {return new ServiceProvider(this);}

    }
}