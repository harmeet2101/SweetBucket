package com.sb.sweetbucket.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telecom.Call;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.sb.sweetbucket.R;
import com.sb.sweetbucket.activities.OrderDetailsActivity;
import com.sb.sweetbucket.activities.SweetBucketApplication;
import com.sb.sweetbucket.adapters.OrdersRecyclerAdapter;
import com.sb.sweetbucket.controllers.SharedPreferncesController;
import com.sb.sweetbucket.rest.RestAPIInterface;
import com.sb.sweetbucket.rest.request.OrderDetailRequest;
import com.sb.sweetbucket.rest.response.Order;
import com.sb.sweetbucket.rest.response.OrderDetailResponse;
import com.sb.sweetbucket.rest.response.OrdersResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by harmeet on 08-10-2019.
 */

public class OrderFragment extends Fragment implements OrdersRecyclerAdapter.IOrderFragCallback{

    private static final String TAG = OrderFragment.class.getSimpleName();
    private List<Order> orderList;
    private RecyclerView recyclerView;
    private OrdersRecyclerAdapter recyclerAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.layout_order_frag,container,false);
        recyclerView = (RecyclerView)view.findViewById(R.id.recylerview);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerAdapter = new OrdersRecyclerAdapter(getContext(),orderList,this);
        recyclerView.setAdapter(recyclerAdapter);
        getOrderList();
        return view;
    }

    private void getOrderList(){
        RestAPIInterface apiInterface = SweetBucketApplication.getApiClient().getClient().create(RestAPIInterface.class);
        String apiToken = SharedPreferncesController.getSharedPrefController(getActivity()).getApiToken();
        if(apiToken!=null && !apiToken.isEmpty()){

            retrofit2.Call<OrdersResponse> responseCall = apiInterface.getOrders("Bearer "+apiToken);

            responseCall.enqueue(new Callback<OrdersResponse>() {
                @Override
                public void onResponse(retrofit2.Call<OrdersResponse> call, Response<OrdersResponse> response) {

                    Log.e(TAG,response.body().getOrders().toString());
                    orderList = response.body().getOrders();
                    recyclerAdapter.updateDataSource(orderList);
                   /* for (int i =0;i<orderList.size();i++){
                        getOrderDetails(new OrderDetailRequest(orderList.get(i).getOrderNo()));
                    }*/
                }

                @Override
                public void onFailure(retrofit2.Call<OrdersResponse> call, Throwable t) {

                    Log.e(TAG,t.getMessage());
                }
            });

        }
    }

     public void getOrderDetails(OrderDetailRequest orderDetailRequest){

        RestAPIInterface apiInterface = SweetBucketApplication.getApiClient().getClient().create(RestAPIInterface.class);
        String apiToken = SharedPreferncesController.getSharedPrefController(getActivity()).getApiToken();
        if(apiToken!=null && !apiToken.isEmpty()){

            retrofit2.Call<OrderDetailResponse> responseCall = apiInterface.getOrderDetails(orderDetailRequest,"Bearer "+apiToken);
            responseCall.enqueue(new Callback<OrderDetailResponse>() {
                @Override
                public void onResponse(retrofit2.Call<OrderDetailResponse> call, Response<OrderDetailResponse> response) {

                    Log.e(TAG,response.body().getOrder().toString());
                    if (response.body()!=null && response.body().getOrder()!=null) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("orderDetails", response.body().getOrder());
                        Intent intent = new Intent(getContext(), OrderDetailsActivity.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                }

                @Override
                public void onFailure(retrofit2.Call<OrderDetailResponse> call, Throwable t) {

                    Log.e(TAG,t.getMessage());
                }
            });
        }
    }

}
