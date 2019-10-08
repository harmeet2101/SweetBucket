package com.sb.sweetbucket.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sb.sweetbucket.R;
import com.sb.sweetbucket.activities.SweetBucketApplication;
import com.sb.sweetbucket.adapters.TransactionRecyclerAdapter;
import com.sb.sweetbucket.controllers.SharedPreferncesController;
import com.sb.sweetbucket.rest.RestAPIInterface;
import com.sb.sweetbucket.rest.response.Transaction;
import com.sb.sweetbucket.rest.response.TransactionResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by harmeet on 08-10-2019.
 */

public class PaymentFragment extends Fragment {

    private static final String TAG = PaymentFragment.class.getSimpleName();
    private RecyclerView recyclerView;
    private List<Transaction> transactionList;
    private TransactionRecyclerAdapter recyclerAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.layout_payment_frag,container,false);
        recyclerView = (RecyclerView)view.findViewById(R.id.recylerview);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerAdapter = new TransactionRecyclerAdapter(getContext(),transactionList);
        recyclerView.setAdapter(recyclerAdapter);
        getTransactions();
        return view;
    }


    private void getTransactions(){

        RestAPIInterface apiInterface = SweetBucketApplication.getApiClient().getClient().create(RestAPIInterface.class);
        String apiToken = SharedPreferncesController.getSharedPrefController(getActivity()).getApiToken();
        if(apiToken!=null && !apiToken.isEmpty()){

            Call<TransactionResponse> responseCall = apiInterface.getTransactionDetails("Bearer "+apiToken);
            responseCall.enqueue(new Callback<TransactionResponse>() {
                @Override
                public void onResponse(Call<TransactionResponse> call, Response<TransactionResponse> response) {
                    Log.e(TAG,response.body().toString());
                    if (response.body()!=null && response.body().getPayments()!=null){

                        recyclerAdapter.updateDataSource(response.body().getPayments());

                    }
                }

                @Override
                public void onFailure(Call<TransactionResponse> call, Throwable t) {
                    Log.e(TAG,t.getMessage());
                }
            });
        }
    }
}
