package com.innocodes.ecommerceapp.service;

import com.innocodes.ecommerceapp.models.User;
import com.innocodes.ecommerceapp.models.WishList;
import com.innocodes.ecommerceapp.repository.WishListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishListService {
    @Autowired
    private WishListRepository wishListRepository;

    public void createWishlist(WishList wishList) {
        wishListRepository.save(wishList);
    }

    public List<WishList> readWishList(User user) {
        return wishListRepository.findAllByUserOrderByCreatedDateDesc(user);
    }
}
