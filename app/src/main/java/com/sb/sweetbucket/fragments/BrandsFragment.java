package com.sb.sweetbucket.fragments;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.sb.sweetbucket.R;
import com.sb.sweetbucket.activities.DashboardActivity;
import com.sb.sweetbucket.activities.ProductDetailsActivity;
import com.sb.sweetbucket.activities.ShopDetailActivity;
import com.sb.sweetbucket.activities.SweetBucketApplication;
import com.sb.sweetbucket.activities.SweetsCategoryActivity;
import com.sb.sweetbucket.activities.TestActivity;
import com.sb.sweetbucket.adapters.BrandsRecylerAdapter;
import com.sb.sweetbucket.adapters.SearchListAdapter;
import com.sb.sweetbucket.adapters.SearchListCustomAdapter;
import com.sb.sweetbucket.model.HomeDataStore;
import com.sb.sweetbucket.model.ProductDetails;
import com.sb.sweetbucket.model.Search;
import com.sb.sweetbucket.rest.RestAPIInterface;
import com.sb.sweetbucket.rest.request.HomeRequest;
import com.sb.sweetbucket.rest.response.Category;
import com.sb.sweetbucket.rest.response.HomeResponse;
import com.sb.sweetbucket.rest.response.Product;
import com.sb.sweetbucket.rest.response.Shop;
import com.sb.sweetbucket.rest.response.ShopsResponse;
import com.sb.sweetbucket.utils.CommonUtils;
import com.sb.sweetbucket.utils.comparators.SortByDateComparator;

import java.io.Serializable;
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
    private List<Search> mSearchList = new ArrayList<>();
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        homeDataStore = HomeDataStore.getInstance();
        dashboardActivity = (DashboardActivity)context;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

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
        searchListAdapter = new SearchListAdapter(getContext(), (ArrayList<Search>) mSearchList);

         searchAutoComplete.setAdapter(searchListAdapter);
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

                            ProductDetails details = new ProductDetails(product.getId(),product.getCat1Id(),product.getProductCode(),product.getName(),
                                    "",
                                    ""
                                    ,product.getInfo(),product.getTags(),product.getImageUrl(),product.getBasePrice(),product.getDealPrice(),product.getSalePrice(),
                                    product.getDiscount(),product.getUnit(),product.getStockQty()
                            );
                            OnProductClick(details);

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
        searchAutoComplete.setOnDismissListener(new AutoCompleteTextView.OnDismissListener() {

            @Override
            public void onDismiss() {
                CommonUtils.hideSoftKeyboard(getContext(),searchAutoComplete);
            }
        });
        /*searchAutoComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "clicked ", Toast.LENGTH_LONG).show();
            }
        });*/
        loadSuggestionData("");
        loadTrandingShops();
       // setSearchAdapter();
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

        Button btn = (Button)view.findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent  = new Intent(getContext(), TestActivity.class);
                Bundle bundle = new Bundle();
                Category category = new Category();
                category.setName("Heading");
                categoryList.add(0,category);
                bundle.putSerializable("cat", (Serializable) categoryList);
                List<Product> products = productList;
               /* Product product = new Product();
                product.setName("Heading");
                products.remove(0);
                products.add(0,product);*/
                bundle.putSerializable("prod", (Serializable)productList);

                Shop shop = new Shop();
                shop.setStoreName("Heading");
                List<Shop> shopList = homeDataStore.getAllShopList();
                shopList.add(0,shop);
                bundle.putSerializable("shop", (Serializable) shopList);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        return view;
    }

    private void setSearchAdapter(){
        Category category = new Category();
        category.setName("Heading");
        List<Category> categories = categoryList;
       // categories.add(0,category);
        Shop shop = new Shop();
        shop.setStoreName("Heading");
        List<Shop> shopList = homeDataStore.getAllShopList();
        //shopList.add(0,shop);

        List<Product> products = productList;
        products.remove(0);
        SearchListCustomAdapter customAdapter = new SearchListCustomAdapter(products,categories,shopList,getContext());
        searchAutoComplete.setAdapter(customAdapter);
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
    public void onShopSelected(String vendorID,String vendorName) {
        Bundle bundle  = new Bundle();
        bundle.putString("vendorName",vendorName);
        bundle.putString("vendorID",vendorID);
        Intent intent = new Intent(getContext(),ShopDetailActivity.class);
        intent.putExtras(bundle);
        getActivity().startActivity(intent);
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
    private List<Category> categoryList;
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
                                     recyclerAdapter.updateDataSource(productList,shopsproductResponseList,categoryList);
                                       // setSearchAdapter();
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
                    loadCategoryData();

                }
            }

            @Override
            public void onFailure(Call<List<ShopsResponse>> call, Throwable t) {
                shopsproductResponseList = new ArrayList<>();
                updateHomeDataStore(shopsproductResponseList);
               // Log.e(TAG,t.getMessage());
            }

            void updateHomeDataStore(List<ShopsResponse> shopsproductResponseList){
                homeDataStore.setShopsproductResponseList(shopsproductResponseList);
            }
        });
    }

    private void loadCategoryData(){
        RestAPIInterface apiInterface = SweetBucketApplication.getApiClient().getClient().create(RestAPIInterface.class);
        Call<List<Category>> responseCall = apiInterface.getCategory();
        responseCall.enqueue(new Callback<List<Category>>() {

            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {


                updateHomeDataStore(response.body());
                categoryList = response.body();
                loadTrandingProducts();
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Log.e(TAG,t.getMessage());
                categoryList = new ArrayList<>();
                updateHomeDataStore(categoryList);
            }

            void updateHomeDataStore(List<Category> categoryList){
                homeDataStore.setCategoryList(categoryList);
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
                HomeDataStore.getInstance().setAllShopList(response.body().getShops());
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
