package com.innocodes.ecommerceapp.dtos;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
@Setter
public class SignUpDto {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
