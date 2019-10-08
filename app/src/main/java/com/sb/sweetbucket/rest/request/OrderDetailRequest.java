package com.sb.sweetbucket.rest.request;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by harmeet on 08-10-2019.
 */

public class OrderDetailRequest implements Serializable {

    @SerializedName("order_no")
    private String orderNo;

    public OrderDetailRequest(String orderNo) {
        this.orderNo = orderNo;
    }
}
