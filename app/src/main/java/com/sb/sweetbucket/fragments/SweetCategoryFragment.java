package com.sb.sweetbucket.fragments;

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
import com.sb.sweetbucket.activities.SweetBucketApplication;
import com.sb.sweetbucket.adapters.SweetsCategoryRecyclerAdapter;
import com.sb.sweetbucket.model.HomeDataStore;
import com.sb.sweetbucket.rest.RestAPIInterface;
import com.sb.sweetbucket.rest.response.Product;
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
 * Created by harmeet on 25-08-2019.
 */

public class SweetCategoryFragment extends Fragment implements Spinner.OnItemSelectedListener {


    private static final String TAG = SweetCategoryFragment.class.getSimpleName();
    private RecyclerView recyclerView;
    private TextView tvProductCount;
    private Spinner sortSpinner;
    private GridLayoutManager gridLayoutManager;
    private String categoryParams = null;
    private SweetsCategoryRecyclerAdapter recyclerAdapter=null;
    private List<Product> productList;
    public static SweetCategoryFragment getInstance(String category){

        SweetCategoryFragment sweetCategoryFragment = new SweetCategoryFragment();
        Bundle mBundle = new Bundle();
        mBundle.putString("category",category);
        sweetCategoryFragment.setArguments(mBundle);
        return sweetCategoryFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments()!=null){
            categoryParams = getArguments().getString("category");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_sweet_category_frag,container,false);
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
        recyclerAdapter = new SweetsCategoryRecyclerAdapter(getContext(),productList,null);
        recyclerView.setAdapter(recyclerAdapter);
        loadCategoryData(categoryParams);
        return view;
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
}
