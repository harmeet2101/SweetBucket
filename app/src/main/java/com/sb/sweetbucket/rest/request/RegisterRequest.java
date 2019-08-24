package com.sb.sweetbucket.rest.request;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by harmeet on 24-08-2019.
 */

public class RegisterRequest implements Serializable {

    @SerializedName("name")
    private String name;
    @SerializedName("mobile")
    private String mobile;
    @SerializedName("password")
    private String password;
    @SerializedName("password_confirmation")
    private String confirmPassword;
    @SerializedName("email")
    private String emailID;

    public RegisterRequest(String name, String mobile, String password, String confirmPassword, String emailID) {
        this.name = name;
        this.mobile = mobile;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.emailID = emailID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getEmailID() {
        return emailID;
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }

    @Override
    public String toString() {
        return "RegisterRequest{" +
                "name='" + name + '\'' +
                ", mobile='" + mobile + '\'' +
                ", password='" + password + '\'' +
                ", confirmPassword='" + confirmPassword + '\'' +
                ", emailID='" + emailID + '\'' +
                '}';
    }
}
