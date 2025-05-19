/* Booking.java

   Author: D.Jordaan (230613152)

   Date: 18 May 2025 */

package za.co.hireahelper.domain;
import java.util.Date;


public class Booking {
    private String bookingId;
    private Date serviceDate;
    private String status;
    private String notes;

    // Relationships
    private Client client;
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