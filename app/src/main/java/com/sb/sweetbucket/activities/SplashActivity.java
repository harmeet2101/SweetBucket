package com.sb.sweetbucket.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;

import com.sb.sweetbucket.R;
import com.sb.sweetbucket.controllers.SharedPreferncesController;

public class SplashActivity extends Activity {

    private static final Long TIME_DELAY = 2000L;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                SharedPreferncesController controller = SharedPreferncesController.getSharedPrefController(getApplicationContext());
                if (controller.isUserLoggedIn())
                moveToHomeScreen();
                else
                    moveToLoginScreen();
            }
        },TIME_DELAY);
    }


    private void moveToLoginScreen(){

        startActivity(new Intent(getBaseContext(),LoginActivity.class));
        finish();
    }

    private void moveToHomeScreen(){

        startActivity(new Intent(getBaseContext(),DashboardActivity.class));
        finish();
    }
}
