package com.hiagosouza.api.quoted.dtos.invoice;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class InvoiceProductResponseDTO {
    private String id;
    private String productName;
    private String description;
    private BigDecimal price;
    private String category;
    private Integer stock;
    private Integer quantity;
    private BigDecimal totalPrice;
}
