package za.co.hireahelper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.hireahelper.domain.Booking;

import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, String> {
    //Custom query methods can be defined here if needed
    //For example, findByCity(String city):
    //Example:

    @Override
    Optional<Booking> findById(String s);
}
