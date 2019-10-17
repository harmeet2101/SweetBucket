package com.sb.sweetbucket.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.sb.sweetbucket.R;
import com.sb.sweetbucket.activities.AddNewAddressActivity;
import com.sb.sweetbucket.rest.response.Address;
import com.sb.sweetbucket.rest.response.CustomAddress;

import java.util.List;

/**
 * Created by harmeet on 05-10-2019.
 */

public class AddressRecylerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = AddressRecylerAdapter.class.getSimpleName();
    private Context context;
    private List<CustomAddress> addressList;
    private static final int ITEM_TYPE__LOADING_LIST = 1;
    private static final int ITEM_TYPE__EMPTY_LIST = 2;
    private static final int ITEM_TYPE_PRODUCT_ITEM = 3;
    private static SingleClickListener sClickListener;
    private int sSelected = -1;
    public AddressRecylerAdapter(Context context, List<CustomAddress> addressList) {
        this.context = context;
        this.addressList = addressList;
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
                final View view3 = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_address,
                        parent, false);
                return new ProductDataViewHolder(view3);
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
                EmptyDataViewHolder emptyDataViewHolder = (EmptyDataViewHolder) holder;
                emptyDataViewHolder.update("No Address Found");
                break;
            case ITEM_TYPE_PRODUCT_ITEM:
                bindProductViewHolder(holder,addressList.get(position),position);
                break;
        }


    }


    private void  bindProductViewHolder(RecyclerView.ViewHolder holder, CustomAddress address, final int position){
        final ProductDataViewHolder dataViewHolder = (ProductDataViewHolder)holder;
        dataViewHolder.updateView(address,position);
        dataViewHolder.rb01.setChecked(sSelected == position);

    }
    @Override
    public int getItemCount() {
        if (addressList == null || addressList.isEmpty()) {
            return 1;
        } else {
            return addressList.size();
        }

    }


    private class ProductDataViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ViewGroup mainView;
        private TextView tvAddress01,tvAddress02;
        public RadioButton rb01;
        public ProductDataViewHolder(View view) {
            super(view);
            mainView = (ViewGroup)view.findViewById(R.id.mainView);
            tvAddress01 = (TextView)view.findViewById(R.id.tvAddress01);
            tvAddress02 = (TextView)view.findViewById(R.id.tvAddress02);
            rb01 = (RadioButton)view.findViewById(R.id.rb01);
            mainView = (ViewGroup) view.findViewById(R.id.mainView);

            view.setOnClickListener(this);
        }

        public void updateView(CustomAddress address,int position){
            tvAddress01.setText(address.getAddress().getAddress1());
            tvAddress02.setText(address.getAddress().getCity()+","+address.getAddress().getState()+" "+address.
                    getAddress().getPinCode()+","+address.getAddress().getCountry());

        }

        @Override
        public void onClick(View view) {
            sSelected = getAdapterPosition();
            sClickListener.onItemClickListener(getAdapterPosition(), view);
        }
    }

    private class EmptyDataViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private Button addNewAddressBtn;
        public EmptyDataViewHolder(View view) {
            super(view);
            tvName = (TextView)view.findViewById(R.id.tvName);
            addNewAddressBtn = (Button) view.findViewById(R.id.newAddBtn);
            addNewAddressBtn.setVisibility(View.VISIBLE);

            addNewAddressBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, AddNewAddressActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });
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




    public void updateDataSource(List<CustomAddress> addressList){
        this.addressList = addressList;
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
