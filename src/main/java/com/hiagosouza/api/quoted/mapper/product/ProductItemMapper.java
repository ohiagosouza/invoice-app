package com.hiagosouza.api.quoted.mapper.product;

import com.hiagosouza.api.quoted.model.Product;
import com.hiagosouza.api.quoted.model.ProductModel;

import java.math.BigDecimal;

public class ProductItemMapper {
    public static ProductModel toModel(Product product) {
        ProductModel productModel = new ProductModel();
        productModel.setProductName(product.getProductName());
        productModel.setPrice(new BigDecimal(String.valueOf(product.getPrice())));
        productModel.setCategory(product.getCategory());
        productModel.setStock(product.getStock());
        productModel.setCreatedAt(product.getCreatedAt());
        productModel.setUpdatedAt(product.getUpdatedAt());

        return productModel;
    }
}
