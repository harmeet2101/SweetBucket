package com.sb.sweetbucket.rest.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by harmeet on 24-08-2019.
 */

public class RegisterResponse implements Serializable{

    @SerializedName("msg")
    private String msg;
    @SerializedName("data")
    private LoginResponse loginResponse;
    @SerializedName("message")
    private String message;

    public RegisterResponse(String msg, LoginResponse loginResponse,String message) {
        this.msg = msg;
        this.loginResponse = loginResponse;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public LoginResponse getLoginResponse() {
        return loginResponse;
    }

    public void setLoginResponse(LoginResponse loginResponse) {
        this.loginResponse = loginResponse;
    }

    @Override
    public String toString() {
        return "RegisterResponse{" +
                "msg='" + msg + '\'' +
                ", loginResponse=" + loginResponse +
                ", message='" + message + '\'' +
                '}';
    }
}
