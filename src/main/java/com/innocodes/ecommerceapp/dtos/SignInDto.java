package com.innocodes.ecommerceapp.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SignInDto {
    private String email;
    private String password;
}
