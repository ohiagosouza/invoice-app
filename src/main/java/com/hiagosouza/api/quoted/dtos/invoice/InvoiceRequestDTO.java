package com.hiagosouza.api.quoted.dtos.invoice;

import com.hiagosouza.api.quoted.enums.InvoiceStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class InvoiceRequestDTO {
    private String invoiceId;
    private String ownerId;
    private String customerDocument;
    private List<InvoiceProductRequestDTO> products;
    private InvoiceStatus invoiceStatus;
    private Double tax;
    private Double discount;
    private BigDecimal total;
    private BigDecimal subtotal;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
