package com.hiagosouza.api.quoted.dtos.invoice;

import com.hiagosouza.api.quoted.enums.InvoiceStatus;
import com.hiagosouza.api.quoted.model.CustomerModel;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class InvoiceResponseDTO {

    private String id;
    private CustomerModel customer;
    private InvoiceStatus status;
    private BigDecimal tax;
    private BigDecimal subtotal;
    private List<InvoiceProductResponseDTO> products;
    private BigDecimal discount;
    private BigDecimal total;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
