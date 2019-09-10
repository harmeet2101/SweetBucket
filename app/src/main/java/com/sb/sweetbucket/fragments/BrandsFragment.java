package com.sb.sweetbucket.fragments;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.sb.sweetbucket.R;
import com.sb.sweetbucket.activities.DashboardActivity;
import com.sb.sweetbucket.activities.ProductDetailsActivity;
import com.sb.sweetbucket.activities.SweetBucketApplication;
import com.sb.sweetbucket.adapters.BrandsRecylerAdapter;
import com.sb.sweetbucket.adapters.PopularProductsRecylerAdapter;
import com.sb.sweetbucket.adapters.SearchListAdapter;
import com.sb.sweetbucket.model.HomeDataStore;
import com.sb.sweetbucket.model.ProductDetails;
import com.sb.sweetbucket.rest.RestAPIInterface;
import com.sb.sweetbucket.rest.request.HomeRequest;
import com.sb.sweetbucket.rest.response.Category;
import com.sb.sweetbucket.rest.response.HomeResponse;
import com.sb.sweetbucket.rest.response.Product;
import com.sb.sweetbucket.rest.response.Shop;
import com.sb.sweetbucket.rest.response.ShopsResponse;
import com.sb.sweetbucket.utils.CommonUtils;
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

public class BrandsFragment extends Fragment implements BrandsRecylerAdapter.IOnProductClick,
        BrandsRecylerAdapter.IOnShopClick,SearchView.OnQueryTextListener{

    private static final String TAG = BrandsFragment.class.getSimpleName();
    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private List<Product> productResponseList;
    private List<ShopsResponse> shopsResponseList;
    private BrandsRecylerAdapter recyclerAdapter;
    private DashboardActivity dashboardActivity ;
    private SearchView searchView;
    private SearchListAdapter searchListAdapter;
    SearchView.SearchAutoComplete searchAutoComplete;
    private List<String> searchList = new ArrayList<>();
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        homeDataStore = HomeDataStore.getInstance();
        dashboardActivity = (DashboardActivity)context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.layout_brands_frag,container,false);
        recyclerView = (RecyclerView)view.findViewById(R.id.recylerview);
        SearchManager searchManager = (SearchManager) getContext().getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) view.findViewById(R.id.search);
        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setIconifiedByDefault(false);
        // searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(this);
// Get SearchView autocomplete object.
        searchAutoComplete = (SearchView.SearchAutoComplete)
                searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchListAdapter = new SearchListAdapter(getContext(), (ArrayList<String>) searchList);
        searchAutoComplete.setAdapter(searchListAdapter);
        searchAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int itemIndex, long id) {
                String queryString=(String)adapterView.getItemAtPosition(itemIndex);
                searchAutoComplete.setText("" + queryString);
                Toast.makeText(getContext(), "you clicked " + queryString, Toast.LENGTH_LONG).show();
                CommonUtils.hideSoftKeyboard(getContext(),view);
            }
        });
        loadSuggestionData("");
        loadTrandingShops();

        productResponseList = homeDataStore.getProductList();
        shopsResponseList = homeDataStore.getShopsproductResponseList();
        gridLayoutManager  = new GridLayoutManager(getContext(),2);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch(recyclerAdapter.getItemViewType(position)){
                    case BrandsRecylerAdapter.ITEM_TYPE_PRODUCT_ITEM:
                        return 1;
                    case BrandsRecylerAdapter.ITEM_TYPE_SHOP_ITEM:
                        return 1;
                    case BrandsRecylerAdapter.ITEM_TYPE_PRODUCT_ITEM_HEADING:
                        return 2;
                    case BrandsRecylerAdapter.ITEM_TYPE_SHOP_ITEM_HEADING:
                        return 2;
                        default:
                        return 1;
                }
            }
        });
        recyclerView.setLayoutManager(gridLayoutManager);
        //recyclerAdapter = new BrandsRecylerAdapter(getActivity().getBaseContext(),productResponseList,this);
        recyclerAdapter = new BrandsRecylerAdapter(getActivity().getBaseContext(),productResponseList,shopsResponseList,this,this);
        recyclerView.setAdapter(recyclerAdapter);

        //recyclerAdapter.updateDataSource(productResponseList);
        return view;
    }

    @Override
    public void OnProductClick(ProductDetails productDetails) {

        Bundle pBundle = new Bundle();
        pBundle.putSerializable("productDetails",productDetails);
        Intent pIntent = new Intent(getContext(),ProductDetailsActivity.class);
        pIntent.putExtras(pBundle);
        getActivity().startActivity(pIntent);
    }

    @Override
    public void OnShopClick() {

        dashboardActivity.switchToShopFragment();

    }

    @Override
    public void onAllProductClickEvent() {
        dashboardActivity.switchToHomeFragment();
    }



    //=============================================================
                // under testing since TODO 11-09-19
    //=============================================================

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
                                     recyclerAdapter.updateDataSource(productList,homeDataStore.getShopsproductResponseList());
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
                    loadTrandingProducts();
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


    private void loadSuggestionData(String suggestion){

        RestAPIInterface apiInterface = SweetBucketApplication.getApiClient().getClient().create(RestAPIInterface.class);
        Call<HomeResponse> responseCall = apiInterface.getSuggestion(new HomeRequest(suggestion));
        responseCall.enqueue(new Callback<HomeResponse>() {

            @Override
            public void onResponse(Call<HomeResponse> call, Response<HomeResponse> response) {
                Log.e(TAG,response.body().toString());
                //updating grid data in tis corresponding adapter
                //updateData(response.body());

                // updating search view data in its corresponding adapter
                for(Category c:response.body().getCategory()){
                    searchList.add(c.getName());
                }
                for(Product p:response.body().getProducts()){
                    searchList.add(p.getName());
                }
                for(Shop sh:response.body().getShops()){
                    searchList.add(sh.getStoreName());
                }
                searchListAdapter.updateDataSource((ArrayList<String>) searchList);
            }

            @Override
            public void onFailure(Call<HomeResponse> call, Throwable t) {
                //Log.e(TAG,t.getMessage());
               /* HomeResponse homeResponse = new HomeResponse();
                homeResponse.setProducts(new ArrayList<Product>());
                homeResponse.setCategory(new ArrayList<Category>());
                homeResponse.setShops(new ArrayList<Shop>());
                updateData(homeResponse);*/
            }
        });
    }
    @Override
    public boolean onQueryTextSubmit(String query) {

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
