/* ReviewService.java
   Author: Donna-Lee Jordaan (230613152)
   Date:25 July 2025 / modified 11 August 2025
*/

package za.co.hireahelper.service;

import za.co.hireahelper.domain.Review;
import java.util.List;

public interface ReviewService extends IService<Review, String> {
    List<Review> getAll();
}
