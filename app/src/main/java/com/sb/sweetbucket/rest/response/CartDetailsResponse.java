package com.sb.sweetbucket.rest.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by harmeet on 22-09-2019.
 */

public class CartDetailsResponse implements Serializable {


    @SerializedName("cart")
    private List<Cart> cartList;
    @SerializedName("cartTotal")
    private Integer cartTotal;
    @SerializedName("subTotal")
    private Integer subTotal;
    @SerializedName("tax")
    private Integer tax;
    @SerializedName("delivery")
    private Integer delivery;


    public List<Cart> getCartList() {
        return cartList;
    }

    public Integer getCartTotal() {
        return cartTotal;
    }

    public Integer getSubTotal() {
        return subTotal;
    }

    public Integer getTax() {
        return tax;
    }

    public Integer getDelivery() {
        return delivery;
    }

    @Override
    public String toString() {
        return "CartDetailsResponse{" +
                "cartList=" + cartList +
                ", cartTotal=" + cartTotal +
                ", subTotal=" + subTotal +
                ", tax=" + tax +
                ", delivery=" + delivery +
                '}';
    }
}
