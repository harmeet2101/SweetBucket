package com.sb.sweetbucket.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sb.sweetbucket.R;
import com.sb.sweetbucket.fragments.ShopFragment;
import com.sb.sweetbucket.rest.RestAppConstants;
import com.sb.sweetbucket.rest.response.Category;
import com.sb.sweetbucket.rest.response.ShopsResponse;
import com.sb.sweetbucket.utils.CommonUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by harmeet on 24-08-2019.
 */

public class ShopRecylerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private final String TAG = HomeRecyclerAdapter.class.getSimpleName();
    private static final int ITEM_TYPE__LOADING_LIST = 1;
    private static final int ITEM_TYPE__EMPTY_LIST = 2;
    private static final int ITEM_TYPE_PRODUCT_ITEM = 3;
    private Context mContext;
    private List<ShopsResponse> responseList;
    private IShopRecylerListener shopRecylerListener;
    public ShopRecylerAdapter(Context mContext, List<ShopsResponse> responseList, ShopFragment shopFragment) {
        this.mContext = mContext;
        this.responseList = responseList;
        this.shopRecylerListener = (IShopRecylerListener)shopFragment;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){

            case ITEM_TYPE__LOADING_LIST: {
                final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_loading,
                        parent, false);
                return new ShopRecylerAdapter.LoadingDataViewHolder(view);
            }
            case ITEM_TYPE__EMPTY_LIST: {
                final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_empty_list,
                        parent, false);
                return new ShopRecylerAdapter.EmptyDataViewHolder(view);
            }
            case ITEM_TYPE_PRODUCT_ITEM: {
                final View view3 = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_shop_items,
                        parent, false);
                return new ShopRecylerAdapter.ProductDataViewHolder(view3);
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


    private void  bindProductViewHolder(RecyclerView.ViewHolder holder,ShopsResponse shopsResponse){
        ShopRecylerAdapter.ProductDataViewHolder dataViewHolder = (ShopRecylerAdapter.ProductDataViewHolder)holder;
        dataViewHolder.updateView(shopsResponse);
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
        private TextView tvSweetName,tvSweetAddress;
        private ImageView imgview01;
        private ShopsResponse shopsResponse;
        public ProductDataViewHolder(View view) {
            super(view);
            mainView = (ViewGroup)view.findViewById(R.id.mainView);
            imgview01 = (ImageView)view.findViewById(R.id.imgview01);
            tvSweetName = (TextView)view.findViewById(R.id.tvSweetName);
            tvSweetAddress = (TextView)view.findViewById(R.id.tvSweetAddress);
            mainView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    String id = CommonUtils.getBase64EncodeString(shopsResponse.getVendorDetails().getVendorId());
                    Log.e(TAG,id);
                    shopRecylerListener.getProductListByShopID(id,shopsResponse.getVendorDetails().getStoreName());
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


    public void updateDataSource(List<ShopsResponse> shopsResponseList){
        this.responseList =shopsResponseList;
        notifyDataSetChanged();
    }


    public interface IShopRecylerListener{
        void getProductListByShopID(String vendorID,String vendorName);
    }
}
