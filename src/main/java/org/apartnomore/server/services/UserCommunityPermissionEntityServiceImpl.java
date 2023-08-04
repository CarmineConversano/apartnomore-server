package org.apartnomore.server.services;

import org.apartnomore.server.models.UserCommunities;
import org.apartnomore.server.models.UserCommunityPermissionsEntity;
import org.apartnomore.server.repository.UserCommunityPermissionEntityRepository;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserCommunityPermissionEntityServiceImpl implements UserCommunityPermissionEntityService {

    private final UserCommunityPermissionEntityRepository userCommunityPermissionEntityRepository;

    public UserCommunityPermissionEntityServiceImpl(UserCommunityPermissionEntityRepository userCommunityPermissionEntityRepository) {
        this.userCommunityPermissionEntityRepository = userCommunityPermissionEntityRepository;
    }

    public UserCommunityPermissionsEntity savePermissions(UserCommunityPermissionsEntity userCommunityPermissionsEntity) {
        return this.userCommunityPermissionEntityRepository.save(userCommunityPermissionsEntity);
    }

    public Set<UserCommunityPermissionsEntity> findByUserCommunityPair(UserCommunities userCommunities) {
        return this.userCommunityPermissionEntityRepository.findAllByUserCommunities(userCommunities);
    }

    @Override
    public void removePermissions(UserCommunityPermissionsEntity perm) {
        this.userCommunityPermissionEntityRepository.delete(perm);
    }
}
