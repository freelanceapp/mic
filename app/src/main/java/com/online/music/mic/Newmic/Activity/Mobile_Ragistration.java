package com.online.music.mic.Newmic.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.online.music.mic.R;
import com.online.music.mic.constant.Constant;
import com.online.music.mic.model.login_responce.LoginModel;
import com.online.music.mic.model.login_responce.LoginModel1;
import com.online.music.mic.retrofit_provider.RetrofitService;
import com.online.music.mic.retrofit_provider.WebResponse;
import com.online.music.mic.utils.Alerts;
import com.online.music.mic.utils.AppPreference;
import com.online.music.mic.utils.BaseActivity;
import com.online.music.mic.utils.ConnectionDetector;

import retrofit2.Response;

public class Mobile_Ragistration extends BaseActivity {
    private TextView loginEmail, change_emage_text, countrycode;
    private EditText etNumber, etEmail;
    private String phoneNumber = "121";
    private String emailAddress = "abc@abc.com";
    private String email1 = "0";
    private String checkLogin = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_registration);
        mContext = this;
        cd = new ConnectionDetector(mContext);
        retrofitRxClient = RetrofitService.getRxClient();
        retrofitApiClient = RetrofitService.getRetrofit();
        init();
    }

    private void init() {
        etNumber = (EditText) findViewById(R.id.etNumber);
        etEmail = (EditText) findViewById(R.id.etEmail);
        loginEmail = (TextView) findViewById(R.id.loginEmail);
        change_emage_text = findViewById(R.id.change_text_email);
        countrycode = findViewById(R.id.tv_country_code);
        Button getOtp = findViewById(R.id.getopt);
        getOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (email1.equals("1")) {
                    emailAddress = etEmail.getText().toString();
                    if (emailAddress.length() == 0) {
                        etEmail.setError("Please Enter Email Address");
                    } else {
                        getEmail();
                    }
                } else {
                    phoneNumber = etNumber.getText().toString();
                    if (phoneNumber.length() == 10) {
                        getMobile();
                    } else {
                        etNumber.setError("Please Enter Valid Mobile Number");
                    }
                }
            }
        });
        loginEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkLogin.equals("0")) {
                    loginEmail.setText("Login with Mobile Number");
                    checkLogin = "1";
                    change_emage_text.setText("Enter Email Address");
                    countrycode.setVisibility(View.GONE);
                    etEmail.setVisibility(View.VISIBLE);
                    etNumber.setVisibility(View.GONE);
                    email1 = "1";
                } else {
                    change_emage_text.setText("Enter Mobile Number");
                    countrycode.setVisibility(View.VISIBLE);
                    loginEmail.setText("Login with Email");
                    etEmail.setVisibility(View.GONE);
                    etNumber.setVisibility(View.VISIBLE);
                    email1 = "0";
                    checkLogin = "0";
                }
            }
        });
    }

    private void getMobile() {
        if (cd.isNetworkAvailable()) {
            RetrofitService.getlogin(new Dialog(mContext), retrofitApiClient.getLogin(phoneNumber, "91"), new WebResponse() {
                @Override
                public void onResponseSuccess(Response<?> result) {
                    LoginModel1 loginModal = (LoginModel1) result.body();
                    assert loginModal != null;
                    if (!loginModal.getError()) {
                        Alerts.show(mContext, loginModal.getMessage());
                        AppPreference.setStringPreference(mContext, Constant.User_Check, loginModal.getUserEmail());
                        AppPreference.setStringPreference(mContext, Constant.User_Mobile, phoneNumber);
                        Intent intent = new Intent(Mobile_Ragistration.this, VerificationActivity.class);
                        intent.putExtra("MobileNumber", phoneNumber);
                        intent.putExtra("EmailID", emailAddress);
                        startActivity(intent);
                        finish();
                    } else {
                        Alerts.show(mContext, loginModal.getMessage());
                    }
                }

                @Override
                public void onResponseFailed(String error) {
                    Alerts.show(mContext, error);
                }
            });
        } else {
            cd.show(mContext);
        }
    }

    private void getEmail() {
        if (cd.isNetworkAvailable()) {
            RetrofitService.getEmaillogin(new Dialog(mContext), retrofitApiClient.getLogin1(emailAddress), new WebResponse() {
                @Override
                public void onResponseSuccess(Response<?> result) {
                    LoginModel loginModal = (LoginModel) result.body();
                    assert loginModal != null;
                    if (!loginModal.getError()) {
                        Alerts.show(mContext, loginModal.getMessage());
                        AppPreference.setStringPreference(mContext, Constant.User_Check, loginModal.getUserEmail());
                        Intent intent = new Intent(Mobile_Ragistration.this, VerificationActivity.class);
                        intent.putExtra("MobileNumber", phoneNumber);
                        intent.putExtra("EmailID", emailAddress);
                        startActivity(intent);
                        finish();
                    } else {
                        Alerts.show(mContext, loginModal.getMessage());
                    }
                }

                @Override
                public void onResponseFailed(String error) {
                    Alerts.show(mContext, error);
                }
            });
        } else {
            cd.show(mContext);
        }
    }
}
