package com.sb.sweetbucket.rest.request;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by harmeet on 22-09-2019.
 */

public class AddCartRequest implements Serializable {


    @SerializedName("product_id")
    private Integer productId;


    public AddCartRequest(Integer productId) {
        this.productId = productId;
    }
}
