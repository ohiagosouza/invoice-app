package com.hiagosouza.api.quoted.controller.impl;

import com.hiagosouza.api.quoted.controller.BaseController;
import com.hiagosouza.api.quoted.mapper.ClientMapper;
import com.hiagosouza.api.quoted.model.ClientModel;
import com.hiagosouza.api.quoted.model.ClientRequest;
import com.hiagosouza.api.quoted.model.ClientResponse;
import com.hiagosouza.api.quoted.model.UserModel;
import com.hiagosouza.api.quoted.services.impl.ClientService;
import com.hiagosouza.api.quoted.services.impl.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
public class ClientController extends BaseController {

    private final ClientService clientService;
    private final UserService userService;

    public ClientController(ClientService clientService, UserService userService) {
        this.clientService = clientService;
        this.userService = userService;
    }

    @PostMapping("/clients/register")
    public ResponseEntity<ClientResponse> createClient(@Valid @RequestBody ClientRequest client) {
        ClientModel clientModel = ClientMapper.toModel(client);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        clientModel.setCreatedAt(LocalDateTime.now());
        clientModel.setUpdatedAt(LocalDateTime.now());

        if (email != null) {
            UserModel user = userService.findByEmail(email);
            clientModel.setOwnerId(user.getId());
        }

        try {
            clientService.createClient(clientModel);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/clients/list-all")
    public ResponseEntity<?> getAllClients() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        if(email != null) {
            UserModel owner = userService.findByEmail(email);
            List<ClientModel> clientList = new ArrayList<>(clientService.getClients(owner.getId()));
            return ResponseEntity.status(HttpStatus.OK).body(clientList);
        } else {
            log.error("***** No authentication found in context *****");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }
}
