package com.sb.sweetbucket.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sb.sweetbucket.R;
import com.sb.sweetbucket.model.HomeDataStore;
import com.sb.sweetbucket.model.ProductDetails;
import com.sb.sweetbucket.rest.RestAppConstants;
import com.sb.sweetbucket.rest.response.Category;
import com.sb.sweetbucket.rest.response.HomeResponse;
import com.sb.sweetbucket.rest.response.Product;
import com.sb.sweetbucket.rest.response.Shop;
import com.sb.sweetbucket.rest.response.ShopsResponse;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by harmeet on 12-09-2019.
 */

public class SimilarProductsRecylerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final String TAG = SimilarProductsRecylerAdapter.class.getSimpleName();
    private static final int ITEM_TYPE__LOADING_LIST = 1;
    private static final int ITEM_TYPE__EMPTY_LIST = 2;
    private static final int ITEM_TYPE_PRODUCT_ITEM = 3;


    private Context mContext;
    private List<Product> responseList;
    private Map<String,String> vendorNameMap = new HashMap<>();
    private Map<Integer,String> categoryNameMap = new HashMap<>();
    private SimilarProductsRecylerAdapter.IOnClick iOnClick;
    public SimilarProductsRecylerAdapter(Context mContext,List<Product> responseList,SimilarProductsRecylerAdapter.IOnClick iOnClick) {
        this.mContext = mContext;
        this.responseList = responseList;
        this.iOnClick = iOnClick;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){

            case ITEM_TYPE__LOADING_LIST: {
                final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_loading,
                        parent, false);
                return new SimilarProductsRecylerAdapter.LoadingDataViewHolder(view);
            }
            case ITEM_TYPE__EMPTY_LIST: {
                final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_empty_list,
                        parent, false);
                return new SimilarProductsRecylerAdapter.EmptyDataViewHolder(view);
            }
            case ITEM_TYPE_PRODUCT_ITEM: {
                final View view3 = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_similar_items,
                        parent, false);
                return new SimilarProductsRecylerAdapter.ProductDataViewHolder(view3);
            }
        }


        return null;

    }

    @Override
    public int getItemViewType(int position) {
        int viewType = 0;

        if (responseList == null) {
            viewType = ITEM_TYPE__LOADING_LIST;
        } else if (responseList.isEmpty()) {
            viewType = ITEM_TYPE__EMPTY_LIST;
        }
        else
            viewType = ITEM_TYPE_PRODUCT_ITEM;
        return viewType;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        int viewType = holder.getItemViewType();
        switch (viewType){
            case ITEM_TYPE__LOADING_LIST:
                break;
            case ITEM_TYPE__EMPTY_LIST:
                SimilarProductsRecylerAdapter.EmptyDataViewHolder emptyDataViewHolder = (SimilarProductsRecylerAdapter.EmptyDataViewHolder) holder;
                break;
            case ITEM_TYPE_PRODUCT_ITEM:
                bindProductViewHolder(holder,responseList.get(position));
                break;
        }


    }


    private void  bindProductViewHolder(RecyclerView.ViewHolder holder,Product product){
        SimilarProductsRecylerAdapter.ProductDataViewHolder dataViewHolder = (SimilarProductsRecylerAdapter.ProductDataViewHolder)holder;
        dataViewHolder.updateView(product);
    }
    @Override
    public int getItemCount() {
        if (responseList == null || responseList.isEmpty()) {
            return 1;
        } else {
            return responseList.size();
        }

    }

    private class ProductDataViewHolder extends RecyclerView.ViewHolder {
        private ViewGroup mainView;
        private TextView tvSweetName,vendorTextview;
        private TextView basePriceTextview,discountTextview,salePriceTextview;
        private ImageView imgview01;
        private Product product;
        public ProductDataViewHolder(View view) {
            super(view);
            mainView = (ViewGroup)view.findViewById(R.id.mainView);
            imgview01 = (ImageView)view.findViewById(R.id.imgview01);
            tvSweetName = (TextView)view.findViewById(R.id.tvSweetName);
            basePriceTextview = (TextView)view.findViewById(R.id.basepriceTextview);
            discountTextview = (TextView)view.findViewById(R.id.discountTextview);
            salePriceTextview = (TextView)view.findViewById(R.id.tvPrice);
            vendorTextview = (TextView)view.findViewById(R.id.vendorTextview);
            mainView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    iOnClick.testOnClick(product);
                }
            });
        }

        public void updateView(Product product){
            this.product = product;
            tvSweetName.setText(product.getName());
            salePriceTextview.setText("Rs "+product.getSalePrice());
            basePriceTextview.setText("Rs "+product.getBasePrice());
            discountTextview.setText(product.getDiscount()+" Off");
            Picasso.with(mContext).load(RestAppConstants.BASE_URL +product.getImageUrl() ).
                    placeholder(R.drawable.dummy_img).into(imgview01);
            vendorTextview.setText(vendorNameMap.get(product.getVendorId()));

        }
    }

    private class EmptyDataViewHolder extends RecyclerView.ViewHolder {
        public EmptyDataViewHolder(View view) {
            super(view);
        }
    }

    private class LoadingDataViewHolder extends RecyclerView.ViewHolder {
        public LoadingDataViewHolder(View view) {
            super(view);
        }
    }

    public interface IOnClick{
        void testOnClick(Product productDetails);
    }
    public void updateDataSource(List<Product> productList){
        this.responseList = productList;
        notifyDataSetChanged();

        for(Shop shop: HomeDataStore.getInstance().getAllShopList()){
            if(shop.getVendorId()!=null)
            vendorNameMap.put(shop.getVendorId(),shop.getStoreName());
        }
        for(Category category:HomeDataStore.getInstance().getCategoryList()){
            categoryNameMap.put(category.getId(),category.getName());
        }
    }
}
