package com.hiagosouza.api.quoted.mapper;

import com.hiagosouza.api.quoted.enums.PlanType;
import com.hiagosouza.api.quoted.enums.UserStatus;
import com.hiagosouza.api.quoted.model.*;

public class ClientMapper {
    public static ClientModel toModel(Client client) {
        ClientModel clientModel = new ClientModel();
        clientModel.setId(client.getId());
        clientModel.setName(client.getName());
        clientModel.setPhoneNumber(client.getPhoneNumber());
        clientModel.setOwnerId(client.getId());
        clientModel.setEmail(client.getEmail());
        clientModel.setDocument(client.getDocument());
        clientModel.setCreatedAt(client.getCreatedAt());
        clientModel.setUpdatedAt(client.getUpdatedAt());

        if (client.getAddress() != null) {
            AddressModel address = getAddress(client);
            clientModel.setAddress(address);
        }

        return clientModel;
    }

    private static AddressModel getAddress(Client client) {
        AddressModel address = new AddressModel();
        if (client.getAddress() != null) {
            address.setStreet(client.getAddress().getStreet());
            address.setNumber(client.getAddress().getNumber());
            address.setComplement(client.getAddress().getComplement());
            address.setNeighborhood(client.getAddress().getNeighborhood());
            address.setCity(client.getAddress().getCity());
            address.setState(client.getAddress().getState());
            address.setCountry(client.getAddress().getCountry());
            address.setZipCode(client.getAddress().getZipCode());
        } else {
            System.out.println("No Address Given");
        }
        return address;
    }
}
