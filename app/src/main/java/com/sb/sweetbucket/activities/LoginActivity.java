package com.sb.sweetbucket.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.sb.sweetbucket.R;
import com.sb.sweetbucket.controllers.SharedPreferncesController;
import com.sb.sweetbucket.rest.RestAPIInterface;
import com.sb.sweetbucket.rest.request.LoginRequest;
import com.sb.sweetbucket.rest.response.LoginResponse;
import com.sb.sweetbucket.utils.CommonUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by harmeet on 15-08-2019.
 */

public class LoginActivity extends AppCompatActivity{

    private TextView accountTextview;
    private Button loginButton;
    private EditText usernameEdittext,passEdittext;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        accountTextview = (TextView)findViewById(R.id.accountTextview);
        usernameEdittext = (EditText)findViewById(R.id.emailEdittext);
        passEdittext = (EditText)findViewById(R.id.passEdittext);
        accountTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
            }
        });
        loginButton = (Button)findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final ProgressDialog progressDialog = CommonUtils.getProgressBar(LoginActivity.this);
                RestAPIInterface apiInterface = SweetBucketApplication.getApiClient().getClient().create(RestAPIInterface.class);
                Call<LoginResponse> responseCall = apiInterface.doLogin(new LoginRequest(usernameEdittext.getText().toString().trim(),
                        passEdittext.getText().toString().trim()));
                responseCall.enqueue(new Callback<LoginResponse>() {

                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                        if(progressDialog!=null && progressDialog.isShowing()){
                            progressDialog.dismiss();
                        }
                        // Log.e("resp",response.body().toString());
                        if(response.body()!=null && response.body().getApiToken()!=null){

                            SharedPreferncesController controller = SharedPreferncesController.getSharedPrefController(getApplicationContext());
                            controller.setIsUserLoggedIn(true);
                            Intent intent = new Intent(getApplicationContext(),DashboardActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }else if (response.body().getMsg()!=null)
                            Toast.makeText(getApplicationContext(),response.body().getMsg(),Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        if(progressDialog!=null && progressDialog.isShowing()){
                            progressDialog.dismiss();
                        }
                        Log.e("fail",t.getMessage());
                    }
                });


            }
        });
    }
}
