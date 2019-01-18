package com.mic.music.mic.Newmic;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mic.music.mic.R;

public class Mobile_Ragistration extends AppCompatActivity {
    private TextView audioDialog,videoDialog;
    private EditText etNumber;
    private String phoneNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_registration);

        init();

    }

    private void init(){
        etNumber = (EditText)findViewById(R.id.etNumber);
        Button getOtp = findViewById(R.id.getopt);
        getOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneNumber = etNumber.getText().toString();
                if (phoneNumber.length() == 10) {
                    Intent intent = new Intent(Mobile_Ragistration.this, VerificationActivity.class);
                    startActivity(intent);
                }else {
                    etNumber.setError("Please Enter Mobile Numeber");
                }
            }
        });
    }
}
