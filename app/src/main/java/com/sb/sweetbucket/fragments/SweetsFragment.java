package com.sb.sweetbucket.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sb.sweetbucket.R;
import com.sb.sweetbucket.activities.SweetBucketApplication;
import com.sb.sweetbucket.adapters.SweetsRecylerAdapter;
import com.sb.sweetbucket.rest.RestAPIInterface;
import com.sb.sweetbucket.rest.response.Category;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by harmeet on 24-08-2019.
 */

public class SweetsFragment extends Fragment implements SweetsRecylerAdapter.ISweetsRecylerListener {

    private static final String TAG = SweetsFragment.class.getSimpleName();
    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private SweetsRecylerAdapter recylerAdapter;
    private List<Category> categoryList;
    private  ISweetFragmentListener sweetFragmentListener;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        sweetFragmentListener = (ISweetFragmentListener)activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_sweets_frag,container,false);
        recyclerView = (RecyclerView)view.findViewById(R.id.recylerview);
        gridLayoutManager  = new GridLayoutManager(getContext(),2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recylerAdapter = new SweetsRecylerAdapter(getContext(),categoryList,this);
        recyclerView.setAdapter(recylerAdapter);
        loadCategoryData();
        return view;
    }



    private void loadCategoryData(){
        RestAPIInterface apiInterface = SweetBucketApplication.getApiClient().getClient().create(RestAPIInterface.class);
        Call<List<Category>> responseCall = apiInterface.getCategory();
        responseCall.enqueue(new Callback<List<Category>>() {

            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {

                recylerAdapter.updateDataSource(response.body());
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Log.e(TAG,t.getMessage());
                recylerAdapter.updateDataSource(new ArrayList<Category>());
            }
        });
    }

    @Override
    public void onShopCategorySelected(String category) {
        sweetFragmentListener.onSweetsItemSelected(category);
    }

    public interface ISweetFragmentListener{
        void onSweetsItemSelected(String category);
    }
}
