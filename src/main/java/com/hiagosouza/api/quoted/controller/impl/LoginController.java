package com.hiagosouza.api.quoted.controller.impl;

import com.hiagosouza.api.quoted.api.AuthApi;
import com.hiagosouza.api.quoted.controller.BaseController;
import com.hiagosouza.api.quoted.model.AuthRequest;
import com.hiagosouza.api.quoted.model.AuthResponse;
import com.hiagosouza.api.quoted.model.UserModel;

import com.hiagosouza.api.quoted.security.JwtUtils;
import com.hiagosouza.api.quoted.services.impl.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@Slf4j
public class LoginController extends BaseController implements AuthApi {

    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    public LoginController(UserService userService, JwtUtils jwtUtils, BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.jwtUtils = jwtUtils;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @PostMapping("/auth/user/login")
    public ResponseEntity<AuthResponse> userLogin(@Valid @RequestBody AuthRequest authRequest) {
        log.info("---- Login attempt with email: {} ----", authRequest.getEmail());
        Optional<UserModel> user = userService.findByEmail(authRequest.getEmail());

        if (user.isEmpty() || !passwordEncoder.matches(authRequest.getPassword(), user.get().getPassword())) {
            log.info("---- Login failed for email: {} ----", authRequest.getEmail());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new AuthResponse());
        }
        AuthResponse authResponse = new AuthResponse();
        String token = jwtUtils.generateToken(user.get().getEmail());
        log.info("---- Login successful for email: {} ----", authRequest.getEmail());
        return ResponseEntity.ok().body(authResponse.token(token));

    }
}
