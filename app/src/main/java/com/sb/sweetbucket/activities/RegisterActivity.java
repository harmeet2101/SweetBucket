package com.sb.sweetbucket.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sb.sweetbucket.R;
import com.sb.sweetbucket.controllers.SharedPreferncesController;
import com.sb.sweetbucket.rest.RestAPIInterface;
import com.sb.sweetbucket.rest.request.RegisterRequest;
import com.sb.sweetbucket.rest.response.RegisterResponse;
import com.sb.sweetbucket.utils.CommonUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by harmeet on 15-08-2019.
 */

public class RegisterActivity extends AppCompatActivity {

    private TextView signInTextview;
    private Button registerButton;
    private ImageView bckImgView;
    private EditText nameEdittext,mobileEdittext,emailEdittext,passEdiitext,cPasswordEdittext;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_signup);
        signInTextview = (TextView)findViewById(R.id.accountTextview);
        nameEdittext = (EditText)findViewById(R.id.nameEdittext);
        mobileEdittext = (EditText)findViewById(R.id.mobileEdittext);
        emailEdittext = (EditText)findViewById(R.id.emailEdittext);
        passEdiitext = (EditText)findViewById(R.id.passEdittext);
        cPasswordEdittext = (EditText)findViewById(R.id.confirmEdittext);
        signInTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        registerButton = (Button)findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final ProgressDialog progressDialog = CommonUtils.getProgressBar(RegisterActivity.this);
                RegisterRequest request = new RegisterRequest(nameEdittext.getText().toString(),
                        mobileEdittext.getText().toString(),passEdiitext.getText().toString(),
                        cPasswordEdittext.getText().toString(),emailEdittext.getText().toString()
                        );

                RestAPIInterface apiInterface = SweetBucketApplication.getApiClient().getClient().create(RestAPIInterface.class);
                Call<RegisterResponse> registerResponseCall = apiInterface.doRegister(request);

                registerResponseCall.enqueue(new Callback<RegisterResponse>() {
                    @Override
                    public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                        if(progressDialog!=null && progressDialog.isShowing()){
                            progressDialog.dismiss();
                        }

                            if(response.code()==200) {
                                SharedPreferncesController controller = SharedPreferncesController.getSharedPrefController(getApplicationContext());
                                controller.setIsUserLoggedIn(true);
                                controller.saveAPIToken(response.body().getLoginResponse().getApiToken());
                                Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }else {
                                Toast.makeText(getApplicationContext(),"Error Ocurred",Toast.LENGTH_SHORT).show();
                            }


                    }

                    @Override
                    public void onFailure(Call<RegisterResponse> call, Throwable t) {
                        if(progressDialog!=null && progressDialog.isShowing()){
                            progressDialog.dismiss();
                        }
                        Log.d(RegisterActivity.class.getSimpleName(),t.getMessage());
                    }
                });


            }
        });
        bckImgView = (ImageView) findViewById(R.id.bckImgbutton);
        bckImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

}
