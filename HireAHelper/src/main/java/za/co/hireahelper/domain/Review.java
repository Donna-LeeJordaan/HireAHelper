/* Review.java

   Author: D.Jordaan (230613152)

   Date: 18 May 2025 */

package za.co.hireahelper.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Review {
    @Id
    private String reviewId;
    private int rating;
    private String comment;
    private LocalDateTime timeStamp;
    private Client client;
    private ServiceProvider serviceProvider;

    protected Review() {
    }

    private Review(Builder builder) {
        this.reviewId = builder.reviewId;
        this.rating = builder.rating;
        this.comment = builder.comment;
        this.timeStamp = builder.timeStamp;
        this.client = builder.client;
        this.serviceProvider = builder.serviceProvider;
    }

    public static Builder builder() {
        return new Builder();
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

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public Client getClient() {
        return client;
    }

    public ServiceProvider getServiceProvider() {
        return serviceProvider;
    }

    public static class Builder {
        private String reviewId;
        private int rating;
        private String comment;
        private LocalDateTime timeStamp;
        private Client client;
        private ServiceProvider serviceProvider;

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

        public Builder setTimeStamp(LocalDateTime timeStamp) {
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

        public Builder copy(Review review) {
            this.reviewId = review.reviewId;
            this.rating = review.rating;
            this.comment = review.comment;
            this.timeStamp = review.timeStamp;
            this.client = review.client;
            this.serviceProvider = review.serviceProvider;
            return this;
        }

        public Review build() {
            return new Review(this);
        }
    }
}