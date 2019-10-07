package com.sb.sweetbucket.rest.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by harmeet on 22-09-2019.
 */

public class Cart implements Serializable {

    @SerializedName("id")
    private Integer id;
    @SerializedName("customer_id")
    private String custId;
    @SerializedName("product_id")
    private String prodId;
    @SerializedName("qty")
    private String qty;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("updated_at")
    private String updatedAt;


    public Integer getId() {
        return id;
    }

    public String getCustId() {
        return custId;
    }

    public String getProdId() {
        return prodId;
    }

    public String getQty() {
        return qty;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", custId='" + custId + '\'' +
                ", prodId='" + prodId + '\'' +
                ", qty='" + qty + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                '}';
    }
}
