package com.sb.sweetbucket.rest.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by harmeet on 08-10-2019.
 */

public class OrderDetailResponse implements Serializable {

    @SerializedName("order")
    private Order order;

    public Order getOrder() {
        return order;
    }

    @Override
    public String toString() {
        return "OrderDetailResponse{" +
                "order=" + order +
                '}';
    }
}
