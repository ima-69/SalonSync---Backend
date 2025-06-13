package com.salonsync.payload.request;

import com.salonsync.domain.UserRole;
import lombok.Data;

@Data
public class SignupDto {
	private String email;
	private String password;
	private String phone;
	private String fullName;
	private UserRole role;
}