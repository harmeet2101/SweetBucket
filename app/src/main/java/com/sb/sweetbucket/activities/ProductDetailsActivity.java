package com.sb.sweetbucket.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.sb.sweetbucket.R;

/**
 * Created by harmeet on 18-08-2019.
 */

public class ProductDetailsActivity extends AppCompatActivity {
    private ImageView backArrowImgview;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_product_details);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        backArrowImgview = (ImageView)findViewById(R.id.backArrow);
        backArrowImgview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
