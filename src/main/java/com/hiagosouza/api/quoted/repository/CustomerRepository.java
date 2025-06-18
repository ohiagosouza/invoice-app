package com.hiagosouza.api.quoted.repository;

import com.hiagosouza.api.quoted.model.CustomerModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.UUID;

public interface CustomerRepository extends MongoRepository<CustomerModel, UUID> {
    CustomerModel findByEmail(String email);
    CustomerModel findByDocument(String document);
    CustomerModel findCustomerByDocumentAndOwnerId(String document, String ownerId);
    List<CustomerModel> findCustomersByOwnerId(String ownerId);
}
