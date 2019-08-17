package com.sb.sweetbucket.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;

import com.sb.sweetbucket.R;

public class SplashActivity extends Activity {

    private static final Long TIME_DELAY = 2000L;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                moveToNextScreen();
            }
        },TIME_DELAY);
    }


    private void moveToNextScreen(){

        startActivity(new Intent(getBaseContext(),LoginActivity.class));
        finish();
    }
}
