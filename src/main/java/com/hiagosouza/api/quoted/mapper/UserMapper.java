package com.hiagosouza.api.quoted.mapper;

import com.hiagosouza.api.quoted.enums.PlanType;
import com.hiagosouza.api.quoted.enums.UserRole;
import com.hiagosouza.api.quoted.enums.UserStatus;
import com.hiagosouza.api.quoted.model.AddressModel;
import com.hiagosouza.api.quoted.model.UserModel;
import com.hiagosouza.api.quoted.model.UserRequest;

import java.util.List;

public class UserMapper {
    public static UserModel toModel(UserRequest user) {
        UserModel userModel = new UserModel();
        userModel.setId(user.getId());
        userModel.setBusinessName(user.getBusinessName());
        userModel.setPhoneNumber(user.getPhoneNumber());
        userModel.setEmail(user.getEmail());
        userModel.setPassword(user.getPassword());
        userModel.setDocument(user.getDocument());
        userModel.setPlanType(PlanType.valueOf(user.getPlanType().name()));
        userModel.setStatus(UserStatus.valueOf(user.getStatus().name()));
        userModel.setCreatedAt(user.getCreatedAt());
        userModel.setUpdatedAt(user.getUpdatedAt());

        if (user.getAddress() != null) {
            AddressModel address = getAddress(user);
            userModel.setAddress(address);
        }

        return userModel;
    }

    private static AddressModel getAddress(UserRequest user) {
        AddressModel address = new AddressModel();
        if (user.getAddress() != null) {
            address.setStreet(user.getAddress().getStreet());
            address.setNumber(user.getAddress().getNumber());
            address.setComplement(user.getAddress().getComplement());
            address.setNeighborhood(user.getAddress().getNeighborhood());
            address.setCity(user.getAddress().getCity());
            address.setState(user.getAddress().getState());
            address.setCountry(user.getAddress().getCountry());
            address.setZipCode(user.getAddress().getZipCode());
        } else {
            System.out.println("No Address Given");
        }
        return address;
    }
}
