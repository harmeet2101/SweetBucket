package com.sb.sweetbucket.activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sb.sweetbucket.R;
import com.sb.sweetbucket.adapters.AddressFragRecylerAdapter;
import com.sb.sweetbucket.adapters.AddressRecylerAdapter;
import com.sb.sweetbucket.adapters.CartRecylerAdapter;
import com.sb.sweetbucket.adapters.PaymentModeRecylerAdapter;
import com.sb.sweetbucket.controllers.SharedPreferncesController;
import com.sb.sweetbucket.rest.RestAPIInterface;
import com.sb.sweetbucket.rest.request.CheckPinRequest;
import com.sb.sweetbucket.rest.request.PlaceOrderRequest;
import com.sb.sweetbucket.rest.response.Address;
import com.sb.sweetbucket.rest.response.CartDetailsResponse;
import com.sb.sweetbucket.rest.response.ConfirmOrderResponse;
import com.sb.sweetbucket.rest.response.CustomAddress;
import com.sb.sweetbucket.rest.response.PaymentModeResponse;
import com.sb.sweetbucket.rest.response.PinCodeResponse;
import com.sb.sweetbucket.rest.response.Product;
import com.sb.sweetbucket.utils.CommonUtils;
import com.sb.sweetbucket.utils.DividerVerticalItemDecoration;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by harmeet on 05-10-2019.
 */

public class CartDetailsActivity extends AppCompatActivity implements AddressRecylerAdapter.SingleClickListener{

