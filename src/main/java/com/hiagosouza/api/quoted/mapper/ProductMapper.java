package com.hiagosouza.api.quoted.mapper;

import com.hiagosouza.api.quoted.model.Product;
import com.hiagosouza.api.quoted.model.ProductModel;

import java.util.Collections;
import java.util.List;

public class ProductMapper {
    public static ProductModel toModel(Product product) {
        ProductModel productModel = new ProductModel();
        productModel.setId(product.getId());
        productModel.setProductName(product.getProductName());
        productModel.setDescription(product.getDescription());
        productModel.setPrice(product.getPrice());
        productModel.setCategory(product.getCategory());
        productModel.setStock(product.getStock());
        productModel.setCreatedAt(product.getCreatedAt());
        productModel.setUpdatedAt(product.getUpdatedAt());

        return productModel;
    }

    public static ProductModel toList(ProductModel product) {
        ProductModel productModel = new ProductModel();
        productModel.setId(product.getId());
        productModel.setProductName(product.getProductName());
        productModel.setDescription(product.getDescription());
        productModel.setPrice(product.getPrice());
        productModel.setCategory(product.getCategory());
        productModel.setStock(product.getStock());
        productModel.setCreatedAt(product.getCreatedAt());
        productModel.setUpdatedAt(product.getUpdatedAt());

        return productModel;
    }
}