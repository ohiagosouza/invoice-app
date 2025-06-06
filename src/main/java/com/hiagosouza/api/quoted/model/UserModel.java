package com.hiagosouza.api.quoted.model;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.UUID;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Encrypted;

@Document(collection = "users")
@Getter
@Setter
public class UserModel {
    @Id
    @UUID
    private String id;
    @NotNull
    private String name;
    private String phoneNumber;
    @NotNull
    private String email;
    @Encrypted
    @NotNull
    private String password;
    @NotNull
    private String document;
    private Address addressModel;
}
