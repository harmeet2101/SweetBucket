package com.sb.sweetbucket;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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
