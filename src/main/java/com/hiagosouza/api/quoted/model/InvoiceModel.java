package com.hiagosouza.api.quoted.model;

import com.hiagosouza.api.quoted.dtos.invoice.InvoiceProductRequestDTO;
import com.hiagosouza.api.quoted.enums.InvoiceStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "invoices")
@Getter
@Setter
public class InvoiceModel {
    @Id
    private String invoiceId;
    @NotNull
    private String ownerId;
    private String customerDocument;
    @NotNull
    private CustomerModel customer;
    @Schema(description = "Plan Type", example = "FREE", allowableValues = { "CREATED", "ACCEPTED", "REJECTED", "OVERDUE", "PAID", "CANCELLED" })
    private InvoiceStatus invoiceStatus;
    private List<InvoiceProductRequestDTO> products;
    private Double tax;
    private Double subtotal;
    private Double discount;
    private Double total;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
