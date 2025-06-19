package com.hiagosouza.api.quoted.controller.impl;

import com.hiagosouza.api.quoted.controller.BaseController;
import com.hiagosouza.api.quoted.mapper.CustomerMapper;
import com.hiagosouza.api.quoted.mapper.InvoiceMapper;
import com.hiagosouza.api.quoted.model.*;
import com.hiagosouza.api.quoted.security.AuthUtils;
import com.hiagosouza.api.quoted.services.impl.CustomerService;
import com.hiagosouza.api.quoted.services.impl.InvoiceService;
import com.hiagosouza.api.quoted.services.impl.ProductService;
import com.hiagosouza.api.quoted.services.impl.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.InternalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
public class InvoiceController extends BaseController {

    private final UserService userService;
    private final InvoiceService invoiceService;
    private final CustomerService customerService;
    private final ProductService productService;

    public InvoiceController(UserService userService, InvoiceService invoiceService, CustomerService customerService, ProductService productService) {
        this.userService = userService;
        this.invoiceService = invoiceService;
        this.customerService = customerService;
        this.productService = productService;
    }

    @PostMapping("/invoices/create")
    public ResponseEntity<?> createInvoice(@Valid @RequestBody InvoiceRequest request) {
        InvoiceModel invoice = InvoiceMapper.toModel(request);
        String email = AuthUtils.getAuthenticatedUserEmail();
        UserModel owner = userService.findByEmail(email);
        invoice.setOwnerId(owner.getId());

        CustomerModel customer = customerService.findCustomerByDocumentAndOwnerId(invoice.getCustomerDocument(), owner.getId());

        try {
            invoice.setCustomer(customer);
            invoiceService.createInvoice(invoice);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (InternalException e) {
            throw new InternalException("Failed creating Invoice: " + e.getMessage());
        }

    }

    @GetMapping("/invoices/{id}")
    public ResponseEntity<?> findInvoice(@PathVariable String id) {
        String email = AuthUtils.getAuthenticatedUserEmail();
        UserModel owner = userService.findByEmail(email);

        try {
            InvoiceModel invoice = invoiceService.findByInvoiceId(id, owner.getId());
            return ResponseEntity.status(HttpStatus.OK).body(invoice);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invoice not Found: " + e.getMessage());
        }
    }
}