    private static final String TAG = CartDetailsActivity.class.getSimpleName();
    private TextView cartItemsCountTextview,subTotalTv,delivryTv,taxTv,totalPayTv;
    private RecyclerView cartItemsRecylerView,addressRecylerView;
    private CartDetailsResponse cartDetailsResponse;
    private CartRecylerAdapter cartRecylerAdapter;
    private AddressRecylerAdapter addressRecylerAdapter;
    private List<Product> productList= new ArrayList<>();
    private List<CustomAddress> addressList;
    private ImageView backArrowImgview;
    private Button confirOrderBtn;
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


    }

    @Override
    protected void onResume() {
        super.onResume();
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
        confirOrderBtn = (Button)findViewById(R.id.placeOrderBtn);
        cartItemsRecylerView = (RecyclerView)findViewById(R.id.recylerview);
        addressRecylerView = (RecyclerView)findViewById(R.id.delRecylerview);
        RecyclerView.ItemDecoration itemDecoration =
                new DividerVerticalItemDecoration(getApplicationContext());
        cartRecylerAdapter = new CartRecylerAdapter(getApplicationContext(),productList,null);
        addressRecylerAdapter =new AddressRecylerAdapter(getApplicationContext(),addressList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        cartItemsRecylerView.setLayoutManager(layoutManager);
        addressRecylerView.setHasFixedSize(true);
        addressRecylerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        addressRecylerAdapter.setOnItemClickListener(this);



        cartItemsRecylerView.setAdapter(cartRecylerAdapter);
        addressRecylerView.setAdapter(addressRecylerAdapter);

        confirOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (selectedPin!=null && !selectedPin.isEmpty())
                checkPincode(selectedPin);
                else
                    Toast.makeText(getApplicationContext(),"Select Delivery Address",Toast.LENGTH_SHORT).show();

            }
        });
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

    private String selectedPin;
    private Integer selectedAddressId;
    private void getAddressList(){

        RestAPIInterface apiInterface = SweetBucketApplication.getApiClient().getClient().create(RestAPIInterface.class);
        String apiToken = SharedPreferncesController.getSharedPrefController(getApplicationContext()).getApiToken();
        if(apiToken!=null && !apiToken.isEmpty()){

            Call<List<Address>> responseCall = apiInterface.getAddressList("Bearer " + apiToken);
            responseCall.enqueue(new Callback<List<Address>>() {
                @Override
                public void onResponse(Call<List<Address>> call, Response<List<Address>> response) {
                    Log.e(TAG,response.body().toString());

                    addressList = new ArrayList<>();
                    for (int i=0;i<response.body().size();i++){

                        CustomAddress customAddress = new CustomAddress(response.body().get(i),false);
                        addressList.add(customAddress);
                    }
                    addressRecylerAdapter.updateDataSource(addressList);
                }


                @Override
                public void onFailure(Call<List<Address>> call, Throwable t) {

                    Log.e(TAG,t.getMessage());
                }
            });
        }

    }
    @Override
    public void onItemClickListener(int position, View view) {
        addressRecylerAdapter.selectedItem();
        selectedPin = addressList.get(position).getAddress().getPinCode();
        selectedAddressId = addressList.get(position).getAddress().getId();
  //      checkPincode(addressList.get(position).getAddress().getPinCode());
    }

    private void checkPincode(String pinCode){

        RestAPIInterface apiInterface = SweetBucketApplication.getApiClient().getClient().create(RestAPIInterface.class);
        Call<PinCodeResponse> responseCall = apiInterface.checkPin(new CheckPinRequest(Integer.parseInt(pinCode)));
        responseCall.enqueue(new Callback<PinCodeResponse>() {

                                 @Override
                                 public void onResponse(Call<PinCodeResponse> call, Response<PinCodeResponse> response) {
                                     Log.e(TAG,response.body().toString());
                                     //Toast.makeText(getApplicationContext(),R.string.pin_delievry_status_succes,Toast.LENGTH_SHORT).show();
                                     getPaymentModes();
                                 }

                                 @Override
                                 public void onFailure(Call<PinCodeResponse> call, Throwable t) {
                                     // Log.e(TAG,"error"+t.getMessage());
                                     Toast.makeText(getApplicationContext(),R.string.pin_delievry_status_fail,Toast.LENGTH_SHORT).show();
                                     // recylerAdapter.updateDataSource(new ArrayList<Product>());

                                 }

                             }
        );
    }

    private void getPaymentModes(){

        RestAPIInterface apiInterface = SweetBucketApplication.getApiClient().getClient().create(RestAPIInterface.class);
        String apiToken = SharedPreferncesController.getSharedPrefController(getApplicationContext()).getApiToken();
        if(apiToken!=null && !apiToken.isEmpty()){

            Call<List<PaymentModeResponse>> responseCall = apiInterface.getPaymentModes("Bearer " + apiToken);
            responseCall.enqueue(new Callback<List<PaymentModeResponse>>() {
                @Override
                public void onResponse(Call<List<PaymentModeResponse>> call, Response<List<PaymentModeResponse>> response) {
                    Log.e(TAG,response.body().toString());

                    if (response.body()!=null && response.body().size()>0){
                        showDialog(response.body());
                    }

                }


                @Override
                public void onFailure(Call<List<PaymentModeResponse>> call, Throwable t) {

                    Log.e(TAG,t.getMessage());
                }
            });
        }
    }

    private void placeOrder(PlaceOrderRequest placeOrderRequest){
        RestAPIInterface apiInterface = SweetBucketApplication.getApiClient().getClient().create(RestAPIInterface.class);
        String apiToken = SharedPreferncesController.getSharedPrefController(getApplicationContext()).getApiToken();
        if (apiToken!=null && !apiToken.isEmpty()){

            Call<ConfirmOrderResponse> responseCall = apiInterface.postOrder(placeOrderRequest,
                    "Bearer " + apiToken);
            responseCall.enqueue(new Callback<ConfirmOrderResponse>() {
                @Override
                public void onResponse(Call<ConfirmOrderResponse> call, Response<ConfirmOrderResponse> response) {
                    Log.e(TAG,response.body().toString());


                    if (response.body()!=null){
                        Toast.makeText(getApplicationContext(),response.body().getMsg(),Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }


                @Override
                public void onFailure(Call<ConfirmOrderResponse> call, Throwable t) {

                    Log.e(TAG,t.getMessage());
                }
            });
        }

    }

    public void showDialog(final List<PaymentModeResponse> responseList){

        final Dialog dialog = new Dialog(CartDetailsActivity.this);
        // dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.layout_payment_dialog);

        Button btndialog = (Button) dialog.findViewById(R.id.cnclBtn);
        btndialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });

        RecyclerView recyclerView = (RecyclerView)dialog.findViewById(R.id.recylerview);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        final PaymentModeRecylerAdapter recylerAdapter = new PaymentModeRecylerAdapter(getApplicationContext(),responseList);
        recyclerView.setAdapter(recylerAdapter);

        recylerAdapter.setOnItemClickListener(new PaymentModeRecylerAdapter.SingleClickListener() {

            @Override
            public void onItemClickListener(int position, View view) {
                recylerAdapter.selectedItem();
                try {
                    PlaceOrderRequest placeOrderRequest = new PlaceOrderRequest(selectedAddressId,responseList.get(position).getId());
                    placeOrder(placeOrderRequest);
                }catch (Exception e){
                    e.printStackTrace();
                }

                //Toast.makeText(getApplicationContext(),"id: "+responseList.get(position).getId(),Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        dialog.show();

    }
}
