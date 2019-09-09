package com.sb.sweetbucket.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sb.sweetbucket.R;
import com.sb.sweetbucket.model.ProductDetails;
import com.sb.sweetbucket.rest.RestAppConstants;
import com.sb.sweetbucket.rest.response.Product;
import com.sb.sweetbucket.rest.response.ShopsResponse;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by harmeet on 01-09-2019.
 */

public class BrandsRecylerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final String TAG = BrandsRecylerAdapter.class.getSimpleName();
    public static final int ITEM_TYPE__LOADING_LIST = 1;
    public static final int ITEM_TYPE__EMPTY_LIST = 2;
    public static final int ITEM_TYPE_PRODUCT_ITEM = 3;
    public static final int ITEM_TYPE_PRODUCT_ITEM_HEADING = 4;
    public static final int ITEM_TYPE_SHOP_ITEM = 5;
    public static final int ITEM_TYPE_SHOP_ITEM_HEADING = 6;
    private final int PRODUCT_LIMIT = 16;
    private final int SHOP_LIMIT = 4;
    private int count =0;
    private Context mContext;
    private List<Product> productResponseList;
    private List<ShopsResponse> shopsResponseList;
    private Map<String,String> vendorNameMap = new HashMap<>();
    private Map<Integer,String> categoryNameMap = new HashMap<>();
    private BrandsRecylerAdapter.IOnClick iOnClick;
    public BrandsRecylerAdapter(Context mContext,List<Product> productResponseList,BrandsRecylerAdapter.IOnClick iOnClick) {
        this.mContext = mContext;
        this.productResponseList = productResponseList;
        this.iOnClick = iOnClick;
    }
    public BrandsRecylerAdapter(Context mContext,List<Product> productResponseList,List<ShopsResponse> shopsResponseList,
                                BrandsRecylerAdapter.IOnClick iOnClick) {
        this.mContext = mContext;
        this.productResponseList = productResponseList;
        this.shopsResponseList = shopsResponseList;
        this.iOnClick = iOnClick;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){

            case ITEM_TYPE__LOADING_LIST: {
                final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_loading,
                        parent, false);
                return new BrandsRecylerAdapter.LoadingDataViewHolder(view);
            }
            case ITEM_TYPE__EMPTY_LIST: {
                final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_empty_list,
                        parent, false);
                return new BrandsRecylerAdapter.EmptyDataViewHolder(view);
            }
            case ITEM_TYPE_PRODUCT_ITEM_HEADING: {
                final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_heading,
                        parent, false);
                return new BrandsRecylerAdapter.HeadingDataViewHolder(view);
            }
            case ITEM_TYPE_PRODUCT_ITEM: {
                final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_home_items,
                        parent, false);
                return new BrandsRecylerAdapter.ProductDataViewHolder(view);
            }
            case ITEM_TYPE_SHOP_ITEM: {
                final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_shop_items,
                        parent, false);
                return new BrandsRecylerAdapter.ShopDataViewHolder(view);
            }
            case ITEM_TYPE_SHOP_ITEM_HEADING: {
                final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_heading,
                        parent, false);
                return new BrandsRecylerAdapter.HeadingDataViewHolder(view);
            }

        }


        return null;

    }

    @Override
    public int getItemViewType(int position) {
        int viewType = 0;

        if (productResponseList == null) {
            viewType = ITEM_TYPE__LOADING_LIST;
        } else if (productResponseList.isEmpty()) {
            viewType = ITEM_TYPE__EMPTY_LIST;
        }/*else if(position==0){
            viewType = ITEM_TYPE_PRODUCT_ITEM_HEADING;
        }*/
        if(position < shopsResponseList.size()){
            if(shopsResponseList.get(position).getName().equalsIgnoreCase("Heading")){
                return ITEM_TYPE_SHOP_ITEM_HEADING;
            }else
            return ITEM_TYPE_SHOP_ITEM;
        }

        if(position - shopsResponseList.size() < productResponseList.size()){
            if(productResponseList.get(position - shopsResponseList.size())
                    .getName().equalsIgnoreCase("Heading")){
            return ITEM_TYPE_PRODUCT_ITEM_HEADING;
            }else
            return ITEM_TYPE_PRODUCT_ITEM;
        }

        /*else
            viewType = ITEM_TYPE_PRODUCT_ITEM;*/
        return viewType;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        int viewType = holder.getItemViewType();
        switch (viewType){
            case ITEM_TYPE__LOADING_LIST:
                break;
            case ITEM_TYPE__EMPTY_LIST:
                BrandsRecylerAdapter.EmptyDataViewHolder emptyDataViewHolder = (BrandsRecylerAdapter.EmptyDataViewHolder) holder;
                break;
            case ITEM_TYPE_PRODUCT_ITEM:
                bindProductViewHolder(holder,productResponseList.get(position - shopsResponseList.size()));
                break;
            case ITEM_TYPE_PRODUCT_ITEM_HEADING:
                bindHeadingViewHolder(holder,productResponseList.get(position - shopsResponseList.size()));
                break;
            case ITEM_TYPE_SHOP_ITEM:
                bindShopViewHolder(holder,shopsResponseList.get(position));
                break;
            case ITEM_TYPE_SHOP_ITEM_HEADING:
                bindShopHeadingViewHolder(holder,shopsResponseList.get(position));
                break;
        }


    }


    private void  bindProductViewHolder(RecyclerView.ViewHolder holder,Product product){
        BrandsRecylerAdapter.ProductDataViewHolder dataViewHolder = (BrandsRecylerAdapter.ProductDataViewHolder)holder;
        dataViewHolder.updateView(product);
    }

    private void  bindShopViewHolder(RecyclerView.ViewHolder holder,ShopsResponse shopsResponse){
        BrandsRecylerAdapter.ShopDataViewHolder dataViewHolder = (BrandsRecylerAdapter.ShopDataViewHolder)holder;
        dataViewHolder.updateView(shopsResponse);
    }

    private void  bindHeadingViewHolder(RecyclerView.ViewHolder holder,Product product){
        BrandsRecylerAdapter.HeadingDataViewHolder dataViewHolder = (BrandsRecylerAdapter.HeadingDataViewHolder)holder;
        dataViewHolder.update("Trending Products");
    }

    private void  bindShopHeadingViewHolder(RecyclerView.ViewHolder holder,ShopsResponse shopsResponse){
        BrandsRecylerAdapter.HeadingDataViewHolder dataViewHolder = (BrandsRecylerAdapter.HeadingDataViewHolder)holder;
        dataViewHolder.update("Trending Shops");
    }
    @Override
    public int getItemCount() {
        if (productResponseList == null || productResponseList.isEmpty()) {
            return 1;
        } else {
            if(productResponseList.size()>PRODUCT_LIMIT){
                count = PRODUCT_LIMIT;
            }else
                count = productResponseList.size();
            if(shopsResponseList.size()>SHOP_LIMIT){
                count = count + SHOP_LIMIT;
            }else
                count = shopsResponseList.size();
            return count/*productResponseList.size()+shopsResponseList.size()*/;
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
                    iOnClick.testOnClick(new ProductDetails(product.getId(),product.getProductCode(),product.getName(),
                            ""/*categoryNameMap.get(Integer.parseInt(product.getCat1Id()))*/,
                            ""/*vendorNameMap.get(product.getVendorId())*/
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

    private class ShopDataViewHolder extends RecyclerView.ViewHolder {
        private ViewGroup mainView;
        private TextView tvSweetName,tvSweetAddress;
        private ImageView imgview01;
        private ShopsResponse shopsResponse;
        public ShopDataViewHolder(View view) {
            super(view);
            mainView = (ViewGroup)view.findViewById(R.id.mainView);
            imgview01 = (ImageView)view.findViewById(R.id.imgview01);
            tvSweetName = (TextView)view.findViewById(R.id.tvSweetName);
            tvSweetAddress = (TextView)view.findViewById(R.id.tvSweetAddress);
            mainView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }

        public void updateView(ShopsResponse shopsResponse){

            this.shopsResponse = shopsResponse;
            tvSweetName.setText(shopsResponse.getVendorDetails().getStoreName());
            tvSweetAddress.setText(shopsResponse.getVendorDetails().getAddress1()+
                    ","+shopsResponse.getVendorDetails().getCity());
            Picasso.with(mContext).load(RestAppConstants.BASE_URL +shopsResponse.getVendorDetails().getStoreFront() ).
                    placeholder(R.drawable.dummy_img).into(imgview01);
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


    private class HeadingDataViewHolder extends RecyclerView.ViewHolder {

        private TextView mNameTextview;
        public HeadingDataViewHolder(View view) {
            super(view);
            mNameTextview = (TextView)view.findViewById(R.id.tvHeading);
        }

        public void update(String txt){

            mNameTextview.setText(txt);
        }
    }

    public interface IOnClick{
        void testOnClick(ProductDetails productDetails);
    }
    public void updateDataSource(List<Product> productList){
        this.productResponseList = productList;
        notifyDataSetChanged();

        /*for(Product product:productList){
            vendorNameMap.put(shop.getVendorId(),shop.getStoreName());
        }
        for(Category category:homeResponse.getCategory()){
            categoryNameMap.put(category.getId(),category.getName());
        }*/
    }
}