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
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.sb.sweetbucket.R;
import com.sb.sweetbucket.adapters.CustomerReviewAdapter;
import com.sb.sweetbucket.adapters.HomeRecyclerAdapter;
import com.sb.sweetbucket.adapters.SimilarProductsRecylerAdapter;
import com.sb.sweetbucket.controllers.SharedPreferncesController;
import com.sb.sweetbucket.model.HomeDataStore;
import com.sb.sweetbucket.model.ProductDetails;
import com.sb.sweetbucket.rest.RestAPIInterface;
import com.sb.sweetbucket.rest.RestAppConstants;
import com.sb.sweetbucket.rest.request.AddCartRequest;
import com.sb.sweetbucket.rest.request.CheckPinRequest;
import com.sb.sweetbucket.rest.request.ProductReviewRequest;
import com.sb.sweetbucket.rest.response.AddCartResponse;
import com.sb.sweetbucket.rest.response.CartDetailsResponse;
import com.sb.sweetbucket.rest.response.PinCodeResponse;
import com.sb.sweetbucket.rest.response.Product;
import com.sb.sweetbucket.rest.response.ProductReviewResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by harmeet on 18-08-2019.
 */

    public class ProductDetailsActivity extends AppCompatActivity implements View.OnClickListener,SimilarProductsRecylerAdapter.IOnClick{
    private static final String TAG = ProductDetailsActivity.class.getSimpleName();
    private ImageView backArrowImgview;
    private TextView pCodeTextview,pCategoryTextview,pNameTextview,cartCountTextview,
            discTextview,basePriceTextview,dealPriceTextview,tvreview,reviewTextview1,reviewTextview2;
    private RatingBar ratingBar1,ratingBar2;
    private Product productDetails;
    private ImageView productImageview;
    private TextView productInfoTextview,vendorTextview;
    private Button pinCheckBtn,reviewSendBtn,cartButton;
    private EditText pincodeEdittext,reviewEdittext;
    private RecyclerView mRecylerView, commentsRecylerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<Product> productList = null;
    private SimilarProductsRecylerAdapter recylerAdapter;
    private CustomerReviewAdapter reviewAdapter;

    private HomeDataStore homeDataStore =HomeDataStore.getInstance();
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
        tvreview = (TextView)findViewById(R.id.tvreview);
        reviewSendBtn = (Button)findViewById(R.id.reviewSendBtn);
        cartButton = (Button)findViewById(R.id.cartButton);
        ratingBar1= (RatingBar)findViewById(R.id.ratingView01);
        ratingBar2 = (RatingBar)findViewById(R.id.ratingView02);
        reviewEdittext = (EditText)findViewById(R.id.reviewEdittext);
        reviewTextview1 = (TextView)findViewById(R.id.reviewTextview1);
        reviewTextview2 = (TextView)findViewById(R.id.reviewTextview2);
        basePriceTextview = (TextView)findViewById(R.id.basepriceTextview);
        dealPriceTextview = (TextView)findViewById(R.id.tvPrice);
        productImageview = (ImageView)findViewById(R.id.imgview01);
        productInfoTextview = (TextView)findViewById(R.id.proInfoTextview);
        vendorTextview = (TextView)findViewById(R.id.vendorTextview);
        cartCountTextview = (TextView)findViewById(R.id.cartCountTextview);
        setUpData();
        mRecylerView = (RecyclerView)findViewById(R.id.similarRecylerview);
        commentsRecylerView = (RecyclerView)findViewById(R.id.customerReviewRecylerview);
        layoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false);
        mRecylerView.setLayoutManager(layoutManager);
        commentsRecylerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));
        recylerAdapter = new SimilarProductsRecylerAdapter(getApplicationContext(),productList,this);
        reviewAdapter = new CustomerReviewAdapter(getApplicationContext(),productDetails.getRatingList());
        commentsRecylerView.setAdapter(reviewAdapter);
        mRecylerView.setAdapter(recylerAdapter);
        mRecylerView.setNestedScrollingEnabled(false);

        pinCheckBtn.setOnClickListener(this);
        reviewSendBtn.setOnClickListener(this);
        cartButton.setOnClickListener(this);
    }

    private void setUpData(){
        Bundle bundle = getIntent().getExtras();
        productDetails = (Product)bundle.getSerializable("productDetails");
        if (productDetails!=null){

            pCodeTextview.setText("Product Code: "+productDetails.getProductCode());
            pCategoryTextview.setText("Product Category: "+homeDataStore.getCategoryNameMap().
                    get(Integer.parseInt(productDetails.getCat1Id())));
            pNameTextview.setText(productDetails.getName());
            dealPriceTextview.setText("Rs "+productDetails.getSalePrice());
            discTextview.setText(productDetails.getDiscount()+" Off");
            basePriceTextview.setText("Rs "+productDetails.getBasePrice());
            productInfoTextview.setText(productDetails.getInfo());
            if (productDetails.getRatingList()!=null && !productDetails.getRatingList().isEmpty()){
                int size = productDetails.getRatingList().size();
                tvreview.setText(size+" Reviews");
                reviewTextview1.setText(size+" Ratings and Reviews");
                ratingBar1.setRating(Float.parseFloat(productDetails.getRatingList().get(0).getRating()));
                reviewTextview2.setText(productDetails.getRatingList().get(0).getRating()+" out of 5 Stars");
            }
            vendorTextview.setText(homeDataStore.getVendorNameMap().get(productDetails.getVendorId()));
            if (homeDataStore.getCartDetailsResponse()!=null)
            cartCountTextview.setText(homeDataStore.getCartDetailsResponse().getCartList().size()+"");
            Picasso.with(this).load(RestAppConstants.BASE_URL +productDetails.getImageUrl() ).
                    placeholder(R.drawable.dummy_img).into(productImageview);
        }
        if (productDetails.getCat1Id()!=null && !productDetails.getCat1Id().isEmpty())
        loadSimilarProducts(productDetails.getCat1Id());
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.pinCheckBtn:
                if (!pincodeEdittext.getText().toString().isEmpty())
                checkPincode(pincodeEdittext.getText().toString().trim());
                break;
            case R.id.reviewSendBtn:
                postYourReviews(new ProductReviewRequest(productDetails.getId(),ratingBar2.getRating(),
                        reviewEdittext.getText().toString()));
                reviewEdittext.setText("");
                ratingBar2.setRating(0f);
                break;
            case R.id.cartButton:
                addToCart(productDetails.getId());
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


    private void checkPincode(String pinCode){

        RestAPIInterface apiInterface = SweetBucketApplication.getApiClient().getClient().create(RestAPIInterface.class);
        Call<PinCodeResponse> responseCall = apiInterface.checkPin(new CheckPinRequest(Integer.parseInt(pinCode)));
        responseCall.enqueue(new Callback<PinCodeResponse>() {

                                 @Override
                                 public void onResponse(Call<PinCodeResponse> call, Response<PinCodeResponse> response) {
                                     Log.e(TAG,response.body().toString());
                                     Toast.makeText(getApplicationContext(),R.string.pin_delievry_status_succes,Toast.LENGTH_SHORT).show();
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

    private void postYourReviews(ProductReviewRequest request){
        RestAPIInterface apiInterface = SweetBucketApplication.getApiClient().getClient().create(RestAPIInterface.class);
        String apiToken = SharedPreferncesController.getSharedPrefController(getApplicationContext()).getApiToken();
        if(apiToken!=null && !apiToken.isEmpty()) {
            Call<ProductReviewResponse> responseCall = apiInterface.postProductReviews(request, "Bearer " + apiToken);
            responseCall.enqueue(new Callback<ProductReviewResponse>() {

                                     @Override
                                     public void onResponse(Call<ProductReviewResponse> call, Response<ProductReviewResponse> response) {
                                         Log.e(TAG, response.body().toString());
                                         Toast.makeText(getApplicationContext(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                                     }

                                     @Override
                                     public void onFailure(Call<ProductReviewResponse> call, Throwable t) {
                                         // Log.e(TAG,"error"+t.getMessage());
                                         Toast.makeText(getApplicationContext(), "Some Error occured", Toast.LENGTH_SHORT).show();
                                         // recylerAdapter.updateDataSource(new ArrayList<Product>());

                                     }

                                 }
            );
        }
    }

    @Override
    public void testOnClick(Product productDetails) {

        Bundle pBundle = new Bundle();
        pBundle.putSerializable("productDetails",productDetails);
        Intent pIntent = new Intent(getApplicationContext(),ProductDetailsActivity.class);
        pIntent.putExtras(pBundle);
        startActivity(pIntent);
    }


    private void addToCart(Integer productId){

        RestAPIInterface apiInterface = SweetBucketApplication.getApiClient().getClient().create(RestAPIInterface.class);
        String apiToken = SharedPreferncesController.getSharedPrefController(getApplicationContext()).getApiToken();
        AddCartRequest request = new AddCartRequest(productId);
        if(apiToken!=null && !apiToken.isEmpty()) {
            Call<AddCartResponse> responseCall = apiInterface.addToCart(request, "Bearer " + apiToken);
            responseCall.enqueue(new Callback<AddCartResponse>() {

                                     @Override
                                     public void onResponse(Call<AddCartResponse> call, Response<AddCartResponse> response) {
                                         Log.e(TAG, response.body().toString());
                                           Toast.makeText(getApplicationContext(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                                           getCartDetails();
                                     }

                                     @Override
                                     public void onFailure(Call<AddCartResponse> call, Throwable t) {
                                         // Log.e(TAG,"error"+t.getMessage());
                                         Toast.makeText(getApplicationContext(), "Some Error occured", Toast.LENGTH_SHORT).show();
                                         // recylerAdapter.updateDataSource(new ArrayList<Product>());

                                     }

                                 }
            );
        }
    }

    private void getCartDetails(){

        RestAPIInterface apiInterface = SweetBucketApplication.getApiClient().getClient().create(RestAPIInterface.class);
        String apiToken = SharedPreferncesController.getSharedPrefController(getApplicationContext()).getApiToken();
        if(apiToken!=null && !apiToken.isEmpty()) {
            Call<CartDetailsResponse> responseCall = apiInterface.getCartDetails("Bearer " + apiToken);
            responseCall.enqueue(new Callback<CartDetailsResponse>() {

                                     @Override
                                     public void onResponse(Call<CartDetailsResponse> call, Response<CartDetailsResponse> response) {
                                         if (response.body()!=null){
                                             Log.e(TAG, response.body().toString());
                                             HomeDataStore homeDataStore = HomeDataStore.getInstance();
                                             homeDataStore.setCartDetailsResponse(response.body());
                                             cartCountTextview.setText(response.body().getCartList().size()+"");
                                         }
                                         //  Toast.makeText(getApplicationContext(), response.body().toString(), Toast.LENGTH_SHORT).show();
                                     }

                                     @Override
                                     public void onFailure(Call<CartDetailsResponse> call, Throwable t) {
                                         Toast.makeText(getApplicationContext(), "Some Error occured", Toast.LENGTH_SHORT).show();

                                     }

                                 }
            );
        }
    }
}
