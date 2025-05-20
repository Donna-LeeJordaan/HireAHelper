/* ClientRepository.java

   Author: S Hendricks (221095136)

   Date: 25 May 2025 */

package za.co.hireahelper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.hireahelper.domain.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, String> {
    // Custom queries can be added here if needed
}

