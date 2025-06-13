package com.hiagosouza.api.quoted.controller.impl;

import com.hiagosouza.api.quoted.api.UserApi;
import com.hiagosouza.api.quoted.controller.BaseController;
import com.hiagosouza.api.quoted.mapper.UserMapper;
import com.hiagosouza.api.quoted.model.UserRequest;
import com.hiagosouza.api.quoted.model.UserModel;
import com.hiagosouza.api.quoted.model.UserResponse;
import com.hiagosouza.api.quoted.services.impl.UserService;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.hiagosouza.api.quoted.model.UserRequest.UserRoleEnum.USER;

@RestController
@Log4j2
public class UserController extends BaseController implements UserApi {

    @Autowired
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Override
    @PostMapping("/user/register")
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest user) {
        log.info("---- Creating new user with document: {} ----", user.getDocument());
        user.setUserRole(USER);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        UserModel userModel = UserMapper.toModel(user);

        try {
            userService.createUser(userModel);
        } catch (IllegalArgumentException e) {
            log.error("User creation failed: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        log.info("User created successfully: {}", user.getDocument());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/user/{document}")
    public ResponseEntity<UserModel> searchUserByDocument(@PathVariable String document) {
        Optional<UserModel> user = userService.findByDocument(document);
        return user.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
