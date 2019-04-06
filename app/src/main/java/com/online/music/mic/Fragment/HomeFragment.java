package com.online.music.mic.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.online.music.mic.R;
import com.online.music.mic.VideoRecord.VideoRecordActivity;
import com.online.music.mic.VideoUpload.VideoFolder;


public class HomeFragment extends Fragment {
    LinearLayout upload_btn,record_btn;
    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        upload_btn = (LinearLayout)view.findViewById(R.id.upload_btn);
        record_btn = (LinearLayout)view.findViewById(R.id.record_btn);

        upload_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),VideoFolder.class);
                startActivity(intent);
            }
        });

        record_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), VideoRecordActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

}
