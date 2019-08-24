package com.sb.sweetbucket.rest.request;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by harmeet on 24-08-2019.
 */

public class HomeRequest implements Serializable {

    @SerializedName("keyword")
    private String keyword;

    public HomeRequest(String keyword) {
        this.keyword = keyword;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public String toString() {
        return "HomeRequest{" +
                "keyword='" + keyword + '\'' +
                '}';
    }
}
