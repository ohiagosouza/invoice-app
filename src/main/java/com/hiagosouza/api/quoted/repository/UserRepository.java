package com.hiagosouza.api.quoted.repository;

import com.hiagosouza.api.quoted.model.UserModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface UserRepository extends MongoRepository<UserModel, UUID> {
    UserModel findByEmail(String email);
    UserModel findByDocument(String document);
    UserModel findById(String userId);
}
