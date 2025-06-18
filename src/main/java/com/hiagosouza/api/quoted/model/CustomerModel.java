package com.hiagosouza.api.quoted.model;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "customers")
@Getter
@Setter
public class CustomerModel {
    @Id
    private String id;
    @NotNull
    private String name;
    private String phoneNumber;
    private String email;
    @NotNull
    private String document;
    @NotNull
    private String ownerId;
    @NotNull
    private LocalDateTime createdAt;
    @NotNull
    private LocalDateTime updatedAt;

    private AddressModel address;
}
