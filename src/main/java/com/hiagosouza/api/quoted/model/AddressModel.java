package com.hiagosouza.api.quoted.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
public class AddressModel {

    @Field("street")
    private String street;
    @Field("number")
    private String number;
    @Field("complement")
    private String complement;
    @Field("neighborhood")
    private String neighborhood;
    @Field("city")
    private String city;
    @Field("state")
    private String state;
    @Field("country")
    private String country;
    @Field("zipCode")
    private String zipCode;
}
