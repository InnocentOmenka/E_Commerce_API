package com.innocodes.ecommerceapp.controllers;

import com.innocodes.ecommerceapp.dtos.ProductDto;
import com.innocodes.ecommerceapp.exception.AuthenticationFailException;
import com.innocodes.ecommerceapp.models.Product;
import com.innocodes.ecommerceapp.models.User;
import com.innocodes.ecommerceapp.models.WishList;
import com.innocodes.ecommerceapp.repository.ProductRepository;
import com.innocodes.ecommerceapp.service.AuthService;
import com.innocodes.ecommerceapp.service.WishListService;
import com.innocodes.ecommerceapp.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/wishlist")
public class WishListController {
    @Autowired
    WishListService wishListService;

    @Autowired
    AuthService authenticationService;

    @Autowired
    private ProductRepository productRepository;

    /*
    API to add a new product in wishlist
    */
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addWishList(@RequestBody ProductDto productDto, @RequestParam("token") String token) throws AuthenticationFailException {
        // first authenticate if the token is valid
        authenticationService.authenticate(token);
        // then fetch the user linked to the token
        User user = authenticationService.getUser(token);

        // get the product from product repo
        Product product = productRepository.getById(productDto.getId());

        // save wish list
        WishList wishList = new WishList(user, product);
        wishListService.createWishlist(wishList);

        return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Added to wishlist"), HttpStatus.CREATED);
    }

    @GetMapping("/{token}")
    public ResponseEntity<List<ProductDto>> getWishList(@PathVariable("token") String token) throws AuthenticationFailException {
        // first authenticate if the token is valid
        authenticationService.authenticate(token);
        // then fetch the user linked to the token
        User user = authenticationService.getUser(token);
        // first retrieve the wishlist items
        List<WishList> wishLists = wishListService.readWishList(user);

        List<ProductDto> products = new ArrayList<>();
        for (WishList wishList : wishLists) {
            // change each product to product DTO
            products.add(new ProductDto(wishList.getProduct()));
        }
        // send the response to user
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}
