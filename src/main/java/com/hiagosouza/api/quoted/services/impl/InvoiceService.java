package com.hiagosouza.api.quoted.services.impl;

import com.hiagosouza.api.quoted.enums.InvoiceStatus;
import com.hiagosouza.api.quoted.model.*;
import com.hiagosouza.api.quoted.repository.InvoiceRepository;
import com.hiagosouza.api.quoted.security.AuthUtils;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class InvoiceService {
    private final InvoiceRepository invoiceRepository;
    private final ProductService productService;
    private final UserService userService;

    public InvoiceService(InvoiceRepository invoiceRepository, ProductService productService, UserService userService) {
        this.invoiceRepository = invoiceRepository;
        this.productService = productService;
        this.userService = userService;
    }

    public InvoiceModel createInvoice(InvoiceModel invoice) {
        String email = AuthUtils.getAuthenticatedUserEmail();

        if (invoice != null) {
            UserModel owner = userService.findByEmail(email);
            int invoiceId = invoiceRepository.countInvoiceByOwnerId(invoice.getOwnerId()) + 1;
            List<ProductItem> productItems = invoice.getProducts().stream().map(itemRequest -> {
                ProductModel product = productService.findProductById(itemRequest.getProductId(), owner.getId());
                ProductItem productItem = new ProductItem();
                productItem.setProductId(product.getId());
                productItem.setProductName(product.getProductName());
                productItem.setQuantity(itemRequest.getQuantity());
                productItem.setUnitPrice(product.getPrice());
                return productItem;
            }).toList();

            double subtotal = productItems.stream()
                    .mapToDouble(item -> item.getQuantity() * item.getUnitPrice())
                    .sum();

            invoice.setInvoiceId("INV-" + invoiceId);
            invoice.setInvoiceStatus(InvoiceStatus.CREATED);
            invoice.setCreatedAt(LocalDateTime.now());
            invoice.setUpdatedAt(LocalDateTime.now());
            invoice.setSubtotal(subtotal + (invoice.getTax() != null ? invoice.getTax() : 0) - (invoice.getDiscount() != null ? invoice.getDiscount() : 0));
            invoice.setTotal(invoice.getSubtotal() - invoice.getDiscount());
            return invoiceRepository.save(invoice);
        } else {
            throw new IllegalArgumentException("***** Invoice not created *****");
        }
    }


    public InvoiceModel findInvoice(String invoiceId, String ownerId) {
        try {
            return invoiceRepository.findByInvoiceIdAndOwnerId(invoiceId, ownerId);
        } catch (NotFoundException e) {
            throw new NotFoundException("Invoice not found " + e.getMessage());
        }
    }

}
