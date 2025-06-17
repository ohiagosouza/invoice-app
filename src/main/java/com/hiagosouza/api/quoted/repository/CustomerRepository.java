package com.hiagosouza.api.quoted.repository;

import com.hiagosouza.api.quoted.model.CustomerModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository extends MongoRepository<CustomerModel, UUID> {
    Optional<CustomerModel> findByEmail(String email);
    Optional<CustomerModel> findByDocument(String document);
    List<CustomerModel> findCustomersByOwnerId(String ownerId);
}
