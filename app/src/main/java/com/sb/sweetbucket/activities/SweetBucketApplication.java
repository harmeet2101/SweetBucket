package com.sb.sweetbucket.activities;

import android.app.Application;

import com.sb.sweetbucket.rest.RetrofitAPIClient;

/**
 * Created by harmeet on 19-08-2019.
 */

public class SweetBucketApplication extends Application {

    public static RetrofitAPIClient getApiClient() {
        return apiClient;
    }

    private static RetrofitAPIClient apiClient;
    @Override
    public void onCreate() {
        super.onCreate();
        apiClient = RetrofitAPIClient.getInstance();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }
}
