package com.mic.music.mic.Newmic.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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

import retrofit2.Response;

public class VerificationActivity extends BaseActivity implements View.OnClickListener {

    Button submitotp;
    TextView micCompititions,audiovideo,btnSend,otpTime,btnResend;
    private EditText et_otp_a, et_otp_b, et_otp_c, et_otp_d, et_otp_e, et_otp_f;
    LinearLayout resendLayout;
    String otpnumber;
    String myNumber,myEmail;
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
    private void init()
    {
        resendLayout = findViewById(R.id.resendLayout);
        otpTime = findViewById(R.id.otpTime);
        btnResend = findViewById(R.id.btnResend);
        myNumber = getIntent().getStringExtra("MobileNumber");
        myEmail = getIntent().getStringExtra("EmailID");
        Log.e("MobileNumber ",".."+myNumber);
        Log.e("MobileNumber ",".."+myEmail);
        otptime();
        et_otp_a = findViewById(R.id.et_otp_a);
        et_otp_b = findViewById(R.id.et_otp_b);
        et_otp_c = findViewById(R.id.et_otp_c);
        et_otp_d = findViewById(R.id.et_otp_d);
        et_otp_e = findViewById(R.id.et_otp_e);
        et_otp_f = findViewById(R.id.et_otp_f);
        submitotp = findViewById(R.id.submit_otp);
        submitotp.setOnClickListener(this);
        verificationCode();
        btnResend.setOnClickListener(this);
    }

    private void otptime()
    {
        new CountDownTimer(120000, 1000) {
            public void onTick(long millisUntilFinished) {
                otpTime.setVisibility(View.VISIBLE);
                otpTime.setText("seconds remaining: " + millisUntilFinished / 1000);
                //here you can have your logic to set text to edittext
            }
            public void onFinish() {
                //otpTime.setText("done!");
                otpTime.setVisibility(View.GONE);
                resendLayout.setVisibility(View.VISIBLE);
            }
        }.start();
    }

