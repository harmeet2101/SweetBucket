package com.sb.sweetbucket.rest.request;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by harmeet on 21-09-2019.
 */

public class CheckPinRequest implements Serializable {


    @SerializedName("pincode")
    private Integer pincode;

    public CheckPinRequest(Integer pincode) {
        this.pincode = pincode;
    }

    @Override
    public String toString() {
        return "CheckPinRequest{" +
                "pincode=" + pincode +
                '}';
    }
}
