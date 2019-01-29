package com.mic.music.mic.Newmic;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.mic.music.mic.R;

public class Setting extends Fragment {

    LinearLayout about, reachus, termsofuse, privatepolicy, refundpolicy, soundsetting, notificationsetting, ourteam, performance;

    private View view;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.activity_setting, container, false);

        performance = view.findViewById(R.id.performance);
        ourteam = view.findViewById(R.id.ourteam);
        about = view.findViewById(R.id.about);
        reachus = view.findViewById(R.id.reachus);
        termsofuse = view.findViewById(R.id.termsofuse);
        privatepolicy = view.findViewById(R.id.privacypolicy);
        refundpolicy = view.findViewById(R.id.refundpolicy);
        soundsetting = view.findViewById(R.id.soundsetting);
        notificationsetting = view.findViewById(R.id.notification);

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), About.class);
                startActivity(intent);
            }
        });
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), About.class);
                startActivity(intent);
            }
        });
        reachus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ReachUs.class);
                startActivity(intent);
            }
        });
        termsofuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), TandC.class);
                startActivity(intent);
            }
        });
        privatepolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PrivatePolicy.class);
                startActivity(intent);
            }
        });
        refundpolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RefundPolicy.class);
                startActivity(intent);
            }
        });
        soundsetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SoundSetting.class);
                startActivity(intent);
            }
        });
        notificationsetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Notification.class);
                startActivity(intent);
            }
        });
        ourteam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), OurTeam.class);
                startActivity(intent);
            }
        });
        performance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Performance.class);
                startActivity(intent);
            }
        });

        return view;
    }

}
