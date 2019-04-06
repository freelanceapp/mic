package com.online.music.mic.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.TextView;

import com.online.music.mic.Newmic.Fragment.AllNotificationFragment;
import com.online.music.mic.R;
import com.online.music.mic.utils.BaseActivity;


public class NotificationFragment extends BaseActivity {

    TextView recent_btn,all_btn;

    public NotificationFragment() {
        // Required empty public constructor
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_notification);

        Fragment fragment1 = new RecentNotificationFragment();
        FragmentManager fragmentManager1 = getSupportFragmentManager();
        fragmentManager1.beginTransaction().replace(R.id.notification_frame, fragment1).commit();

        recent_btn = (TextView)findViewById(R.id.recent_btn1);
        recent_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment1 = new RecentNotificationFragment();
                all_btn.setBackground(getResources().getDrawable(R.drawable.mic_background));
                recent_btn.setBackgroundColor(Color.TRANSPARENT);
                FragmentManager fragmentManager1 = getSupportFragmentManager();
                fragmentManager1.beginTransaction().replace(R.id.notification_frame, fragment1).addToBackStack("0").commit();
            }
        });
        all_btn = (TextView)findViewById(R.id.all_btn1);
        all_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment1 = new AllNotificationFragment();
                recent_btn.setBackground(getResources().getDrawable(R.drawable.mic_background));
                all_btn.setBackgroundColor(Color.TRANSPARENT);
                FragmentManager fragmentManager1 = getSupportFragmentManager();
                fragmentManager1.beginTransaction().replace(R.id.notification_frame, fragment1).addToBackStack("0").commit();
            }
        });
    }


}
