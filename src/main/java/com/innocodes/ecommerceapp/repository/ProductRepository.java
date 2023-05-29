package com.innocodes.ecommerceapp.repository;

import com.innocodes.ecommerceapp.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
