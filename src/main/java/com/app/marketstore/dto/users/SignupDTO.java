package com.app.marketstore.dto.users;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupDTO {

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private String phoneNumber;

    private String address;

    private String country;

    private String city;

    private Integer postalCode;
}
