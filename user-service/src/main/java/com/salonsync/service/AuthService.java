package com.salonsync.service;

import com.salonsync.payload.response.AuthResponse;

public interface AuthService {

    AuthResponse login(String username, String password);
    AuthResponse signUp(String username, String password);
    AuthResponse getAccessTokenFromRefreshToken(String refreshToken);

}
