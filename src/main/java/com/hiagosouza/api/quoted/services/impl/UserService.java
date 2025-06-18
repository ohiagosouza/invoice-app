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
            String encryptedPassword = bCryptPassword.encode(user.getPassword());
            user.setPassword(encryptedPassword);
            String cleanedDocument = DocumentUtils.cleanDocument(user.getDocument());
            user.setDocument(cleanedDocument);
            String cleanedPhone = PhoneUtils.cleanPhoneNumber(user.getPhoneNumber());
            user.setPhoneNumber(cleanedPhone);
            user.setUserRoles(List.of(UserRole.USER));
            user.setCreatedAt(LocalDateTime.now());
            user.setUpdatedAt(LocalDateTime.now());
            userRepository.save(user);
        } else {
            log.error("***** User not created, either null or already exists: {} *****", user.getDocument());
            throw new IllegalArgumentException("User not created");
        }
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
}
