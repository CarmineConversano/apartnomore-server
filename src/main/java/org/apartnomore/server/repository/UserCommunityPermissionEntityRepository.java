package org.apartnomore.server.repository;

import org.apartnomore.server.models.UserCommunities;
import org.apartnomore.server.models.UserCommunityPermissionsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface UserCommunityPermissionEntityRepository extends JpaRepository<UserCommunityPermissionsEntity, Long> {

    Set<UserCommunityPermissionsEntity> findAllByUserCommunities(UserCommunities userCommunities);
}
