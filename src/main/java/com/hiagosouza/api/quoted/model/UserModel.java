package com.hiagosouza.api.quoted.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.UUID;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Encrypted;

import java.time.LocalDateTime;

@Document(collection = "users")
@Getter
@Setter
public class UserModel {
    @Id
    private String id;
    @NotNull
    @NotBlank(message = "Name can't be empty")
    private String name;
    private String phoneNumber;
    @NotNull
    @NotBlank(message = "Email can't be empty")
    private String email;
    @Encrypted
    @NotNull
    @NotBlank(message = "Password can't be empty")
    private String password;
    @NotNull
    @NotBlank(message = "Document can't be empty")
    private String document;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Address addressModel;
}
