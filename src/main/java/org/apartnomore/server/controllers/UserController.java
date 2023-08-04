package org.apartnomore.server.controllers;

import org.apartnomore.server.exceptions.UnauthorizedException;
import org.apartnomore.server.models.User;
import org.apartnomore.server.payload.response.MessageResponse;
import org.apartnomore.server.payload.response.PermissionsList;
import org.apartnomore.server.payload.response.PrivateUserData;
import org.apartnomore.server.payload.response.PrivateUserDataImpl;
import org.apartnomore.server.services.UserServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/users")
public class UserController {
    final
    UserServiceImpl userService;


    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<?> getMe(Principal principal) {
        try {
            PrivateUserDataImpl privateUserData = userService.findMyDataByLogin(principal.getName());
            return ResponseEntity.ok(privateUserData);
        } catch (Exception exception) {
            return ResponseEntity
                    .status(500)
                    .body(new MessageResponse("Error: ".concat(exception.getMessage())));
        }
    }

    @GetMapping("/find/{userId}")
    public ResponseEntity<?> getById(@PathVariable Long userId) {
        try {
            User user = userService.findById(userId);
            return ResponseEntity.ok(user);
        } catch (Exception exception) {
            return ResponseEntity
                    .status(500)
                    .body(new MessageResponse("Error: ".concat(exception.getMessage())));
        }
    }

    @GetMapping("/permissions/{communityId}")
    public ResponseEntity<?> getPermissionsByCommunityId(@PathVariable Long communityId, Principal principal) {
        try {
            User loggedUser = userService.findByLogin(principal.getName());
            PermissionsList permission = this.userService.getPermissionsByCommunityId(communityId, loggedUser);
            return ResponseEntity.ok(permission);
        } catch (Exception exception) {
            return ResponseEntity
                    .status(500)
                    .body(new MessageResponse("Error: ".concat(exception.getMessage())));
        }
    }

    @GetMapping("/permissions/{communityId}/{userId}")
    public ResponseEntity<?> getPermissionsByCommunityId(@PathVariable Long communityId, @PathVariable Optional<Long> userId, Principal principal) {
        try {
            PermissionsList permission;
            User loggedUser = userService.findByLogin(principal.getName());
            if (userId.isEmpty()) {
                permission = this.userService.getPermissionsByCommunityId(communityId, loggedUser);
            } else {
                permission = this.userService.getPermissionsByCommunityIdAndUserId(communityId, userId.get(), loggedUser);
            }
            return ResponseEntity.ok(permission);
        } catch (UnauthorizedException exception) {
            return ResponseEntity
                    .status(401)
                    .body(new MessageResponse("Error: ".concat(exception.getMessage())));
        } catch (Exception exception) {
            return ResponseEntity
                    .status(500)
                    .body(new MessageResponse("Unauthorized: ".concat(exception.getMessage())));
        }
    }
}
