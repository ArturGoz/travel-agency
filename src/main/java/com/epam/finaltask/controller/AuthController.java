package com.epam.finaltask.controller;

import com.epam.finaltask.auth.AuthenticationRequest;
import com.epam.finaltask.auth.AuthenticationResponse;
import com.epam.finaltask.auth.AuthenticationService;
import com.epam.finaltask.dto.RemoteResponse;
import com.epam.finaltask.service.UserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    @Autowired
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<RemoteResponse> login(@RequestBody AuthenticationRequest authenticationRequest) {
        RemoteResponse remoteResponse = RemoteResponse.create(
                true,
                StatusCodes.OK.name(),
                "User is successfully authenticated",
                List.of(authenticationService.authenticate(authenticationRequest))
        );
        return new ResponseEntity<>(remoteResponse, HttpStatus.ACCEPTED);
    }
}
