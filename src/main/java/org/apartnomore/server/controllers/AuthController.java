package org.apartnomore.server.controllers;

import org.apartnomore.server.exceptions.AlreadyExistingEmailException;
import org.apartnomore.server.exceptions.AlreadyExistingUsernameException;
import org.apartnomore.server.payload.request.LoginRequest;
import org.apartnomore.server.payload.request.SignupRequest;
import org.apartnomore.server.payload.response.JwtResponse;
import org.apartnomore.server.payload.response.MessageResponse;
import org.apartnomore.server.payload.response.SignUpResponse;
import org.apartnomore.server.services.AuthServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    final
    AuthServiceImpl authService;


    public AuthController(AuthServiceImpl authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            JwtResponse jwtResponse = this.authService.authenticateUser(loginRequest);
            return ResponseEntity.ok(jwtResponse);
        } catch (AuthenticationException authenticationException) {
            return ResponseEntity
                    .status(401)
                    .body(new MessageResponse("Error: ".concat(authenticationException.getMessage())));
        } catch (Exception exception) {
            return ResponseEntity
                    .status(500)
                    .body(new MessageResponse("Error: ".concat(exception.getMessage())));
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest) {
        try {
            SignUpResponse signUpResponse = this.authService.registerUser(signupRequest);

            return ResponseEntity.ok(signUpResponse);
        } catch (AlreadyExistingEmailException | AlreadyExistingUsernameException alreadyExistingUserException) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: ".concat(alreadyExistingUserException.getMessage())));
        } catch (Exception exception) {
            return ResponseEntity
                    .status(500)
                    .body(new MessageResponse("Error: ".concat(exception.getMessage())));
        }
    }
}
