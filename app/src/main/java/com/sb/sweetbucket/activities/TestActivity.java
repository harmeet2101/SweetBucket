package com.sb.sweetbucket.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.sb.sweetbucket.R;
import com.sb.sweetbucket.adapters.SearchListCustomAdapter;
import com.sb.sweetbucket.rest.response.Category;
import com.sb.sweetbucket.rest.response.Product;
import com.sb.sweetbucket.rest.response.Shop;

import java.util.List;

/**
 * Created by harmeet on 15-09-2019.
 */

public class TestActivity extends AppCompatActivity {


    ListView listView;
    private List<Category> categoryList;
    private List<Product>productList;
    private List<Shop> shopList;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_test);
        listView = (ListView)findViewById(R.id.listview);

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            categoryList = (List<Category>)bundle.getSerializable("cat");
            productList = (List<Product>)bundle.getSerializable("prod");
            shopList = (List<Shop>)bundle.getSerializable("shop");
        }
        SearchListCustomAdapter searchListCustomAdapter = new SearchListCustomAdapter(productList,categoryList,shopList,getApplicationContext());

        listView.setAdapter(searchListCustomAdapter);
    }
}
