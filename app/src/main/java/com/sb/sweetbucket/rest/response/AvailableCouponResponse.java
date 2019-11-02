package com.sb.sweetbucket.rest.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AvailableCouponResponse implements Serializable {


    @SerializedName("code")
    private String code;
    @SerializedName("amount")
    private String amount;
    @SerializedName("percent")
    private String percent;
    @SerializedName("min_order_amount")
    private String min_order_amount;
    @SerializedName("start_at")
    private String start_at;
    @SerializedName("end_at")
    private String end_at;

    public String getCode() {
        return code;
    }

    public String getAmount() {
        return amount;
    }

    public String getPercent() {
        return percent;
    }

    public String getMin_order_amount() {
        return min_order_amount;
    }

    public String getStart_at() {
        return start_at;
    }

    public String getEnd_at() {
        return end_at;
    }

    @Override
    public String toString() {
        return "AvailableCouponResponse{" +
                "code='" + code + '\'' +
                ", amount='" + amount + '\'' +
                ", percent='" + percent + '\'' +
                ", min_order_amount='" + min_order_amount + '\'' +
                ", start_at='" + start_at + '\'' +
                ", end_at='" + end_at + '\'' +
                '}';
    }
}
