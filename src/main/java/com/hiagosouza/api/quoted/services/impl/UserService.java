package com.hiagosouza.api.quoted.services.impl;

import com.hiagosouza.api.quoted.enums.UserRole;
import com.hiagosouza.api.quoted.model.UserModel;
import com.hiagosouza.api.quoted.repository.UserRepository;
import com.hiagosouza.api.quoted.utils.DocumentUtils;
import com.hiagosouza.api.quoted.utils.PhoneUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class UserService {
    @Autowired
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private final BCryptPasswordEncoder bCryptPassword = new BCryptPasswordEncoder();

    public void createUser(UserModel user) {

        if (user.getDocument() != null
                && findByDocument(user.getDocument()) == null
                && findByEmail(user.getEmail()) == null) {
            log.info("***** Creating user with document: {} *****", user.getDocument());
            user.setPassword(bCryptPassword.encode(user.getPassword()));
            user.setDocument(DocumentUtils.cleanDocument(user.getDocument()));
            user.setPhoneNumber(PhoneUtils.cleanPhoneNumber(user.getPhoneNumber()));
            user.setUserRoles(List.of(UserRole.USER));
            user.setCreatedAt(LocalDateTime.now());
            user.setUpdatedAt(LocalDateTime.now());
            userRepository.save(user);
        } else {
            log.error("***** User not created, either null or already exists: {} *****", user.getDocument());
            throw new IllegalArgumentException("User not created");
        }
    }

    public void updateUserInformation(UserModel user) {
        UserModel userToUpdate = findById(user.getId());

        if (userToUpdate == null) {
            log.error("***** User not found for update: {} *****", user.getDocument());
            throw new IllegalArgumentException("User not found");
        }

        if (user.getPassword() != null && !user.getPassword().isBlank()) {
            userToUpdate.setPassword(bCryptPassword.encode(user.getPassword()));
        }

        if (user.getPhoneNumber() != null && !user.getPhoneNumber().isBlank()) {
            userToUpdate.setPhoneNumber(PhoneUtils.cleanPhoneNumber(user.getPhoneNumber()));
        }

        if (user.getEmail() != null && !user.getEmail().isBlank()) {
            userToUpdate.setEmail(user.getEmail());
        }

        if (user.getAddress() != null) {
            userToUpdate.setAddress(user.getAddress());
        }

        userToUpdate.setUpdatedAt(LocalDateTime.now());

        log.info("***** Updated user with id: {} *****", user.getId());
        userRepository.save(userToUpdate);
    }

    public UserModel findByDocument(String document) {
        log.info("***** Searching for user by document: {} *****", document);
        try {
            return userRepository.findByDocument(document);
        } catch (RuntimeException e) {
            throw new RuntimeException("Document not found: " + e.getMessage());
        }
    }

    public UserModel findByEmail(String email) {
        log.info("***** Searching for user by email: {} *****", email);
        try {
            return userRepository.findByEmail(email);
        } catch (RuntimeException e) {
            throw new RuntimeException("Email not found: " + email);
        }
    }

    public UserModel findById(String userId) {
        log.info("***** Searching for user by Id: {} *****", userId);
        try {
            return userRepository.findById(userId);
        } catch (RuntimeException e) {
            throw new RuntimeException("Id not found: " + userId);
        }
    }
}
