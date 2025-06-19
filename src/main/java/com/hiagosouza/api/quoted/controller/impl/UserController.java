package com.hiagosouza.api.quoted.controller.impl;

import com.hiagosouza.api.quoted.controller.BaseController;
import com.hiagosouza.api.quoted.enums.UserRole;
import com.hiagosouza.api.quoted.mapper.UserMapper;
import com.hiagosouza.api.quoted.model.UserRequest;
import com.hiagosouza.api.quoted.model.UserModel;
import com.hiagosouza.api.quoted.model.UserResponse;
import com.hiagosouza.api.quoted.security.AuthUtils;
import com.hiagosouza.api.quoted.services.impl.UserService;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

@RestController
@Log4j2
public class UserController extends BaseController {

    @Autowired
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user/register")
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest user) {
        UserModel userModel = UserMapper.toModel(user);

        try {
            userService.createUser(userModel);
        } catch (IllegalArgumentException e) {
            log.error("***** User creation failed: {} *****", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/user/me")
    public ResponseEntity<?> showMe() {
        String email = AuthUtils.getAuthenticatedUserEmail();

        try {
            UserModel user = userService.findByEmail(email);
            return ResponseEntity.status(HttpStatus.OK).body(user);
        } catch (NotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/user/update")
    public ResponseEntity<UserResponse> updateUser(@Valid @RequestBody UserRequest user) {
        UserModel userModel = UserMapper.toModel(user);

        try {
            userService.updateUser(userModel);
        } catch (IllegalArgumentException e) {
            log.error("***** User update failed: {} *****", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
