package com.hiagosouza.api.quoted.controller.impl;

import com.hiagosouza.api.quoted.api.AuthApi;
import com.hiagosouza.api.quoted.controller.BaseController;
import com.hiagosouza.api.quoted.model.AuthRequest;
import com.hiagosouza.api.quoted.model.AuthResponse;
import com.hiagosouza.api.quoted.services.AuthLoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.Optional;

@Slf4j
@RestController
public class UserLoginController extends BaseController implements AuthApi {

    private AuthLoginService authLoginService;

    public UserLoginController(AuthLoginService authLoginService){
        this.authLoginService = authLoginService;
    }

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return AuthApi.super.getRequest();
    }

    @Override
    public ResponseEntity<AuthResponse> authLoginPost(@RequestBody AuthRequest authRequest) {
        log.info("----- AUTH LOGIN POST -----");

        return AuthApi.super.authLoginPost(authRequest);
    }
}
