package com.sb.sweetbucket.rest.request;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by harmeet on 06-10-2019.
 */

public class AddressSaveRequest implements Serializable {

    @SerializedName("address1")
    private String address01;
    @SerializedName("address2")
    private String address02;
    @SerializedName("pin")
    private Integer pinCode;
    @SerializedName("city")
    private String city;
    @SerializedName("state")
    private String state;

    public AddressSaveRequest(String address01, String address02, Integer pinCode, String city, String state) {
        this.address01 = address01;
        this.address02 = address02;
        this.pinCode = pinCode;
        this.city = city;
        this.state = state;
    }
}
