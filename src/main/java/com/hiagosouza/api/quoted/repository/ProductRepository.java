package com.hiagosouza.api.quoted.repository;

import com.hiagosouza.api.quoted.model.ProductModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends MongoRepository<ProductModel, UUID> {
    ProductModel findByIdAndOwnerId(String productId, String ownerId);
    Optional<ProductModel> findByProductNameAndOwnerId(String productName, String ownerId);
    List<ProductModel> findByProductNameStartingWithIgnoreCase(String prefix);
    List<ProductModel> findByOwnerId(String ownerId);

}
