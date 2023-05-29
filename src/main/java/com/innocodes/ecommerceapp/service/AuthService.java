package com.innocodes.ecommerceapp.service;

import com.innocodes.ecommerceapp.exception.AuthenticationFailException;
import com.innocodes.ecommerceapp.models.AuthToken;
import com.innocodes.ecommerceapp.models.User;
import com.innocodes.ecommerceapp.repository.TokenRepository;
import com.innocodes.ecommerceapp.utils.MessageStrings;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

//@RequiredArgsConstructor
@Service
public class AuthService {
    @Autowired
     TokenRepository repository;

    // save the confirmation token
    public void saveConfirmationToken(AuthToken authenticationToken) {
        repository.save(authenticationToken);
    }

    // get token of the User
    public AuthToken getToken(User user) {
        return repository.findTokenByUser(user);
    }

    // get Uer from the token
    public User getUser(String token) {
        AuthToken authenticationToken = repository.findTokenByToken(token);
        if (Objects.nonNull(authenticationToken)) {
            if (Objects.nonNull(authenticationToken.getUser())) {
                return authenticationToken.getUser();
            }
        }
        return null;
    }

    // check if the token is valid
    public void authenticate(String token) throws AuthenticationFailException {
        if (!Objects.nonNull(token)) {
            throw new AuthenticationFailException(MessageStrings.AUTH_TOKEN_NOT_PRESENT);
        }
        if (!Objects.nonNull(getUser(token))) {
            throw new AuthenticationFailException(MessageStrings.AUTH_TOKEN_NOT_VALID);
        }
    }
}
