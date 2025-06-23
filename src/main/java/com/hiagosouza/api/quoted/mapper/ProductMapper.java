package com.hiagosouza.api.quoted.mapper;

import com.hiagosouza.api.quoted.model.Product;
import com.hiagosouza.api.quoted.model.ProductModel;

public class ProductMapper {
    public static ProductModel toModel(Product product) {
        ProductModel productModel = new ProductModel();
        productModel.setProductName(product.getProductName());
        productModel.setDescription(product.getDescription());
        productModel.setPrice(product.getPrice());
        productModel.setCategory(product.getCategory());
        productModel.setStock(product.getStock());

        return productModel;
    }
}