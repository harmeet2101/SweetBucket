package com.sb.sweetbucket.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.sb.sweetbucket.R;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * Created by harmeet on 26-08-2019.
 */

public class CommonUtils
{


    public static ProgressDialog getProgressBar(Context context) {
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(false);
        progressDialog.show();
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.setContentView(R.layout.custom_progressbar);
        return progressDialog;
    }

    public static void hideSoftKeyboard(Context context, View currentFocus) {
        if (currentFocus != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
        }
    }


}
