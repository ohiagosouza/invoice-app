package com.hiagosouza.api.quoted.enums;

public enum CustomerType {
    PESSOA_FISICA("Pessoa Fisica"),
    PESSOA_JURIDICA("Pessoa Juridica"),
    GOVERNO("Governo");

    private final String customerType;

    CustomerType(String customerType) {
        this.customerType = customerType;
    }

    public String getCustomerType() {
        return customerType;
    }
}
