package org.apartnomore.server.services;

import org.apartnomore.server.exceptions.AddressNotFoundException;
import org.apartnomore.server.exceptions.CommunityNotFoundException;
import org.apartnomore.server.exceptions.UnauthorizedException;
import org.apartnomore.server.models.*;
import org.apartnomore.server.payload.response.PermissionsList;
import org.apartnomore.server.payload.response.PrivateUserData;
import org.apartnomore.server.payload.response.PrivateUserDataImpl;
import org.apartnomore.server.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserCommunitiesServiceImpl userCommunitiesService;
    private final UserCommunityPermissionEntityServiceImpl userCommunityPermissionEntityService;
    private final CommunityServiceImpl communityService;
    private final AddressServiceImpl addressService;

    public UserServiceImpl(UserRepository userRepository, UserCommunitiesServiceImpl userCommunitiesService, UserCommunityPermissionEntityServiceImpl userCommunityPermissionEntityService, CommunityServiceImpl communityService, AddressServiceImpl addressService) {
        this.userRepository = userRepository;
        this.userCommunitiesService = userCommunitiesService;
        this.userCommunityPermissionEntityService = userCommunityPermissionEntityService;
        this.communityService = communityService;
        this.addressService = addressService;
    }

    @Override
    public User findByLogin(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("Username not found with provided string");
        } else {
            return user.get();
        }
    }

    @Override
    public User findById(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("Username not found with provided id");
        } else {
            return user.get();
        }
    }

    public User updateUser(User updated) {
        return this.userRepository.save(updated);
    }

    @Override
    public PermissionsList getPermissionsByCommunityId(Long communityId, User loggedUser) throws CommunityNotFoundException {
        Community community = this.communityService.findById(communityId);
        UserCommunities userCommunities = this.userCommunitiesService.findByUserAndCommunity(loggedUser, community);
        Set<UserCommunityPermissionsEntity> userCommunityPermissionsEntities = this.userCommunityPermissionEntityService.findByUserCommunityPair(userCommunities);
        PermissionsList permissionsList = new PermissionsList();

        for (UserCommunityPermissionsEntity userCommunityPermissionsEntity : userCommunityPermissionsEntities) {
            if (userCommunityPermissionsEntity.getPermission().getName() != null)
                permissionsList.addPermission(userCommunityPermissionsEntity.getPermission().getName());
        }
        return permissionsList;
    }

    public boolean canChangePermissions(Long communityId, User user) {
        /*Find permissions of request user*/
        UserCommunities ownerComunity = this.userCommunitiesService.findByUserAndCommunityId(user, communityId);

        Set<UserCommunityPermissionsEntity> ownerPermissions = this.userCommunityPermissionEntityService.findByUserCommunityPair(ownerComunity);
        return ownerPermissions.stream().filter(permission ->
                EPermission.USE_CREATOR == permission.getPermission().getName() ||
                        EPermission.USE_MOD == permission.getPermission().getName()
        ).findFirst().orElse(null) != null;
    }

    @Override
    public PermissionsList getPermissionsByCommunityIdAndUserId(Long communityId, Long userId, User loggedUser) throws CommunityNotFoundException, UnauthorizedException {
        boolean canChangePermissions = this.canChangePermissions(communityId, loggedUser);

        if (canChangePermissions) {
            /*Find permission of member*/
            User member = this.findById(userId);
            UserCommunities userCommunities = this.userCommunitiesService.findByUserAndCommunityId(member, communityId);

            Set<UserCommunityPermissionsEntity> userCommunityPermissionsEntities = this.userCommunityPermissionEntityService.findByUserCommunityPair(userCommunities);
            PermissionsList permissionsList = new PermissionsList();

            for (UserCommunityPermissionsEntity userCommunityPermissionsEntity : userCommunityPermissionsEntities) {
                if (userCommunityPermissionsEntity.getPermission().getName() != null)
                    permissionsList.addPermission(userCommunityPermissionsEntity.getPermission().getName());
            }
            return permissionsList;
        } else {
            throw new UnauthorizedException("Non puoi accedere ai permessi dell'utente!");
        }

    }

    @Override
    public PrivateUserDataImpl findMyDataByLogin(String name) {
        Optional<PrivateUserData> user = userRepository.findPrivateDataByUsername(name);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("Username not found with provided string");
        } else {
            PrivateUserDataImpl privateUserData = new PrivateUserDataImpl(user.get());
            User userNew = this.findById(user.get().getId());
            privateUserData.setRoles(userNew.getRoles());
            try {
                Address userAddress = this.addressService.findByUserId(userNew.getId());
                privateUserData.setAddress(userAddress);
            } catch (AddressNotFoundException ignored) {
            }
            return privateUserData;
        }
    }
}
