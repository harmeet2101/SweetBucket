package com.sb.sweetbucket.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.sb.sweetbucket.R;
import com.sb.sweetbucket.rest.request.OrderDetailRequest;
import com.sb.sweetbucket.rest.response.Order;

import java.util.List;

/**
 * Created by harmeet on 08-10-2019.
 */

public class OrdersRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = OrdersRecyclerAdapter.class.getSimpleName();
    private Context context;
    private static final int ITEM_TYPE__LOADING_LIST = 1;
    private static final int ITEM_TYPE__EMPTY_LIST = 2;
    private static final int ITEM_TYPE_PRODUCT_ITEM = 3;
    private List<Order> responseList;
    private IOrderFragCallback callback;
    public OrdersRecyclerAdapter(Context context, List<Order> responseList,IOrderFragCallback callback) {
        this.context = context;
        this.responseList = responseList;
        this.callback = callback;
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
                final View view3 = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_order_list_items,
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
                emptyDataViewHolder.updateView("Empty List");
                break;
            case ITEM_TYPE_PRODUCT_ITEM:
                bindProductViewHolder(holder,responseList.get(position));
                break;
        }


    }


    private void  bindProductViewHolder(RecyclerView.ViewHolder holder,Order order){
        ProductDataViewHolder dataViewHolder = (ProductDataViewHolder)holder;
        dataViewHolder.updateView(order);
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

        private Order order;
        private TextView orderNoTextview,orderDateTextview,orderPriceTextview;
        private Button detailsButton;
        public ProductDataViewHolder(View view) {
            super(view);
            orderNoTextview = (TextView)view.findViewById(R.id.orderNoTextview);
            orderDateTextview = (TextView)view.findViewById(R.id.orderDateTextview);
            orderPriceTextview = (TextView)view.findViewById(R.id.orderPriceTextview);
            detailsButton = (Button)view.findViewById(R.id.detailsButton);
            detailsButton.setOnClickListener(this);

        }

        public void updateView(Order order){
            this.order = order;
            orderNoTextview.setText("Order #"+order.getOrderNo());
            orderDateTextview.setText(order.getCreatedAt());
            orderPriceTextview.setText("Rs."+order.getOrderBaseTotal());
        }

        @Override
        public void onClick(View view) {

            int id = view.getId();
            switch (id){

                case R.id.detailsButton:
                    callback.getOrderDetails(new OrderDetailRequest(order.getOrderNo()));
                    break;
            }
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
    public void updateDataSource(List<Order> responseList){
        this.responseList = responseList;
        notifyDataSetChanged();
    }

    public interface  IOrderFragCallback{

        void getOrderDetails(OrderDetailRequest orderDetailRequest);
    }
}
