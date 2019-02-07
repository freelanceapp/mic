package com.mic.music.mic.Newmic;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.widget.TextView;

import com.mic.music.mic.R;

public class About extends AppCompatActivity {

    private TextView tvaboutTitle, tvaboutContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        tvaboutTitle = findViewById(R.id.tv_about_title);
        tvaboutContent = findViewById(R.id.tv_about_content);

        Intent intent = getIntent();
        String strtitle = intent.getStringExtra("pagetitile");
        String strContent = intent.getStringExtra("pagecontent");
        tvaboutTitle.setText(strtitle);
        tvaboutContent.setText(strContent);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tvaboutContent.setText(Html.fromHtml(strContent, Html.FROM_HTML_MODE_COMPACT));
        } else {
            tvaboutContent.setText(Html.fromHtml(strContent));
        }
    }
}
