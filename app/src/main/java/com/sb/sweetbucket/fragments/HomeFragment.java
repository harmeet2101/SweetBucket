package com.sb.sweetbucket.fragments;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.CursorAdapter;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.sb.sweetbucket.activities.LoginActivity;
import com.sb.sweetbucket.activities.ProductDetailsActivity;
import com.sb.sweetbucket.activities.ShopDetailActivity;
import com.sb.sweetbucket.activities.SweetBucketApplication;
import com.sb.sweetbucket.activities.SweetsCategoryActivity;
import com.sb.sweetbucket.adapters.HomeRecyclerAdapter;
import com.sb.sweetbucket.R;
import com.sb.sweetbucket.adapters.SearchListAdapter;
import com.sb.sweetbucket.model.HomeDataStore;
import com.sb.sweetbucket.model.ProductDetails;
import com.sb.sweetbucket.model.Search;
import com.sb.sweetbucket.rest.RestAPIInterface;
import com.sb.sweetbucket.rest.request.HomeRequest;
import com.sb.sweetbucket.rest.response.Category;
import com.sb.sweetbucket.rest.response.HomeResponse;
import com.sb.sweetbucket.rest.response.Product;
import com.sb.sweetbucket.rest.response.Shop;
import com.sb.sweetbucket.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

import okhttp3.internal.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by harmeet on 17-08-2019.
 */

@SuppressLint("ValidFragment")
public class HomeFragment extends Fragment implements HomeRecyclerAdapter.IOnClick,SearchView.OnQueryTextListener{

    private static final String TAG = HomeFragment.class.getSimpleName();
    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private HomeRecyclerAdapter recyclerAdapter;
    private List<Product> responseList;
    private SearchView searchView;
    private SearchListAdapter searchListAdapter;
    SearchView.SearchAutoComplete searchAutoComplete;
    private List<Search> mSearchList = new ArrayList<>();
    private HomeDataStore homeDataStore;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.layout_home_frag,container,false);
        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getContext().getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) view.findViewById(R.id.search);
        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setIconifiedByDefault(false);
       // searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(this);
        homeDataStore = HomeDataStore.getInstance();
// Get SearchView autocomplete object.
        searchAutoComplete = (SearchView.SearchAutoComplete)
                searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchListAdapter = new SearchListAdapter(getContext(),(ArrayList<Search>)mSearchList);
        searchAutoComplete.setAdapter(searchListAdapter);
        // Listen to search view item on click event.
        searchAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int itemIndex, long id) {
                Search queryObj=(Search)adapterView.getItemAtPosition(itemIndex);
                searchAutoComplete.setText("" + queryObj.getName());

                switch (queryObj.getType()){

                    case "store": {
                        if (homeDataStore.getShopsResponseMap().containsKey(queryObj.getName())) {
                            Bundle bundle = new Bundle();
                            Shop shopsResponse = homeDataStore.getShopsResponseMap().get(queryObj.getName());
                            String _id = CommonUtils.getBase64EncodeString(shopsResponse.getVendorId());
                            bundle.putString("vendorName", shopsResponse.getStoreName());
                            bundle.putString("vendorID", _id);
                            Intent intent = new Intent(getContext(), ShopDetailActivity.class);
                            intent.putExtras(bundle);
                            getActivity().startActivity(intent);
                        }
                    }
                    break;

                    case "prod": {
                        if (homeDataStore.getProductMap().containsKey(queryObj.getName())) {
                            Product product = homeDataStore.getProductMap().get(queryObj.getName());
                            OnProductClick(product.getId()+"");

                        }
                    }
                    break;
                    case "cat": {
                        Bundle bundle = new Bundle();
                        bundle.putString("category",queryObj.getName());
                        Intent intent = new Intent(getContext(),SweetsCategoryActivity.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                }
                // Toast.makeText(getContext(), "you clicked " + queryObj.getName()+": "+queryObj.getType(), Toast.LENGTH_LONG).show();
                CommonUtils.hideSoftKeyboard(getContext(),view);
            }
        });

        recyclerView = (RecyclerView)view.findViewById(R.id.recylerview);
        gridLayoutManager  = new GridLayoutManager(getContext(),2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerAdapter = new HomeRecyclerAdapter(getActivity().getBaseContext(),responseList,this);
        recyclerView.setAdapter(recyclerAdapter);
        loadSuggestionData("");
        return view;
    }
    public void OnProductClick(String id) {

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
                                        /* ProductDetails details = new ProductDetails(product.getId(),product.getCat1Id(),product.getProductCode(),product.getName(),
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
    @Override
    public void testOnClick(String id) {

        loadProductDetails(id);
    }

    public void updateData(HomeResponse homeResponse){
        Log.d(HomeFragment.class.getSimpleName(),homeResponse.toString());
        recyclerAdapter.updateDataSource(homeResponse);
    }


    private void loadSuggestionData(String suggestion){

        RestAPIInterface apiInterface = SweetBucketApplication.getApiClient().getClient().create(RestAPIInterface.class);
        Call<HomeResponse> responseCall = apiInterface.getSuggestion(new HomeRequest(suggestion));
        responseCall.enqueue(new Callback<HomeResponse>() {

            @Override
            public void onResponse(Call<HomeResponse> call, Response<HomeResponse> response) {
                Log.e(TAG,response.body().toString());
                //updating grid data in tis corresponding adapter
                updateData(response.body());

                for(Category c:response.body().getCategory()){

                    mSearchList.add(new Search(c.getName(),"cat"));
                }

                for(Product p:response.body().getProducts()){

                    mSearchList.add(new Search(p.getName(),"prod"));
                }
                for(Shop sh:response.body().getShops()){

                    mSearchList.add(new Search(sh.getStoreName(),"store"));
                }
                searchListAdapter.updateDataSource((ArrayList<Search>) mSearchList);
            }

            @Override
            public void onFailure(Call<HomeResponse> call, Throwable t) {
                //Log.e(TAG,t.getMessage());
                HomeResponse homeResponse = new HomeResponse();
                homeResponse.setProducts(new ArrayList<Product>());
                homeResponse.setCategory(new ArrayList<Category>());
                homeResponse.setShops(new ArrayList<Shop>());
                updateData(homeResponse);
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
