package org.apartnomore.server.services;

import org.apartnomore.server.exceptions.PermissionNotFoundException;
import org.apartnomore.server.models.EPermission;
import org.apartnomore.server.models.Permission;
import org.apartnomore.server.repository.PermissionRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository permissionRepository;

    public PermissionServiceImpl(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    @Override
    public Permission findByName(EPermission name) throws PermissionNotFoundException {
        Optional<Permission> permission = permissionRepository.findByName(name);
        if (permission.isEmpty()) {
            throw new PermissionNotFoundException("Permission not found with provided name");
        } else {
            return permission.get();
        }
    }
}
