package com.sb.sweetbucket.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.sb.sweetbucket.R;
import com.sb.sweetbucket.model.HomeDataStore;
import com.sb.sweetbucket.rest.RestAppConstants;
import com.sb.sweetbucket.rest.response.Cart;
import com.sb.sweetbucket.rest.response.Product;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by harmeet on 05-10-2019.
 */

public class CartRecylerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context context;
    private List<Product> responseList;
    private List<Cart> cartList;
    private final String TAG = CartRecylerAdapter.class.getSimpleName();
    private static final int ITEM_TYPE__LOADING_LIST = 1;
    private static final int ITEM_TYPE__EMPTY_LIST = 2;
    private static final int ITEM_TYPE_PRODUCT_ITEM = 3;


    public CartRecylerAdapter(Context context, List<Product> responseList,List<Cart> cartList) {
        this.context = context;
        this.responseList = responseList;
        this.cartList = cartList;
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
                final View view3 = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_cart_list_items,
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
                bindProductViewHolder(holder,responseList.get(position),cartList.get(position));
                break;
        }


    }


    private void  bindProductViewHolder(RecyclerView.ViewHolder holder,Product product,Cart cart){
        ProductDataViewHolder dataViewHolder = (ProductDataViewHolder)holder;
        dataViewHolder.updateView(product,cart);
    }
    @Override
    public int getItemCount() {
        if (responseList == null || responseList.isEmpty()) {
            return 1;
        } else {
            return responseList.size();
        }

    }

    private class ProductDataViewHolder extends RecyclerView.ViewHolder implements
            AdapterView.OnItemSelectedListener{
        private ViewGroup mainView;
        private TextView tvSweetName,vendorTextview,itemCountTv;
        private TextView basePriceTextview;
        private ImageView imgview01;
        private Product product;
        private HomeDataStore homeDataStore = HomeDataStore.getInstance();
        private Spinner incDecSpinner;
        private Cart cart;
        public ProductDataViewHolder(View view) {
            super(view);
            mainView = (ViewGroup)view.findViewById(R.id.mainView);
            imgview01 = (ImageView)view.findViewById(R.id.imgview01);
            tvSweetName = (TextView)view.findViewById(R.id.tvSweetName);
            itemCountTv = (TextView)view.findViewById(R.id.itemCountTv);
            basePriceTextview = (TextView)view.findViewById(R.id.basepriceTextview);
            vendorTextview = (TextView)view.findViewById(R.id.vendorTextview);
            incDecSpinner = (Spinner)view.findViewById(R.id.incSpinner);
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,
                    R.array.inc_spinner_items, android.R.layout.simple_spinner_item);
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            // Apply the adapter to the spinner
            incDecSpinner.setAdapter(adapter);
            incDecSpinner.setOnItemSelectedListener(this);

        }

        public void updateView(Product product,Cart cart){
            this.product = product;
            this.cart = cart;
            tvSweetName.setText(product.getName());
            basePriceTextview.setText("Rs "+product.getBasePrice());

            Picasso.with(context).load(RestAppConstants.BASE_URL +product.getImageUrl() ).
                    placeholder(R.drawable.dummy_img).into(imgview01);
            vendorTextview.setText(homeDataStore.getVendorNameMap().get(product.getVendorId()));
            itemCountTv.setText(cart.getQty());
        }

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            Log.e("count",i+"");
            switch (i)
            {
                case 0:
                   // itemCountTv.setText("1");
                    break;
                case 1:
                   // itemCountTv.setText("2");
                    break;
                case 2:
                   // itemCountTv.setText("3");
                    break;
                case 3:
                   // itemCountTv.setText("more");
                    break;
            }

        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

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
    public void updateDataSource(List<Product> responseList,List<Cart> cartList){
        this.responseList = responseList;
        this.cartList = cartList;
        notifyDataSetChanged();
    }
}
