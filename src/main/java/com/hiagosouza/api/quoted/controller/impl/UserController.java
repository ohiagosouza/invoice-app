package com.hiagosouza.api.quoted.controller.impl;

import com.hiagosouza.api.quoted.api.ClientApi;
import com.hiagosouza.api.quoted.controller.BaseController;
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
public class UserController extends BaseController implements ClientApi {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/public/user")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        User userModel = new User();
        userModel.setName(user.getName());
        userModel.setPhoneNumber(user.getPhoneNumber());
        userModel.setEmail(user.getEmail());
        userModel.setAddress(user.getAddress());
        userModel.setDocument(user.getDocument());
        userModel.setPassword(user.getPassword());
        userModel.setCreatedAt(LocalDateTime.now());
        userModel.setUpdatedAt(LocalDateTime.now());
        userModel.setStatus(User.StatusEnum.ACTIVE);

        try {
            System.out.println("----- START CREATE USER -----");
            userService.createUser(userModel);
            System.out.println("----- END CREATE USER -----");
        } catch (Exception e) {
            System.out.println("----- END CREATE USER -----");
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
