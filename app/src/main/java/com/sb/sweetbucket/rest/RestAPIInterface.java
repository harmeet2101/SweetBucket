package com.sb.sweetbucket.rest;

import com.sb.sweetbucket.rest.request.HomeRequest;
import com.sb.sweetbucket.rest.request.LoginRequest;
import com.sb.sweetbucket.rest.request.RegisterRequest;
import com.sb.sweetbucket.rest.response.Category;
import com.sb.sweetbucket.rest.response.HomeResponse;
import com.sb.sweetbucket.rest.response.LoginResponse;
import com.sb.sweetbucket.rest.response.RegisterResponse;
import com.sb.sweetbucket.rest.response.ShopsResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
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

    @Headers({
            "Content-Type: application/json",
            "X-Requested-With: XMLHttpRequest"
    })
    @POST("api/apiRegister")
    Call<RegisterResponse> doRegister(@Body RegisterRequest registerRequest);


    @Headers({
            "Content-Type: application/json",
            "X-Requested-With: XMLHttpRequest"
    })
    @POST("api/sugestion")
    Call<HomeResponse> getSuggestion(@Body HomeRequest homeRequest);

    @Headers({
            "Content-Type: application/json",
            "X-Requested-With: XMLHttpRequest"
    })
    @GET("api/category")
    Call<List<Category>> getCategory();

    @Headers({
            "Content-Type: application/json",
            "X-Requested-With: XMLHttpRequest"
    })
    @GET("api/shops")
    Call<List<ShopsResponse>> getShops();
}
