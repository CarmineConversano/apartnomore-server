package org.apartnomore.server.services;

import org.apartnomore.server.exceptions.AddressNotFoundException;
import org.apartnomore.server.exceptions.CommunityNotFoundException;
import org.apartnomore.server.exceptions.UnauthorizedException;
import org.apartnomore.server.models.User;
import org.apartnomore.server.payload.response.PermissionsList;
import org.apartnomore.server.payload.response.PrivateUserData;

public interface UserService {
    User findByLogin(String username);

    User findById(Long userId);

    PermissionsList getPermissionsByCommunityId(Long communityId, User loggedUser) throws CommunityNotFoundException;

    PermissionsList getPermissionsByCommunityIdAndUserId(Long communityId, Long userId, User loggedUser) throws CommunityNotFoundException, UnauthorizedException;

    PrivateUserData findMyDataByLogin(String name) throws AddressNotFoundException;
}
