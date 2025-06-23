package com.hiagosouza.api.quoted.mapper.invoice;

import com.hiagosouza.api.quoted.dtos.invoice.InvoiceProductRequestDTO;
import com.hiagosouza.api.quoted.model.InvoiceProductModel;
import com.hiagosouza.api.quoted.model.ProductModel;
import com.hiagosouza.api.quoted.services.impl.ProductService;

import java.math.BigDecimal;

public class InvoiceProductMapper {
    private final ProductService productService;

    public InvoiceProductMapper(ProductService productService) {
        this.productService = productService;
    }

    public InvoiceProductModel toInvoiceProductModel(InvoiceProductRequestDTO productRequest, String ownerId) {
        ProductModel product = productService.findProductById(productRequest.getProductId(), ownerId);
        BigDecimal subtotal = product.getPrice().multiply(BigDecimal.valueOf(productRequest.getQuantity()));
        return new InvoiceProductModel(
                product,
                productRequest.getQuantity(),
                subtotal
        );
    }
}
