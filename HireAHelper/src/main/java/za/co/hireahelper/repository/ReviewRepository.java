package za.co.hireahelper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.hireahelper.domain.Review;

import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, String> {
    //Custom query methods can be defined here if needed


    @Override
    Optional<Review> findById(String s);


}
