package com.innocodes.ecommerceapp.repository;

import com.innocodes.ecommerceapp.models.AuthToken;
import com.innocodes.ecommerceapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<AuthToken, Integer> {
    AuthToken findTokenByUser(User user);
    AuthToken findTokenByToken(String token);
}
