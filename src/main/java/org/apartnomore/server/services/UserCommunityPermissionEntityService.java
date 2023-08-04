package org.apartnomore.server.services;

import org.apartnomore.server.models.UserCommunityPermissionsEntity;

public interface UserCommunityPermissionEntityService {
    void removePermissions(UserCommunityPermissionsEntity perm);
}
