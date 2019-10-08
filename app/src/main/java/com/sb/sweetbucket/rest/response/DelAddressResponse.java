package com.sb.sweetbucket.rest.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by harmeet on 08-10-2019.
 */

public class DelAddressResponse implements Serializable {

    @SerializedName("msg")
    private String msg;

    public String getMsg() {
        return msg;
    }

    @Override
    public String toString() {
        return "DelAddressResponse{" +
                "msg='" + msg + '\'' +
                '}';
    }
}
