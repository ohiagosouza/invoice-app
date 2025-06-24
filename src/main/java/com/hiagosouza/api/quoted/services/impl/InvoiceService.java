package com.hiagosouza.api.quoted.services.impl;

import com.hiagosouza.api.quoted.dtos.invoice.InvoiceProductRequestDTO;
import com.hiagosouza.api.quoted.dtos.invoice.InvoiceRequestDTO;
import com.hiagosouza.api.quoted.dtos.invoice.InvoiceResponseDTO;
import com.hiagosouza.api.quoted.enums.InvoiceStatus;
import com.hiagosouza.api.quoted.mapper.invoice.InvoiceMapper;
import com.hiagosouza.api.quoted.mapper.invoice.InvoiceProductMapper;
import com.hiagosouza.api.quoted.model.*;
import com.hiagosouza.api.quoted.repository.InvoiceRepository;
import com.hiagosouza.api.quoted.security.AuthUtils;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class InvoiceService {
    private final InvoiceRepository invoiceRepository;
    private final ProductService productService;
    private final UserService userService;
    private final CustomerService customerService;

    public InvoiceService(InvoiceRepository invoiceRepository, ProductService productService, UserService userService, CustomerService customerService) {
        this.invoiceRepository = invoiceRepository;
        this.productService = productService;
        this.userService = userService;
        this.customerService = customerService;
    }

    public InvoiceResponseDTO createInvoice(InvoiceRequestDTO invoiceRequestDTO) {
        String email = AuthUtils.getAuthenticatedUserEmail();
        UserModel owner = userService.findByEmail(email);

        if (invoiceRequestDTO.getOwnerId() == null) {
            throw new IllegalArgumentException("***** Invoice not created *****");
        }

        int invoiceId = invoiceRepository.countInvoiceByOwnerId(invoiceRequestDTO.getOwnerId()) + 1;
        List<InvoiceProductModel> productItems = new ArrayList<>();
        BigDecimal subtotal = BigDecimal.ZERO;

        for (InvoiceProductRequestDTO productDTO : invoiceRequestDTO.getProducts()) {
            ProductModel product = productService.findProductById(productDTO.getProductId(), owner.getId());
            InvoiceProductModel productModel = InvoiceProductMapper.toModel(productDTO, product.getPrice());
            productModel.setProductName(product.getProductName());
            productModel.setDescription(product.getDescription());
            productModel.setCategory(product.getCategory());
            productItems.add(productModel);

            BigDecimal itemSubtotal = productModel.getUnitPrice().multiply(new BigDecimal(productModel.getQuantity()));
            subtotal = subtotal.add(itemSubtotal);
        }

        BigDecimal discount = invoiceRequestDTO.getDiscount() != null ? invoiceRequestDTO.getDiscount() : BigDecimal.ZERO;

        CustomerModel customer = customerService.findCustomerByDocumentAndOwnerId(invoiceRequestDTO.getCustomerDocument(), owner.getId());

        InvoiceModel invoiceModel = InvoiceMapper.toModel(invoiceRequestDTO);
        invoiceModel.setId(UUID.randomUUID().toString());
        invoiceModel.setInvoiceId("INV-" + invoiceId);
        invoiceModel.setInvoiceStatus(InvoiceStatus.CREATED);
        invoiceModel.setOwnerId(owner.getId());
        invoiceModel.setProducts(productItems);
        invoiceModel.setCustomer(customer);
        invoiceModel.setSubtotal(subtotal);
        invoiceModel.setTotal(subtotal.subtract(discount));
        invoiceModel.setCreatedAt(LocalDateTime.now());
        invoiceModel.setUpdatedAt(LocalDateTime.now());
        InvoiceModel saved = invoiceRepository.save(invoiceModel);
        return InvoiceMapper.toResponseDTO(saved);
    }


    public InvoiceResponseDTO findInvoice(String invoiceId, String ownerId) {
        try {
            return invoiceRepository.findByInvoiceIdAndOwnerId(invoiceId, ownerId);
        } catch (NotFoundException e) {
            throw new NotFoundException("Invoice not found " + e.getMessage());
        }
    }

}
