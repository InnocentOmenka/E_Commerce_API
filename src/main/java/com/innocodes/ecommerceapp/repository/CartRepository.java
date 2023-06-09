package com.innocodes.ecommerceapp.repository;

import com.innocodes.ecommerceapp.models.Cart;
import com.innocodes.ecommerceapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
    List<Cart> findAllByUserOrderByCreatedDateDesc(User user);

    void deleteByUser(User user);
}
