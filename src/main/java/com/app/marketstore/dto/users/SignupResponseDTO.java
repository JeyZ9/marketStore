package com.app.marketstore.dto.users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class SignupResponseDTO {

    private String status;

    private String message;
}
