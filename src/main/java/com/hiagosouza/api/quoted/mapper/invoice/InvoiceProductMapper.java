package com.hiagosouza.api.quoted.mapper.invoice;

import com.hiagosouza.api.quoted.dtos.invoice.InvoiceProductRequestDTO;
import com.hiagosouza.api.quoted.model.InvoiceProductModel;

import java.math.BigDecimal;

public class InvoiceProductMapper {
    public static InvoiceProductModel toModel(InvoiceProductRequestDTO dto, BigDecimal unitPrice) {
       InvoiceProductModel model = new InvoiceProductModel();
       model.setProductId(dto.getProductId());
       model.setQuantity(dto.getQuantity());
       model.setUnitPrice(unitPrice);
       model.setSubtotal(unitPrice.multiply(BigDecimal.valueOf(dto.getQuantity())));

       return model;
    }
}
