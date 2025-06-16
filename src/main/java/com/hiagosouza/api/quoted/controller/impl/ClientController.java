package com.hiagosouza.api.quoted.controller.impl;

import com.hiagosouza.api.quoted.api.ClientsApi;
import com.hiagosouza.api.quoted.controller.BaseController;
import com.hiagosouza.api.quoted.mapper.ClientMapper;
import com.hiagosouza.api.quoted.model.ClientModel;
import com.hiagosouza.api.quoted.model.ClientRequest;
import com.hiagosouza.api.quoted.model.ClientResponse;
import com.hiagosouza.api.quoted.services.impl.ClientService;
import com.hiagosouza.api.quoted.services.impl.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@Slf4j
public class ClientController extends BaseController implements ClientsApi {

    private final ClientService clientService;
    private final UserService userService;

    public ClientController(ClientService clientService, UserService userService) {
        this.clientService = clientService;
        this.userService = userService;
    }

    @Override
    @PostMapping("/clients/register")
    public ResponseEntity<ClientResponse> createClient(@Valid @RequestBody ClientRequest client) {
        ClientModel clientModel = ClientMapper.toModel(client);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        clientModel.setCreatedAt(LocalDateTime.now());
        clientModel.setUpdatedAt(LocalDateTime.now());

        userService.findByEmail(email).ifPresent(user -> {
            clientModel.setOwnerId(user.getId());
        });

        try {
            clientService.createClient(clientModel);
        } catch (Exception e) {
            log.error("***** Client creation failed: {} *****", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
