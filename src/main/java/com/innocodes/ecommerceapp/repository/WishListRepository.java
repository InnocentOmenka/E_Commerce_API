package com.innocodes.ecommerceapp.repository;

import com.innocodes.ecommerceapp.models.User;
import com.innocodes.ecommerceapp.models.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishListRepository extends JpaRepository<WishList, Integer> {
    // find all the products saved to wishlist for a user
    // sort by latest created date
    List<WishList> findAllByUserOrderByCreatedDateDesc(User user);
}