    private void verificationCode() {
        et_otp_a.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String strA = String.valueOf(s);
                if (strA.isEmpty()) {
                    et_otp_a.requestFocus();
                    et_otp_b.clearFocus();
                    et_otp_c.clearFocus();
                    et_otp_d.clearFocus();
                } else {
                    et_otp_a.clearFocus();
                    et_otp_b.requestFocus();
                    et_otp_c.clearFocus();
                    et_otp_d.clearFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_otp_b.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String strA = String.valueOf(s);

                if (strA.isEmpty()) {
                    et_otp_a.requestFocus();
                    et_otp_b.clearFocus();
                    et_otp_c.clearFocus();
                    et_otp_d.clearFocus();
                } else {
                    et_otp_a.clearFocus();
                    et_otp_b.clearFocus();
                    et_otp_c.requestFocus();
                    et_otp_d.clearFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_otp_c.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String strA = String.valueOf(s);

                if (strA.isEmpty()) {
                    et_otp_a.clearFocus();
                    et_otp_b.requestFocus();
                    et_otp_c.clearFocus();
                    et_otp_d.clearFocus();
                } else {
                    et_otp_a.clearFocus();
                    et_otp_b.clearFocus();
                    et_otp_c.clearFocus();
                    et_otp_d.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_otp_d.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String strA = String.valueOf(s);

                if (strA.isEmpty()) {
                    et_otp_a.clearFocus();
                    et_otp_b.clearFocus();
                    et_otp_c.requestFocus();
                    et_otp_d.clearFocus();
                    et_otp_e.clearFocus();
                    et_otp_f.clearFocus();
                } else {
                    et_otp_a.clearFocus();
                    et_otp_b.clearFocus();
                    et_otp_c.clearFocus();
                    et_otp_d.clearFocus();
                    et_otp_e.requestFocus();
                    et_otp_f.clearFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_otp_e.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String strA = String.valueOf(s);

                if (strA.isEmpty()) {
                    et_otp_a.clearFocus();
                    et_otp_b.clearFocus();
                    et_otp_c.clearFocus();
                    et_otp_d.requestFocus();
                    et_otp_e.clearFocus();
                    et_otp_f.clearFocus();
                } else {
                    et_otp_a.clearFocus();
                    et_otp_b.clearFocus();
                    et_otp_c.clearFocus();
                    et_otp_d.clearFocus();
                    et_otp_e.clearFocus();
                    et_otp_f.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_otp_f.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String strA = String.valueOf(s);

                if (strA.isEmpty()) {
                    et_otp_a.clearFocus();
                    et_otp_b.clearFocus();
                    et_otp_c.clearFocus();
                    et_otp_d.clearFocus();
                    et_otp_e.requestFocus();
                    et_otp_f.clearFocus();
                } else {
                    et_otp_a.clearFocus();
                    et_otp_b.clearFocus();
                    et_otp_c.clearFocus();
                    et_otp_d.clearFocus();
                    et_otp_e.clearFocus();
                    et_otp_f.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btnResend :
                //Alerts.show(mContext,"Resend OTP");
                if (myNumber.equals("121"))
                {
                    resendEmailOtp();
                }else {
                    resendMobileOtp();
                }
                resendEmailOtp();

                resendLayout.setVisibility(View.GONE);
                break;
            case R.id.submit_otp :
                otpnumber = et_otp_a.getText().toString()+et_otp_b.getText().toString()+et_otp_c.getText().toString()+et_otp_d.getText().toString()+et_otp_e.getText().toString()+et_otp_f.getText().toString();
                if (myNumber.equals("121"))
                {
                    otpVarification1();
                }else {
                    otpVarification();
                }
                break;
        }
    }


    private void otpVarification() {
        if (cd.isNetworkAvailable()) {
            RetrofitService.getOtp(new Dialog(mContext), retrofitApiClient.getOtp(myNumber,otpnumber), new WebResponse() {
                @Override
                public void onResponseSuccess(Response<?> result) {
                    OtpModel loginModal = (OtpModel) result.body();
                    assert loginModal != null;
                    if (!loginModal.getError()) {
                        Alerts.show(mContext, loginModal.getMessage());
                        AppPreference.setStringPreference(mContext, Constant.User_Id, loginModal.getUser().getParticipantId());

                        Gson gson = new GsonBuilder().setLenient().create();
                        String data = gson.toJson(loginModal);
                        Log.e("Login", ".."+data);
                        AppPreference.setStringPreference(mContext, Constant.User_Data, data);
                        User.setUser(loginModal);
                        if (loginModal.getUserType().equals("registered user")) {
                            AppPreference.setBooleanPreference(mContext, Constant.Is_Login, true);
                            Intent intent = new Intent(VerificationActivity.this, HomeActivity.class);
                            startActivity(intent);
                            finish();
                        }else {
                            Intent intent = new Intent(VerificationActivity.this, MainActivity.class);
                            intent.putExtra("user_id",loginModal.getUser().getParticipantId());
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
            RetrofitService.getOtp(new Dialog(mContext), retrofitApiClient.getOtp1(myEmail,otpnumber), new WebResponse() {
                @Override
                public void onResponseSuccess(Response<?> result) {
                    OtpModel loginModal = (OtpModel) result.body();
                    assert loginModal != null;
                    if (!loginModal.getError()) {
                        Alerts.show(mContext, loginModal.getMessage());

                        AppPreference.setStringPreference(mContext, Constant.User_Id, loginModal.getUser().getParticipantId());

                        Gson gson = new GsonBuilder().setLenient().create();
                        String data = gson.toJson(loginModal);
                        Log.e("Login", ".."+data);
                        AppPreference.setStringPreference(mContext, Constant.User_Data, data);
                        User.setUser(loginModal);

                        String strCheck = AppPreference.getStringPreference(mContext, Constant.User_Check );
                        Log.e("strCheck ", ".."+strCheck);

                        if (loginModal.getUser().getParticipantEmailVerificationStatus().equals("Verified")) {
                            AppPreference.setBooleanPreference(mContext, Constant.Is_Login, true);
                            Intent intent = new Intent(VerificationActivity.this, HomeActivity.class);
                            startActivity(intent);
                            finish();
                        }else {
                            Intent intent = new Intent(VerificationActivity.this, MainActivity.class);
                            intent.putExtra("user_id",loginModal.getUser().getParticipantId());
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
                        Alerts.show(mContext, gloginModal.getMessage());
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
