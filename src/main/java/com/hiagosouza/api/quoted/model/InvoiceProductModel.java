package com.hiagosouza.api.quoted.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceProductModel {
    private ProductModel product;
    private int quantity;
    private BigDecimal subtotal;
}
