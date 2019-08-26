package com.sb.sweetbucket.fragments;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sb.sweetbucket.activities.LoginActivity;
import com.sb.sweetbucket.activities.ProductDetailsActivity;
import com.sb.sweetbucket.activities.SweetBucketApplication;
import com.sb.sweetbucket.adapters.HomeRecyclerAdapter;
import com.sb.sweetbucket.R;
import com.sb.sweetbucket.model.ProductDetails;
import com.sb.sweetbucket.rest.RestAPIInterface;
import com.sb.sweetbucket.rest.request.HomeRequest;
import com.sb.sweetbucket.rest.response.Category;
import com.sb.sweetbucket.rest.response.HomeResponse;
import com.sb.sweetbucket.rest.response.Product;
import com.sb.sweetbucket.rest.response.Shop;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by harmeet on 17-08-2019.
 */

@SuppressLint("ValidFragment")
public class HomeFragment extends Fragment implements HomeRecyclerAdapter.IOnClick{

    private static final String TAG = HomeFragment.class.getSimpleName();
    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private HomeRecyclerAdapter recyclerAdapter;
    private List<Product> responseList;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.layout_home_frag,container,false);
        recyclerView = (RecyclerView)view.findViewById(R.id.recylerview);
        gridLayoutManager  = new GridLayoutManager(getContext(),2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerAdapter = new HomeRecyclerAdapter(getActivity().getBaseContext(),responseList,this);
        /*recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.HORIZONTAL));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL));*/
        recyclerView.setAdapter(recyclerAdapter);
        loadSuggestionData("");
        return view;
    }

    private List<String> createDummyData(){

        List<String> l = new ArrayList<>();
        for(int i=0;i<=10;i++)
            l.add(""+i);
        return l;
    }

    @Override
    public void testOnClick(ProductDetails productDetails) {

        Bundle pBundle = new Bundle();
        pBundle.putSerializable("productDetails",productDetails);
        Intent pIntent = new Intent(getContext(),ProductDetailsActivity.class);
        pIntent.putExtras(pBundle);
        getActivity().startActivity(pIntent);
    }

    public void updateData(HomeResponse homeResponse){
        Log.d(HomeFragment.class.getSimpleName(),homeResponse.toString());
        recyclerAdapter.updateDataSource(homeResponse);
    }


    private void loadSuggestionData(String suggestion){

        RestAPIInterface apiInterface = SweetBucketApplication.getApiClient().getClient().create(RestAPIInterface.class);
        Call<HomeResponse> responseCall = apiInterface.getSuggestion(new HomeRequest(suggestion));
        responseCall.enqueue(new Callback<HomeResponse>() {

            @Override
            public void onResponse(Call<HomeResponse> call, Response<HomeResponse> response) {
                Log.e(TAG,response.body().toString());
                updateData(response.body());
            }

            @Override
            public void onFailure(Call<HomeResponse> call, Throwable t) {
                //Log.e(TAG,t.getMessage());
                HomeResponse homeResponse = new HomeResponse();
                homeResponse.setProducts(new ArrayList<Product>());
                homeResponse.setCategory(new ArrayList<Category>());
                homeResponse.setShops(new ArrayList<Shop>());
                updateData(homeResponse);
            }
        });
    }
}
