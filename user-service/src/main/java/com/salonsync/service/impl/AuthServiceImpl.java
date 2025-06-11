package com.salonsync.service.impl;

import com.salonsync.payload.response.AuthResponse;
import com.salonsync.service.AuthService;

public class AuthServiceImpl implements AuthService {

    @Override
    public AuthResponse login(String username, String password) {
        return null;
    }

    @Override
    public AuthResponse signUp(String username, String password) {
        return null;
    }

    @Override
    public AuthResponse getAccessTokenFromRefreshToken(String refreshToken) {
        return null;
    }
}
