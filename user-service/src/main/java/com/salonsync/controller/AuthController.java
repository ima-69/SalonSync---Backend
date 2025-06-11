package com.salonsync.controller;

import com.salonsync.payload.dto.LoginDTO;
import com.salonsync.payload.dto.SignupDTO;
import com.salonsync.payload.response.AuthResponse;
import com.salonsync.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signup(
            @RequestBody SignupDTO req
    ) throws Exception {

        AuthResponse res = authService.signUp(req);
        return ResponseEntity.ok(res);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @RequestBody LoginDTO req
    ) throws Exception {

        AuthResponse res = authService.login(
                req.getUsername(),
                req.getPassword()
        );
        return ResponseEntity.ok(res);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @RequestBody LoginDTO req
    ) throws Exception {

        AuthResponse res = authService.login(
                req.getUsername(),
                req.getPassword()
        );
        return ResponseEntity.ok(res);
    }



}
