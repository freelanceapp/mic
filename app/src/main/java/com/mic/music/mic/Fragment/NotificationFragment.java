package com.mic.music.mic.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mic.music.mic.R;

import org.w3c.dom.Text;


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
                FragmentManager fragmentManager1 = getActivity().getSupportFragmentManager();
                fragmentManager1.beginTransaction().replace(R.id.notification_frame, fragment1).addToBackStack("0").commit();
            }
        });
        all_btn = (TextView)view.findViewById(R.id.all_btn1);
        all_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment1 = new AllNotificationFragment();
                FragmentManager fragmentManager1 = getActivity().getSupportFragmentManager();
                fragmentManager1.beginTransaction().replace(R.id.notification_frame, fragment1).addToBackStack("0").commit();
            }
        });
        return view;
    }


}
