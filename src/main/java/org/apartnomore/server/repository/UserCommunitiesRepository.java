package org.apartnomore.server.repository;

import org.apartnomore.server.models.Community;
import org.apartnomore.server.models.User;
import org.apartnomore.server.models.UserCommunities;
import org.apartnomore.server.payload.response.MemberDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserCommunitiesRepository extends JpaRepository<UserCommunities, Long> {
    @Query("SELECT u.community from UserCommunities u where (u.user = :user) AND (u.community.description LIKE %:query% or u.community.name LIKE %:query% or u.community.accessLink LIKE %:query% or u.community.location LIKE %:query%)")
    Page<Community> findAllByUser(@Param("user") User user, Pageable pageable, String query);

    UserCommunities findByUserAndCommunity(User user, Community community);

    UserCommunities findByUserIdAndCommunity(User user, Community community);

    @Query("SELECT u.user from UserCommunities u where u.community.id = :communityId")
    Page<User> findAllByCommunityId(Long communityId, Pageable pageable);

    @Query(value = "SELECT u.id, u.username, u.email, up.name,  up.surname, up.avatar_url as avatarUrl, up.phone_number as phoneNumber from user_communities join users u on u.id = user_communities.user_id join user_profile up on u.id = up.user_id where community_id = :communityId",
            nativeQuery = true)
    Page<MemberDetail> findAllWithCommunityIdComplete(@Param("communityId") Long communityId, Pageable pageable);

    UserCommunities findByUserAndCommunityId(User user, Long communityId);

    UserCommunities findByUserIdAndCommunityId(Long userId, Long communityId);
}
