package com.online.music.mic.Newmic.Fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.online.music.mic.AudioUpload.AudioListActivity;
import com.online.music.mic.AudioUpload.AudioRecordActivity;
import com.online.music.mic.Newmic.Activity.HomeActivity;
import com.online.music.mic.Newmic.Activity.RankLevelActivity;
import com.online.music.mic.Newmic.Adapter.ParticipationListAdapter;
import com.online.music.mic.R;
import com.online.music.mic.VideoRecord.VideoRecordActivity;
import com.online.music.mic.VideoUpload.VideoFolder;
import com.online.music.mic.constant.Constant;
import com.online.music.mic.model.participation_responce.Participation;
import com.online.music.mic.model.participation_responce.ParticipationModel;
import com.online.music.mic.model.token_responce.TokenModel;
import com.online.music.mic.retrofit_provider.RetrofitService;
import com.online.music.mic.retrofit_provider.WebResponse;
import com.online.music.mic.utils.Alerts;
import com.online.music.mic.utils.AppPreference;
import com.online.music.mic.utils.BaseActivity;
import com.online.music.mic.utils.ConnectionDetector;

import java.util.ArrayList;

import retrofit2.Response;

import static com.online.music.mic.Newmic.Activity.HomeActivity.user_id;
import static com.online.music.mic.Newmic.AudioVedio.formant1;


public class ParticipationDetailFragment extends BaseActivity implements View.OnClickListener {
    public RadioButton radioVideoButton;
    public RadioButton radioAudioButton;
    private View view;
    private RecyclerView rvParticipationList;
    private ParticipationListAdapter adapter;
    ArrayList<Participation> participationArrayList = new ArrayList<>();
    private String companyId, compatitonLevelContentType, compatitonLevelId, compatitonLevelPaymentType, compatitonLevelAdminStatus, compatitonLevelFile;
    private ImageView ivBackBtn;
    TextView tvAdminStatus1, tvShowRank1;

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
        participationArrayList.clear();
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

                View view1 = rvParticipationList.getChildAt(pos);
                tvAdminStatus1 = (TextView) view1.findViewById(R.id.tvAdminStatus);
                tvShowRank1 = (TextView) view1.findViewById(R.id.tvShowRank);
                compatitonLevelFile = participationArrayList.get(pos).getContent_status();
                compatitonLevelId = participationArrayList.get(pos).getCompetitionLevel();
                compatitonLevelPaymentType = participationArrayList.get(pos).getType();
                compatitonLevelAdminStatus = participationArrayList.get(pos).getAdminStatus();
                competitionApi();
                AppPreference.setStringPreference(mContext, Constant.FILE_TYPE, compatitonLevelContentType);
                AppPreference.setStringPreference(mContext, Constant.COMPANY_ID, companyId);
                AppPreference.setStringPreference(mContext, Constant.LEVEL_ID, participationArrayList.get(pos).getCompetitionLevel());
                Log.e("content_status", participationArrayList.get(pos).getContent_status());
                Log.e("PaymentStatus", participationArrayList.get(pos).getPaymentStatus());
                break;

            case R.id.tvShowRank:
                Intent intent = new Intent(mContext, RankLevelActivity.class);
                intent.putExtra("CompatitonLevelId", compatitonLevelId);
                startActivity(intent);

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


    private void competitionApi() {
        cd = new ConnectionDetector(mContext);
        retrofitRxClient = RetrofitService.getRxClient();
        retrofitApiClient = RetrofitService.getRetrofit();
        String strId = AppPreference.getStringPreference(mContext, Constant.User_Id);
        Log.e("userid", strId);
        Log.e("compatitonLevelId", compatitonLevelId);
        Log.e("companyId", companyId);
        Log.e("PaymentType", compatitonLevelPaymentType);
        if (cd.isNetworkAvailable()) {
            RetrofitService.getParticipation(new Dialog(mContext), retrofitApiClient.getParticipation(compatitonLevelId, companyId, strId, compatitonLevelPaymentType), new WebResponse() {
                @Override
                public void onResponseSuccess(Response<?> result) {
                    TokenModel loginModal = (TokenModel) result.body();
                    assert loginModal != null;
                    if (!loginModal.getError()) {
                        Alerts.show(mContext, loginModal.getMessage());

                        participat();
                        tvAdminStatus1.setText("Apply");
                        tvAdminStatus1.setVisibility(View.VISIBLE);
                        tvShowRank1.setVisibility(View.GONE);
                    } else {
                        Alerts.show(mContext, loginModal.getMessage());
                        if (compatitonLevelFile.equals("false")) {
                            tvAdminStatus1.setVisibility(View.VISIBLE);
                            tvShowRank1.setVisibility(View.GONE);
                            participat();
                        } else {
                            tvAdminStatus1.setVisibility(View.GONE);
                            tvShowRank1.setVisibility(View.VISIBLE);
                        }
                    }
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

    private void participat() {
        if (compatitonLevelPaymentType.equals("Free")) {
            if (compatitonLevelAdminStatus.equals("Active")) {
                if (formant1 == 1) {
                    if (compatitonLevelContentType.equals("Video")) {
                        Toast.makeText(mContext, "Pleae Select Video File", Toast.LENGTH_SHORT).show();
                    } else {
                        showAudioDialog();
                    }
                } else if (formant1 == 2) {
                    if (compatitonLevelContentType.equals("Audio")) {
                        Toast.makeText(mContext, "Pleae Select Audio File", Toast.LENGTH_SHORT).show();
                    } else {
                        showVideoDialog();
                    }
                } else {
                    Toast.makeText(mContext, "Pleae Select Upload File", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ParticipationDetailFragment.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                }
            } else {
                Alerts.show(mContext, "Yor can not Active admin side");
                finish();
            }
        } else {
            if (compatitonLevelPaymentType.equals("Pending")) {
                Alerts.show(mContext, "Your Payment is Pending");
            } else {
                if (compatitonLevelAdminStatus.equals("Active")) {
                    if (formant1 == 1) {
                        if (compatitonLevelContentType.equals("Video")) {
                            Toast.makeText(mContext, "Please Select Video File", Toast.LENGTH_SHORT).show();
                        } else {
                            showAudioDialog();
                        }
                    } else if (formant1 == 2) {
                        if (compatitonLevelContentType.equals("Audio")) {
                            Toast.makeText(mContext, "Please Select Audio File", Toast.LENGTH_SHORT).show();
                        } else {
                            showVideoDialog();
                        }
                    } else {
                        Toast.makeText(mContext, "Please Select Upload File", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ParticipationDetailFragment.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    }
                } else {
                    Alerts.show(mContext, "Yor can not Active admin side");
                    finish();
                }
            }
        }
    }

}
