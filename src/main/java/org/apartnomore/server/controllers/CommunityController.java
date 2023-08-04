package org.apartnomore.server.controllers;

import org.apartnomore.server.exceptions.AddressNotFoundException;
import org.apartnomore.server.exceptions.ImageNotFoundException;
import org.apartnomore.server.models.Community;
import org.apartnomore.server.models.User;
import org.apartnomore.server.payload.request.CreateCommunityRequest;
import org.apartnomore.server.payload.response.*;
import org.apartnomore.server.services.CommunityService;
import org.apartnomore.server.services.CommunityServiceImpl;
import org.apartnomore.server.services.UserServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.security.Principal;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/community")
public class CommunityController {

    private final CommunityServiceImpl communityService;
    private final UserServiceImpl userService;

    public CommunityController(CommunityServiceImpl communityService, UserServiceImpl userService) {
        this.communityService = communityService;
        this.userService = userService;
    }

    @GetMapping("/{accessLink}")
    public ResponseEntity<?> findByAccessLink(@PathVariable String accessLink, Principal principal) {
        try {
            User loggedUser = userService.findByLogin(principal.getName());
            CommunityByAccessLink community = this.communityService.findByAccessLink(accessLink, loggedUser);
            return ResponseEntity.ok(community);
        } catch (Exception exception) {
            return ResponseEntity
                    .status(500)
                    .body(new MessageResponse("Error: ".concat(exception.getMessage())));
        }
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> createCommunity(@Valid @RequestBody CreateCommunityRequest createCommunityRequest, Principal principal) {
        try {
            User loggedUser = userService.findByLogin(principal.getName());
            Community community = this.communityService.createCommunity(createCommunityRequest, loggedUser);
            return ResponseEntity.ok(community);
        } catch (ImageNotFoundException imageNotFoundException) {
            return ResponseEntity
                    .status(400)
                    .body(new MessageResponse("Error: ".concat(imageNotFoundException.getMessage())));
        } catch (Exception permissionNotFoundException) {
            return ResponseEntity
                    .status(500)
                    .body(new MessageResponse("Error: ".concat(permissionNotFoundException.getMessage())));
        }
    }

    @GetMapping("/public/list/nearme")
    public Page<CommunityLocation> listNearMe(Pageable pageable, Principal principal, @PathParam("distance") double distance) throws AddressNotFoundException {
        User loggedUser = userService.findByLogin(principal.getName());
        return this.communityService.findAllPublicsNearUser(pageable, distance, loggedUser);
    }

    @GetMapping("/public/list/nearloc")
    public Page<CommunityLocation> listNearLocation(Pageable pageable, Principal principal, @PathParam("distance") double distance, @PathParam("lat") String lat, @PathParam("lng") String lng) throws AddressNotFoundException {
        return this.communityService.findAllPublicsNearLocation(pageable, distance, lat, lng);
    }

    @GetMapping("/public/list")
    public Page<Community> listPublicCommunities(Pageable pageable, @RequestParam String query) {
        return this.communityService.findPublics(pageable, query);
    }

    @GetMapping("/{communityId}/members")
    public Page<MemberDetail> listMembers(@PathVariable Long communityId, Pageable pageable, Principal principal) throws Exception {
        User loggedUser = userService.findByLogin(principal.getName());
        return this.communityService.findMembersByCommunity(pageable, loggedUser, communityId);
    }

    @PutMapping("/{communityId}/change/member/{memberId}")
    public ResponseEntity<?> changeMemberPermission(@PathVariable Long communityId, @PathVariable Long memberId, Principal principal, @RequestBody PermissionsList permissionsList) {
        try {
            User loggedUser = userService.findByLogin(principal.getName());
            PermissionsList permissions = this.communityService.changePermission(communityId, memberId, loggedUser, permissionsList);
            return ResponseEntity.ok(permissions);
        } catch (Exception exception) {
            return ResponseEntity
                    .status(500)
                    .body(new MessageResponse("Error: ".concat(exception.getMessage())));
        }
    }

    @PostMapping("/join/{communityId}")
    public ResponseEntity<?> joinCommunity(@PathVariable Long communityId, Principal principal) {
        try {
            User loggedUser = userService.findByLogin(principal.getName());
            JoinedCommunityResponse joinCommunity = this.communityService.joinCommunity(communityId, loggedUser);
            return ResponseEntity.ok(joinCommunity);
        } catch (Exception exception) {
            return ResponseEntity
                    .status(500)
                    .body(new MessageResponse("Error: ".concat(exception.getMessage())));
        }
    }

    @GetMapping("/subscribed/list")
    public Page<Community> listSubscribedCommunities(Pageable pageable, Principal principal, @RequestParam String query) {
        User loggedUser = userService.findByLogin(principal.getName());
        return this.communityService.findSubscribed(pageable, loggedUser, query);
    }
}
