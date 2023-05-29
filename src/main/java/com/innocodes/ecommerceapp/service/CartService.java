package com.innocodes.ecommerceapp.service;

import com.innocodes.ecommerceapp.dtos.AddToCartDto;
import com.innocodes.ecommerceapp.dtos.CartDto;
import com.innocodes.ecommerceapp.dtos.CartItemDto;
import com.innocodes.ecommerceapp.exception.CartItemNotExistException;
import com.innocodes.ecommerceapp.models.Cart;
import com.innocodes.ecommerceapp.models.Product;
import com.innocodes.ecommerceapp.models.User;
import com.innocodes.ecommerceapp.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {

    @Autowired
    CartRepository cartRepository;

    public void addToCart(AddToCartDto addToCartDto, Product product, User user) {
        Cart cart = new Cart(product, addToCartDto.getQuantity(), user);
        cartRepository.save(cart);
    }

    public CartDto listCartItems(User user) {
        // first get all the cart items for user
        List<Cart> cartList = cartRepository.findAllByUserOrderByCreatedDateDesc(user);

        // convert cart to cartItemDto
        List<CartItemDto> cartItems = new ArrayList<>();
        for (Cart cart:cartList){
            CartItemDto cartItemDto = new CartItemDto(cart);
            cartItems.add(cartItemDto);
        }

        // calculate the total price
        double totalCost = 0;
        for (CartItemDto cartItemDto :cartItems){
            totalCost += cartItemDto.getProduct().getPrice() * cartItemDto.getQuantity();
        }

        // return cart DTO
        return new CartDto(cartItems,totalCost);
    }

    public void deleteCartItem(int cartItemId, User user) throws CartItemNotExistException {

        // first check if cartItemId is valid else throw an CartItemNotExistException
        Cart cartItem = cartRepository.findById(cartItemId)
                .orElseThrow(() -> new CartItemNotExistException("Cart Item ID invalid"));

        // next check if the cartItem belongs to the user else throw CartItemNotExistException exception
        if (!cartItem.getUser().equals(user)) throw new CartItemNotExistException("Cannot Perform Action");
        // delete the cart item
        cartRepository.delete(cartItem);
    }

    public void deleteUserCartItems(User user) {
        cartRepository.deleteByUser(user);
    }

}
