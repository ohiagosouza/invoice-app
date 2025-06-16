package com.hiagosouza.api.quoted.services.impl;

import com.hiagosouza.api.quoted.model.ClientModel;
import com.hiagosouza.api.quoted.repository.ClientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ClientService {
    @Autowired
    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public void createClient(ClientModel client) {
        if (client.getDocument() != null && clientRepository.findByDocument(client.getDocument()).isEmpty()) {
            clientRepository.save(client);
        } else {
            log.error("***** Client not created, either null or already exists: {} *****", client.getDocument());
            throw new IllegalArgumentException("Client not created");
        }
    }

}
