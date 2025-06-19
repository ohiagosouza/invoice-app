package com.hiagosouza.api.quoted.services.impl;

import com.hiagosouza.api.quoted.enums.InvoiceStatus;
import com.hiagosouza.api.quoted.model.InvoiceModel;
import com.hiagosouza.api.quoted.model.ProductItem;
import com.hiagosouza.api.quoted.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class InvoiceService {
    private final InvoiceRepository invoiceRepository;

    @Autowired
    public InvoiceService(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    public InvoiceModel createInvoice(InvoiceModel invoice) {
        if (invoice != null) {
            int invoiceId = invoiceRepository.countInvoiceByOwnerId(invoice.getOwnerId()) + 1;
            invoice.setInvoiceId("INV-" + invoiceId);
            invoice.setInvoiceStatus(InvoiceStatus.CREATED);
            invoice.setCreatedAt(LocalDateTime.now());
            invoice.setUpdatedAt(LocalDateTime.now());
            invoice.setSubtotal(calculateSubtotal(invoice.getItems()) + invoice.getTax());
            invoice.setTotal(invoice.getSubtotal() - invoice.getDiscount());
            return invoiceRepository.save(invoice);
        } else {
            throw new IllegalArgumentException("***** Invoice not created *****");
        }
    }

    public double calculateSubtotal(List<ProductItem> items) {
        return items.stream()
                .mapToDouble(item -> item.getQuantity() * item.getUnitPrice())
                .sum();
    }

    public InvoiceModel findByInvoiceId(String invoiceId, String ownerId) {
        try {
           return invoiceRepository.findByInvoiceIdAndOwnerId(invoiceId, ownerId);
        } catch (NotFoundException e){
            throw new NotFoundException("Invoice not found " + e.getMessage());
        }
    }
}
