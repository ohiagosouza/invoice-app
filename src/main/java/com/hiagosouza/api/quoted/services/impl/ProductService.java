package com.hiagosouza.api.quoted.services.impl;

import com.hiagosouza.api.quoted.model.ProductModel;
import com.hiagosouza.api.quoted.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public ProductModel createProduct(ProductModel product) {
        if(product != null && findByName(product.getProductName()) == null) {
            product.setId(UUID.randomUUID().toString());
            product.setCreatedAt(LocalDateTime.now());
            product.setUpdatedAt(LocalDateTime.now());
            return productRepository.save(product);
        } else {
            throw new IllegalArgumentException("Product name cannot be null or empty");
        }
    }

    public List<ProductModel> findByNameStartingWithIgnoreCase(String prefix) {
        return productRepository.findByProductNameStartingWithIgnoreCase(prefix);
    }

    public ProductModel findByName(String productName) {
        if (productName != null) {
            return productRepository.findByProductName(productName);
        } else {
            throw new IllegalArgumentException("Product not found");
        }
    }

    public ProductModel findProductById(String productId, String ownerId) {
        if (productId != null) {
            return productRepository.findByIdAndOwnerId(productId, ownerId);
        } else {
            throw new IllegalArgumentException("Product not found");
        }
    }
}
