package com.sb.sweetbucket.rest;

import com.sb.sweetbucket.rest.request.LoginRequest;
import com.sb.sweetbucket.rest.response.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by harmeet on 19-08-2019.
 */

public interface RestAPIInterface {

    @Headers({
            "Content-Type: application/json",
            "X-Requested-With: XMLHttpRequest"
    })
    @POST("api/apiLogin")
    Call<LoginResponse> doLogin(@Body LoginRequest loginRequest);
}
