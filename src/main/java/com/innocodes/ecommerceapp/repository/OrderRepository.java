package com.innocodes.ecommerceapp.repository;

import com.innocodes.ecommerceapp.models.Order;
import com.innocodes.ecommerceapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findAllByUserOrderByCreatedDateDesc(User user);

}
