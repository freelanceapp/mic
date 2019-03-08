package com.mic.music.mic.Newmic.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mic.music.mic.R;
import com.mic.music.mic.constant.Constant;
import com.mic.music.mic.model.User;
import com.mic.music.mic.model.login_responce.LoginModel1;
import com.mic.music.mic.model.otp_responce.OtpModel;
import com.mic.music.mic.model.token_responce.TokenModel;
import com.mic.music.mic.retrofit_provider.RetrofitService;
import com.mic.music.mic.retrofit_provider.WebResponse;
import com.mic.music.mic.utils.Alerts;
import com.mic.music.mic.utils.AppPreference;
import com.mic.music.mic.utils.BaseActivity;
import com.mic.music.mic.utils.ConnectionDetector;
import com.mic.music.mic.utils.pinview.Pinview;

import retrofit2.Response;

public class VerificationActivity extends BaseActivity implements View.OnClickListener {

    private Button submitotp;
    private TextView micCompititions, audiovideo, btnSend, otpTime, btnResend;
    private LinearLayout resendLayout;
    private String otpnumber;
    private String myNumber, myEmail;
    private Pinview pinview1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);
        mContext = this;
        cd = new ConnectionDetector(mContext);
        retrofitRxClient = RetrofitService.getRxClient();
        retrofitApiClient = RetrofitService.getRetrofit();
        init();
    }

    private void init() {
        pinview1 = findViewById(R.id.pinview1);
        resendLayout = findViewById(R.id.resendLayout);
        otpTime = findViewById(R.id.otpTime);
        btnResend = findViewById(R.id.btnResend);
        myNumber = getIntent().getStringExtra("MobileNumber");
        myEmail = getIntent().getStringExtra("EmailID");
        Log.e("MobileNumber ", ".." + myNumber);
        Log.e("MobileNumber ", ".." + myEmail);
        otptime();
        submitotp = findViewById(R.id.submit_otp);
        submitotp.setOnClickListener(this);
        btnResend.setOnClickListener(this);
    }

    private void otptime() {
        new CountDownTimer(12000, 1000) {
            public void onTick(long millisUntilFinished) {
                otpTime.setVisibility(View.VISIBLE);
                otpTime.setText("seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                otpTime.setVisibility(View.GONE);
                resendLayout.setVisibility(View.VISIBLE);
            }
        }.start();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnResend:
                pinview1.setValue("");
                if (myNumber.equals("121")) {
                    resendEmailOtp();
                } else {
                    resendMobileOtp();
                }
                resendLayout.setVisibility(View.GONE);
                break;
            case R.id.submit_otp:
                //otpnumber = et_otp_a.getText().toString() + et_otp_b.getText().toString() + et_otp_c.getText().toString() + et_otp_d.getText().toString() + et_otp_e.getText().toString() + et_otp_f.getText().toString();
                otpnumber = pinview1.getValue();
                if (otpnumber.isEmpty()) {
                    Alerts.show(mContext, "Please enter OTP number");
                } else {
                    if (myNumber.equals("121")) {
                        otpVarification1();
                    } else {
                        otpVarification();
                    }
                }
                break;
        }
    }

    private void otpVarification() {
        if (cd.isNetworkAvailable()) {
            RetrofitService.getOtp(new Dialog(mContext), retrofitApiClient.getOtp(myNumber, otpnumber), new WebResponse() {
                @Override
                public void onResponseSuccess(Response<?> result) {
                    OtpModel loginModal = (OtpModel) result.body();
                    assert loginModal != null;
                    if (!loginModal.getError()) {
                        Alerts.show(mContext, loginModal.getMessage());
                        AppPreference.setStringPreference(mContext, Constant.User_Id, loginModal.getUser().getParticipantId());

                        Gson gson = new GsonBuilder().setLenient().create();
                        String data = gson.toJson(loginModal);
                        Log.e("Login", ".." + data);
                        AppPreference.setStringPreference(mContext, Constant.User_Data, data);
                        User.setUser(loginModal);
                        if (loginModal.getUserType().equals("registered user")) {
                            AppPreference.setBooleanPreference(mContext, Constant.Is_Login, true);
                            Intent intent = new Intent(VerificationActivity.this, HomeActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Intent intent = new Intent(VerificationActivity.this, MainActivity.class);
                            intent.putExtra("user_id", loginModal.getUser().getParticipantId());
                            startActivity(intent);
                            finish();
                        }
                        otpTime.setVisibility(View.GONE);
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

    private void otpVarification1() {
        if (cd.isNetworkAvailable()) {
            RetrofitService.getOtp(new Dialog(mContext), retrofitApiClient.getOtp1(myEmail, otpnumber), new WebResponse() {
                @Override
                public void onResponseSuccess(Response<?> result) {
                    OtpModel loginModal = (OtpModel) result.body();
                    assert loginModal != null;
                    if (!loginModal.getError()) {
                        Alerts.show(mContext, loginModal.getMessage());

                        AppPreference.setStringPreference(mContext, Constant.User_Id, loginModal.getUser().getParticipantId());

                        Gson gson = new GsonBuilder().setLenient().create();
                        String data = gson.toJson(loginModal);
                        Log.e("Login", ".." + data);
                        AppPreference.setStringPreference(mContext, Constant.User_Data, data);
                        User.setUser(loginModal);

                        String strCheck = AppPreference.getStringPreference(mContext, Constant.User_Check);
                        Log.e("strCheck ", ".." + strCheck);

                        if (loginModal.getUser().getParticipantEmailVerificationStatus().equals("Verified")) {
                            AppPreference.setBooleanPreference(mContext, Constant.Is_Login, true);
                            Intent intent = new Intent(VerificationActivity.this, HomeActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Intent intent = new Intent(VerificationActivity.this, MainActivity.class);
                            intent.putExtra("user_id", loginModal.getUser().getParticipantId());
                            startActivity(intent);
                            finish();
                        }
                        otpTime.setVisibility(View.GONE);
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

    private void resendEmailOtp() {
        if (cd.isNetworkAvailable()) {
            RetrofitService.getResend(new Dialog(mContext), retrofitApiClient.getResend(myEmail), new WebResponse() {
                @Override
                public void onResponseSuccess(Response<?> result) {
                    TokenModel loginModal = (TokenModel) result.body();
                    assert loginModal != null;
                    if (!loginModal.getError()) {
                        Alerts.show(mContext, loginModal.getMessage());
                        otptime();
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

    private void resendMobileOtp() {
        if (cd.isNetworkAvailable()) {
            RetrofitService.getResendMobile(new Dialog(mContext), retrofitApiClient.getResendMobile(myNumber), new WebResponse() {
                @Override
                public void onResponseSuccess(Response<?> result) {
                    LoginModel1 loginModal = (LoginModel1) result.body();
                    assert loginModal != null;
                    if (!loginModal.getError()) {
                        Alerts.show(mContext, loginModal.getMessage());
                        otptime();
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