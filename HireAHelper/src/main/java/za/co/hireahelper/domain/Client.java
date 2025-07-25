/* Client.java

   Author: S Hendricks (221095136)

   Date: 18 May 2025, updated on 25 July 2025
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

    protected Client() {
        super();
    }

    private Client(Builder builder) {
        super(builder);
        this.bookings = builder.bookings;
        this.messages = builder.messages;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public List<Message> getMessages() {
        return messages;
    }

    @Override
    public String toString() {
        return super.toString().replace("User", "Client")  // reuse parent output
                + ", bookings=" + bookings
                + ", messages=" + messages
                + '}';
    }

    public static class Builder extends User.Builder<Builder> {
        private List<Booking> bookings;
        private List<Message> messages;

        public Builder setBookings(List<Booking> bookings) {
            this.bookings = bookings;
            return this;
        }

        public Builder setMessages(List<Message> messages) {
            this.messages = messages;
            return this;
        }

        public Builder copy(Client client) {
            super.copy(client); // This now copies userId, name, email, password, mobileNumber, and area
            this.bookings = client.bookings;
            this.messages = client.messages;
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


