package com.sb.sweetbucket.rest.request;

import java.io.Serializable;

/**
 * Created by harmeet on 19-08-2019.
 */

public class LoginRequest implements Serializable{

    private String login;
    private String password;

    public LoginRequest(String login, String password) {
        this.login = login;
        this.password = password;
    }
}
