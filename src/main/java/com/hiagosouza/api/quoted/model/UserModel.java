package com.hiagosouza.api.quoted.model;

import com.hiagosouza.api.quoted.enums.UserStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
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
    private String name;
    private String phoneNumber;
    @NotNull
    private String email;
    @NotNull
    private String password;
    @NotNull
    private String document;
    private User.PlanTypeEnum planType;
    private User.StatusEnum status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private AddressModel address;
}
