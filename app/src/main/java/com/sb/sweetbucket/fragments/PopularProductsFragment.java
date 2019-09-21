package com.sb.sweetbucket.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.sb.sweetbucket.R;
import com.sb.sweetbucket.activities.ProductDetailsActivity;
import com.sb.sweetbucket.activities.SweetBucketApplication;
import com.sb.sweetbucket.adapters.HomeRecyclerAdapter;
import com.sb.sweetbucket.adapters.PopularProductsRecylerAdapter;
import com.sb.sweetbucket.model.HomeDataStore;
import com.sb.sweetbucket.model.ProductDetails;
import com.sb.sweetbucket.rest.RestAPIInterface;
import com.sb.sweetbucket.rest.response.Product;
import com.sb.sweetbucket.utils.CommonUtils;
import com.sb.sweetbucket.utils.comparators.HIghToLowComparator;
import com.sb.sweetbucket.utils.comparators.LowToHighComparator;
import com.sb.sweetbucket.utils.comparators.SortByDateComparator;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by harmeet on 31-08-2019.
 */

public class PopularProductsFragment extends Fragment implements PopularProductsRecylerAdapter.IOnClick, Spinner.OnItemSelectedListener{

    private static final String TAG = PopularProductsFragment.class.getSimpleName();
    private RecyclerView recyclerView;
    private TextView tvProductCount;
    private Spinner sortSpinner;
    private GridLayoutManager gridLayoutManager;
    private List<Product> responseList;
    private PopularProductsRecylerAdapter recyclerAdapter;
    private HomeDataStore homeDataStore = HomeDataStore.getInstance();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.layout_popular_frag,container,false);
        recyclerView = (RecyclerView)view.findViewById(R.id.recylerview);
        tvProductCount = (TextView)view.findViewById(R.id.tvProductCount);
        sortSpinner = (Spinner)view.findViewById(R.id.sortSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.sort_spinner_items, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        sortSpinner.setAdapter(adapter);
        sortSpinner.setOnItemSelectedListener(this);
        gridLayoutManager  = new GridLayoutManager(getContext(),2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerAdapter = new PopularProductsRecylerAdapter(getActivity().getBaseContext(),responseList,this);
        recyclerView.setAdapter(recyclerAdapter);
        loadData();
        return view;
    }

    @Override
    public void testOnClick(String id) {

        loadProductDetails(id);
    }

    private void loadData(){

        RestAPIInterface apiInterface = SweetBucketApplication.getApiClient().getClient().create(RestAPIInterface.class);
        Call<List<Product>> responseCall = apiInterface.getTredingProducts();
        responseCall.enqueue(new Callback<List<Product>>() {

            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                Log.e(TAG,response.body().toString());
                responseList = response.body();
                if(responseList.size()==0){
                    tvProductCount.setText(responseList.size()+" Product");
                }
                else if(responseList.size()>0){
                    tvProductCount.setText(responseList.size()+" Products");
                }
                Collections.sort(responseList,new SortByDateComparator( new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")));
                updateData(responseList);
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                //Log.e(TAG,t.getMessage());
            }
        });
    }

    public void updateData(List<Product> products){
        Log.d(HomeFragment.class.getSimpleName(),products.toString());
        recyclerAdapter.updateDataSource(products);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        int position = i;
        switch (position){

            case 0:
                if (responseList!=null){
                    Collections.sort(responseList,new SortByDateComparator( new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")));
                    recyclerAdapter.updateDataSource(responseList);
                }
                break;
            case 1:
                if(responseList!=null){
                    Collections.sort(responseList,new LowToHighComparator());
                    recyclerAdapter.updateDataSource(responseList);
                }
                break;
            case 2:
                if(responseList!=null){
                    Collections.sort(responseList,new HIghToLowComparator());
                    recyclerAdapter.updateDataSource(responseList);
                }
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

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
                                       /*  ProductDetails details = new ProductDetails(product.getId(),product.getCat1Id(),product.getProductCode(),product.getName(),
                                                 homeDataStore.getCategoryNameMap().get(Integer.parseInt(product.getCat1Id())),
                                                 homeDataStore.getVendorNameMap().get(product.getVendorId())
                                                 ,product.getInfo(),product.getTags(),product.getImageUrl(),product.getBasePrice(),product.getDealPrice(),product.getSalePrice(),
                                                 product.getDiscount(),product.getUnit(),product.getStockQty()
                                         );*/
                                         Bundle pBundle = new Bundle();
                                         pBundle.putSerializable("productDetails",response.body());
                                         Intent pIntent = new Intent(getContext(),ProductDetailsActivity.class);
                                         pIntent.putExtras(pBundle);
                                         getActivity().startActivity(pIntent);
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
