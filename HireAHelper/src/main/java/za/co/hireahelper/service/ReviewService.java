/* ReviewService.java

   Author: D.Jordaan (230613152)

   Date: 13 July 2025 */
package za.co.hireahelper.service;

import za.co.hireahelper.domain.Review;
import java.util.List;

public interface ReviewService extends IService<Review, String> {
    List<Review> getAll();

}