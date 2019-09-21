package com.sb.sweetbucket.rest.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by harmeet on 21-09-2019.
 */

public class PinCodeResponse implements Serializable {

    @SerializedName("city")
    private String city;
    @SerializedName("street")
    private String street;
    @SerializedName("pin")
    private String pin;

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public String getPin() {
        return pin;
    }

    @Override
    public String toString() {
        return "PinCodeResponse{" +
                "city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", pin='" + pin + '\'' +
                '}';
    }
}
