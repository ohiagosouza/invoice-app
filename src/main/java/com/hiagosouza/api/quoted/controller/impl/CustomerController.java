package com.hiagosouza.api.quoted.controller.impl;

import com.hiagosouza.api.quoted.controller.BaseController;
import com.hiagosouza.api.quoted.mapper.CustomerMapper;
import com.hiagosouza.api.quoted.model.CustomerModel;
import com.hiagosouza.api.quoted.model.CustomerRequest;
import com.hiagosouza.api.quoted.model.CustomerResponse;
import com.hiagosouza.api.quoted.model.UserModel;
import com.hiagosouza.api.quoted.security.AuthUtils;
import com.hiagosouza.api.quoted.services.impl.CustomerService;
import com.hiagosouza.api.quoted.services.impl.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
public class CustomerController extends BaseController {

    private final CustomerService customerService;
    private final UserService userService;

    public CustomerController(CustomerService customerService, UserService userService) {
        this.customerService = customerService;
        this.userService = userService;
    }

    @PostMapping("/customers/register")
    public ResponseEntity<CustomerResponse> createCustomer(@Valid @RequestBody CustomerRequest customer) {
        CustomerModel customerModel = CustomerMapper.toModel(customer);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        if (email != null) {
            UserModel user = userService.findByEmail(email);
            customerModel.setOwnerId(user.getId());
        }

        try {
            customerService.createCustomer(customerModel);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/customers/list-all")
    public ResponseEntity<?> getAllCustomers() {
        String email = AuthUtils.getAuthenticatedUserEmail();

        if(email != null) {
            UserModel owner = userService.findByEmail(email);
            List<CustomerModel> customerList = new ArrayList<>(customerService.getCustomers(owner.getId()));
            return ResponseEntity.status(HttpStatus.OK).body(customerList);
        } else {
            log.error("***** No authentication found in context *****");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }
}
