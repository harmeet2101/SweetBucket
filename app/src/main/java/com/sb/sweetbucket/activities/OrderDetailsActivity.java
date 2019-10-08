package com.sb.sweetbucket.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sb.sweetbucket.R;
import com.sb.sweetbucket.adapters.OrderedProductRecyclerAdapter;
import com.sb.sweetbucket.rest.response.Order;

/**
 * Created by harmeet on 08-10-2019.
 */

public class OrderDetailsActivity extends AppCompatActivity {

    private Order order;
    private ImageView backArrowImgview;
    private TextView statusTextview,subTotalRsTv,taxRsTv,delRsTv,
            totalPayRsTv,tvAddress01,tvAddress02,tvAddress03;
    private RecyclerView recyclerView;
    private OrderedProductRecyclerAdapter  recyclerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_order_details);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        backArrowImgview = (ImageView)findViewById(R.id.backArrow);
        statusTextview = (TextView)findViewById(R.id.statusTextview);
        subTotalRsTv = (TextView)findViewById(R.id.subTotalRsTv);
        taxRsTv = (TextView)findViewById(R.id.TaxRsTv);
        delRsTv = (TextView)findViewById(R.id.DelRsTv);
        totalPayRsTv = (TextView)findViewById(R.id.TotalPayRsTv);
        tvAddress01 = (TextView)findViewById(R.id.tvAddress01);
        tvAddress02 = (TextView)findViewById(R.id.tvAddress02);
        tvAddress03 = (TextView)findViewById(R.id.tvAddress03);
        recyclerView = (RecyclerView)findViewById(R.id.recylerview);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        backArrowImgview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Bundle bundle  =getIntent().getExtras();
        if (bundle!=null){
            order = (Order) bundle.getSerializable("orderDetails");
            statusTextview.setText("Status: "+order.getOrderStatus().getStatus());
            subTotalRsTv.setText("Rs."+order.getOrderBaseTotal());
            taxRsTv.setText("Rs."+order.getOrderTax());
            delRsTv.setText("Rs."+order.getOrderShipping());
            totalPayRsTv.setText("Rs."+order.getOrderBaseTotal());
            tvAddress01.setText(order.getAddress().getCustomerName()+"-"+order.getAddress().getCustomerMobile());
            tvAddress02.setText(order.getAddress().getCustomerStreet()+"\n"+order.getAddress().getCustomerLandmark());
            tvAddress03.setText(order.getAddress().getCustomerCity()+","+order.getAddress().getCustomerState()+" "+
                    order.getAddress().getCustomerPin()+","+order.getAddress().getCustomerCountry());

            recyclerAdapter = new OrderedProductRecyclerAdapter(getApplicationContext(),order.getOrderProduct());
            recyclerView.setAdapter(recyclerAdapter);
        }
    }
}
