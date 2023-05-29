package com.innocodes.ecommerceapp.repository;

import com.innocodes.ecommerceapp.models.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
}
