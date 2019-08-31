package com.sb.sweetbucket.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sb.sweetbucket.R;
import com.sb.sweetbucket.adapters.HomeRecyclerAdapter;
import com.sb.sweetbucket.model.ProductDetails;
import com.sb.sweetbucket.rest.RestAppConstants;
import com.squareup.picasso.Picasso;

/**
 * Created by harmeet on 18-08-2019.
 */

public class ProductDetailsActivity extends AppCompatActivity {
    private ImageView backArrowImgview;
    private TextView pCodeTextview,pCategoryTextview,pNameTextview,discTextview,basePriceTextview,dealPriceTextview;
    private ProductDetails productDetails;
    private ImageView productImageview;
    private TextView productInfoTextview,vendorTextview;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_product_details);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
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
        setUpData();
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
    }
}
