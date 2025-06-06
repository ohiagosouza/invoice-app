package com.hiagosouza.api.quoted.services;

import com.hiagosouza.api.quoted.model.UserModel;
import com.hiagosouza.api.quoted.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserModel createUser(UserModel userModel) {
        if(userModel != null) {
            try {
                userRepository.save(userModel);
            } catch (RuntimeException ex) {
                throw new RuntimeException(ex.getMessage());
            }
        }
        return userModel;
    }

    public Optional<UserModel> findByEmail(String email) {
        try {
            return userRepository.findByEmail(email);
        } catch (RuntimeException e) {
            throw new RuntimeException("User Not found" + e.getMessage());
        }
    }
}
