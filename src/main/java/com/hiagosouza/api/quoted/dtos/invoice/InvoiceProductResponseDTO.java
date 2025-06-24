package com.hiagosouza.api.quoted.dtos.invoice;

import lombok.Data;

@Data
public class InvoiceProductResponseDTO {
    private String productId;
    private String productName;
    private String description;
    private Double unitPrice;
    private String category;
    private Integer quantity;
    private Double subtotal;
}
