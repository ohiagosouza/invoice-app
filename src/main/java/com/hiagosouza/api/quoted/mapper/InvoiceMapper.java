package com.hiagosouza.api.quoted.mapper;

import com.hiagosouza.api.quoted.model.InvoiceModel;
import com.hiagosouza.api.quoted.model.InvoiceRequest;

public class InvoiceMapper {
    public static InvoiceModel toModel(InvoiceRequest invoice) {
        InvoiceModel invoiceModel = new InvoiceModel();
        invoiceModel.setOwnerId(invoice.getOwnerId());
        invoiceModel.setCustomerDocument(invoice.getCustomerDocument());
        invoiceModel.setProducts(invoice.getProducts());
        invoiceModel.setTax(invoice.getTax());
        invoiceModel.setSubtotal(invoice.getSubtotal());
        invoiceModel.setDiscount(invoice.getDiscount());
        invoiceModel.setTotal(invoice.getTotal());
        return invoiceModel;
    }
}
