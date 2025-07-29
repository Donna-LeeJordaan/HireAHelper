/* Booking.java
   Author: D.Jordaan (230613152)
   Date: 18 May 2025 / modified on 25 July 2025
*/

package za.co.hireahelper.domain;

import jakarta.persistence.*;
import java.util.Date;

@Entity
public class Booking {

    @Id
    private String bookingId;
    private Date serviceDate;
    private String status;
    private String notes;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @ManyToOne
    @JoinColumn(name = "service_provider_id", nullable = false)
    private ServiceProvider serviceProvider;

    protected Booking() {}

    private Booking(Builder builder) {
        this.bookingId = builder.bookingId;
        this.serviceDate = builder.serviceDate;
        this.status = builder.status;
        this.notes = builder.notes;
        this.client = builder.client;
        this.serviceProvider = builder.serviceProvider;
    }

    public static Builder builder() {
        return new Builder();
    }

    // Getters
    public String getBookingId() {
        return bookingId;
    }

    public Date getServiceDate() {
        return serviceDate;
    }

    public String getStatus() {
        return status;
    }

    public String getNotes() {
        return notes;
    }

    public Client getClient() {
        return client;
    }

    public ServiceProvider getServiceProvider() {
        return serviceProvider;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "bookingId='" + bookingId + '\'' +
                ", serviceDate=" + serviceDate +
                ", status='" + status + '\'' +
                ", notes='" + notes + '\'' +
                ", clientId=" + (client != null ? client.getUserId() : "null") +
                ", serviceProviderId=" + (serviceProvider != null ? serviceProvider.getUserId() : "null") +
                '}';
    }

    // Builder class
    public static class Builder {
        private String bookingId;
        private Date serviceDate;
        private String status;
        private String notes;
        private Client client;
        private ServiceProvider serviceProvider;

        public Builder setBookingId(String bookingId) {
            this.bookingId = bookingId;
            return this;
        }

        public Builder setServiceDate(Date serviceDate) {
            this.serviceDate = serviceDate;
            return this;
        }

        public Builder setStatus(String status) {
            this.status = status;
            return this;
        }

        public Builder setNotes(String notes) {
            this.notes = notes;
            return this;
        }

        public Builder setClient(Client client) {
            this.client = client;
            return this;
        }

        public Builder setServiceProvider(ServiceProvider serviceProvider) {
            this.serviceProvider = serviceProvider;
            return this;
        }

        public Builder copy(Booking booking) {
            this.bookingId = booking.bookingId;
            this.serviceDate = booking.serviceDate;
            this.status = booking.status;
            this.notes = booking.notes;
            this.client = booking.client;
            this.serviceProvider = booking.serviceProvider;
            return this;
        }

        public Booking build() {
            return new Booking(this);
        }
    }
}

