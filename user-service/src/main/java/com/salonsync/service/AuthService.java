package com.salonsync.service;

import com.salonsync.payload.dto.SignupDTO;
import com.salonsync.payload.response.AuthResponse;

public interface AuthService {

    AuthResponse login(String username, String password) throws Exception;
    AuthResponse signUp(SignupDTO req) throws Exception;
    AuthResponse getAccessTokenFromRefreshToken(String refreshToken) throws Exception;

}
