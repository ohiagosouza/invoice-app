package com.hiagosouza.api.quoted.repository;

import com.hiagosouza.api.quoted.model.ClientModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.UUID;

public interface ClientRepository extends MongoRepository<ClientModel, UUID> {
    Optional<ClientModel> findByEmail(String email);
    Optional<ClientModel> findByDocument(String document);
    Optional<ClientModel> findByName(String name);
}
