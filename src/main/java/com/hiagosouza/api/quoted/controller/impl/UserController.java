package com.hiagosouza.api.quoted.controller.impl;

import com.hiagosouza.api.quoted.api.UsersApi;
import com.hiagosouza.api.quoted.model.User;
import com.hiagosouza.api.quoted.model.UserModel;
import com.hiagosouza.api.quoted.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController implements UsersApi {
    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    public ResponseEntity<Void> createUser(@Valid @RequestBody User user) {
        UserModel userModel = new UserModel();
        userModel.setName(user.getName());
        userModel.setPhoneNumber(user.getPhoneNumber());
        userModel.setEmail(user.getEmail());
        userModel.setAddressModel(user.getAddress());
        userModel.setDocument(user.getDocument());
        userModel.setPassword(user.getPassword());

        userService.createUser(userModel);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
