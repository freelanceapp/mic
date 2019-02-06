package com.mic.music.mic.Newmic;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.mic.music.mic.R;
import com.squareup.picasso.Picasso;

public class OurTeam extends AppCompatActivity {

    private ImageView imgriteshanand,imgrita,imgsneha,imgrohitprakash,imgakansha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_our_team);

        imgriteshanand = findViewById(R.id.img_ritesh_anand);
        imgrita = findViewById(R.id.img_rita_ambhastha);
        imgsneha = findViewById(R.id.img_sneha_agarwalla);
        imgrohitprakash = findViewById(R.id.img_rohit_prakash_prit);
        imgakansha = findViewById(R.id.img_akansha_agarwalla);

        Picasso.get().load("http://onlinemic.in/images/Ritesh1.jpg").into(imgriteshanand);
        Picasso.get().load("http://onlinemic.in/images/Rita.jpg").into(imgrita);
        Picasso.get().load("http://onlinemic.in/images/Sneha.jpg").into(imgsneha);
        Picasso.get().load("http://onlinemic.in/images/Rohit Prakash.jpg").into(imgrohitprakash);
        Picasso.get().load("http://onlinemic.in/images/Akansha.jpg").into(imgakansha);

    }
}
