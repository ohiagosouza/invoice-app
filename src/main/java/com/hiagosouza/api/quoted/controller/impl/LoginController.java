package com.hiagosouza.api.quoted.controller.impl;

import com.hiagosouza.api.quoted.api.AuthApi;
import com.hiagosouza.api.quoted.controller.BaseController;
import com.hiagosouza.api.quoted.model.AuthRequest;
import com.hiagosouza.api.quoted.model.AuthResponse;
import com.hiagosouza.api.quoted.model.UserModel;

import com.hiagosouza.api.quoted.security.JwtUtils;
import com.hiagosouza.api.quoted.services.AuthLoginService;
import com.hiagosouza.api.quoted.services.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class LoginController extends BaseController implements AuthApi {
    @Autowired
    private final UserService userService;


    private AuthLoginService authLoginService;

    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/auth/login")
    public ResponseEntity<?> authLogin(@RequestBody AuthRequest authRequest) {
        System.out.println("---- Login Request Started ----");
        Optional<UserModel> user = userService.findByEmail(authRequest.getEmail());

        if (user.isEmpty() || !passwordEncoder.matches(authRequest.getPassword(), user.get().getPassword())) {
            System.out.println("---- Login Request Failed ----");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new AuthResponse());
        }
        AuthResponse authResponse = new AuthResponse();
        String token = jwtUtils.generateToken(user.get().getEmail());
        return ResponseEntity.ok(authResponse.token(token));

    }
}
