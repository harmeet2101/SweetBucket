package com.sb.sweetbucket.rest.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by harmeet on 08-10-2019.
 */

public class OrdersResponse implements Serializable {

    @SerializedName("orders")
    @Expose
    private List<Order> orders = null;

    public List<Order> getOrders() {
        return orders;
    }

    @Override
    public String toString() {
        return "OrdersResponse{" +
                "orders=" + orders +
                '}';
    }
}
