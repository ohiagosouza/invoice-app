package com.hiagosouza.api.quoted.services.impl;

import com.hiagosouza.api.quoted.exception.NotFound;
import com.hiagosouza.api.quoted.model.ProductModel;
import com.hiagosouza.api.quoted.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public ProductModel createProduct(ProductModel product) {
        log.info("***** Starting method createProduct *****");

        if (product == null) {
            log.error("Product cannot be null");
            throw new IllegalArgumentException("Product cannot be null");
        }

        product.setId(UUID.randomUUID().toString());
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());

        try {
            ProductModel productSaved = productRepository.save(product);
            return productRepository.save(productSaved);
        } catch (Exception e) {
            log.error("Error creating product: {}", e.getMessage());
            throw new IllegalArgumentException("Product creation failed: " + e.getMessage());
        }
    }

    public List<ProductModel> findAllByOwnerId(String ownerId) {
        return productRepository.findByOwnerId(ownerId);
    }

    public List<ProductModel> findByPrefix(String prefix) {
        return productRepository.findByProductNameStartingWithIgnoreCase(prefix);
    }

    public ProductModel findByName(String productName, String ownerId) {
        Optional<ProductModel> product = productRepository.findByProductNameAndOwnerId(productName, ownerId);
        return product.orElseThrow(() -> new NotFound("Product not found with name: ", productName));
    }

    public ProductModel findProductById(String productId, String ownerId) {
        Optional<ProductModel> product = Optional.ofNullable(productRepository.findByIdAndOwnerId(productId, ownerId));
        return product.orElseThrow(() -> new NotFound("Product not found with id: ", productId));

    }
}
