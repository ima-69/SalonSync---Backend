package com.salonsync.service.impl;

import com.salonsync.model.User;
import com.salonsync.payload.dto.SignupDTO;
import com.salonsync.payload.response.AuthResponse;
import com.salonsync.payload.response.TokenResponse;
import com.salonsync.repository.UserRepository;
import com.salonsync.service.AuthService;
import com.salonsync.service.KeycloakService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final KeycloakService keycloakService;

    @Override
    public AuthResponse login(String username, String password) throws Exception {
        TokenResponse tokenResponse = keycloakService.getAdminAccessToken(
                username,
                password,
                "password",
                null
        );

        AuthResponse authResponse = new AuthResponse();
        authResponse.setRefreshToken(tokenResponse.getRefreshToken());
        authResponse.setJwt(tokenResponse.getAccessToken());

        authResponse.setMessage("User logged in successfully");


        return authResponse;
    }

    @Override
    public AuthResponse signUp(SignupDTO req) throws Exception {

        keycloakService.createUser(req);

        User user = new User();
        user.setUsername(req.getUsername());
        user.setPassword(req.getPassword());
        user.setEmail(req.getEmail());
        user.setRole(req.getRole());
        user.setFirstName(req.getFirstName());
        user.setLastName(req.getLastName());
        user.setCreatedAt(LocalDateTime.now());

        user = userRepository.save(user);

        TokenResponse tokenResponse = keycloakService.getAdminAccessToken(
                req.getUsername(),
                req.getPassword(),
                "password",
                null
        );

        AuthResponse authResponse = new AuthResponse();
        authResponse.setRefreshToken(tokenResponse.getRefreshToken());
        authResponse.setJwt(tokenResponse.getAccessToken());
        authResponse.setRole(user.getRole());
        authResponse.setMessage("User registered successfully");


        return authResponse;
    }

    @Override
    public AuthResponse getAccessTokenFromRefreshToken(String refreshToken) throws Exception {

        TokenResponse tokenResponse = keycloakService.getAdminAccessToken(
                null,
                null,
                "refresh_token",
                refreshToken
        );

        AuthResponse authResponse = new AuthResponse();
        authResponse.setRefreshToken(tokenResponse.getRefreshToken());
        authResponse.setJwt(tokenResponse.getAccessToken());

        authResponse.setMessage("User logged in successfully");


        return authResponse;


        return null;
    }
}
