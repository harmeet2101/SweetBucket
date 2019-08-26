package com.sb.sweetbucket.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import com.sb.sweetbucket.R;

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

}
