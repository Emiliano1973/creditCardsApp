package ie.myinfinity.demo.creditcard.dao.repositories;

import ie.myinfinity.demo.creditcard.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByDescription(String description);
}
