package com.salonsync.payload.response;

import lombok.Data;

@Data
public class AuthResponse {

    private String jwt;
    private String refreshToken;
    private String message;
    private String title;
    private String role;
}
