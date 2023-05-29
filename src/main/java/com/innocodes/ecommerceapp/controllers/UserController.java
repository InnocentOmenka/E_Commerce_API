package com.innocodes.ecommerceapp.controllers;

import com.innocodes.ecommerceapp.dtos.SignInDto;
import com.innocodes.ecommerceapp.dtos.SignInResponseDto;
import com.innocodes.ecommerceapp.dtos.SignUpDto;
import com.innocodes.ecommerceapp.dtos.SignUpResponseDto;
import com.innocodes.ecommerceapp.exception.AuthenticationFailException;
import com.innocodes.ecommerceapp.exception.CustomException;
import com.innocodes.ecommerceapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/user")
@RestController
public class UserController {
    @Autowired
    UserService userService;


    @PostMapping("/signup")
    public SignUpResponseDto SignUp(@RequestBody SignUpDto signupDto) throws CustomException {
        return userService.signUp(signupDto);
    }

    @PostMapping("/signin")
    public SignInResponseDto SignIn(@RequestBody SignInDto signInDto) throws CustomException, AuthenticationFailException {
        return userService.signIn(signInDto);
    }
}
