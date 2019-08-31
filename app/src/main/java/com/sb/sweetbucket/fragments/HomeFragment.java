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
import com.sb.sweetbucket.activities.SweetBucketApplication;
import com.sb.sweetbucket.adapters.HomeRecyclerAdapter;
import com.sb.sweetbucket.R;
import com.sb.sweetbucket.adapters.SearchListAdapter;
import com.sb.sweetbucket.model.ProductDetails;
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
    private List<String> searchList = new ArrayList<>();
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
// Get SearchView autocomplete object.
        searchAutoComplete = (SearchView.SearchAutoComplete)
                searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchListAdapter = new SearchListAdapter(getContext(), (ArrayList<String>) searchList);
        searchAutoComplete.setAdapter(searchListAdapter);
        // Listen to search view item on click event.
        searchAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int itemIndex, long id) {
                String queryString=(String)adapterView.getItemAtPosition(itemIndex);
                searchAutoComplete.setText("" + queryString);
                Toast.makeText(getContext(), "you clicked " + queryString, Toast.LENGTH_LONG).show();
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


    @Override
    public void testOnClick(ProductDetails productDetails) {

        Bundle pBundle = new Bundle();
        pBundle.putSerializable("productDetails",productDetails);
        Intent pIntent = new Intent(getContext(),ProductDetailsActivity.class);
        pIntent.putExtras(pBundle);
        getActivity().startActivity(pIntent);
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
