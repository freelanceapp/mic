package com.online.music.mic.utils;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.online.music.mic.retrofit_provider.RetrofitApiClient;
import com.online.music.mic.retrofit_provider.RetrofitService;


public class BaseActivity extends AppCompatActivity {
    public RetrofitApiClient retrofitApiClient;
    public RetrofitApiClient retrofitRxClient;
    public ConnectionDetector cd;
    public Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        cd = new ConnectionDetector(mContext);
        retrofitRxClient = RetrofitService.getRxClient();
        retrofitApiClient = RetrofitService.getRetrofit();
    }
}
