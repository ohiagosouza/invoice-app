package com.hiagosouza.api.quoted.controller.impl;

import com.hiagosouza.api.quoted.controller.BaseController;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


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

        InvoiceModel invoice = invoiceService.findInvoice(id, owner.getId());

        if(invoice != null) {
            return ResponseEntity.status(HttpStatus.OK).body(invoice);
        } else  {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
