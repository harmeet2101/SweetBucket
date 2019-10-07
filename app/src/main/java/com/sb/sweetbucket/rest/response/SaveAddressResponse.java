package com.sb.sweetbucket.rest.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by harmeet on 06-10-2019.
 */

public class SaveAddressResponse implements Serializable {

    @SerializedName("msg")
    private String msg;
    @SerializedName("data")
    private Address address;

    public String getMsg() {
        return msg;
    }

    public Address getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return "SaveAddressResponse{" +
                "msg='" + msg + '\'' +
                ", address=" + address +
                '}';
    }
}
