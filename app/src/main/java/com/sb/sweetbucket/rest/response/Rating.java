package com.sb.sweetbucket.rest.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by harmeet on 21-09-2019.
 */

public class Rating implements Serializable {

    private Integer id;
    @SerializedName("user_id")
    private String userId;
    private String productId;
    private String productVendor;
    private String rating;
    private String comments;
    private String created_at;
    private String updated_at;


    public Integer getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getProductId() {
        return productId;
    }

    public String getProductVendor() {
        return productVendor;
    }

    public String getRating() {
        return rating;
    }

    public String getComments() {
        return comments;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    @Override
    public String toString() {
        return "Rating{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", productId='" + productId + '\'' +
                ", productVendor='" + productVendor + '\'' +
                ", rating='" + rating + '\'' +
                ", comments='" + comments + '\'' +
                ", created_at='" + created_at + '\'' +
                ", updated_at='" + updated_at + '\'' +
                '}';
    }
}
