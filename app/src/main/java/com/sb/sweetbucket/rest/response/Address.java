package com.sb.sweetbucket.rest.response;

import android.content.Intent;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by harmeet on 05-10-2019.
 */

public class Address implements Serializable {

    private Integer id;
    @SerializedName("user_id")
    private String userId;
    @SerializedName("address1")
    private String address1;
    @SerializedName("address2")
    private String address2;
    @SerializedName("state")
    private String state;
    @SerializedName("country")
    private String country;
    @SerializedName("city")
    private String city;
    @SerializedName("pin")
    private String pinCode;

    public Integer getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getAddress1() {
        return address1;
    }

    public String getAddress2() {
        return address2;
    }

    public String getState() {
        return state;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getPinCode() {
        return pinCode;
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", address1='" + address1 + '\'' +
                ", address2='" + address2 + '\'' +
                ", state='" + state + '\'' +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", pinCode='" + pinCode + '\'' +
                '}';
    }
}
