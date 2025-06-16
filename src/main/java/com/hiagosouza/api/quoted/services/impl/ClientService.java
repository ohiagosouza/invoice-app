package com.hiagosouza.api.quoted.services.impl;

import com.hiagosouza.api.quoted.model.ClientModel;
import com.hiagosouza.api.quoted.repository.ClientRepository;
import com.hiagosouza.api.quoted.utils.DocumentUtils;
import com.hiagosouza.api.quoted.utils.PhoneUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
            log.info("***** Creating client with document: {} *****", client.getDocument());
            String cleanedDocument = DocumentUtils.cleanDocument(client.getDocument());
            client.setDocument(cleanedDocument);
            String cleanedPhone = PhoneUtils.cleanPhoneNumber(client.getPhoneNumber());
            client.setPhoneNumber(cleanedPhone);
            clientRepository.save(client);
        } else {
            log.error("***** Client not created, either null or already exists: {} *****", client.getDocument());
            throw new IllegalArgumentException("Client not created");
        }
    }

    public List<ClientModel> getClients(String ownerId) {
        List<ClientModel> clients;
        try {
            log.info("***** Fetching clients for owner ID: {} *****", ownerId);
            clients = clientRepository.findClientsByOwnerId(ownerId);
        } catch (ArrayIndexOutOfBoundsException e) {
            log.error("***** Error fetching clients: {} *****", e.getMessage());
            throw new ArrayIndexOutOfBoundsException(e.getMessage());
        }
        return clients;
    }

}
