package com.hiagosouza.api.quoted.mapper.invoice;

import com.hiagosouza.api.quoted.dtos.invoice.InvoiceRequestDTO;
import com.hiagosouza.api.quoted.dtos.invoice.InvoiceResponseDTO;
import com.hiagosouza.api.quoted.model.InvoiceModel;

import java.util.Optional;

public class InvoiceMapper {
    public static InvoiceModel toModel(InvoiceRequestDTO invoiceRequest) {
        InvoiceModel invoiceModel = new InvoiceModel();
        invoiceModel.setOwnerId(invoiceRequest.getOwnerId());
        invoiceModel.setCustomerDocument(invoiceRequest.getCustomerDocument());
        invoiceModel.setTax(invoiceRequest.getTax());
        invoiceModel.setDiscount(invoiceRequest.getDiscount());
        return invoiceModel;
    }

    public static InvoiceResponseDTO toResponseDTO(InvoiceModel invoiceModel) {
        InvoiceResponseDTO responseDTO = new InvoiceResponseDTO();
        responseDTO.setInvoiceId(invoiceModel.getInvoiceId());
        responseDTO.setCustomer(invoiceModel.getCustomer());
        responseDTO.setTax(invoiceModel.getTax());
        responseDTO.setDiscount(invoiceModel.getDiscount());
        responseDTO.setInvoiceStatus(invoiceModel.getInvoiceStatus());
        responseDTO.setCreatedAt(invoiceModel.getCreatedAt());
        responseDTO.setUpdatedAt(invoiceModel.getUpdatedAt());

        return responseDTO;
    }
}
