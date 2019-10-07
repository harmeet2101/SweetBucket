package com.sb.sweetbucket.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sb.sweetbucket.R;
import com.sb.sweetbucket.rest.response.Address;

import java.util.List;

/**
 * Created by harmeet on 06-10-2019.
 */

public class AddressFragRecylerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM_TYPE__LOADING_LIST = 1;
    private static final int ITEM_TYPE__EMPTY_LIST = 2;
    private static final int ITEM_TYPE_PRODUCT_ITEM = 3;
    private Context context;
    private List<Address> addressList;


    public AddressFragRecylerAdapter(Context context, List<Address> addressList) {
        this.context = context;
        this.addressList = addressList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){

            case ITEM_TYPE__LOADING_LIST: {
                final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_loading,
                        parent, false);
                return new AddressFragRecylerAdapter.LoadingDataViewHolder(view);
            }
            case ITEM_TYPE__EMPTY_LIST: {
                final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_empty_list,
                        parent, false);
                return new AddressFragRecylerAdapter.EmptyDataViewHolder(view);
            }
            case ITEM_TYPE_PRODUCT_ITEM: {
                final View view3 = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_address_recycler_items,
                        parent, false);
                return new AddressFragRecylerAdapter.ProductDataViewHolder(view3);
            }
        }


        return null;

    }

    @Override
    public int getItemViewType(int position) {
        int viewType = 0;

        if (addressList == null) {
            viewType = ITEM_TYPE__LOADING_LIST;
        } else if (addressList.isEmpty()) {
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
                AddressFragRecylerAdapter.EmptyDataViewHolder emptyDataViewHolder = (AddressFragRecylerAdapter.EmptyDataViewHolder) holder;
                emptyDataViewHolder.update("No Address Found");
                break;
            case ITEM_TYPE_PRODUCT_ITEM:
                bindProductViewHolder(holder,addressList.get(position));
                break;
        }


    }


    private void  bindProductViewHolder(RecyclerView.ViewHolder holder,Address address){
        AddressFragRecylerAdapter.ProductDataViewHolder dataViewHolder = (AddressFragRecylerAdapter.ProductDataViewHolder)holder;
        dataViewHolder.updateView(address);
    }
    @Override
    public int getItemCount() {
        if (addressList == null || addressList.isEmpty()) {
            return 1;
        } else {
            return addressList.size();
        }

    }

    private class ProductDataViewHolder extends RecyclerView.ViewHolder{
        private ViewGroup mainView;
        private TextView tvAddress01,tvAddress02;
        public ProductDataViewHolder(View view) {
            super(view);
            mainView = (ViewGroup)view.findViewById(R.id.mainView);
            tvAddress01 = (TextView)view.findViewById(R.id.tvAddress01);
            tvAddress02 = (TextView)view.findViewById(R.id.tvAddress02);

        }

        public void updateView(Address address){
            tvAddress01.setText(address.getAddress1());
            tvAddress02.setText(address.getCity()+","+address.getState()+" "+address.getPinCode()+","+address.getCountry());
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




    public void updateDataSource(List<Address> addressList){
        this.addressList = addressList;
        notifyDataSetChanged();
    }
}
