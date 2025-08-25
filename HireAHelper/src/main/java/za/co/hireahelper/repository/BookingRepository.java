/* BookingRepository.java
   Author: Donna-Lee Jordaan (230613152)
   Date: 25 July 2025 / modified 11 August 2025
*/
package za.co.hireahelper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.hireahelper.domain.Booking;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, String> {

    //Query methods added for client and service provider
    List<Booking> findByClientId(String clientId);
    List<Booking> findByServiceProviderId(String serviceProviderId);
}
