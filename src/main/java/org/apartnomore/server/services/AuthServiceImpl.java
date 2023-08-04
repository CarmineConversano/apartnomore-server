package org.apartnomore.server.services;

import org.apartnomore.server.exceptions.AlreadyExistingEmailException;
import org.apartnomore.server.exceptions.AlreadyExistingUsernameException;
import org.apartnomore.server.models.*;
import org.apartnomore.server.payload.request.LoginRequest;
import org.apartnomore.server.payload.request.SignupRequest;
import org.apartnomore.server.payload.response.AddressResponse;
import org.apartnomore.server.payload.response.JwtResponse;
import org.apartnomore.server.payload.response.SignUpResponse;
import org.apartnomore.server.payload.response.UserProfileResponse;
import org.apartnomore.server.repository.RoleRepository;
import org.apartnomore.server.repository.UserRepository;
import org.apartnomore.server.security.jwt.JwtUtils;
import org.apartnomore.server.security.services.UserDetailsImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService {
    private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);
    private static final String logPattern = "[AUTH SERVICE]";
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserProfileServiceImpl userProfileService;
    private final AddressServiceImpl addressService;
    private final PasswordEncoder encoder;


    public AuthServiceImpl(AuthenticationManager authenticationManager, JwtUtils jwtUtils, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder encoder, UserProfileServiceImpl userProfileService, AddressServiceImpl addressService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
        this.userProfileService = userProfileService;
        this.addressService = addressService;
    }

    @Override
    public JwtResponse authenticateUser(LoginRequest loginRequest) throws AuthenticationException {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return new JwtResponse(jwt,
                userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles);
    }

    @Override
    public SignUpResponse registerUser(SignupRequest signupRequest) throws AlreadyExistingUsernameException, AlreadyExistingEmailException {
        if (userRepository.existsByUsername(signupRequest.getEmail())) {
            throw new AlreadyExistingUsernameException("Username already taken!");
        }

        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            throw new AlreadyExistingEmailException("Username already taken!");
        }

        SignUpResponse signUpResponse = new SignUpResponse();

        User user = new User(signupRequest.getUsername(),
                signupRequest.getEmail(), encoder.encode(signupRequest.getPassword()));

        Set<Role> roles = new HashSet<>();

        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Error: Role not found."));
        roles.add(userRole);
        user.setRoles(roles);
        user = userRepository.save(user);

        signUpResponse.setId(user.getId());
        signUpResponse.setUsername(user.getUsername());
        signUpResponse.setEmail(user.getEmail());
        signUpResponse.setRoles(user.getRoles());

        UserProfile userProfile = new UserProfile(
                signupRequest.getProfile().getName(),
                signupRequest.getProfile().getSurname(),
                signupRequest.getProfile().getPhoneNumber(),
                signupRequest.getProfile().getPhoneNumber(), user);


        userProfile = this.userProfileService.save(userProfile);
        UserProfileResponse userProfileResponse = new UserProfileResponse(
                signupRequest.getProfile().getName(),
                signupRequest.getProfile().getSurname(),
                signupRequest.getProfile().getPhoneNumber(),
                signupRequest.getProfile().getPhoneNumber(), userProfile.getId());

        signUpResponse.setUserProfileResponse(userProfileResponse);

        if (signupRequest.getAddress() != null) {
            AddressResponse addressResponse = new AddressResponse(signupRequest.getAddress());
            Address address = new Address(signupRequest.getAddress(), user);
            address = this.addressService.save(address);
            addressResponse.setId(address.getId());
            signUpResponse.setAddressResponse(addressResponse);
        }
        return signUpResponse;
    }
}
