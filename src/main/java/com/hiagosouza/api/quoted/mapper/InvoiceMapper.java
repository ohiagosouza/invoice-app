package com.hiagosouza.api.quoted.mapper;

import com.hiagosouza.api.quoted.enums.InvoiceStatus;
import com.hiagosouza.api.quoted.model.CustomerModel;
import com.hiagosouza.api.quoted.model.InvoiceModel;
import com.hiagosouza.api.quoted.model.InvoiceRequest;

import java.time.LocalDateTime;

public class InvoiceMapper {
    public static InvoiceModel toModel(InvoiceRequest invoice) {
        InvoiceModel invoiceModel = new InvoiceModel();
        invoiceModel.setInvoiceId(invoice.getId());
        invoiceModel.setOwnerId(invoice.getOwnerId());
        invoiceModel.setCustomerDocument(invoice.getCustomerDocument());
        invoiceModel.setInvoiceStatus(InvoiceStatus.valueOf(InvoiceStatus.CREATED.name()));
        invoiceModel.setItems(invoice.getItems());
        invoiceModel.setTax(invoice.getTax());
        invoiceModel.setSubtotal(invoice.getSubtotal());
        invoiceModel.setDiscount(invoice.getDiscount());
        invoiceModel.setTotal(invoice.getTotal());
        invoiceModel.setCreatedAt(LocalDateTime.now());
        invoiceModel.setUpdatedAt(LocalDateTime.now());
        return invoiceModel;
    }
}
