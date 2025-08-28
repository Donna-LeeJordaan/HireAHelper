/* ServiceProvider.java

   Author: MT Osman (230599125)

   Date: 18 May 2025 */

package za.co.hireahelper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.hireahelper.domain.ServiceProvider;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServiceProviderRepository extends JpaRepository<ServiceProvider, String> {
    List<ServiceProvider> findByServiceType_TypeName(String typeName);
    Optional<ServiceProvider> findByEmailAndPassword(String email, String password);

}
