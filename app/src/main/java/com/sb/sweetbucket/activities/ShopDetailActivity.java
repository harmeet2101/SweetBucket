package com.sb.sweetbucket.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.sb.sweetbucket.R;
import com.sb.sweetbucket.adapters.SweetsCategoryRecyclerAdapter;
import com.sb.sweetbucket.model.HomeDataStore;
import com.sb.sweetbucket.model.ProductDetails;
import com.sb.sweetbucket.rest.RestAPIInterface;
import com.sb.sweetbucket.rest.response.Category;
import com.sb.sweetbucket.rest.response.Product;
import com.sb.sweetbucket.rest.response.VendorResponse;
import com.sb.sweetbucket.utils.comparators.HIghToLowComparator;
import com.sb.sweetbucket.utils.comparators.LowToHighComparator;
import com.sb.sweetbucket.utils.comparators.SortByDateComparator;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by harmeet on 14-09-2019.
 */

public class ShopDetailActivity extends AppCompatActivity implements Spinner.OnItemSelectedListener,SweetsCategoryRecyclerAdapter.IOnItemClick{


    private static final String TAG = ShopDetailActivity.class.getSimpleName();
    private RecyclerView recyclerView;
    private ImageView backArrowImgview;
    private TextView tvProductCount,tvCategoryName;
    private Spinner sortSpinner;
    private GridLayoutManager gridLayoutManager;
    private String vendorID = null;
    private String vendorName = null;
    private List<Product> productList;
    private SweetsCategoryRecyclerAdapter recyclerAdapter=null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_shop_detail);
        recyclerView = (RecyclerView)findViewById(R.id.recylerview);
        tvProductCount = (TextView)findViewById(R.id.tvProductCount);
        tvCategoryName = (TextView)findViewById(R.id.tvCategoryName);
        sortSpinner = (Spinner)findViewById(R.id.sortSpinner);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        backArrowImgview = (ImageView)findViewById(R.id.backArrow);
        backArrowImgview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(),
                R.array.sort_spinner_items, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        sortSpinner.setAdapter(adapter);
        sortSpinner.setOnItemSelectedListener(this);
        gridLayoutManager  = new GridLayoutManager(getApplicationContext(),2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerAdapter = new SweetsCategoryRecyclerAdapter(getApplicationContext(),productList,this);
        recyclerView.setAdapter(recyclerAdapter);
        Bundle bundle = getIntent().getExtras();

        if(bundle!=null){
            vendorID = bundle.getString("vendorID");
            vendorName = bundle.getString("vendorName");
            tvCategoryName.setText(vendorName);
            loadProductsByVendorID(vendorID);
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        int position = i;
        switch (position){

            case 0:
                if (productList!=null){
                    Collections.sort(productList,new SortByDateComparator( new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")));
                    recyclerAdapter.updateDataSource(productList, HomeDataStore.getInstance().getCategoryList());
                }
                break;
            case 1:
                if(productList!=null){
                    Collections.sort(productList,new LowToHighComparator());
                    recyclerAdapter.updateDataSource(productList,HomeDataStore.getInstance().getCategoryList());
                }
                break;
            case 2:
                if(productList!=null){
                    Collections.sort(productList,new HIghToLowComparator());
                    recyclerAdapter.updateDataSource(productList,HomeDataStore.getInstance().getCategoryList());
                }
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void loadProductsByVendorID(String vendorId){

        RestAPIInterface apiInterface = SweetBucketApplication.getApiClient().getClient().create(RestAPIInterface.class);
        Call<VendorResponse> responseCall = apiInterface.getProductsByShopID(vendorId);
        responseCall.enqueue(new Callback<VendorResponse>() {

            @Override
            public void onResponse(Call<VendorResponse> call, Response<VendorResponse> response) {

                if(response.code()==200) {

                    productList = response.body().getProductList();
                    if(productList.size()==0){
                        tvProductCount.setText(productList.size()+" Product");
                    }
                    else if(productList.size()>0){
                        tvProductCount.setText(productList.size()+" Products");
                    }
                    Log.e(TAG,response.body().getProductList().toString());
                    Collections.sort(productList,new SortByDateComparator( new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")));
                    recyclerAdapter.updateDataSource(response.body().getProductList(),HomeDataStore.getInstance().getCategoryList());
                }
                else Toast.makeText(getApplicationContext(),"Error ocurred",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<VendorResponse> call, Throwable t) {
                Log.e(TAG,t.getMessage());
                recyclerAdapter.updateDataSource(new ArrayList<Product>(),null);
            }
        });
    }

    @Override
    public void OnItemClick(ProductDetails productDetails) {
        Bundle pBundle = new Bundle();
        pBundle.putSerializable("productDetails",productDetails);
        Intent pIntent = new Intent(getApplicationContext(),ProductDetailsActivity.class);
        pIntent.putExtras(pBundle);
        startActivity(pIntent);
    }
}
