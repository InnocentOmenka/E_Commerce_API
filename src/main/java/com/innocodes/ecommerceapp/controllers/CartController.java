package com.innocodes.ecommerceapp.controllers;

import com.innocodes.ecommerceapp.dtos.AddToCartDto;
import com.innocodes.ecommerceapp.dtos.CartDto;
import com.innocodes.ecommerceapp.exception.AuthenticationFailException;
import com.innocodes.ecommerceapp.exception.CartItemNotExistException;
import com.innocodes.ecommerceapp.exception.ProductNotExistException;
import com.innocodes.ecommerceapp.models.Product;
import com.innocodes.ecommerceapp.models.User;
import com.innocodes.ecommerceapp.service.AuthService;
import com.innocodes.ecommerceapp.service.CartService;
import com.innocodes.ecommerceapp.service.CategoryService;
import com.innocodes.ecommerceapp.service.ProductService;
import com.innocodes.ecommerceapp.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    CategoryService categoryService;

    @Autowired
    ProductService productService;

    @Autowired
    AuthService authenticationService;

    @Autowired
    CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addToCart(@RequestBody AddToCartDto addToCartDto, @RequestParam("token") String token)
            throws AuthenticationFailException, ProductNotExistException {
        // first authenticate the token
        authenticationService.authenticate(token);

        // get the user
        User user = authenticationService.getUser(token);

        // find the product to add and add item by service
        Product product = productService.getProductById(addToCartDto.getProductId());
        cartService.addToCart(addToCartDto, product, user);

        // return response
        return new ResponseEntity<>(new ApiResponse(true, "Added to cart"), HttpStatus.CREATED);

    }

    @GetMapping("/")
    public ResponseEntity<CartDto> getCartItems(@RequestParam("token") String token) throws AuthenticationFailException {
        // first authenticate the token
        authenticationService.authenticate(token);

        // get the user
        User user = authenticationService.getUser(token);

        // get items in the cart for the user.
        CartDto cartDto = cartService.listCartItems(user);

        return new ResponseEntity<CartDto>(cartDto,HttpStatus.OK);
    }

    // task delete cart item
    @DeleteMapping("/delete/{cartItemId}")
    public ResponseEntity<ApiResponse> deleteCartItem(@PathVariable("cartItemId") int cartItemId,
                                                      @RequestParam("token") String token)
            throws AuthenticationFailException, CartItemNotExistException {
        authenticationService.authenticate(token);
        User user = authenticationService.getUser(token);
        // method to be completed
        cartService.deleteCartItem(cartItemId, user);
        return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Item has been removed"), HttpStatus.OK);
    }
}
