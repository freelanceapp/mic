package com.mic.music.mic.Newmic;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
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
import android.widget.TextView;
import android.widget.Toast;

import com.cleveroad.sy.cyclemenuwidget.CycleMenuWidget;
import com.cleveroad.sy.cyclemenuwidget.OnMenuItemClickListener;
import com.cleveroad.sy.cyclemenuwidget.OnStateChangedListener;
import com.google.gson.Gson;
import com.mic.music.mic.AudioUpload.AudioListActivity;
import com.mic.music.mic.AudioUpload.AudioRecordActivity;
import com.mic.music.mic.Newmic.Activity.HomeActivity;
import com.mic.music.mic.Newmic.Activity.Mobile_Ragistration;
import com.mic.music.mic.Newmic.Activity.SplashScreen;
import com.mic.music.mic.Newmic.Adapter.CompetitionsAdapter;
import com.mic.music.mic.R;
import com.mic.music.mic.VideoRecord.VideoRecordActivity;
import com.mic.music.mic.VideoUpload.VideoFolder;
import com.mic.music.mic.constant.Constant;
import com.mic.music.mic.model.User;
import com.mic.music.mic.model.competition_responce.Competition;
import com.mic.music.mic.model.competition_responce.CompetitionLevel;
import com.mic.music.mic.model.competition_responce.CompletionModel;
import com.mic.music.mic.model.otp_responce.OtpModel;
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
    LinearLayout audiodialog, videodialog;
    public  RadioButton radioVideoButton, radioAudioButton;
    private  View view;
    private Fragment fragment;
    String videoSelect, audioSelect;
    private ArrayList<Competition> arrayList = new ArrayList<>();
    private ArrayList<CompetitionLevel> itemsList = new ArrayList<>();
    private CompetitionsAdapter adapter;
    public static int formant = 0;
    public static Dialog CompetitionDialog;
    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
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
        view =  inflater.inflate(R.layout.activity_audio_vedio, container, false);
        mContext = getActivity();
        activity = getActivity();
        cd = new ConnectionDetector(mContext);
        ButtonSound.getInstance().init(mContext);
        retrofitRxClient = RetrofitService.getRxClient();
        retrofitApiClient = RetrofitService.getRetrofit();

        audiodialog = view.findViewById(R.id.audio_dialog);
        videodialog = view.findViewById(R.id.video_dialog);

        audiodialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //showCompetitionDialog();
                String strCompany = AppPreference.getStringPreference(mContext, Constant.COMPANY_ID);
                Log.e("Company ", ".."+strCompany);
                if (strCompany.equals(""))
                {
                    fragment = new MicCompetitions();
                    loadFragment(fragment);
                }else {
                    showAudioDialog();
                }

                ButtonSound.getInstance().playSound(ButtonSound.SOUND_1);
            }
        });
        videodialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String strCompany = AppPreference.getStringPreference(mContext, Constant.COMPANY_ID);
                Log.e("Company ", ".."+strCompany);
                if (strCompany.equals(""))
                {
                    fragment = new MicCompetitions();
                    loadFragment(fragment);
                }else {
                    showVideoDialog();
                }
                ButtonSound.getInstance().playSound(ButtonSound.SOUND_1);
            }
        });
        return view;
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
        btnSelectAudio.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                int selectedId = rgAudio.getCheckedRadioButtonId();

                // find the radiobutton by returned id
                radioAudioButton = (RadioButton) dialog.findViewById(selectedId);
                if (radioAudioButton.getText().equals("Record Audio"))
                {
                   // Toast.makeText(context, "Select Option 1 "+radioAudioButton.getText(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(mContext,AudioRecordActivity.class);
                    startActivity(intent);
                }else  if (radioAudioButton.getText().equals("Upload Audio"))
                {
                   // Toast.makeText(context, "Select Option 1 "+radioAudioButton.getText(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(mContext, AudioListActivity.class);
                    startActivity(intent);
                }
                else {
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
        btnSelectVideo.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                // get selected radio button from radioGroup
                int selectedId = rgVideo.getCheckedRadioButtonId();
                // find the radiobutton by returned id
                radioVideoButton = (RadioButton) dialog.findViewById(selectedId);
                if (radioVideoButton.getText().equals("Record Video"))
                {
                    Intent intent = new Intent(mContext,VideoRecordActivity.class);
                    startActivity(intent);
                }else  if (radioVideoButton.getText().equals("Upload Video"))
                {
                    Intent intent = new Intent(mContext,VideoFolder.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(mContext, "Select Option any one option", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


    public void showCompetitionDialog() {
        CompetitionDialog = new Dialog(context);
        CompetitionDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        CompetitionDialog.setCancelable(false);
        CompetitionDialog.setContentView(R.layout.dialog_competition);
        final RecyclerView rvDCompetitionList = (RecyclerView) CompetitionDialog.findViewById(R.id.rvDCompetitionList);
        competitionApi();
        ImageView dialogButton = (ImageView) CompetitionDialog.findViewById(R.id.cancleBtn);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CompetitionDialog.dismiss();
            }
        });
        adapter = new CompetitionsAdapter(getActivity(),arrayList, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        rvDCompetitionList.setLayoutManager(mLayoutManager);
        rvDCompetitionList.setItemAnimator(new DefaultItemAnimator());
        rvDCompetitionList.setAdapter(adapter);
        CompetitionDialog.show();
    }

    private void competitionApi() {
        if (cd.isNetworkAvailable()) {
            RetrofitService.getCompetition(new Dialog(mContext), retrofitApiClient.getcompetition(), new WebResponse() {
                @Override
                public void onResponseSuccess(Response<?> result) {
                    CompletionModel loginModal = (CompletionModel) result.body();
                    assert loginModal != null;
                    arrayList.clear();
                    if (!loginModal.getError()) {
                        Alerts.show(mContext, loginModal.getMessage());
                        arrayList.addAll(loginModal.getCompetition());

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



}
