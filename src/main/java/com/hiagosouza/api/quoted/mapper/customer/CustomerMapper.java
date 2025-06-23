package com.hiagosouza.api.quoted.mapper.customer;

import com.hiagosouza.api.quoted.model.*;

public class CustomerMapper {
    public static CustomerModel toModel(CustomerRequest customer) {
        CustomerModel customerModel = new CustomerModel();
        customerModel.setName(customer.getName());
        customerModel.setPhoneNumber(customer.getPhoneNumber());
        customerModel.setOwnerId(customer.getId());
        customerModel.setEmail(customer.getEmail());
        customerModel.setDocument(customer.getDocument());
        customerModel.setCreatedAt(customer.getCreatedAt());
        customerModel.setUpdatedAt(customer.getUpdatedAt());

        if (customer.getAddress() != null) {
            AddressModel address = getAddress(customer);
            customerModel.setAddress(address);
        }

        return customerModel;
    }

    private static AddressModel getAddress(CustomerRequest customer) {
        AddressModel address = new AddressModel();
        if (customer.getAddress() != null) {
            address.setStreet(customer.getAddress().getStreet());
            address.setNumber(customer.getAddress().getNumber());
            address.setComplement(customer.getAddress().getComplement());
            address.setNeighborhood(customer.getAddress().getNeighborhood());
            address.setCity(customer.getAddress().getCity());
            address.setState(customer.getAddress().getState());
            address.setCountry(customer.getAddress().getCountry());
            address.setZipCode(customer.getAddress().getZipCode());
        } else {
            System.out.println("No Address Given");
        }
        return address;
    }
}
