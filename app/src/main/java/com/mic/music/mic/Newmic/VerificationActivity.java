package com.mic.music.mic.Newmic;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mic.music.mic.R;
import com.mic.music.mic.retrofit_provider.RetrofitService;
import com.mic.music.mic.utils.Alerts;
import com.mic.music.mic.utils.BaseActivity;
import com.mic.music.mic.utils.ConnectionDetector;

public class VerificationActivity extends BaseActivity implements View.OnClickListener {

    Button submitotp;
    TextView micCompititions,audiovideo,btnSend;
    private EditText et_otp_a, et_otp_b, et_otp_c, et_otp_d, et_otp_e, et_otp_f;

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

        btnSend = findViewById(R.id.btnResend);
        et_otp_a = findViewById(R.id.et_otp_a);
        et_otp_b = findViewById(R.id.et_otp_b);
        et_otp_c = findViewById(R.id.et_otp_c);
        et_otp_d = findViewById(R.id.et_otp_d);
        et_otp_e = findViewById(R.id.et_otp_e);
        et_otp_f = findViewById(R.id.et_otp_f);

        submitotp = findViewById(R.id.submit_otp);
        submitotp.setOnClickListener(this);
        verificationCode();

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
                Alerts.show(mContext,"Resend OTP");
                break;

            case R.id.submit_otp :
                Intent intent = new Intent(VerificationActivity.this,MainActivity.class);
                startActivity(intent);

                break;
        }
    }
}
