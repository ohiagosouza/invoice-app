package com.hiagosouza.api.quoted.controller.impl;

import com.hiagosouza.api.quoted.controller.BaseController;
import com.hiagosouza.api.quoted.mapper.product.ProductMapper;
import com.hiagosouza.api.quoted.model.Product;
import com.hiagosouza.api.quoted.model.ProductModel;
import com.hiagosouza.api.quoted.model.UserModel;
import com.hiagosouza.api.quoted.security.AuthUtils;
import com.hiagosouza.api.quoted.services.impl.ProductService;
import com.hiagosouza.api.quoted.services.impl.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@RestController
public class ProductsController extends BaseController {

    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;


    @PostMapping("/products/create")
    public ResponseEntity<?> createProduct(@Valid @RequestBody Product product) {
        log.info("Creating product: {}", product.getProductName());
        ProductModel productModel = ProductMapper.toModel(product);

        String email = AuthUtils.getAuthenticatedUserEmail();

        if (email != null) {
            UserModel user = userService.findByEmail(email);
            productModel.setOwnerId(user.getId());
        }

        try {
            productService.createProduct(productModel);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (IllegalArgumentException e) {
            log.error("Product creation failed: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Product creation failed: " + e.getMessage());
        }
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<?> findProductById(@PathVariable String id) {
        String email = AuthUtils.getAuthenticatedUserEmail();

        if (email != null) {
            UserModel user = userService.findByEmail(email);
            Optional<ProductModel> product = Optional.ofNullable(productService.findProductById(id, user.getId()));

            return ResponseEntity.status(HttpStatus.OK).body(product);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/products/search/all")
    public ResponseEntity<?> searchAllProducts() {
        String email = AuthUtils.getAuthenticatedUserEmail();

        if (email != null) {
            UserModel user = userService.findByEmail(email);
            List<ProductModel> products = productService.findAllByOwnerId(user.getId()).stream()
                    .filter(owner -> Objects.equals(owner.getOwnerId(), user.getId())).toList();

            return ResponseEntity.status(HttpStatus.OK).body(products);
        } else {
            log.error("All product search failed");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No products found");
        }
    }

    @GetMapping("/products/search/prefix")
    public ResponseEntity<?> searchProductsWithPrefix(@RequestParam String prefix) {
        String email = AuthUtils.getAuthenticatedUserEmail();

        if (email != null) {
            UserModel user = userService.findByEmail(email);
            List<ProductModel> products = productService.findByPrefix(prefix).stream()
                    .filter(owner -> Objects.equals(owner.getOwnerId(), user.getId())).toList();

            return ResponseEntity.status(HttpStatus.OK).body(products);
        } else {
            log.error("Product search failed for prefix: {}", prefix);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }
    }
}
