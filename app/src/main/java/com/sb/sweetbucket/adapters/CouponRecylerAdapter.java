package com.sb.sweetbucket.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sb.sweetbucket.R;
import com.sb.sweetbucket.rest.response.AvailableCouponResponse;

import java.util.List;

public class CouponRecylerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    private static final String TAG = CouponRecylerAdapter.class.getSimpleName();
    private Context mContext;
    private List<AvailableCouponResponse> responseList;
    private static final int ITEM_TYPE__LOADING_LIST = 1;
    private static final int ITEM_TYPE__EMPTY_LIST = 2;
    private static final int ITEM_TYPE_DATA_ITEM = 3;
    private ICouponListener couponListener;
    public CouponRecylerAdapter(Context mContext, List<AvailableCouponResponse> responseList,ICouponListener couponListener) {
        this.mContext = mContext;
        this.responseList = responseList;
        this.couponListener = couponListener;
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
            case ITEM_TYPE_DATA_ITEM: {
                final View view3 = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_coupon_recycler_items,
                        parent, false);
                return new DataViewHolder(view3);
            }
        }


        return null;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        int viewType = holder.getItemViewType();
        switch (viewType){
            case ITEM_TYPE__LOADING_LIST:
                break;
            case ITEM_TYPE__EMPTY_LIST:
                EmptyDataViewHolder emptyDataViewHolder = (EmptyDataViewHolder) holder;
                emptyDataViewHolder.update("No Coupons Present");
                break;
            case ITEM_TYPE_DATA_ITEM:
                bindRatingViewHolder(holder,responseList.get(position));
                break;
        }


    }
    private void  bindRatingViewHolder(RecyclerView.ViewHolder holder,AvailableCouponResponse response){
        DataViewHolder dataViewHolder = (DataViewHolder)holder;
        dataViewHolder.updateView(response);
    }
    private class EmptyDataViewHolder extends RecyclerView.ViewHolder {

        private TextView tvName;
        public EmptyDataViewHolder(View view) {
            super(view);
            tvName = (TextView)view.findViewById(R.id.tvName);
        }
        public void update(String txt){
            tvName.setText(txt);
        }
    }

    private class LoadingDataViewHolder extends RecyclerView.ViewHolder {
        public LoadingDataViewHolder(View view) {
            super(view);
        }
    }
    @Override
    public int getItemCount() {
        if (responseList == null || responseList.isEmpty()) {
            return 1;
        } else {
            return responseList.size();
        }

    }
    @Override
    public int getItemViewType(int position) {
        int viewType = 0;

        if (responseList == null) {
            viewType = ITEM_TYPE__EMPTY_LIST;
        } else if (responseList.isEmpty()) {
            viewType = ITEM_TYPE__EMPTY_LIST;
        }
        else
            viewType = ITEM_TYPE_DATA_ITEM;
        return viewType;
    }

    public void updateDataSource(List<AvailableCouponResponse> responseList){
        this.responseList = responseList;
        notifyDataSetChanged();
    }

    private class DataViewHolder extends RecyclerView.ViewHolder{

        private AvailableCouponResponse response;
        private TextView tvCouponCode,tvAmountOff,tvDesc;
        private ViewGroup mainView;
        public DataViewHolder(View view) {
            super(view);
            tvCouponCode = (TextView)view.findViewById(R.id.tvCouponCode);
            tvAmountOff = (TextView)view.findViewById(R.id.tvAmountOff);
            tvDesc = (TextView)view.findViewById(R.id.tvDesc);
            mainView = view.findViewById(R.id.mainView);
            mainView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    couponListener.applyCoupon(response.getCode());
                }
            });
        }

        public void updateView(AvailableCouponResponse response) {
            this.response= response;
            tvCouponCode.setText(response.getCode());
            tvAmountOff.setText(response.getAmount()+" Rs. Off");
            tvDesc.setText("Use Coupon code "+response.getCode()+ " and get "+response.getAmount()+
                    " Rs.Off on your orders above Rs."+ response.getMin_order_amount());
        }
    }

    public interface  ICouponListener{

        void applyCoupon(String couponCode);
    }
}
