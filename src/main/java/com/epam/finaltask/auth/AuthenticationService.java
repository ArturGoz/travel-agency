package com.epam.finaltask.auth;

import com.epam.finaltask.config.JwtService;
import com.epam.finaltask.controller.StatusCodes;
import com.epam.finaltask.exception.EntityNotFoundException;
import com.epam.finaltask.model.User;
import com.epam.finaltask.repository.UserRepository;
import com.epam.finaltask.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;


    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        User user = userRepository.findUserByUsername(authenticationRequest.getUsername())
                .orElseThrow(() -> new EntityNotFoundException(StatusCodes.WRONG_PASSWORD.name(),
                        "Such login does not exist"));

        if(!passwordEncoder.matches(authenticationRequest.getPassword(), user.getPassword())) {
            throw new EntityNotFoundException(StatusCodes.WRONG_PASSWORD.name(),"Wrong password");
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getUsername(),
                        authenticationRequest.getPassword()
                ));

        String generatedToken = jwtService.generateToken(user);

        return new AuthenticationResponse(generatedToken);
    }
}
