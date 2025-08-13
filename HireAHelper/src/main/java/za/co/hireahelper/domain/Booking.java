/* Booking.java
   Author: Donna-Lee Jordaan (230613152)
   Date: 25 July 2025 / modified 11 August 2025
*/

package za.co.hireahelper.domain;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Booking {

    @Id
    private String bookingId;

    private LocalDate serviceDate;

    private String status;
    private String notes;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @ManyToOne
    @JoinColumn(name = "service_provider_id", nullable = false)
    private ServiceProvider serviceProvider;

    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL)
    private List<Review> reviews;

    protected Booking() {}

    private Booking(Builder builder) {
        this.bookingId = builder.bookingId;
        this.serviceDate = builder.serviceDate;
        this.status = builder.status;
        this.notes = builder.notes;
        this.client = builder.client;
        this.serviceProvider = builder.serviceProvider;
        this.reviews = builder.reviews;
    }

    // Getters
    public String getBookingId() {
        return bookingId;
    }

    public LocalDate getServiceDate() {  // Changed return type from Date to LocalDate
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

    public List<Review> getReviews() {
        return reviews;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "bookingId='" + bookingId + '\'' +
                ", serviceDate=" + serviceDate +
                ", status='" + status + '\'' +
                ", notes='" + notes + '\'' +
                ", client=" + (client != null ? client.getUserId() : "null") +
                ", serviceProvider=" + (serviceProvider != null ? serviceProvider.getUserId() : "null") +
                '}';
    }

    public static class Builder {
        private String bookingId;
        private LocalDate serviceDate;
        private String status;
        private String notes;
        private Client client;
        private ServiceProvider serviceProvider;
        private List<Review> reviews;

        public Builder setBookingId(String bookingId) {
            this.bookingId = bookingId;
            return this;
        }

        public Builder setServiceDate(LocalDate serviceDate) {
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

        public Builder setReviews(List<Review> reviews) {
            this.reviews = reviews;
            return this;
        }

        public Builder copy(Booking booking) {
            this.bookingId = booking.bookingId;
            this.serviceDate = booking.serviceDate;
            this.status = booking.status;
            this.notes = booking.notes;
            this.client = booking.client;
            this.serviceProvider = booking.serviceProvider;
            this.reviews = booking.reviews;
            return this;
        }

        public Booking build() {
            return new Booking(this);
        }
    }
}