package com.sb.sweetbucket.rest.request;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by harmeet on 21-09-2019.
 */

public class ProductReviewRequest implements Serializable {

    @SerializedName("product_id")
    private Integer productId;
    @SerializedName("rating")
    private Float rating;
    @SerializedName("comments")
    private String comments;

    public ProductReviewRequest(Integer productId, Float rating, String comments) {
        this.productId = productId;
        this.rating = rating;
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "ProductReviewRequest{" +
                "productId=" + productId +
                ", rating=" + rating +
                ", comments='" + comments + '\'' +
                '}';
    }
}
