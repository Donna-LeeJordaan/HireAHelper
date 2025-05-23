/*
Gabriel Kiewietz 230990703
25 May
 */

package za.co.hireahelper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.hireahelper.domain.Message;
import java.util.List;

public interface MessageRepository extends JpaRepository<Message, String> {

    List<Message> findByClient_UserId(String clientId);
    List<Message> findByServiceProvider_UserId(String serviceProviderId);

}
