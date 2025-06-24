package com.hiagosouza.api.quoted.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceProductModel {
    private String productId;
    private String productName;
    private String description;
    private Integer quantity;
    private String category;
    private BigDecimal unitPrice;
    private BigDecimal subtotal;
}
