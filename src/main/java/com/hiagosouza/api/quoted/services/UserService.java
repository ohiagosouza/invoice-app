package com.hiagosouza.api.quoted.services;

import com.hiagosouza.api.quoted.model.UserModel;
import com.hiagosouza.api.quoted.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private final BCryptPasswordEncoder bCryptPassword = new BCryptPasswordEncoder();
    
    public void createUser(UserModel user) {

        if (user != null
                && findByDocument(user.getDocument()).isEmpty()
                && findByEmail(user.getEmail()).isEmpty()) {
            String encryptedPassword = bCryptPassword.encode(user.getPassword());
            user.setPassword(encryptedPassword);
            userRepository.save(user);
        } else {
            throw new IllegalArgumentException("User not created");
        }
    }

    public Optional<UserModel> findByDocument(String document) {
        try {
            return userRepository.findByDocument(document);
        } catch (RuntimeException e) {
            throw new RuntimeException("Document not found: " + document);
        }
    }

    public Optional<UserModel> findByEmail(String email) {
        try {
            return userRepository.findByEmail(email);
        } catch (RuntimeException e) {
            throw new RuntimeException("Email not found: " + email);
        }
    }
}
