package com.hiagosouza.api.quoted.model;

import com.hiagosouza.api.quoted.enums.PlanType;
import com.hiagosouza.api.quoted.enums.UserStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;

@Document(collection = "clients")
@Getter
@Setter
public class ClientModel {
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
