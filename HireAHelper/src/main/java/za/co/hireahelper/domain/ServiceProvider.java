/* ServiceProvider.java
   Author: MT Osman (230599125)
   Date: 18 May 2025
*/

package za.co.hireahelper.domain;

import jakarta.persistence.*;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({"bookings", "messages"}) // Prevent infinite looping due to bidirectional relationships
public class ServiceProvider extends User {

    @Lob
    private byte[] profileImage;
    private String description;
    private double rate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "type_id", nullable = false)
    private ServiceType serviceType;

    @OneToMany(mappedBy = "serviceProvider", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Booking> bookings;

    @OneToMany(mappedBy = "serviceProvider", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Message> messages;

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
    }

    public byte[] getProfileImage() { return profileImage; }
    public String getDescription() { return description; }
    public double getRate() { return rate; }
    public ServiceType getServiceType() { return serviceType; }
    public List<Booking> getBookings() { return bookings; }
    public List<Message> getMessages() { return messages; }

    @Override
    public String toString() {
        return "ServiceProvider{" +
                "userId='" + getUserId() + '\'' +
                ", name='" + getName() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", password='" + getPassword() + '\'' +
                ", mobileNumber='" + getMobileNumber() + '\'' +
                ", area=" + getArea() +
                ", profileImage='" + profileImage + '\'' +
                ", description='" + description + '\'' +
                ", rate=R" + rate +
                ", serviceType=" + getServiceType() +
                ", bookings=" + (bookings != null ? bookings.size() : 0) +
                ", messages=" + (messages != null ? messages.size() : 0) +
                ", role=" + getRole() +
                '}';
    }

    public static class Builder extends User.Builder<ServiceProvider.Builder> {
        private byte[] profileImage;
        private String description;
        private double rate;
        private ServiceType serviceType;
        private List<Booking> bookings;
        private List<Message> messages;

        public Builder() {this.setRole(Role.SERVICE_PROVIDER);}

        public Builder setProfileImage(byte[] profileImage) {
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

        public Builder copy(ServiceProvider serviceProvider) {
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
        protected Builder self() { return this; }

        @Override
        public ServiceProvider build() { return new ServiceProvider(this); }
    }
}
