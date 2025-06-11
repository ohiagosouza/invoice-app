package com.hiagosouza.api.quoted.controller.impl;

import com.hiagosouza.api.quoted.api.ClientApi;
import com.hiagosouza.api.quoted.api.UserApi;
import com.hiagosouza.api.quoted.controller.BaseController;
import com.hiagosouza.api.quoted.mapper.UserMapper;
import com.hiagosouza.api.quoted.model.User;
import com.hiagosouza.api.quoted.model.UserModel;
import com.hiagosouza.api.quoted.services.UserService;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@Log4j2
public class UserController extends BaseController implements UserApi {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/public/user/register")
    public ResponseEntity<User> createNewUser(@Valid @RequestBody User user) {
        log.info("----- START CREATING USER -----");
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        UserModel userModel = UserMapper.toModel(user);

        try {
            userService.createUser(userModel);
           log.info("----- FINISHED CREATING USER -----");
        } catch (Exception e) {
            log.info("----- CONFLICT CREATING USER-----");
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    @GetMapping("/public/user/{document}")
    public ResponseEntity<UserModel> searchUserByDocument(@PathVariable String document) {
        System.out.println("---- Start Searching User -----");
        Optional<UserModel> user = userService.findByDocument(document);
        System.out.println("---- End Searching User -----");
        return user.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());

    }

}
