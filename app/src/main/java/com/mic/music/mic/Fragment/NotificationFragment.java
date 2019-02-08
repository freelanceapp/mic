package com.mic.music.mic.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mic.music.mic.Newmic.Fragment.AllNotificationFragment;
import com.mic.music.mic.R;


public class NotificationFragment extends Fragment {

    TextView recent_btn,all_btn;

    public NotificationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_notification, container, false);
        Fragment fragment1 = new RecentNotificationFragment();
        FragmentManager fragmentManager1 = getActivity().getSupportFragmentManager();
        fragmentManager1.beginTransaction().replace(R.id.notification_frame, fragment1).commit();

        recent_btn = (TextView)view.findViewById(R.id.recent_btn1);
        recent_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment1 = new RecentNotificationFragment();
                all_btn.setBackground(getResources().getDrawable(R.drawable.mic_background));
                recent_btn.setBackgroundColor(Color.TRANSPARENT);
                FragmentManager fragmentManager1 = getActivity().getSupportFragmentManager();
                fragmentManager1.beginTransaction().replace(R.id.notification_frame, fragment1).addToBackStack("0").commit();
            }
        });
        all_btn = (TextView)view.findViewById(R.id.all_btn1);
        all_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment1 = new AllNotificationFragment();
                recent_btn.setBackground(getResources().getDrawable(R.drawable.mic_background));
                all_btn.setBackgroundColor(Color.TRANSPARENT);
                FragmentManager fragmentManager1 = getActivity().getSupportFragmentManager();
                fragmentManager1.beginTransaction().replace(R.id.notification_frame, fragment1).addToBackStack("0").commit();
            }
        });
        return view;
    }


}
