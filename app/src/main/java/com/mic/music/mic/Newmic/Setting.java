package com.mic.music.mic.Newmic;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.mic.music.mic.R;

public class Setting extends AppCompatActivity {

    LinearLayout about, reachus, termsofuse, privatepolicy, refundpolicy, soundsetting, notificationsetting, ourteam, performance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        performance = findViewById(R.id.performance);
        ourteam = findViewById(R.id.ourteam);
        about = findViewById(R.id.about);
        reachus = findViewById(R.id.reachus);
        termsofuse = findViewById(R.id.termsofuse);
        privatepolicy = findViewById(R.id.privacypolicy);
        refundpolicy = findViewById(R.id.refundpolicy);
        soundsetting = findViewById(R.id.soundsetting);
        notificationsetting = findViewById(R.id.notification);

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Setting.this, About.class);
                startActivity(intent);
            }
        });
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Setting.this, About.class);
                startActivity(intent);
            }
        });
        reachus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Setting.this, ReachUs.class);
                startActivity(intent);
            }
        });
        termsofuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Setting.this, TandC.class);
                startActivity(intent);
            }
        });
        privatepolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Setting.this, PrivatePolicy.class);
                startActivity(intent);
            }
        });
        refundpolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Setting.this, RefundPolicy.class);
                startActivity(intent);
            }
        });
        soundsetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Setting.this, SoundSetting.class);
                startActivity(intent);
            }
        });
        notificationsetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Setting.this, Notification.class);
                startActivity(intent);
            }
        });
        ourteam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Setting.this, OurTeam.class);
                startActivity(intent);
            }
        });
        performance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Setting.this, Performance.class);
                startActivity(intent);
            }
        });
    }
}
