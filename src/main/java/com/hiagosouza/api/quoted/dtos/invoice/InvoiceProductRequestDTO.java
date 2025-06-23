package com.hiagosouza.api.quoted.dtos.invoice;

import lombok.Data;

@Data
public class InvoiceProductRequestDTO {
    private String productId;
    private Integer quantity;
}
