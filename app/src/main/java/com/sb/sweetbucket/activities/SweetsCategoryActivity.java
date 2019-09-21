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

import com.sb.sweetbucket.R;
import com.sb.sweetbucket.adapters.SweetsCategoryRecyclerAdapter;
import com.sb.sweetbucket.model.HomeDataStore;
import com.sb.sweetbucket.model.ProductDetails;
import com.sb.sweetbucket.rest.RestAPIInterface;
import com.sb.sweetbucket.rest.response.Product;
import com.sb.sweetbucket.utils.CommonUtils;
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
 * Created by harmeet on 31-08-2019.
 */

public class SweetsCategoryActivity extends AppCompatActivity implements Spinner.OnItemSelectedListener,SweetsCategoryRecyclerAdapter.IOnItemClick {

    private static final String TAG = SweetsCategoryActivity.class.getSimpleName();
    private RecyclerView recyclerView;
    private ImageView backArrowImgview;
    private TextView tvProductCount,tvCategoryName;
    private Spinner sortSpinner;
    private GridLayoutManager gridLayoutManager;
    private String categoryParams = null;
    private SweetsCategoryRecyclerAdapter recyclerAdapter=null;
    private List<Product> productList;
    private HomeDataStore homeDataStore = HomeDataStore.getInstance();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_sweet_category_frag);
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
            categoryParams = bundle.getString("category");
            tvCategoryName.setText(categoryParams);
            loadCategoryData(categoryParams);
        }
    }

    private void loadCategoryData(String category){
        RestAPIInterface apiInterface = SweetBucketApplication.getApiClient().getClient().create(RestAPIInterface.class);
        Call<List<Product>> responseCall = apiInterface.getSweetsByCategory(category);
        responseCall.enqueue(new Callback<List<Product>>() {

            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {

                productList = response.body();
                if(productList.size()==0){
                    tvProductCount.setText(productList.size()+" Product");
                }
                else if(productList.size()>0){
                    tvProductCount.setText(productList.size()+" Products");
                }
                Log.e("resp",response.body().toString());
                Collections.sort(productList,new SortByDateComparator( new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")));
                recyclerAdapter.updateDataSource(productList, HomeDataStore.getInstance().getCategoryList());
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.e(TAG,t.getMessage());
                recyclerAdapter.updateDataSource(new ArrayList<Product>(),null);
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        int position = i;
        switch (position){

            case 0:
                if (productList!=null){
                    Collections.sort(productList,new SortByDateComparator( new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")));
                    recyclerAdapter.updateDataSource(productList,HomeDataStore.getInstance().getCategoryList());
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

    @Override
    public void OnItemClick(String id) {

        loadProductDetails(id);
    }

    private void loadProductDetails(String id){

        RestAPIInterface apiInterface = SweetBucketApplication.getApiClient().getClient().create(RestAPIInterface.class);
        String base64ID = CommonUtils.getBase64EncodeString(id);
        Call<Product> responseCall = apiInterface.getProductByID(base64ID);
        responseCall.enqueue(new Callback<Product>() {

                                 @Override
                                 public void onResponse(Call<Product> call, Response<Product> response) {
                                     Log.e(TAG,response.body().toString());
                                     if(response.code()==200){
                                         Product product = response.body();
                                         /*ProductDetails details = new ProductDetails(product.getId(),product.getCat1Id(),product.getProductCode(),product.getName(),
                                                 homeDataStore.getCategoryNameMap().get(Integer.parseInt(product.getCat1Id())),
                                                 homeDataStore.getVendorNameMap().get(product.getVendorId())
                                                 ,product.getInfo(),product.getTags(),product.getImageUrl(),product.getBasePrice(),product.getDealPrice(),product.getSalePrice(),
                                                 product.getDiscount(),product.getUnit(),product.getStockQty()
                                         );*/
                                         Bundle pBundle = new Bundle();
                                         pBundle.putSerializable("productDetails",response.body());
                                         Intent pIntent = new Intent(getApplicationContext(),ProductDetailsActivity.class);
                                         pIntent.putExtras(pBundle);
                                         startActivity(pIntent);
                                     }
                                 }

                                 @Override
                                 public void onFailure(Call<Product> call, Throwable t) {
                                     Log.e(TAG,t.getMessage());
                                 }
                             }
        );
    }
}
