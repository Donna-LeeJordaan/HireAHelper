/* Review.java
   Author: Donna-Lee Jordaan (230613152)
   Date:25 July 2025 / modified 11 August 2025
*/

package za.co.hireahelper.domain;

import jakarta.persistence.*;
import java.util.Date;

@Entity
public class Review {

    @Id
    private String reviewId;

    private int rating;
    private String comment;

    @Temporal(TemporalType.TIMESTAMP)
    private Date timeStamp;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @ManyToOne
    @JoinColumn(name = "service_provider_id", nullable = false)
    private ServiceProvider serviceProvider;

    @ManyToOne
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;

    protected Review() {}

    private Review(Builder builder) {
        this.reviewId = builder.reviewId;
        this.rating = builder.rating;
        this.comment = builder.comment;
        this.timeStamp = builder.timeStamp;
        this.client = builder.client;
        this.serviceProvider = builder.serviceProvider;
        this.booking = builder.booking;
    }

    public String getReviewId() {
        return reviewId;
    }

    public int getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public Client getClient() {
        return client;
    }

    public ServiceProvider getServiceProvider() {
        return serviceProvider;
    }

    public Booking getBooking() {
        return booking;
    }

    @Override
    public String toString() {
        return "Review{" +
                "reviewId='" + reviewId + '\'' +
                ", rating=" + rating +
                ", comment='" + comment + '\'' +
                ", timeStamp=" + timeStamp +
                ", client=" + (client != null ? client.getUserId() : "null") +
                ", serviceProvider=" + (serviceProvider != null ? serviceProvider.getUserId() : "null") +
                ", booking=" + (booking != null ? booking.getBookingId() : "null") +
                '}';
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public static class Builder {
        private String reviewId;
        private int rating;
        private String comment;
        private Date timeStamp;
        private Client client;
        private ServiceProvider serviceProvider;
        private Booking booking;

        public Builder setReviewId(String reviewId) {
            this.reviewId = reviewId;
            return this;
        }

        public Builder setRating(int rating) {
            this.rating = rating;
            return this;
        }

        public Builder setComment(String comment) {
            this.comment = comment;
            return this;
        }

        public Builder setTimeStamp(Date timeStamp) {
            this.timeStamp = timeStamp;
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

        public Builder setBooking(Booking booking) {
            this.booking = booking;
            return this;
        }

        public Builder copy(Review review) {
            this.reviewId = review.reviewId;
            this.rating = review.rating;
            this.comment = review.comment;
            this.timeStamp = review.timeStamp;
            this.client = review.client;
            this.serviceProvider = review.serviceProvider;
            this.booking = review.booking;
            return this;
        }

        public Review build() {
            return new Review(this);
        }
    }
}
