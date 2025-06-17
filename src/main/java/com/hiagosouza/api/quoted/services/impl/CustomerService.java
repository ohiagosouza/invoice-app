package com.hiagosouza.api.quoted.services.impl;

import com.hiagosouza.api.quoted.model.CustomerModel;
import com.hiagosouza.api.quoted.repository.CustomerRepository;
import com.hiagosouza.api.quoted.utils.DocumentUtils;
import com.hiagosouza.api.quoted.utils.PhoneUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CustomerService {
    @Autowired
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void createCustomer(CustomerModel customer) {
        if (customer.getDocument() != null && customerRepository.findByDocument(customer.getDocument()).isEmpty()) {
            log.info("***** Creating customer with document: {} *****", customer.getDocument());
            String cleanedDocument = DocumentUtils.cleanDocument(customer.getDocument());
            customer.setDocument(cleanedDocument);
            String cleanedPhone = PhoneUtils.cleanPhoneNumber(customer.getPhoneNumber());
            customer.setPhoneNumber(cleanedPhone);
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
        } catch (ArrayIndexOutOfBoundsException e) {
            log.error("***** Error fetching customers: {} *****", e.getMessage());
            throw new ArrayIndexOutOfBoundsException(e.getMessage());
        }
        return customers;
    }

}
