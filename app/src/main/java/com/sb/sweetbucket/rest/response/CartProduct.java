package com.sb.sweetbucket.rest.response;

import java.io.Serializable;

/**
 * Created by harmeet on 20-10-2019.
 */

public class CartProduct implements Serializable {

    private Product product;
    private Cart cart;

    public CartProduct(Product product, Cart cart) {
        this.product = product;
        this.cart = cart;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    @Override
    public String toString() {
        return "CartProduct{" +
                "product=" + product +
                ", cart=" + cart +
                '}';
    }
}
