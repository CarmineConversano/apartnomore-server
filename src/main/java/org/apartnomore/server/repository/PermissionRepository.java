package org.apartnomore.server.repository;

import org.apartnomore.server.models.EPermission;
import org.apartnomore.server.models.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PermissionRepository extends JpaRepository<Permission, Long> {

    Optional<Permission> findByName(EPermission name);
}
