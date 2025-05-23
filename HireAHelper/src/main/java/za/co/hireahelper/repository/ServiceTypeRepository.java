/*
Gabriel Kiewietz 230990703
25 May

 */
package za.co.hireahelper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.hireahelper.domain.ServiceType;

import java.util.Optional;

@Repository

public interface ServiceTypeRepository extends JpaRepository<ServiceType, String> {
    Optional<ServiceType> findByTypeName(String typeName);

}
