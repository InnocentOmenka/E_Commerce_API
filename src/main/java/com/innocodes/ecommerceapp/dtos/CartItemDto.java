package com.innocodes.ecommerceapp.dtos;

import com.innocodes.ecommerceapp.models.Cart;
import com.innocodes.ecommerceapp.models.Product;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CartItemDto {
    private Integer id;
    private @NotNull Integer quantity;
    private @NotNull Product product;

    public CartItemDto(Cart cart) {
        this.setId(cart.getId());
        this.setQuantity(cart.getQuantity());
        this.setProduct(cart.getProduct());
    }
}
