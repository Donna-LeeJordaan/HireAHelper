package za.co.hireahelper.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import za.co.hireahelper.domain.User;

public interface UserRepository extends JpaRepository<User, String> {
    User findByEmail(String email);
}
