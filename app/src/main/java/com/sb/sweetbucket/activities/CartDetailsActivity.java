package com.sb.sweetbucket.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sb.sweetbucket.R;
import com.sb.sweetbucket.adapters.AddressRecylerAdapter;
import com.sb.sweetbucket.adapters.CartRecylerAdapter;
import com.sb.sweetbucket.controllers.SharedPreferncesController;
import com.sb.sweetbucket.rest.RestAPIInterface;
import com.sb.sweetbucket.rest.response.Address;
import com.sb.sweetbucket.rest.response.CartDetailsResponse;
import com.sb.sweetbucket.rest.response.Product;
import com.sb.sweetbucket.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by harmeet on 05-10-2019.
 */

public class CartDetailsActivity extends AppCompatActivity {

    private static final String TAG = CartDetailsActivity.class.getSimpleName();
    private TextView cartItemsCountTextview,subTotalTv,delivryTv,taxTv,totalPayTv;
    private RecyclerView cartItemsRecylerView,addressRecylerView;
    private CartDetailsResponse cartDetailsResponse;
    private CartRecylerAdapter cartRecylerAdapter;
    private AddressRecylerAdapter addressRecylerAdapter;
    private List<Product> productList= new ArrayList<>();
    private List<Address> addressList;
    private ImageView backArrowImgview;
    private int cartSize=0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_cart_details);
        setupViews();
        Bundle bundle= getIntent().getExtras();
        if (bundle!=null){
            cartDetailsResponse = (CartDetailsResponse)bundle.getSerializable("cartDetails");
            if (cartDetailsResponse!=null){
                cartItemsCountTextview.setText(cartDetailsResponse.getCartList().size()+" Items");
                subTotalTv.setText("Rs."+cartDetailsResponse.getSubTotal());
                taxTv.setText("Rs."+cartDetailsResponse.getTax());
                delivryTv.setText("Rs."+cartDetailsResponse.getDelivery());
                totalPayTv.setText("Rs."+cartDetailsResponse.getCartTotal());
                getCartInfo(cartDetailsResponse);
            }
        }
        getAddressList();

    }

    private void setupViews(){

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        backArrowImgview = (ImageView)findViewById(R.id.backArrow);
        backArrowImgview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        cartItemsCountTextview = (TextView)findViewById(R.id.countTextview);
        subTotalTv = (TextView)findViewById(R.id.subTotalRsTv);
        delivryTv = (TextView)findViewById(R.id.DelRsTv);
        taxTv = (TextView)findViewById(R.id.TaxRsTv);
        totalPayTv = (TextView)findViewById(R.id.TotalPayRsTv);
        cartItemsRecylerView = (RecyclerView)findViewById(R.id.recylerview);
        addressRecylerView = (RecyclerView)findViewById(R.id.delRecylerview);
        cartRecylerAdapter = new CartRecylerAdapter(getApplicationContext(),productList,null);
        addressRecylerAdapter =new AddressRecylerAdapter(getApplicationContext(),addressList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        cartItemsRecylerView.setLayoutManager(layoutManager);
        addressRecylerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        cartItemsRecylerView.setAdapter(cartRecylerAdapter);
        addressRecylerView.setAdapter(addressRecylerAdapter);
    }

    private void getCartInfo(CartDetailsResponse cartDetailsResponse){

        cartSize= cartDetailsResponse.getCartList().size();
        productList = new ArrayList<>();
        for(int i = 0; i< cartSize; i++){

            loadProductDetails(cartDetailsResponse.getCartList().get(i).getProdId(),i);

        }
    }


    private void loadProductDetails(String id, final int pos){

        RestAPIInterface apiInterface = SweetBucketApplication.getApiClient().getClient()
                .create(RestAPIInterface.class);
        String base64ID = CommonUtils.getBase64EncodeString(id);
        Call<Product> responseCall = apiInterface.getProductByID(base64ID);
        responseCall.enqueue(new Callback<Product>() {

                                 @Override
                                 public void onResponse(Call<Product> call, Response<Product> response) {
                                     Log.e(TAG,response.body().toString());
                                     if(response.code()==200){

                                         productList.add(response.body());
                                         if (pos==(cartSize-1))
                                         cartRecylerAdapter.updateDataSource(productList,cartDetailsResponse.getCartList());
                                     }
                                 }

                                 @Override
                                 public void onFailure(Call<Product> call, Throwable t) {
                                     Log.e(TAG,t.getMessage());
                                 }
                             }
        );
    }


    private void getAddressList(){

        RestAPIInterface apiInterface = SweetBucketApplication.getApiClient().getClient().create(RestAPIInterface.class);
        String apiToken = SharedPreferncesController.getSharedPrefController(getApplicationContext()).getApiToken();
        if(apiToken!=null && !apiToken.isEmpty()){

            Call<List<Address>> responseCall = apiInterface.getAddressList("Bearer " + apiToken);
            responseCall.enqueue(new Callback<List<Address>>() {
                @Override
                public void onResponse(Call<List<Address>> call, Response<List<Address>> response) {
                    Log.e(TAG,response.body().toString());
                    addressList = response.body();
                    addressRecylerAdapter.updateDataSource(addressList);
                }


                @Override
                public void onFailure(Call<List<Address>> call, Throwable t) {

                    Log.e(TAG,t.getMessage());
                }
            });
        }

    }

}
