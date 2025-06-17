package com.hiagosouza.api.quoted.model;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "products")
@Getter
@Setter
public class ProductModel {
    @Id
    private String id;
    @NotNull
    private String productName;
    @NotNull
    private String ownerId;
    private String description;
    @NotNull
    private Double price;
    @NotNull
    private String category;
    @NotNull
    private Integer stock;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
