package com.salonsync.payload.dto;

import lombok.Data;

@Data
public class UserRequest {
    private String username;
    private String enabled;
    private String firstName;
    private String lastName;
    private String email;
}
