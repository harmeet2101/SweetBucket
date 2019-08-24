package com.sb.sweetbucket.rest.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by harmeet on 19-08-2019.
 */

public class LoginResponse implements Serializable{

    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("email")
    private String email;
    @SerializedName("api_token")
    private String apiToken;
    @SerializedName("mobile")
    private String mobile;
    @SerializedName("msg")
    private String msg;

    public LoginResponse(int id, String name, String email, String apiToken, String mobile,String msg) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.apiToken = apiToken;
        this.mobile = mobile;
        this.msg = msg;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getApiToken() {
        return apiToken;
    }

    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "LoginResponse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", apiToken='" + apiToken + '\'' +
                ", mobile='" + mobile + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}
