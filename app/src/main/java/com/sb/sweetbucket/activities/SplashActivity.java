package com.sb.sweetbucket.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.sb.sweetbucket.R;
import com.sb.sweetbucket.controllers.SharedPreferncesController;
import com.sb.sweetbucket.model.HomeDataStore;
import com.sb.sweetbucket.rest.RestAPIInterface;
import com.sb.sweetbucket.rest.response.Product;
import com.sb.sweetbucket.rest.response.ShopsResponse;
import com.sb.sweetbucket.utils.comparators.SortByDateComparator;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends Activity {

    private static final String TAG = SplashActivity.class.getSimpleName();
    private static final Long TIME_DELAY = 2000L;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferncesController controller = SharedPreferncesController.getSharedPrefController(getApplicationContext());
                if (controller.isUserLoggedIn())
                {
                    homeDataStore = HomeDataStore.getInstance();
                    try {
                        loadTrandingProducts();
                        loadTrandingShops();
                    }catch (Exception e){
                        e.printStackTrace();
                    }finally {
                        moveToHomeScreen();
                    }
                }else
                moveToLoginScreen();
            }
        },TIME_DELAY);
    }


    private void moveToLoginScreen(){

        startActivity(new Intent(getBaseContext(),LoginActivity.class));
        finish();
    }

    private void moveToHomeScreen(){
        startActivity(new Intent(getBaseContext(),DashboardActivity.class));
        finish();
    }


    private List<Product> productList = null;
    private List<ShopsResponse> shopsproductResponseList = null;
    private HomeDataStore homeDataStore = null;
    private void loadTrandingProducts(){

        RestAPIInterface apiInterface = SweetBucketApplication.getApiClient().getClient().create(RestAPIInterface.class);
        Call<List<Product>> responseCall = apiInterface.getTredingProducts();
        responseCall.enqueue(new Callback<List<Product>>() {

            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                Log.e(TAG,response.body().toString());
                productList = response.body();
                Collections.sort(productList,new SortByDateComparator( new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")));
                updateHomeDataStore(productList);
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                //Log.e(TAG,t.getMessage());
                productList = new ArrayList<>();
                updateHomeDataStore(productList);
            }

            void updateHomeDataStore(List<Product> productList){
                homeDataStore.setProductList(productList);
            }
        }
        );
    }

    private void loadTrandingShops(){
        RestAPIInterface apiInterface = SweetBucketApplication.getApiClient().getClient().create(RestAPIInterface.class);
        Call<List<ShopsResponse>> responseCall = apiInterface.getTredingShops();
        responseCall.enqueue(new Callback<List<ShopsResponse>>() {

            @Override
            public void onResponse(Call<List<ShopsResponse>> call, Response<List<ShopsResponse>> response) {

                if(response.code()==200) {
                    shopsproductResponseList = response.body();
                    updateHomeDataStore(shopsproductResponseList);
                }
            }

            @Override
            public void onFailure(Call<List<ShopsResponse>> call, Throwable t) {
                shopsproductResponseList = new ArrayList<>();
                updateHomeDataStore(shopsproductResponseList);
                Log.e(TAG,t.getMessage());
            }

            void updateHomeDataStore(List<ShopsResponse> shopsproductResponseList){
                homeDataStore.setShopsproductResponseList(shopsproductResponseList);
            }
        });
    }
}
