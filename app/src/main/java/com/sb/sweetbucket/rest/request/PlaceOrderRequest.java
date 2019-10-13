package com.sb.sweetbucket.rest.request;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by harmeet on 13-10-2019.
 */

public class PlaceOrderRequest implements Serializable {

    @SerializedName("activeAddress")
    private Integer addressId;
    @SerializedName("payment_mode")
    private Integer paymentMode;

    public PlaceOrderRequest(Integer addressId, Integer paymentMode) {
        this.addressId = addressId;
        this.paymentMode = paymentMode;
    }

    @Override
    public String toString() {
        return "PlaceOrderRequest{" +
                "addressId=" + addressId +
                ", paymentMode=" + paymentMode +
                '}';
    }
}
