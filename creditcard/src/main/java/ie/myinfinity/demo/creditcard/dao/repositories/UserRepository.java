package ie.myinfinity.demo.creditcard.dao.repositories;

import ie.myinfinity.demo.creditcard.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public  interface UserRepository extends JpaRepository<User, Long> {

    long countByUsernameIgnoreCase(String username);

    Optional<User> findByUsernameIgnoreCase(String username);


    long countByUsernameIgnoreCaseAndPassword(String username, String password);
}
