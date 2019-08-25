package com.sb.sweetbucket.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sb.sweetbucket.activities.SweetBucketApplication;
import com.sb.sweetbucket.rest.RestAPIInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by harmeet on 25-08-2019.
 */

public class SweetCategoryFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        loadCategoryData();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void loadCategoryData(){

    }
}
