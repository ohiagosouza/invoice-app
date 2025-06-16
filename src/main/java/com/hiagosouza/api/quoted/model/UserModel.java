package com.hiagosouza.api.quoted.model;

import com.hiagosouza.api.quoted.enums.PlanType;
import com.hiagosouza.api.quoted.enums.UserRole;
import com.hiagosouza.api.quoted.enums.UserStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "users")
@Getter
@Setter
public class UserModel {
    @Id
    private String id;
    @NotNull
    private String businessName;
    private String phoneNumber;
    @NotNull
    private String email;
    @NotNull
    private String password;
    @NotNull
    private String document;
    private List<UserRole> userRoles;
    @Schema(description = "Plan Type", example = "FREE", allowableValues = {"FREE", "ESSENTIAL", "PRO", "ENTERPRISE"})
    private PlanType planType;
    @Schema(description = "User status", example = "ACTIVE", allowableValues = {"ACTIVE", "INACTIVE", "SUSPENDED"})
    private UserStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private AddressModel address;
}
