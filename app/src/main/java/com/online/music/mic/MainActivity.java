package com.online.music.mic;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.github.abdularis.civ.CircleImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    CircleImageView menu_btn,calendar_btn,rating_btn,friends_btn,notification_btn,profile_btn;

    public static String u_name;
    public static String u_email;
    public static String u_mobile;
    public static String u_gender;
    public static String u_dob;
    public static String u_id;

    public static Boolean menu_status = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      /*  Fragment fragment = new HomeFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.store_frame, fragment).commit();

        SharedPreferences prefs = getSharedPreferences(MyApp, 0);
        u_id = prefs.getString("id", "");
        u_name = prefs.getString("name", "mic");
        u_email = prefs.getString("email", "mic");
        u_gender = prefs.getString("gendar", "mic");
        u_mobile = prefs.getString("mobile", "121");
        u_dob = prefs.getString("dob", "1-1-2000");

        Log.e("name","..."+u_name);
        Log.e("email","..."+u_email);
        Log.e("gender","..."+u_gender);
        Log.e("phone","..."+u_mobile);
        Log.e("DOB","..."+u_dob);
        Log.e("ID","..."+u_id);


        menu_btn = (CircleImageView)findViewById(R.id.menu_btn);
        calendar_btn = (CircleImageView)findViewById(R.id.calender_btn);
        rating_btn = (CircleImageView)findViewById(R.id.rating_btn);
        friends_btn = (CircleImageView)findViewById(R.id.friends_btn);
        notification_btn = (CircleImageView)findViewById(R.id.notification_btn);
        profile_btn = (CircleImageView)findViewById(R.id.profile_btn);

        menu_btn.setOnClickListener(this);
        calendar_btn.setOnClickListener(this);
        rating_btn.setOnClickListener(this);
        friends_btn.setOnClickListener(this);
        notification_btn.setOnClickListener(this);
        profile_btn.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.menu_btn :

                if (menu_status == true) {
               *//* Animation animation = AnimationUtils.loadAnimation(this,R.anim.rotation);
                menu_btn.startAnimation(animation);*//*
                    menu_status = false;
                    calendar_btn.setVisibility(View.VISIBLE);
                    friends_btn.setVisibility(View.VISIBLE);
                    notification_btn.setVisibility(View.VISIBLE);
                    rating_btn.setVisibility(View.VISIBLE);
                    profile_btn.setVisibility(View.VISIBLE);
                }else {
                    menu_status = true;
                    calendar_btn.setVisibility(View.GONE);
                    friends_btn.setVisibility(View.GONE);
                    notification_btn.setVisibility(View.GONE);
                    rating_btn.setVisibility(View.GONE);
                    profile_btn.setVisibility(View.GONE);
                }
                break;



            case R.id.calender_btn :
                calendar_btn.setVisibility(View.GONE);
                friends_btn.setVisibility(View.GONE);
                notification_btn.setVisibility(View.GONE);
                rating_btn.setVisibility(View.GONE);
                profile_btn.setVisibility(View.GONE);
                Fragment fragment = new CalenderFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.store_frame, fragment).addToBackStack("0").commit();

                break;

            case R.id.profile_btn :
                calendar_btn.setVisibility(View.GONE);
                friends_btn.setVisibility(View.GONE);
                notification_btn.setVisibility(View.GONE);
                rating_btn.setVisibility(View.GONE);
                profile_btn.setVisibility(View.GONE);
                Fragment fragment6 = new ProfileFragment();
                FragmentManager fragmentManager6 = getSupportFragmentManager();
                fragmentManager6.beginTransaction().replace(R.id.store_frame, fragment6).addToBackStack("0").commit();

                break;

            case R.id.rating_btn :
                calendar_btn.setVisibility(View.GONE);
                friends_btn.setVisibility(View.GONE);
                notification_btn.setVisibility(View.GONE);
                rating_btn.setVisibility(View.GONE);
                profile_btn.setVisibility(View.GONE);
                Fragment fragment1 = new RatingFragment();
                FragmentManager fragmentManager1 = getSupportFragmentManager();
                fragmentManager1.beginTransaction().replace(R.id.store_frame, fragment1).addToBackStack("0").commit();
                break;

            case R.id.friends_btn :
                calendar_btn.setVisibility(View.GONE);
                friends_btn.setVisibility(View.GONE);
                notification_btn.setVisibility(View.GONE);
                rating_btn.setVisibility(View.GONE);
                profile_btn.setVisibility(View.GONE);
                Fragment fragment5 = new HomeFragment();
                FragmentManager fragmentManager5 = getSupportFragmentManager();
                fragmentManager5.beginTransaction().replace(R.id.store_frame, fragment5).addToBackStack("0").commit();
                break;

            case R.id.notification_btn :
                calendar_btn.setVisibility(View.GONE);
                friends_btn.setVisibility(View.GONE);
                notification_btn.setVisibility(View.GONE);
                rating_btn.setVisibility(View.GONE);
                profile_btn.setVisibility(View.GONE);
                Fragment fragment4 = new NotificationFragment();
                FragmentManager fragmentManager4 = getSupportFragmentManager();
                fragmentManager4.beginTransaction().replace(R.id.store_frame, fragment4).addToBackStack("0").commit();
                break;
        }*/
    }

    @Override
    public void onClick(View view) {

    }
}
