package com.sb.sweetbucket.rest.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by harmeet on 14-09-2019.
 */

public class VendorResponse implements Serializable {


    @SerializedName("products")
    private List<Product> productList;

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    @Override
    public String toString() {
        return "VendorResponse{" +
                "productList=" + productList +
                '}';
    }
}
