/* ReviewController.java
   Author: Donna-Lee Jordaan (230613152)
   Date:25 July 2025 / modified 11 August 2025
*/

package za.co.hireahelper.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import za.co.hireahelper.domain.Review;
import za.co.hireahelper.service.ReviewService;
import java.util.List;

@RestController
@RequestMapping("/review")
public class ReviewController {

    private final ReviewService service;

    @Autowired
    public ReviewController(ReviewService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public Review create(@RequestBody Review review) {
        return service.create(review);
    }

    @GetMapping("/read/{reviewId}")
    public Review read(@PathVariable String reviewId) {
        return service.read(reviewId);
    }

    @PutMapping("/update")
    public Review update(@RequestBody Review review) {
        return service.update(review);
    }

    @DeleteMapping("/delete/{reviewId}")
    public boolean delete(@PathVariable String reviewId) {
        return service.delete(reviewId);
    }

    @GetMapping("/all")
    public List<Review> getAll() {
        return service.getAll();
    }
}
