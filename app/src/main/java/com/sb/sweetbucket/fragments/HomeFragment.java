package com.sb.sweetbucket.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sb.sweetbucket.adapters.HomeRecyclerAdapter;
import com.sb.sweetbucket.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by harmeet on 17-08-2019.
 */

@SuppressLint("ValidFragment")
public class HomeFragment extends Fragment{

    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private HomeRecyclerAdapter recyclerAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.layout_home_frag,container,false);
        recyclerView = (RecyclerView)view.findViewById(R.id.recylerview);
        gridLayoutManager  = new GridLayoutManager(getContext(),2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerAdapter = new HomeRecyclerAdapter(getActivity().getBaseContext(),createDummyData());
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.HORIZONTAL));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL));

        recyclerView.setAdapter(recyclerAdapter);
        return view;
    }

    private List<String> createDummyData(){

        List<String> l = new ArrayList<>();
        for(int i=0;i<=10;i++)
            l.add(""+i);
        return l;
    }
}
