package com.sb.sweetbucket.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sb.sweetbucket.R;
import com.sb.sweetbucket.rest.RestAppConstants;
import com.sb.sweetbucket.rest.response.OrderProduct;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by harmeet on 08-10-2019.
 */

public class OrderedProductRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = OrderedProductRecyclerAdapter.class.getSimpleName();
    private Context context;
    private static final int ITEM_TYPE__LOADING_LIST = 1;
    private static final int ITEM_TYPE__EMPTY_LIST = 2;
    private static final int ITEM_TYPE_PRODUCT_ITEM = 3;
    private List<OrderProduct> responseList;

    public OrderedProductRecyclerAdapter(Context context, List<OrderProduct> responseList) {
        this.context = context;
        this.responseList = responseList;
    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){

            case ITEM_TYPE__LOADING_LIST: {
                final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_loading,
                        parent, false);
                return new LoadingDataViewHolder(view);
            }
            case ITEM_TYPE__EMPTY_LIST: {
                final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_empty_list,
                        parent, false);
                return new EmptyDataViewHolder(view);
            }
            case ITEM_TYPE_PRODUCT_ITEM: {
                final View view3 = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_order_details_screen_recyler_items,
                        parent, false);
                return new ProductDataViewHolder(view3);
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
                EmptyDataViewHolder emptyDataViewHolder = (EmptyDataViewHolder) holder;
                emptyDataViewHolder.updateView("Cart Empty");
                break;
            case ITEM_TYPE_PRODUCT_ITEM:
                bindProductViewHolder(holder,responseList.get(position));
                break;
        }


    }


    private void  bindProductViewHolder(RecyclerView.ViewHolder holder,OrderProduct product){
        ProductDataViewHolder dataViewHolder = (ProductDataViewHolder)holder;
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

    private class ProductDataViewHolder extends RecyclerView.ViewHolder{
        private ViewGroup mainView;
        private TextView tvSweetName,itemCountTv;
        private TextView basePriceTextview;
        private ImageView imgview01;
        private OrderProduct product;
        public ProductDataViewHolder(View view) {
            super(view);
            mainView = (ViewGroup)view.findViewById(R.id.mainView);
            imgview01 = (ImageView)view.findViewById(R.id.imgview01);
            tvSweetName = (TextView)view.findViewById(R.id.tvSweetName);
            itemCountTv = (TextView)view.findViewById(R.id.itemCountTv);
            basePriceTextview = (TextView)view.findViewById(R.id.basepriceTextview);

        }

        public void updateView(OrderProduct product){
            this.product = product;
            tvSweetName.setText(product.getName());
            basePriceTextview.setText("Rs "+product.getOrderAmount());

            Picasso.with(context).load(RestAppConstants.BASE_URL +product.getImageUrl() ).
                    placeholder(R.drawable.dummy_img).into(imgview01);
            itemCountTv.setText(product.getProductQty());

        }

    }

    private class EmptyDataViewHolder extends RecyclerView.ViewHolder {

        private TextView tvName;
        public EmptyDataViewHolder(View view) {
            super(view);
            tvName = (TextView)view.findViewById(R.id.tvName);
        }

        public void updateView(String txt){
            tvName.setText(txt);
        }
    }

    private class LoadingDataViewHolder extends RecyclerView.ViewHolder {
        public LoadingDataViewHolder(View view) {
            super(view);
        }
    }
    public void updateDataSource(List<OrderProduct> responseList){
        this.responseList = responseList;
        notifyDataSetChanged();
    }
}
