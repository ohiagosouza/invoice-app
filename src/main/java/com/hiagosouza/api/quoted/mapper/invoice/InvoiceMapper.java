package com.hiagosouza.api.quoted.mapper.invoice;

import com.hiagosouza.api.quoted.dtos.invoice.InvoiceProductRequestDTO;
import com.hiagosouza.api.quoted.dtos.invoice.InvoiceRequestDTO;
import com.hiagosouza.api.quoted.model.InvoiceModel;

public class InvoiceMapper {
    public static InvoiceModel toModel(InvoiceRequestDTO invoiceRequest) {
        InvoiceModel invoiceModel = new InvoiceModel();
        invoiceModel.setOwnerId(invoiceRequest.getOwnerId());
        invoiceModel.setCustomerDocument(invoiceRequest.getCustomerDocument());
        invoiceModel.setProducts(invoiceRequest.getProducts());
        invoiceModel.setTax(invoiceRequest.getTax());
        invoiceModel.setDiscount(invoiceRequest.getDiscount());
        return invoiceModel;
    }
}
