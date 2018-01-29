package edu.shapovalov.library.repositiry;

import edu.shapovalov.library.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Artem Shapovalov
 * @version 0.1
 * @since 05.01.2018.
 */

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
