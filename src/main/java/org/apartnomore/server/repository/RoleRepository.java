package org.apartnomore.server.repository;

import org.apartnomore.server.models.ERole;
import org.apartnomore.server.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
