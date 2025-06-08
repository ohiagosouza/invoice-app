package com.hiagosouza.api.quoted.repository;

import com.hiagosouza.api.quoted.model.User;
import com.hiagosouza.api.quoted.model.UserModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends MongoRepository<User, UUID> {
    Optional<UserModel> findByEmail(String email);
    Optional<UserModel> findByDocument(String document);
}
