package com.hiagosouza.api.quoted.services.impl;

import com.hiagosouza.api.quoted.model.CustomerModel;
import com.hiagosouza.api.quoted.repository.CustomerRepository;
import com.hiagosouza.api.quoted.utils.DocumentUtils;
import com.hiagosouza.api.quoted.utils.PhoneUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class CustomerService {
    @Autowired
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void createCustomer(CustomerModel customer) {
        if (customer.getDocument() != null && customerRepository.findByDocument(customer.getDocument()) == null) {
            log.info("***** Creating customer with document: {} *****", customer.getDocument());
            customer.setId(UUID.randomUUID().toString());
            String cleanedDocument = DocumentUtils.cleanDocument(customer.getDocument());
            customer.setDocument(cleanedDocument);
            String cleanedPhone = PhoneUtils.cleanPhoneNumber(customer.getPhoneNumber());
            customer.setPhoneNumber(cleanedPhone);
            customer.setCreatedAt(LocalDateTime.now());
            customer.setUpdatedAt(LocalDateTime.now());

            customerRepository.save(customer);
        } else {
            log.error("***** Customer not created, either null or already exists: {} *****", customer.getDocument());
            throw new IllegalArgumentException("Customer not created");
        }
    }

    public List<CustomerModel> getCustomers(String ownerId) {
        List<CustomerModel> customers;
        try {
            log.info("***** Fetching customers for owner ID: {} *****", ownerId);
            customers = customerRepository.findCustomersByOwnerId(ownerId);
        } catch (IllegalArgumentException e) {
            log.error("***** Error fetching customers: {} *****", e.getMessage());
            throw new IllegalArgumentException(e.getMessage());
        }
        return customers;
    }

    public CustomerModel findCustomerByDocumentAndOwnerId(String document, String ownerId) {
        try {
            return customerRepository.findCustomerByDocumentAndOwnerId(document, ownerId);
        } catch (NotFoundException e) {
            throw new NotFoundException("Customer Not Found: " + e.getMessage());
        }
    }

}
