package com.epam.finaltask.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        return new AuthenticationResponse("generated-jwt-token");
    }
}
