package com.sb.sweetbucket.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sb.sweetbucket.R;

import java.util.List;

/**
 * Created by harmeet on 17-08-2019.
 */

public class HomeRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final String TAG = HomeRecyclerAdapter.class.getSimpleName();
    private Context mContext;
    private List<String> dataList;
    public HomeRecyclerAdapter(Context mContext,List<String> dataList) {
        this.mContext = mContext;
        this.dataList = dataList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder = null;
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_home_items,
                parent, false);
        viewHolder = new DummyDataViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        DummyDataViewHolder dataViewHolder = (DummyDataViewHolder)holder;

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    private class DummyDataViewHolder extends RecyclerView.ViewHolder {
        public DummyDataViewHolder(View view) {
            super(view);
        }
    }
}
