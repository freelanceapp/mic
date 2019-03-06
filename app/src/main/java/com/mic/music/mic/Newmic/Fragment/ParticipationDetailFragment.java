package com.mic.music.mic.Newmic.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mic.music.mic.AudioUpload.AudioListActivity;
import com.mic.music.mic.AudioUpload.AudioRecordActivity;
import com.mic.music.mic.Newmic.Activity.HomeActivity;
import com.mic.music.mic.Newmic.Adapter.CompetitionsAdapter;
import com.mic.music.mic.Newmic.Adapter.ParticipationListAdapter;
import com.mic.music.mic.Newmic.AudioVedio;
import com.mic.music.mic.R;
import com.mic.music.mic.VideoRecord.SelectPictureActivity;
import com.mic.music.mic.VideoRecord.VideoRecordActivity;
import com.mic.music.mic.VideoUpload.VideoFolder;
import com.mic.music.mic.constant.Constant;
import com.mic.music.mic.model.Compatition1;
import com.mic.music.mic.model.User;
import com.mic.music.mic.model.competition_responce.Competition;
import com.mic.music.mic.model.competition_responce.CompletionModel;
import com.mic.music.mic.model.otp_responce.OtpModel;
import com.mic.music.mic.model.participation_responce.Participation;
import com.mic.music.mic.model.participation_responce.ParticipationModel;
import com.mic.music.mic.retrofit_provider.RetrofitService;
import com.mic.music.mic.retrofit_provider.WebResponse;
import com.mic.music.mic.utils.Alerts;
import com.mic.music.mic.utils.AppPreference;
import com.mic.music.mic.utils.BaseActivity;
import com.mic.music.mic.utils.BaseFragment;
import com.mic.music.mic.utils.ConnectionDetector;

import java.util.ArrayList;

import retrofit2.Response;

import static com.mic.music.mic.Newmic.Activity.HomeActivity.user_id;
import static com.mic.music.mic.Newmic.AudioVedio.formant1;


public class ParticipationDetailFragment extends BaseActivity implements View.OnClickListener {
    public RadioButton radioVideoButton;
    public RadioButton radioAudioButton;
    private View view;
    private RecyclerView rvParticipationList;
    private ParticipationListAdapter adapter;
    ArrayList<Participation> participationArrayList = new ArrayList<>();
    private String companyId, compatitonLevelContentType;
    private ImageView ivBackBtn;

    public ParticipationDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_participation_detail);
        init();

    }

    private void init() {
        companyId = getIntent().getStringExtra("companyId");
        compatitonLevelContentType = getIntent().getStringExtra("CompatitonLevelContentType");
        rvParticipationList = (RecyclerView) findViewById(R.id.rvParticipationList);
        mContext = this;
        cd = new ConnectionDetector(mContext);
        retrofitRxClient = RetrofitService.getRxClient();
        retrofitApiClient = RetrofitService.getRetrofit();
        selectParticipationApi();
        ivBackBtn = (ImageView) findViewById(R.id.ivBackBtn);
        ivBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        adapter = new ParticipationListAdapter(ParticipationDetailFragment.this, participationArrayList, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(ParticipationDetailFragment.this);
        rvParticipationList.setLayoutManager(mLayoutManager);
        rvParticipationList.setItemAnimator(new DefaultItemAnimator());
        rvParticipationList.setAdapter(adapter);
    }

    private void selectParticipationApi() {
        String strId = AppPreference.getStringPreference(getApplicationContext(), Constant.User_Id);
        if (cd.isNetworkAvailable()) {
            RetrofitService.getSelectParticipation(new Dialog(mContext), retrofitApiClient.getSelectParticipation(companyId, user_id), new WebResponse() {
                @Override
                public void onResponseSuccess(Response<?> result) {
                    ParticipationModel loginModal = (ParticipationModel) result.body();
                    assert loginModal != null;
                    if (!loginModal.getError()) {
                        // Alerts.show(mContext, loginModal.getMessage());
                        participationArrayList.addAll(loginModal.getParticipation());
                    } else {
                        Alerts.show(mContext, loginModal.getMessage());
                    }
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onResponseFailed(String error) {
                    Alerts.show(mContext, error);
                }
            });
        } else {
            cd.show(mContext);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvAdminStatus:
                int pos = Integer.parseInt(view.getTag().toString());
                AppPreference.setStringPreference(mContext, Constant.COMPANY_ID, companyId);
                AppPreference.setStringPreference(mContext, Constant.LEVEL_ID, participationArrayList.get(pos).getCompetitionLevel());
                if (participationArrayList.get(pos).getAdminStatus().equals("Active")) {
                    AudioVedio audioVedio = new AudioVedio();
                    if (formant1 == 1) {
                        if (compatitonLevelContentType.equals("Video"))
                        {
                            Toast.makeText(mContext, "Pleae Select Audio File", Toast.LENGTH_SHORT).show();
                        }else {
                            showAudioDialog();
                        }
                    } else if (formant1 == 2) {
                        if (compatitonLevelContentType.equals("Audio"))
                        {
                            Toast.makeText(mContext, "Pleae Select Video File", Toast.LENGTH_SHORT).show();
                        }else {
                            showVideoDialog();
                        }
                    } else {
                        Toast.makeText(mContext, "Pleae Select Upload File", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ParticipationDetailFragment.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    }
                } else {
                    Alerts.show(mContext, "Yor are not selected");
                    finish();
                }
                break;
        }
    }

    public void showAudioDialog() {
        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.activity_autdio_dialog);
        ImageView dialogButton = (ImageView) dialog.findViewById(R.id.cancleBtn);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        final RadioGroup rgAudio = (RadioGroup) dialog.findViewById(R.id.rgAudio);
        Button btnSelectAudio = (Button) dialog.findViewById(R.id.btnSelectAudio);
        btnSelectAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedId = rgAudio.getCheckedRadioButtonId();
                // find the radiobutton by returned id
                radioAudioButton = (RadioButton) dialog.findViewById(selectedId);
                if (radioAudioButton.getText().equals("Record Audio")) {
                    Intent intent = new Intent(mContext, AudioRecordActivity.class);
                    mContext.startActivity(intent);
                } else if (radioAudioButton.getText().equals("Upload Audio")) {
                    Intent intent = new Intent(mContext, AudioListActivity.class);
                    mContext.startActivity(intent);
                } else {
                    Toast.makeText(mContext, "Select Option any one option", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void showVideoDialog() {
        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.activity_video_dialog_box);
        final RadioGroup rgVideo = (RadioGroup) dialog.findViewById(R.id.rgVideo);
        ImageView dialogButton = (ImageView) dialog.findViewById(R.id.cancleVideoBtn);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        Button btnSelectVideo = (Button) dialog.findViewById(R.id.btnSelectVideo);
        btnSelectVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // get selected radio button from radioGroup
                int selectedId = rgVideo.getCheckedRadioButtonId();
                // find the radiobutton by returned id
                radioVideoButton = (RadioButton) dialog.findViewById(selectedId);
                if (radioVideoButton.getText().equals("Record Video")) {
                    Intent intent = new Intent(mContext, VideoRecordActivity.class);
                    mContext.startActivity(intent);
                } else if (radioVideoButton.getText().equals("Upload Video")) {
                    Intent intent = new Intent(mContext, VideoFolder.class);
                    mContext.startActivity(intent);
                } else {
                    Toast.makeText(mContext, "Select Option any one option", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
