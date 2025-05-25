//Ameeruddin Arai 230190839
//24 May 2025

package za.co.hireahelper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.hireahelper.domain.Area;
import java.util.Optional;

@Repository
public interface AreaRepository extends JpaRepository<Area, String> {
    // Custom query methods can be defined here if needed
}

