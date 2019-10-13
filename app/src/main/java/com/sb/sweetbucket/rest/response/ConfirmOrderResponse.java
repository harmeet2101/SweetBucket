package com.sb.sweetbucket.rest.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by harmeet on 13-10-2019.
 */

public class ConfirmOrderResponse implements Serializable {

    @SerializedName("status")
    private String status;
    @SerializedName("msg")
    private String msg;
    @SerializedName("order_no")
    private String order_no;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    @Override
    public String toString() {
        return "ConfirmOrderResponse{" +
                "status='" + status + '\'' +
                ", msg='" + msg + '\'' +
                ", order_no='" + order_no + '\'' +
                '}';
    }
}
