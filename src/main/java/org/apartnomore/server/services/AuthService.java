package org.apartnomore.server.services;

import org.apartnomore.server.exceptions.AlreadyExistingEmailException;
import org.apartnomore.server.exceptions.AlreadyExistingUsernameException;
import org.apartnomore.server.payload.request.LoginRequest;
import org.apartnomore.server.payload.request.SignupRequest;
import org.apartnomore.server.payload.response.JwtResponse;
import org.apartnomore.server.payload.response.SignUpResponse;

public interface AuthService {

    JwtResponse authenticateUser(LoginRequest loginRequest);

    SignUpResponse registerUser(SignupRequest signupRequest) throws AlreadyExistingUsernameException, AlreadyExistingEmailException;
}
