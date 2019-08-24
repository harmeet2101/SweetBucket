package com.sb.sweetbucket.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.sb.sweetbucket.R;
import com.sb.sweetbucket.activities.SweetBucketApplication;
import com.sb.sweetbucket.adapters.ShopRecylerAdapter;
import com.sb.sweetbucket.adapters.SweetsRecylerAdapter;
import com.sb.sweetbucket.rest.RestAPIInterface;
import com.sb.sweetbucket.rest.response.Category;
import com.sb.sweetbucket.rest.response.ShopsResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by harmeet on 24-08-2019.
 */

public class ShopFragment extends Fragment {

    private static final String TAG = ShopFragment.class.getSimpleName();
    private RecyclerView recyclerView;
    private TextView storeCountTextview;
    private GridLayoutManager gridLayoutManager;
    private ShopRecylerAdapter recylerAdapter;
    private List<ShopsResponse> shopsResponseList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_shop_fragment,container,false);
        storeCountTextview = (TextView)view.findViewById(R.id.countTextview);
        recyclerView = (RecyclerView)view.findViewById(R.id.recylerview);
        gridLayoutManager  = new GridLayoutManager(getContext(),2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recylerAdapter = new ShopRecylerAdapter(getContext(),shopsResponseList);
        recyclerView.setAdapter(recylerAdapter);
        loadShopData();
        return view;
    }



    private void loadShopData(){
        RestAPIInterface apiInterface = SweetBucketApplication.getApiClient().getClient().create(RestAPIInterface.class);
        Call<List<ShopsResponse>> responseCall = apiInterface.getShops();
        responseCall.enqueue(new Callback<List<ShopsResponse>>() {

            @Override
            public void onResponse(Call<List<ShopsResponse>> call, Response<List<ShopsResponse>> response) {

                if(response.code()==200) {
                    storeCountTextview.setText(response.body().size() + " Stores");
                    recylerAdapter.updateDataSource(response.body());
                }else Toast.makeText(getContext(),"Error ocurred",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<ShopsResponse>> call, Throwable t) {
                Log.e(TAG,t.getMessage());
                recylerAdapter.updateDataSource(new ArrayList<ShopsResponse>());
            }
        });
    }
}
