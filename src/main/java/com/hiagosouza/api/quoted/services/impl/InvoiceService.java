package com.hiagosouza.api.quoted.services.impl;

import com.hiagosouza.api.quoted.dtos.invoice.InvoiceProductRequestDTO;
import com.hiagosouza.api.quoted.dtos.invoice.InvoiceRequestDTO;
import com.hiagosouza.api.quoted.dtos.invoice.InvoiceResponseDTO;
import com.hiagosouza.api.quoted.exception.NotFound;
import com.hiagosouza.api.quoted.mapper.invoice.InvoiceMapper;
import com.hiagosouza.api.quoted.mapper.invoice.InvoiceProductMapper;
import com.hiagosouza.api.quoted.model.*;
import com.hiagosouza.api.quoted.repository.InvoiceRepository;
import com.hiagosouza.api.quoted.security.AuthUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.InternalException;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
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
        invoiceModel.setInvoiceId("INV-" + invoiceId);
        invoiceModel.setProducts(productItems);
        invoiceModel.setCustomer(customer);
        invoiceModel.setSubtotal(subtotal);
        invoiceModel.setOwnerId(owner.getId());
        invoiceModel.setTotal(subtotal.subtract(discount));
        invoiceModel.setCreatedAt(LocalDateTime.now());
        invoiceModel.setUpdatedAt(LocalDateTime.now());
        try {
            InvoiceModel saved = invoiceRepository.save(invoiceModel);
            return InvoiceMapper.toResponseDTO(saved);
        } catch (
                Exception e) {
            log.info("***** Failed creating Invoice *****");
            throw new InternalException("Failed creating Invoice: " + e.getMessage());
        }
    }


    public InvoiceResponseDTO findInvoice(String invoiceId, String ownerId) {
        InvoiceModel invoiceModel = invoiceRepository.findByInvoiceIdAndOwnerId(invoiceId, ownerId);
        if (invoiceModel == null) {
            throw new NotFound("Invoice not found for user: ", ownerId);
        }
        return InvoiceMapper.toResponseDTO(invoiceModel);
    }

}
