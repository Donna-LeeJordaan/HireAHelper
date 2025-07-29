/* Client.java
   Author: S Hendricks (221095136)
   Date: 18 May 2025
*/

package za.co.hireahelper.domain;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Client extends User {

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Booking> bookings;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Message> messages;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Review> reviews;

    protected Client() {
        super();
    }

    private Client(Builder builder) {
        super(builder);
        this.bookings = builder.bookings;
        this.messages = builder.messages;
        this.reviews = builder.reviews;
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
        return "Client{" +
                "userId='" + getUserId() + '\'' +
                ", name='" + getName() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", password='" + getPassword() + '\'' +
                ", mobileNumber='" + getMobileNumber() + '\'' +
                ", areaId='" + (getArea() != null ? getArea().getAreaId() : "null") + '\'' +
                ", bookings=" + (bookings != null ? bookings.size() : 0) +
                ", messages=" + (messages != null ? messages.size() : 0) +
                ", reviews=" + (reviews != null ? reviews.size() : 0) +
                '}';
    }

    public static class Builder extends User.Builder<Builder> {
        private List<Booking> bookings;
        private List<Message> messages;
        private List<Review> reviews;

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

        public Builder copy(Client client) {
            super.copy(client);
            this.bookings = client.bookings;
            this.messages = client.messages;
            this.reviews = client.reviews;
            return this;
        }

        @Override
        protected Builder self() {
            return this;
        }

        @Override
        public Client build() {
            return new Client(this);
        }
    }
}



