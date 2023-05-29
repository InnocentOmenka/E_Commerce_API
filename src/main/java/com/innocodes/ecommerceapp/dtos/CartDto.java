package com.innocodes.ecommerceapp.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CartDto {

    //list of each item
    private List<CartItemDto> cartItems;

    // total cost for the cart
    private double totalCost;

    public CartDto(List<CartItemDto> cartItemDtoList, double totalCost) {
        this.cartItems = cartItemDtoList;
        this.totalCost = totalCost;
    }
}
