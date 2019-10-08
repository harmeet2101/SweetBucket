package com.sb.sweetbucket.rest.request;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by harmeet on 08-10-2019.
 */

public class DelAddressRequest implements Serializable {


    @SerializedName("address_id")
    private String addressId;

    public DelAddressRequest(String addressId) {
        this.addressId = addressId;
    }


}
