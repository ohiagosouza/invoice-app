package com.hiagosouza.api.quoted.repository;

import com.hiagosouza.api.quoted.model.InvoiceModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface InvoiceRepository extends MongoRepository<InvoiceModel, String> {
    InvoiceModel findByInvoiceIdAndOwnerId(String invoiceId, String ownerId);
    Integer countInvoiceByOwnerId(String ownerId);

}
