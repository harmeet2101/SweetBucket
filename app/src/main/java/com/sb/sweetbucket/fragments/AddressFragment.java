package com.sb.sweetbucket.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.sb.sweetbucket.R;
import com.sb.sweetbucket.activities.AddNewAddressActivity;
import com.sb.sweetbucket.activities.SweetBucketApplication;
import com.sb.sweetbucket.adapters.AddressFragRecylerAdapter;
import com.sb.sweetbucket.controllers.SharedPreferncesController;
import com.sb.sweetbucket.rest.RestAPIInterface;
import com.sb.sweetbucket.rest.response.Address;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by harmeet on 06-10-2019.
 */

public class AddressFragment extends Fragment {

    private static final String TAG = AddressFragment.class.getSimpleName();
    private Button addButton;
    private RecyclerView recyclerView;
    private AddressFragRecylerAdapter recylerAdapter;
    private List<Address> addressList;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.layout_address_frag,container,false);

        addButton = (Button)view.findViewById(R.id.addButton);
        recyclerView = (RecyclerView)view.findViewById(R.id.delRecylerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recylerAdapter = new AddressFragRecylerAdapter(getActivity(),addressList);
        recyclerView.setAdapter(recylerAdapter);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getContext(), AddNewAddressActivity.class);
                getActivity().startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getAddressList();
    }

    private void getAddressList(){

        RestAPIInterface apiInterface = SweetBucketApplication.getApiClient().getClient().create(RestAPIInterface.class);
        String apiToken = SharedPreferncesController.getSharedPrefController(getActivity()).getApiToken();
        if(apiToken!=null && !apiToken.isEmpty()){

            Call<List<Address>> responseCall = apiInterface.getAddressList("Bearer " + apiToken);
            responseCall.enqueue(new Callback<List<Address>>() {
                @Override
                public void onResponse(Call<List<Address>> call, Response<List<Address>> response) {
                    Log.e(TAG,response.body().toString());
                    addressList = response.body();
                    recylerAdapter.updateDataSource(addressList);
                }


                @Override
                public void onFailure(Call<List<Address>> call, Throwable t) {

                    Log.e(TAG,t.getMessage());
                    addressList = new ArrayList<>();
                    recylerAdapter.updateDataSource(addressList);
                }
            });
        }

    }
}
