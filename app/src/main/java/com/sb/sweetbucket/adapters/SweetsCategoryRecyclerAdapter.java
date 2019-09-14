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
import com.sb.sweetbucket.rest.response.Product;
import com.sb.sweetbucket.rest.response.Shop;
import com.sb.sweetbucket.rest.response.ShopsResponse;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by harmeet on 26-08-2019.
 */

public class SweetsCategoryRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private final String TAG = SweetsCategoryRecyclerAdapter.class.getSimpleName();
    private static final int ITEM_TYPE__LOADING_LIST = 1;
    private static final int ITEM_TYPE__EMPTY_LIST = 2;
    private static final int ITEM_TYPE_PRODUCT_ITEM = 3;

    private Context mContext;
    private List<Product> responseList;
    private IOnItemClick iOnItemClick;
    private Map<Integer,String> categoryNameMap = new HashMap<>();
    private Map<String,String> vendorNameMap = new HashMap<>();
    public SweetsCategoryRecyclerAdapter(Context mContext, List<Product> responseList, IOnItemClick iOnItemClick) {
        this.mContext = mContext;
        this.responseList = responseList;
        this.iOnItemClick = iOnItemClick;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){

            case ITEM_TYPE__LOADING_LIST: {
                final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_loading,
                        parent, false);
                return new SweetsCategoryRecyclerAdapter.LoadingDataViewHolder(view);
            }
            case ITEM_TYPE__EMPTY_LIST: {
                final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_empty_list,
                        parent, false);
                return new SweetsCategoryRecyclerAdapter.EmptyDataViewHolder(view);
            }
            case ITEM_TYPE_PRODUCT_ITEM: {
                final View view3 = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_home_items,
                        parent, false);
                return new SweetsCategoryRecyclerAdapter.ProductDataViewHolder(view3);
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
                break;
            case ITEM_TYPE_PRODUCT_ITEM:
                bindProductViewHolder(holder,responseList.get(position));
                break;
        }
    }

    private void  bindProductViewHolder(RecyclerView.ViewHolder holder,Product product){
        SweetsCategoryRecyclerAdapter.ProductDataViewHolder dataViewHolder = (SweetsCategoryRecyclerAdapter.ProductDataViewHolder)holder;
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
                    iOnItemClick.OnItemClick(new ProductDetails(product.getId(),product.getCat1Id(),product.getProductCode(),product.getName(),
                            categoryNameMap.get(Integer.parseInt(product.getCat1Id())),
                            vendorNameMap.get(product.getVendorId())
                            ,product.getInfo(),product.getTags(),product.getImageUrl(),product.getBasePrice(),product.getDealPrice(),product.getSalePrice(),
                            product.getDiscount(),product.getUnit(),product.getStockQty()
                    ));
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


    public void updateDataSource(List<Product> products,List<Category> categoryList){
        this.responseList =products;


        for(Shop shop: HomeDataStore.getInstance().getAllShopList()){
            if(shop.getVendorId()!=null)
                vendorNameMap.put(shop.getVendorId(),shop.getStoreName());
        }
        for(Category category:categoryList){
            categoryNameMap.put(category.getId(),category.getName());
        }
        notifyDataSetChanged();
    }

    public interface IOnItemClick{
        void OnItemClick(ProductDetails productDetails);
    }
}
