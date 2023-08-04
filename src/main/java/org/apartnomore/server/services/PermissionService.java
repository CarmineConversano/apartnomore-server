package org.apartnomore.server.services;

import org.apartnomore.server.exceptions.PermissionNotFoundException;
import org.apartnomore.server.models.EPermission;
import org.apartnomore.server.models.Permission;

public interface PermissionService {

    Permission findByName(EPermission name) throws PermissionNotFoundException;
}
