/* BookingRepository.java
   Author: Donna-Lee Jordaan (230613152)
   Date: 25 July 2025 / modified 11 August 2025
*/
package za.co.hireahelper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.hireahelper.domain.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, String> {
    // Custom query methods can be defined here if needed
}
