package com.mic.music.mic.Newmic;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.mic.music.mic.R;
import com.mic.music.mic.utils.BaseActivity;

public class ReachUs extends BaseActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reach_us);

        ((ImageView)findViewById(R.id.iv_facebook)).setOnClickListener(this);
        ((ImageView)findViewById(R.id.iv_insta)).setOnClickListener(this);
        ((ImageView)findViewById(R.id.iv_twiter)).setOnClickListener(this);

        }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
           case  R.id.iv_facebook:
               String fburl = "https://www.facebook.com/theonlinemic/";
               Uri strfb = Uri.parse(fburl);
               Intent urlintent = new Intent();
               urlintent.setData(strfb);
               urlintent.setAction(Intent.ACTION_VIEW);
               startActivity(urlintent);
            break;
           case  R.id.iv_insta:
               String instaurl = "https://www.instagram.com/onlinemic/";
               Uri strinsta = Uri.parse(instaurl);
               Intent urlintent1 = new Intent();
               urlintent1.setData(strinsta);
               urlintent1.setAction(Intent.ACTION_VIEW);
               startActivity(urlintent1);
            break;
           case  R.id.iv_twiter:
               String twitterurl = "https://twitter.com/theonlinemic";
               Uri strtwitter = Uri.parse(twitterurl);
               Intent urlintent2 = new Intent();
               urlintent2.setData(strtwitter);
               urlintent2.setAction(Intent.ACTION_VIEW);
               startActivity(urlintent2);
            break;
        }

    }
}
