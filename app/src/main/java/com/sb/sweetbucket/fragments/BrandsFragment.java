package com.sb.sweetbucket.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sb.sweetbucket.R;
import com.sb.sweetbucket.adapters.BrandsRecylerAdapter;
import com.sb.sweetbucket.adapters.PopularProductsRecylerAdapter;
import com.sb.sweetbucket.model.HomeDataStore;
import com.sb.sweetbucket.model.ProductDetails;
import com.sb.sweetbucket.rest.response.Product;
import com.sb.sweetbucket.rest.response.ShopsResponse;

import java.util.List;

/**
 * Created by harmeet on 31-08-2019.
 */

public class BrandsFragment extends Fragment implements BrandsRecylerAdapter.IOnClick{

    private static final String TAG = BrandsFragment.class.getSimpleName();
    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private List<Product> productResponseList;
    private List<ShopsResponse> shopsResponseList;
    private BrandsRecylerAdapter recyclerAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.layout_brands_frag,container,false);
        recyclerView = (RecyclerView)view.findViewById(R.id.recylerview);
        HomeDataStore homeDataStore = HomeDataStore.getInstance();
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
        recyclerAdapter = new BrandsRecylerAdapter(getActivity().getBaseContext(),productResponseList,shopsResponseList,this);
        recyclerView.setAdapter(recyclerAdapter);

        //recyclerAdapter.updateDataSource(productResponseList);
        return view;
    }

    @Override
    public void testOnClick(ProductDetails productDetails) {

    }
}
