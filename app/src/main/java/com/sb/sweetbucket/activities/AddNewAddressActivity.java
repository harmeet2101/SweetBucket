package com.sb.sweetbucket.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.sb.sweetbucket.R;
import com.sb.sweetbucket.controllers.SharedPreferncesController;
import com.sb.sweetbucket.rest.RestAPIInterface;
import com.sb.sweetbucket.rest.request.AddressSaveRequest;
import com.sb.sweetbucket.rest.response.SaveAddressResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by harmeet on 06-10-2019.
 */

public class AddNewAddressActivity extends AppCompatActivity {

    private static final String TAG = AddNewAddressActivity.class.getSimpleName();

    private ImageView backArrowImgview;
    private EditText add01Edittext,add02Edittext,pinEdittext,cityEdittext,stateEdittext;
    private Button saveButton;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_new_address);
        add01Edittext = (EditText)findViewById(R.id.add01Edittext);
        add02Edittext = (EditText)findViewById(R.id.add02Edittext);
        pinEdittext = (EditText)findViewById(R.id.pinEdittext);
        cityEdittext = (EditText)findViewById(R.id.cityEdittext);
        stateEdittext = (EditText)findViewById(R.id.stateEdittext);
        saveButton = (Button)findViewById(R.id.saveButton);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        backArrowImgview = (ImageView)findViewById(R.id.backArrow);
        backArrowImgview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AddressSaveRequest addressSaveRequest = new AddressSaveRequest(add01Edittext.getText().toString(),
                        add02Edittext.getText().toString(),Integer.parseInt(pinEdittext.getText().toString()),
                        cityEdittext.getText().toString(),stateEdittext.getText().toString());
                saveAddress(addressSaveRequest);
            }
        });
    }


    private void saveAddress(AddressSaveRequest addressSaveRequest){

        RestAPIInterface apiInterface = SweetBucketApplication.getApiClient().getClient().create(RestAPIInterface.class);
        String apiToken = SharedPreferncesController.getSharedPrefController(getApplicationContext()).getApiToken();
        if(apiToken!=null && !apiToken.isEmpty()){

            Call<SaveAddressResponse> responseCall = apiInterface.postSaveAddress(addressSaveRequest,"Bearer "+apiToken);
            responseCall.enqueue(new Callback<SaveAddressResponse>() {
                @Override
                public void onResponse(Call<SaveAddressResponse> call, Response<SaveAddressResponse> response) {
                    Log.e(TAG,response.body().toString());
                    if (response!=null){
                        Toast.makeText(getApplicationContext(),response.body().getMsg(),Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<SaveAddressResponse> call, Throwable t) {

                    Log.e(TAG,t.getMessage());
                    Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
