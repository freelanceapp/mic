package com.mic.music.mic.Newmic;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.mic.music.mic.Newmic.Activity.Mobile_Ragistration;
import com.mic.music.mic.Newmic.Activity.NotificationActivity;
import com.mic.music.mic.R;
import com.mic.music.mic.constant.Constant;
import com.mic.music.mic.utils.AppPreference;
import com.mic.music.mic.utils.BaseFragment;

public class Setting extends BaseFragment {

    LinearLayout about, reachus, termsofuse, privatepolicy, refundpolicy, soundsetting, notificationsetting, ourteam, performance, logoutBtn;

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
        mContext = getActivity();

        performance = view.findViewById(R.id.performance);
        ourteam = view.findViewById(R.id.ourteam);
        about = view.findViewById(R.id.about);
        reachus = view.findViewById(R.id.reachus);
        termsofuse = view.findViewById(R.id.termsofuse);
        privatepolicy = view.findViewById(R.id.privacypolicy);
        refundpolicy = view.findViewById(R.id.refundpolicy);
        soundsetting = view.findViewById(R.id.soundsetting);
        notificationsetting = view.findViewById(R.id.notification);
        logoutBtn = view.findViewById(R.id.logoutBtn);

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
                Intent intent = new Intent(getActivity(), NotificationActivity.class);
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


        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Confirmation PopUp!").
                        setMessage("You sure, that you want to logout?");
                builder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                AppPreference.setBooleanPreference(mContext, Constant.Is_Login, false);
                                Intent i = new Intent(mContext, Mobile_Ragistration.class);
                                startActivity(i);
                                getActivity().finish();
                            }
                        });
                builder.setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert11 = builder.create();
                alert11.show();
            }
        });

        return view;
    }




}
