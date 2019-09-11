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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.sb.sweetbucket.R;
import com.sb.sweetbucket.adapters.HomeRecyclerAdapter;
import com.sb.sweetbucket.adapters.SimilarProductsRecylerAdapter;
import com.sb.sweetbucket.model.ProductDetails;
import com.sb.sweetbucket.rest.RestAPIInterface;
import com.sb.sweetbucket.rest.RestAppConstants;
import com.sb.sweetbucket.rest.response.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by harmeet on 18-08-2019.
 */

    public class ProductDetailsActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = ProductDetailsActivity.class.getSimpleName();
    private ImageView backArrowImgview;
    private TextView pCodeTextview,pCategoryTextview,pNameTextview,discTextview,basePriceTextview,dealPriceTextview;
    private ProductDetails productDetails;
    private ImageView productImageview;
    private TextView productInfoTextview,vendorTextview;
    private Button pinCheckBtn;
    private EditText pincodeEdittext;
    private RecyclerView mRecylerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<Product> productList = null;
    private SimilarProductsRecylerAdapter recylerAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_product_details);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        pinCheckBtn = (Button)findViewById(R.id.pinCheckBtn);
        pincodeEdittext = (EditText)findViewById(R.id.edittextPinCode);
        backArrowImgview = (ImageView)findViewById(R.id.backArrow);
        backArrowImgview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        pCodeTextview = (TextView)findViewById(R.id.pCodeTextview);
        pCategoryTextview = (TextView)findViewById(R.id.pCategoryTextview);
        pNameTextview = (TextView)findViewById(R.id.tvSweetName);
        discTextview = (TextView)findViewById(R.id.discountTextview);
        basePriceTextview = (TextView)findViewById(R.id.basepriceTextview);
        dealPriceTextview = (TextView)findViewById(R.id.tvPrice);
        productImageview = (ImageView)findViewById(R.id.imgview01);
        productInfoTextview = (TextView)findViewById(R.id.proInfoTextview);
        vendorTextview = (TextView)findViewById(R.id.vendorTextview);
        mRecylerView = (RecyclerView)findViewById(R.id.similarRecylerview);
        layoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false);
        mRecylerView.setLayoutManager(layoutManager);
        recylerAdapter = new SimilarProductsRecylerAdapter(getApplicationContext(),productList,null);
        mRecylerView.setAdapter(recylerAdapter);
        mRecylerView.setNestedScrollingEnabled(false);
        setUpData();
        pinCheckBtn.setOnClickListener(this);
    }

    private void setUpData(){
        Bundle bundle = getIntent().getExtras();
        productDetails = (ProductDetails)bundle.getSerializable("productDetails");
        if (productDetails!=null){

            pCodeTextview.setText("Product Code: "+productDetails.getProductCode());
            pCategoryTextview.setText("Product Category: "+productDetails.getCatName());
            pNameTextview.setText(productDetails.getName());
            dealPriceTextview.setText("Rs "+productDetails.getSalePrice());
            discTextview.setText(productDetails.getDiscount()+" Off");
            basePriceTextview.setText("Rs "+productDetails.getBasePrice());
            productInfoTextview.setText(productDetails.getInfo());
            vendorTextview.setText(productDetails.getVendorName());
            Picasso.with(this).load(RestAppConstants.BASE_URL +productDetails.getImageUrl() ).
                    placeholder(R.drawable.dummy_img).into(productImageview);
        }
        if (productDetails.getCatId()!=null && !productDetails.getCatId().isEmpty())
        loadSimilarProducts(productDetails.getCatId());
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.pinCheckBtn:
                break;
        }
    }

    private void loadSimilarProducts(String cat_id){

        RestAPIInterface apiInterface = SweetBucketApplication.getApiClient().getClient().create(RestAPIInterface.class);
        Call<List<Product>> responseCall = apiInterface.getSimilarProductsByCategory(cat_id);
        responseCall.enqueue(new Callback<List<Product>>() {

                                 @Override
                                 public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                                     Log.e(TAG,response.body().toString());

                                     recylerAdapter.updateDataSource(response.body());
                                 }

                                 @Override
                                 public void onFailure(Call<List<Product>> call, Throwable t) {
                                     Log.e(TAG,t.getMessage());
                                     recylerAdapter.updateDataSource(new ArrayList<Product>());

                                 }

                             }
        );
    }
}
