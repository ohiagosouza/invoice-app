package com.hiagosouza.api.quoted.repository;

import com.hiagosouza.api.quoted.model.ProductModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.UUID;

public interface ProductRepository extends MongoRepository<ProductModel, UUID> {
    ProductModel findByIdAndOwnerId(String productId, String ownerId);
    ProductModel findByProductName(String name);
    List<ProductModel> findByProductNameStartingWithIgnoreCase(String prefix);

}
