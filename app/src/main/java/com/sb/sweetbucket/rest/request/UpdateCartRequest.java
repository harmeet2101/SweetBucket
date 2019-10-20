package com.sb.sweetbucket.rest.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by harmeet on 19-10-2019.
 */

public class UpdateCartRequest {

    @SerializedName("qty")
    private int qty;
    @SerializedName("cart")
    private int cart;

    public UpdateCartRequest(int qty, int cart) {
        this.qty = qty;
        this.cart = cart;
    }
}
