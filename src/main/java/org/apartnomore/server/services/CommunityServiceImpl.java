package org.apartnomore.server.services;

import org.apartnomore.server.exceptions.*;
import org.apartnomore.server.models.*;
import org.apartnomore.server.payload.request.CreateCommunityRequest;
import org.apartnomore.server.payload.response.*;
import org.apartnomore.server.repository.CommunityRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class CommunityServiceImpl implements CommunityService {

    private final CommunityRepository communityRepository;
    private final PermissionServiceImpl permissionService;
    private final UserCommunityPermissionEntityServiceImpl userCommunityPermissionEntityService;
    private final UserCommunitiesServiceImpl userCommunitiesService;
    private final StorageService storageService;
    private final UserServiceImpl userService;
    private final AddressServiceImpl addressService;

    public CommunityServiceImpl(CommunityRepository communityRepository, PermissionServiceImpl permissionService, UserCommunityPermissionEntityServiceImpl userCommunityPermissionEntityService, UserCommunitiesServiceImpl userCommunitiesService, StorageService storageService, @Lazy UserServiceImpl userService, AddressServiceImpl addressService) {
        this.communityRepository = communityRepository;
        this.permissionService = permissionService;
        this.userCommunityPermissionEntityService = userCommunityPermissionEntityService;
        this.userCommunitiesService = userCommunitiesService;
        this.storageService = storageService;
        this.userService = userService;
        this.addressService = addressService;
    }

    @Override
    public Community createCommunity(CreateCommunityRequest createCommunityRequest, User user) throws PermissionNotFoundException, CommunityNotFoundException, ImageNotFoundException {
        Community toCreateCommunity = new Community();
        toCreateCommunity.setName(createCommunityRequest.getName());
        toCreateCommunity.setDescription(createCommunityRequest.getDescription());
        toCreateCommunity.setLocation(createCommunityRequest.getLocation());
        toCreateCommunity.setLat(createCommunityRequest.getLat());
        toCreateCommunity.setLng(createCommunityRequest.getLng());
        toCreateCommunity.setIsPublic(createCommunityRequest.getIsPublic());
        toCreateCommunity.setImage(this.storageService.findById(createCommunityRequest.getImageId()));
        if (createCommunityRequest.getIsPublic()) {
            toCreateCommunity.setAccessLink(createCommunityRequest.getAccessLink());
        } else {
            UUID uuid;
            do {
                uuid = UUID.randomUUID();
            } while (this.communityRepository.findByAccessLink(uuid.toString()) != null);

            toCreateCommunity.setAccessLink(uuid.toString());
        }
        Community savedCommunity = this.communityRepository.save(toCreateCommunity);

        /*Permissions*/
        Set<Permission> listOfPermissions = new HashSet<>();
        listOfPermissions.add(this.permissionService.findByName(EPermission.USE_CHANGESETTINGS));
        listOfPermissions.add(this.permissionService.findByName(EPermission.USE_DELETE));
        listOfPermissions.add(this.permissionService.findByName(EPermission.USE_MOD));
        listOfPermissions.add(this.permissionService.findByName(EPermission.USE_CREATE));
        listOfPermissions.add(this.permissionService.findByName(EPermission.USE_READ));
        listOfPermissions.add(this.permissionService.findByName(EPermission.USE_WRITE));
        listOfPermissions.add(this.permissionService.findByName(EPermission.USE_CREATOR));

        /*Add user to community*/
        UserCommunities userCommunities = new UserCommunities();
        userCommunities.setCommunity(savedCommunity);
        userCommunities.setUser(user);
        this.userCommunitiesService.addUserToCommunity(userCommunities);

        /*Add permissions*/
        for (Permission perm : listOfPermissions) {
            UserCommunityPermissionsEntity permissions = new UserCommunityPermissionsEntity();
            permissions.setUserCommunities(userCommunities);
            permissions.setPermission(perm);
            this.userCommunityPermissionEntityService.savePermissions(permissions);
        }

        return this.findByName(savedCommunity.getName());
    }

    @Override
    public Page<Community> findPublics(Pageable pageable, String query) {
        return this.communityRepository.findPublics(pageable, query);
    }

    @Override
    public JoinedCommunityResponse joinCommunity(Long communityId, User loggedUser) throws CommunityNotFoundException {
        UserCommunities userCommunities = new UserCommunities();
        Community community = this.findById(communityId);
        userCommunities.setUser(loggedUser);
        userCommunities.setCommunity(community);
        this.userCommunitiesService.addUserToCommunity(userCommunities);
        return new JoinedCommunityResponse(community.getName(), "Joined community!");
    }

    @Override
    public Page<Community> findSubscribed(Pageable pageable, User loggedUser, String query) {
        return this.userCommunitiesService.findByUser(loggedUser, pageable, query);
    }

    @Override
    public CommunityByAccessLink findByAccessLink(String accessLink, User loggedUser) throws CommunityNotFoundException {
        Optional<Community> community = this.communityRepository.findCommunityByAccessLink(accessLink);
        if (community.isEmpty()) {
            throw new CommunityNotFoundException("Community not found with provided string");
        } else {
            Community c = community.get();
            boolean subscribed = c.getUserCommunities().stream().filter(userCommunities -> loggedUser.getId().equals(userCommunities.getUser().getId())).findFirst().orElse(null) != null;
            return new CommunityByAccessLink(c.getId(), c.getName(), c.getIsPublic(), c.getAccessLink(), c.getDescription(), c.getLocation(), c.getImage(), subscribed);
        }
    }

    @Override
    public Page<MemberDetail> findMembersByCommunity(Pageable pageable, User loggedUser, Long communityId) throws Exception {
        PermissionsList permissions = this.userService.getPermissionsByCommunityId(communityId, loggedUser);
        if (permissions.getPermissionSet().size() > 0) {
            return this.userCommunitiesService.findAllByCommunity(communityId, pageable);
        } else {
            throw new Exception("Non hai i permessi");
        }
    }

    @Override
    public PermissionsList changePermission(Long communityId, Long memberId, User loggedUser, PermissionsList permissionsList) throws UnauthorizedException, PermissionNotFoundException {
        if (memberId.equals(loggedUser.getId())) {
            throw new UnauthorizedException("Non puoi cambiare i tuoi permessi!");
        }

        if (this.userService.canChangePermissions(communityId, loggedUser)) {
            UserCommunities userCommunities = this.userCommunitiesService.findByUserIdAndCommunityId(memberId, communityId);

            /*Permissions*/
            Set<Permission> listOfPermissions = new HashSet<>();
            for (EPermission permission : permissionsList.getPermissionSet()) {
                listOfPermissions.add(this.permissionService.findByName(permission));
            }

            User member = this.userService.findById(memberId);

            Set<UserCommunityPermissionsEntity> originalPermissions = this.userCommunityPermissionEntityService.findByUserCommunityPair(userCommunities);
            Set<UserCommunityPermissionsEntity> removedPermissions = new HashSet<>(originalPermissions);


            Set<UserCommunityPermissionsEntity> allPermissions = new HashSet<>();
//            allPermissions.removeIf(element -> element.getUserCommunities().equals(userCommunities));
            /*Add permissions*/
            for (Permission perm : listOfPermissions) {
                UserCommunityPermissionsEntity permissions = new UserCommunityPermissionsEntity();
                permissions.setUserCommunities(userCommunities);
                permissions.setPermission(perm);
                allPermissions.add(permissions);
//                this.userCommunityPermissionEntityService.savePermissions(permissions);
            }


            removedPermissions.removeIf(element -> {
                UserCommunityPermissionsEntity u = allPermissions.stream().filter(perm ->
                        perm.getPermission() == element.getPermission() && perm.getUserCommunities() == element.getUserCommunities()
                ).findFirst().orElse(null);
                return u != null;
            });

            allPermissions.removeIf(element -> {
                UserCommunityPermissionsEntity u = originalPermissions.stream().filter(perm ->
                        perm.getPermission() == element.getPermission() && perm.getUserCommunities() == element.getUserCommunities()
                ).findFirst().orElse(null);
                return u != null;
            });

            for (UserCommunityPermissionsEntity perm : allPermissions) {
                this.userCommunityPermissionEntityService.savePermissions(perm);
            }

            for (UserCommunityPermissionsEntity perm : removedPermissions) {
                this.userCommunityPermissionEntityService.removePermissions(perm);
            }
        } else {
            throw new UnauthorizedException("Non hai i permessi per cambiare autorizzazioni");
        }
        return permissionsList;
    }

    @Override
    public Page<CommunityLocation> findAllPublicsNearLocation(Pageable pageable, double distance, String lat, String lng) {
        double latN = Double.parseDouble(lat);
        double lngN = Double.parseDouble(lng);
        double abs = Math.abs(Math.cos(Math.toRadians(latN)) * 69);
        double lon1 = lngN / abs;
        double lon2 = lngN + distance / abs;
        double lat1 = latN - (distance / 69);
        double lat2 = latN + (distance / 69);

        return communityRepository.findAllPublicsNearLocation(pageable, lon1, lon2, lat1, lat2, latN, lngN);
    }

    @Override
    public Page<CommunityLocation> findAllPublicsNearUser(Pageable pageable, double distance, User loggedUser) throws AddressNotFoundException {
        Address userAddress = this.addressService.findByUserId(loggedUser.getId());
        return this.findAllPublicsNearLocation(pageable, distance, userAddress.getLat(), userAddress.getLng());
    }

    public Community findByName(String name) throws CommunityNotFoundException {
        Optional<Community> community = this.communityRepository.findCommunityByName(name);
        if (community.isEmpty()) {
            throw new CommunityNotFoundException("Community not found with provided string");
        } else {
            return community.get();
        }
    }

    public Community findById(Long id) throws CommunityNotFoundException {
        Optional<Community> community = this.communityRepository.findById(id);
        if (community.isEmpty()) {
            throw new CommunityNotFoundException("Community not found with provided id");
        } else {
            return community.get();
        }
    }
}
