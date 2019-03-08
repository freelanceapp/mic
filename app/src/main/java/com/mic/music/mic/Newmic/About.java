package com.mic.music.mic.Newmic;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.mic.music.mic.R;

public class About extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        init();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void init() {
        findViewById(R.id.imgBack).setOnClickListener(this);
        WebView webView = findViewById(R.id.webView);
        TextView tvaboutTitle = findViewById(R.id.tv_about_title);
        Intent intent = getIntent();
        String strtitle = intent.getStringExtra("pagetitile");
        String strContent = intent.getStringExtra("pagecontent");
        tvaboutTitle.setText(strtitle);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.loadData(strContent, "text/html; charset=utf-8", "UTF-8");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgBack:
                finish();
                break;
        }
    }
}
