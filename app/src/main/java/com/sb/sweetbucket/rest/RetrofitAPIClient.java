package com.sb.sweetbucket.rest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by harmeet on 19-08-2019.
 */

public class RetrofitAPIClient {


    private Retrofit retrofit = null;
    private static RetrofitAPIClient apiClient;

    private RetrofitAPIClient(){
        init();
    }

    private void init(){
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(RestAppConstants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
    }

    public static RetrofitAPIClient getInstance(){
        if(apiClient==null){
            apiClient = new RetrofitAPIClient();
        }
        return apiClient;
    }

    public Retrofit getClient() {
        return retrofit;
    }
}
