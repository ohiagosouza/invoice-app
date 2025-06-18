package com.hiagosouza.api.quoted.mapper;

import com.hiagosouza.api.quoted.model.Product;
import com.hiagosouza.api.quoted.model.ProductModel;

public class ProductItemMapper {
    public static ProductModel toModel(Product product) {
        ProductModel productModel = new ProductModel();
        productModel.setId(product.getId());
        productModel.setProductName(product.getProductName());
        productModel.setPrice(product.getPrice());
        productModel.setCategory(product.getCategory());
        productModel.setStock(product.getStock());
        productModel.setCreatedAt(product.getCreatedAt());
        productModel.setUpdatedAt(product.getUpdatedAt());

        return productModel;
    }
}
