package com.mic.music.mic.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.mic.music.mic.R;
import com.mic.music.mic.SplashScreen;

import static com.mic.music.mic.MainActivity.u_dob;
import static com.mic.music.mic.MainActivity.u_email;
import static com.mic.music.mic.MainActivity.u_gender;
import static com.mic.music.mic.MainActivity.u_mobile;
import static com.mic.music.mic.MainActivity.u_name;
import static com.mic.music.mic.SplashScreen.MyApp;


public class ProfileFragment extends Fragment {

    TextView pname,pemail,pgendar,pdob,pnumber;
    TextView logout_btn;
    public ProfileFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        pname = (TextView)view.findViewById(R.id.p_name);
        pemail = (TextView)view.findViewById(R.id.p_email);
        pgendar = (TextView)view.findViewById(R.id.p_gendar);
        pdob = (TextView)view.findViewById(R.id.p_dob);
        pnumber = (TextView)view.findViewById(R.id.p_mobile);
        logout_btn = (TextView)view.findViewById(R.id.logout_btn);

        pname.setText(u_name);
        pdob.setText(u_dob);
        pnumber.setText(u_mobile);
        pgendar.setText(u_gender);
        pemail.setText(u_email);

        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              /*  SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences(MyApp, 0); // 0 - for private mode
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("id","0");
                editor.apply();
                Intent intent = new Intent(getActivity(), SplashScreen.class);
                getActivity().startActivity(intent);
                getActivity().finish();*/
                /*Fragment fragment = new EditProfile();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.store_frame, fragment).addToBackStack("0").commit();*/

            }
        });

        return view;
    }

}
