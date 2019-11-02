package com.sb.sweetbucket.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.sb.sweetbucket.R;
import com.sb.sweetbucket.adapters.CouponRecylerAdapter;
import com.sb.sweetbucket.rest.response.AvailableCouponResponse;

import java.util.List;

public class AvailableCouponActivity extends AppCompatActivity  implements CouponRecylerAdapter.ICouponListener {

    private static final String TAG = AvailableCouponActivity.class.getSimpleName();
    private ImageView backArrowImgview;
    private EditText couponEdittext;
    private Button applyBtn;
    private RecyclerView recyclerView;
    private List<AvailableCouponResponse> responseList;
    private CouponRecylerAdapter couponRecylerAdapter ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_coupon_dialog);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        backArrowImgview = (ImageView)findViewById(R.id.backArrow);
        couponEdittext = (EditText)findViewById(R.id.couponEdittext);
        Bundle bundle  = getIntent().getExtras();
        if (bundle!=null){
            responseList = (List<AvailableCouponResponse>) bundle.getSerializable("coupons");
        }
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recylerview);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        CouponRecylerAdapter couponRecylerAdapter = new CouponRecylerAdapter(getApplicationContext(),responseList,this);
        recyclerView.setAdapter(couponRecylerAdapter);
        backArrowImgview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        applyBtn = (Button)findViewById(R.id.applyBtn);
        applyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!couponEdittext.getText().toString().isEmpty()){
                    Intent intent = new Intent();
                    Bundle bundle1 = new Bundle();
                    bundle1.putString("couponCode",couponEdittext.getText().toString());
                    intent.putExtras(bundle1);
                    setResult(200,intent);
                    finish();
                }else
                    Toast.makeText(getApplicationContext(),"Please select the coupon first",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void applyCoupon(String couponCode) {

        couponEdittext.setText(couponCode);
    }
}
