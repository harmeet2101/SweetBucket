package com.sb.sweetbucket.rest.response;

import java.io.Serializable;

/**
 * Created by harmeet on 21-09-2019.
 */

public class ProductReviewResponse implements Serializable {


    private String status;
    private String msg;

    public String getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    @Override
    public String toString() {
        return "ProductReviewResponse{" +
                "status='" + status + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}
