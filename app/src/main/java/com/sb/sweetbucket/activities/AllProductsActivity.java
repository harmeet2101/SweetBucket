package com.sb.sweetbucket.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.sb.sweetbucket.R;
import com.sb.sweetbucket.fragments.HomeFragment;

/**
 * Created by harmeet on 10-09-2019.
 */

public class AllProductsActivity extends AppCompatActivity {

    private static final String TAG = AllProductsActivity.class.getSimpleName();
    private Toolbar toolbar;
    private TextView toolbarTitleTextview;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_all_products);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbarTitleTextview = (TextView)findViewById(R.id.custom_toolbar_title);
        setToolbarTitle();
        Fragment fragment = new HomeFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                android.R.anim.fade_out);
        fragmentTransaction.replace(R.id.frame, fragment);
        fragmentTransaction.commitAllowingStateLoss();
    }

    private void setToolbarTitle() {
        //getSupportActionBar().setTitle("All Products");
        toolbarTitleTextview.setText("All Products");
    }
}
