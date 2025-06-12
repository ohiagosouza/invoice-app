package com.hiagosouza.api.quoted.controller.impl;

import com.hiagosouza.api.quoted.api.ClientsApi;
import com.hiagosouza.api.quoted.controller.BaseController;
import com.hiagosouza.api.quoted.mapper.ClientMapper;
import com.hiagosouza.api.quoted.model.Client;
import com.hiagosouza.api.quoted.model.ClientModel;
import com.hiagosouza.api.quoted.model.UserModel;
import com.hiagosouza.api.quoted.services.impl.ClientService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@Slf4j
public class ClientController extends BaseController implements ClientsApi {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping("/clients/register")
    public ResponseEntity<Client> createNewClient(@Valid @RequestBody Client client) {
        UserModel userModel = new UserModel();
        log.info("Creating new client with document: {}", client.getDocument());
        client.setCreatedAt(LocalDateTime.now());
        client.setUpdatedAt(LocalDateTime.now());
        client.setOwnerId(userModel.getId());
        ClientModel clientModel = ClientMapper.toModel(client);

        try {
            clientService.createClient(clientModel);
        } catch (Exception e) {
            log.error("Client creation failed: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        log.info("Client created successfully: {}", client.getDocument());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
