package com.sb.sweetbucket.controllers;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by harmeet on 24-08-2019.
 */

public class SharedPreferncesController {

    private final SharedPreferences sharedPreferences;
    private static SharedPreferncesController controller;
    private static final String isUserLoggedIn = "isUserLoggedIn";
    private SharedPreferncesController(Context context) {

        this.sharedPreferences = context.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE);
    }

    public static SharedPreferncesController getSharedPrefController(Context context){
        if (controller==null){
            controller = new SharedPreferncesController(context);
        }
        return controller;
    }


    public void setIsUserLoggedIn(boolean status){
        sharedPreferences.edit().putBoolean(isUserLoggedIn,status).commit();
    }

    public boolean isUserLoggedIn(){
        return  sharedPreferences.getBoolean(isUserLoggedIn,false);
    }
}
