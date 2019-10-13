package com.sb.sweetbucket.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.sb.sweetbucket.R;
import com.sb.sweetbucket.rest.response.PaymentModeResponse;

import java.util.List;

/**
 * Created by harmeet on 13-10-2019.
 */

public class PaymentModeRecylerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = PaymentModeRecylerAdapter.class.getSimpleName();
    private Context context;
    private List<PaymentModeResponse> responseList;
    private static final int ITEM_TYPE__LOADING_LIST = 1;
    private static final int ITEM_TYPE__EMPTY_LIST = 2;
    private static final int ITEM_TYPE_PRODUCT_ITEM = 3;
    private static SingleClickListener sClickListener;
    private int sSelected = -1;


    public PaymentModeRecylerAdapter(Context context, List<PaymentModeResponse> responseList) {
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
                final View view3 = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_payment_mode_items,
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
                emptyDataViewHolder.update("Not Found");
                break;
            case ITEM_TYPE_PRODUCT_ITEM:
                bindProductViewHolder(holder,responseList.get(position),position);
                break;
        }


    }


    private void  bindProductViewHolder(RecyclerView.ViewHolder holder, PaymentModeResponse modeResponse, final int position){
        final ProductDataViewHolder dataViewHolder = (ProductDataViewHolder)holder;
        dataViewHolder.updateView(modeResponse,position);
       dataViewHolder.rb01.setChecked(sSelected == position);

    }
    @Override
    public int getItemCount() {
        if (responseList == null || responseList.isEmpty()) {
            return 1;
        } else {
            return responseList.size();
        }

    }


    private class ProductDataViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView tvAddress01;
        public RadioButton rb01;
        public PaymentModeResponse modeResponse;
        public ProductDataViewHolder(View view) {
            super(view);

            tvAddress01 = (TextView)view.findViewById(R.id.payModeTv);
            rb01 = (RadioButton)view.findViewById(R.id.rb01);

            view.setOnClickListener(this);
        }

        public void updateView(PaymentModeResponse modeResponse,int position){
            tvAddress01.setText(modeResponse.getName());

        }

        @Override
        public void onClick(View view) {
            sSelected = getAdapterPosition();
            sClickListener.onItemClickListener(getAdapterPosition(), view);
        }
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




    public void updateDataSource(List<PaymentModeResponse> responseList){
        this.responseList = responseList;
        notifyDataSetChanged();
    }

    public void selectedItem() {
        notifyDataSetChanged();
    }
    public void setOnItemClickListener(SingleClickListener clickListener) {
        sClickListener = clickListener;
    }
    public interface SingleClickListener {
        void onItemClickListener(int position, View view);
    }
}
