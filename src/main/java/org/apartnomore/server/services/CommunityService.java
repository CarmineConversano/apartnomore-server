package org.apartnomore.server.services;

import org.apartnomore.server.exceptions.*;
import org.apartnomore.server.models.Community;
import org.apartnomore.server.models.User;
import org.apartnomore.server.payload.request.CreateCommunityRequest;
import org.apartnomore.server.payload.response.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommunityService {
    Community createCommunity(CreateCommunityRequest createCommunityRequest, User user) throws PermissionNotFoundException, CommunityNotFoundException, ImageNotFoundException;

    Page<Community> findPublics(Pageable pageable, String query);

    JoinedCommunityResponse joinCommunity(Long communityId, User loggedUser) throws CommunityNotFoundException;

    Page<Community> findSubscribed(Pageable pageable, User loggedUser, String query);

    CommunityByAccessLink findByAccessLink(String accessLink, User loggedUser) throws CommunityNotFoundException;

    Page<MemberDetail> findMembersByCommunity(Pageable pageable, User loggedUser, Long communityId) throws Exception;

    PermissionsList changePermission(Long communityId, Long memberId, User loggedUser, PermissionsList permissionsList) throws UnauthorizedException, PermissionNotFoundException;

    Page<CommunityLocation> findAllPublicsNearLocation(Pageable pageable, double distance, String lat, String lng);

    Page<CommunityLocation> findAllPublicsNearUser(Pageable pageable, double distance, User loggedUser) throws AddressNotFoundException;
}

