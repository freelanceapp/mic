package com.mic.music.mic.Newmic;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.mic.music.mic.AudioUpload.AudioListActivity;
import com.mic.music.mic.AudioUpload.AudioRecordActivity;
import com.mic.music.mic.Newmic.Adapter.CompetitionsAdapter;
import com.mic.music.mic.Newmic.Adapter.WinnersAdapter;
import com.mic.music.mic.R;
import com.mic.music.mic.VideoRecord.RecordCompressActivity;
import com.mic.music.mic.VideoRecord.SelectPictureActivity;
import com.mic.music.mic.VideoRecord.VideoRecordActivity;
import com.mic.music.mic.VideoUpload.VideoFolder;
import com.mic.music.mic.constant.Constant;
import com.mic.music.mic.model.competition_responce.Competition;
import com.mic.music.mic.model.competition_responce.CompetitionLevel;
import com.mic.music.mic.model.winner_responce.Participation;
import com.mic.music.mic.model.winner_responce.WinnersModel;
import com.mic.music.mic.retrofit_provider.RetrofitService;
import com.mic.music.mic.retrofit_provider.WebResponse;
import com.mic.music.mic.utils.Alerts;
import com.mic.music.mic.utils.AppPreference;
import com.mic.music.mic.utils.BaseFragment;
import com.mic.music.mic.utils.ButtonSound;
import com.mic.music.mic.utils.ConnectionDetector;

import java.util.ArrayList;

import retrofit2.Response;

public class AudioVedio extends BaseFragment implements View.OnClickListener {

    public LinearLayout audiodialog, videodialog;
    public RadioButton radioVideoButton;
    public RadioButton radioAudioButton;
    private View view;
    public Fragment fragment;
    String videoSelect, audioSelect;
    private ArrayList<Competition> arrayList = new ArrayList<>();
    private ArrayList<CompetitionLevel> itemsList = new ArrayList<>();
    private ArrayList<Participation> participationArrayList = new ArrayList<>();
    private WinnersAdapter winnersAdapter;
    private CompetitionsAdapter adapter;
    public static int formant1 = 0;
    public static Dialog CompetitionDialog;
    private RecyclerView recylerview;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.levelBtn:
               /* int pos = Integer.parseInt(view.getTag().toString());
                Competition category = arrayList.get(position1);;
                String strCompetitionName = category.getCompetitionLevel().get(pos).getCompetitionLevelName();
                Toast.makeText(context,"Name "+strCompetitionName, Toast.LENGTH_SHORT).show();
                if (formant == 0)
                {
                    showAudioDialog();
                }else {
                    showVideoDialog();
                }
                CompetitionDialog.dismiss();*/
                break;
        }
    }

    public enum STATE {
        OPEN, CLOSED, IN_OPEN_PROCESS, IN_CLOSE_PROCESS
    }

    private STATE mState = STATE.CLOSED;
    static Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.activity_audio_vedio, container, false);
        mContext = getActivity();
        activity = getActivity();
        cd = new ConnectionDetector(mContext);
        ButtonSound.getInstance().init(mContext);
        retrofitRxClient = RetrofitService.getRxClient();
        retrofitApiClient = RetrofitService.getRetrofit();

        audiodialog = view.findViewById(R.id.audio_dialog);
        videodialog = view.findViewById(R.id.video_dialog);
        recylerview = view.findViewById(R.id.recylerview);

        setWinnersData();
        winnerApi();

        audiodialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //showCompetitionDialog();
                formant1 = 1;
                ButtonSound.getInstance().playSound(ButtonSound.SOUND_1);
                ButtonSound.getInstance().vibration(mContext);
                String strCompany = AppPreference.getStringPreference(mContext, Constant.COMPANY_ID);

                Log.e("Company ", ".." + strCompany);
                if (strCompany.equals("")) {
                    fragment = new MicCompetitions();
                    loadFragment(fragment);
                } else {
                    showAudioDialog();
                }

                ButtonSound.getInstance().playSound(ButtonSound.SOUND_1);
            }
        });
        videodialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ButtonSound.getInstance().playSound(ButtonSound.SOUND_1);
                ButtonSound.getInstance().vibration(mContext);
                formant1 = 2;
                String strCompany = AppPreference.getStringPreference(mContext, Constant.COMPANY_ID);
                Log.e("Company ", ".." + strCompany);
                if (strCompany.equals("")) {
                    fragment = new MicCompetitions();
                    loadFragment(fragment);
                } else {
                    showVideoDialog();
                }
                ButtonSound.getInstance().playSound(ButtonSound.SOUND_1);
            }
        });
        return view;
    }

    private void setWinnersData() {
        winnersAdapter = new WinnersAdapter(getActivity(), participationArrayList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recylerview.setLayoutManager(mLayoutManager);
        recylerview.setItemAnimator(new DefaultItemAnimator());
        recylerview.setAdapter(winnersAdapter);
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
                    // Toast.makeText(context, "Select Option 1 "+radioAudioButton.getText(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(mContext, AudioRecordActivity.class);
                    mContext.startActivity(intent);
                } else if (radioAudioButton.getText().equals("Upload Audio")) {
                    // Toast.makeText(context, "Select Option 1 "+radioAudioButton.getText(), Toast.LENGTH_SHORT).show();
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

    public void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void winnerApi() {
        if (cd.isNetworkAvailable()) {
            RetrofitService.getwinner(new Dialog(mContext), retrofitApiClient.getwinner(), new WebResponse() {
                @Override
                public void onResponseSuccess(Response<?> result) {
                    WinnersModel loginModal = (WinnersModel) result.body();
                    assert loginModal != null;
                    participationArrayList.clear();
                    if (!loginModal.getError()) {
                   //     Alerts.show(mContext, loginModal.getMessage());
                        participationArrayList.addAll(loginModal.getParticipation());
                    } else {
                        Alerts.show(mContext, loginModal.getMessage());
                    }
                    winnersAdapter.notifyDataSetChanged();
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
}
