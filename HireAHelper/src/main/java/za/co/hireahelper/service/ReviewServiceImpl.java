/* ReviewServiceImpl.java

   Author: D.Jordaan (230613152)

   Date: 13 July 2025 */

package za.co.hireahelper.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.co.hireahelper.domain.Review;
import za.co.hireahelper.repository.ReviewRepository;
import java.util.List;

@Service
public class ReviewServiceImpl  implements ReviewService {

    private final ReviewRepository repository;

    @Autowired
    public ReviewServiceImpl(ReviewRepository repository) {
        this.repository = repository;
    }
    @Override
    public Review create(Review review) {
        return this.repository.save(review);
    }

    @Override
    public Review read(String reviewId) {
        return this.repository.findById(reviewId)
                .orElse(null);
    }

    @Override
    public Review update(Review review) {
        return this.repository.save(review);
    }

    @Override
    public boolean delete(String reviewId) {
        if(this.repository.existsById(reviewId)) {
            this.repository.deleteById(reviewId);
            return true;
        }
        return false;
    }

    @Override
    public List<Review> getAll() {
        return this.repository.findAll();
    }


}
