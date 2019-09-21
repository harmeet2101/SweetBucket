package com.sb.sweetbucket.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sb.sweetbucket.R;
import com.sb.sweetbucket.rest.response.Rating;

import java.util.List;

/**
 * Created by harmeet on 21-09-2019.
 */

public class CustomerReviewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = CustomerReviewAdapter.class.getSimpleName();
    private Context mContext;
    private List<Rating> ratingList;
    private static final int ITEM_TYPE__LOADING_LIST = 1;
    private static final int ITEM_TYPE__EMPTY_LIST = 2;
    private static final int ITEM_TYPE_RATING_ITEM = 3;


    public CustomerReviewAdapter(Context mContext, List<Rating> ratingList) {
        this.mContext = mContext;
        this.ratingList = ratingList;
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
            case ITEM_TYPE_RATING_ITEM: {
                final View view3 = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_rate_items,
                        parent, false);
                return new RatingDataViewHolder(view3);
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
                emptyDataViewHolder.update("No Reviews Present");
                break;
            case ITEM_TYPE_RATING_ITEM:
                bindRatingViewHolder(holder,ratingList.get(position));
                break;
        }


    }
    private void  bindRatingViewHolder(RecyclerView.ViewHolder holder,Rating rating){
        RatingDataViewHolder dataViewHolder = (RatingDataViewHolder)holder;
        dataViewHolder.updateView(rating);
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
        if (ratingList == null || ratingList.isEmpty()) {
            return 1;
        } else {
            return ratingList.size();
        }

    }
    @Override
    public int getItemViewType(int position) {
        int viewType = 0;

        if (ratingList == null) {
            viewType = ITEM_TYPE__EMPTY_LIST;
        } else if (ratingList.isEmpty()) {
            viewType = ITEM_TYPE__EMPTY_LIST;
        }
        else
            viewType = ITEM_TYPE_RATING_ITEM;
        return viewType;
    }

    public void updateDataSource(List<Rating> ratingList){
        this.ratingList = ratingList;
        notifyDataSetChanged();
    }

    private class RatingDataViewHolder extends RecyclerView.ViewHolder{

        private Rating rating;
        private TextView userNameTextview,dateTimeTextview,commentsTextview;
        public RatingDataViewHolder(View view) {
            super(view);
            userNameTextview = (TextView)view.findViewById(R.id.tvName);
            dateTimeTextview = (TextView)view.findViewById(R.id.tvDateTimeStamp);
            commentsTextview = (TextView)view.findViewById(R.id.tvComments);
        }

        public void updateView(Rating rating) {
            this.rating = rating;
            userNameTextview.setText(rating.getUserId());
            dateTimeTextview.setText(rating.getCreated_at());
            commentsTextview.setText(rating.getComments());
        }
    }
}
