package com.innocodes.ecommerceapp.service;

import com.innocodes.ecommerceapp.dtos.SignInDto;
import com.innocodes.ecommerceapp.dtos.SignInResponseDto;
import com.innocodes.ecommerceapp.dtos.SignUpDto;
import com.innocodes.ecommerceapp.dtos.SignUpResponseDto;
import com.innocodes.ecommerceapp.exception.AuthenticationFailException;
import com.innocodes.ecommerceapp.exception.CustomException;
import com.innocodes.ecommerceapp.models.AuthToken;
import com.innocodes.ecommerceapp.models.User;
import com.innocodes.ecommerceapp.repository.UserRepository;
import com.innocodes.ecommerceapp.utils.MessageStrings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthService authenticationService;
    Logger logger = LoggerFactory.getLogger(UserService.class);

    public SignUpResponseDto signUp(SignUpDto signUpDto) throws CustomException {
        // Check to see if the current email address has already been registered.
        if (Objects.nonNull(userRepository.findByEmail(signUpDto.getEmail()))) {
            // If the email address has been registered then throw an exception.
            throw new CustomException("User already exists");
        }

        String encryptedPassword = signUpDto.getPassword();
        try {
            encryptedPassword = hashPassword(signUpDto.getPassword());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            logger.error("hashing password failed {}", e.getMessage());
        }


    User user = new User(signUpDto.getFirstName(), signUpDto.getLastName(), signUpDto.getEmail(), encryptedPassword );
        try {
        // save the User
        userRepository.save(user);
        // success in creating
        // generate token for user
        final AuthToken authenticationToken = new AuthToken(user);
        // save token in database
        authenticationService.saveConfirmationToken(authenticationToken);
        return new SignUpResponseDto("success", "user created successfully");
    } catch (Exception e) {
        // handle signup error
        throw new CustomException(e.getMessage());
    }
}

    public SignInResponseDto signIn(SignInDto signInDto) throws AuthenticationFailException, CustomException {
        // first find User by email
        User user = userRepository.findByEmail(signInDto.getEmail());
        if(!Objects.nonNull(user)){
            throw new AuthenticationFailException("user not present");
        }
        try {
            // check if password is right
            if (!user.getPassword().equals(hashPassword(signInDto.getPassword()))){
                // passwords do not match
                throw  new AuthenticationFailException(MessageStrings.WRONG_PASSWORD);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            logger.error("hashing password failed {}", e.getMessage());
            throw new CustomException(e.getMessage());
        }

        AuthToken token = authenticationService.getToken(user);

        if(!Objects.nonNull(token)) {
            // token not present
            throw new CustomException(MessageStrings.AUTH_TOKEN_NOT_PRESENT);
        }

        return new SignInResponseDto("success", token.getToken());
    }

    String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        byte[] digest = md.digest();
        String myHash = DatatypeConverter
                .printHexBinary(digest).toUpperCase();
        return myHash;
    }
}
