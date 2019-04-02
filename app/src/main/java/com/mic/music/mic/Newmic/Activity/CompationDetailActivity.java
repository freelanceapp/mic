package com.mic.music.mic.Newmic.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mic.music.mic.AudioUpload.AudioListActivity;
import com.mic.music.mic.AudioUpload.AudioRecordActivity;
import com.mic.music.mic.Newmic.Fragment.ParticipationDetailFragment;
import com.mic.music.mic.R;
import com.mic.music.mic.VideoRecord.VideoRecordActivity;
import com.mic.music.mic.VideoUpload.VideoFolder;
import com.mic.music.mic.constant.Constant;
import com.mic.music.mic.model.level_detail_modal.SingleLevelMainModal;
import com.mic.music.mic.model.level_detail_modal.SingleLevelParticipation;
import com.mic.music.mic.model.token_responce.TokenModel;
import com.mic.music.mic.retrofit_provider.RetrofitService;
import com.mic.music.mic.retrofit_provider.WebResponse;
import com.mic.music.mic.utils.Alerts;
import com.mic.music.mic.utils.AppPreference;
import com.mic.music.mic.utils.BaseActivity;
import com.mic.music.mic.utils.ConnectionDetector;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Response;

import static com.mic.music.mic.Newmic.AudioVedio.formant1;

public class CompationDetailActivity extends BaseActivity implements View.OnClickListener {

    public RadioButton radioVideoButton;
    public RadioButton radioAudioButton;

    private String CompationId, compatitionLevelId, compatitionLevelName, compatitionLevelDetail,
            compatitionLevelDuration, compatitionLevelResult, compatitonLevelPaymentType, compatitonLevelPaymentAmount,
            compatitonLevelContentType, compatitonLevelRules, resultStatus, strType;

    private TextView tvCompatitionLevelName;
    private TextView tvCompatitionLevelDetail;
    private TextView tvCompatitionLevelDuretion;
    private TextView tvCompatitionLevelRules;
    private TextView tvCompatitionLevelResult;
    private TextView tvCompatitionLevelPaymentType;
    private TextView tvCompatitionLevelPaymentAmount;
    private TextView tvCompatitionLevelContantType;
    private TextView txtMessage;
    private Button btnApply, btnRank, btnContentUpload;
    private ImageView backCompationDetail;
    private String sDate, eDate, rDate;
    LinearLayout llAmount;
    private ImageView content_Video, content_Audio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compation_detail);

        mContext = this;
        cd = new ConnectionDetector(mContext);
        retrofitRxClient = RetrofitService.getRxClient();
        retrofitApiClient = RetrofitService.getRetrofit();

        tvCompatitionLevelName = findViewById(R.id.tvCompetitionLevelName);
        tvCompatitionLevelDetail = findViewById(R.id.tvCompetitionLevelDetail1);
        tvCompatitionLevelDuretion = findViewById(R.id.tvCompetitionDuretion);
        tvCompatitionLevelPaymentType = findViewById(R.id.tvCompetitionPaymentType);
        tvCompatitionLevelPaymentAmount = findViewById(R.id.tvCompetitionPrice);
        tvCompatitionLevelRules = findViewById(R.id.tvCompetitionRules);
        tvCompatitionLevelContantType = findViewById(R.id.tvCompetitionType);
        tvCompatitionLevelResult = findViewById(R.id.tvCompetitionResult);
        txtMessage = findViewById(R.id.txtMessage);
        btnContentUpload = findViewById(R.id.btnContentUpload);
        btnContentUpload.setOnClickListener(this);
        btnApply = findViewById(R.id.btnApply);
        btnRank = findViewById(R.id.btnRank);
        llAmount = findViewById(R.id.llAmount);
        content_Video = findViewById(R.id.content_Video);
        content_Audio = findViewById(R.id.content_Audio);
        backCompationDetail = findViewById(R.id.backCompationDetail);

        CompationId = getIntent().getStringExtra("CompationId");
        compatitionLevelId = getIntent().getStringExtra("level_id");
        strType = getIntent().getStringExtra("type");
        levelDetailApi();
    }

    private void levelDetailApi() {
        String strUserId = AppPreference.getStringPreference(mContext, Constant.User_Id);

        if (cd.isNetworkAvailable()) {
            RetrofitService.getLevelData(new Dialog(mContext), retrofitApiClient.levelDetail(strUserId,
                    compatitionLevelId), new WebResponse() {
                @Override
                public void onResponseSuccess(Response<?> result) {
                    SingleLevelMainModal levelMainModal = (SingleLevelMainModal) result.body();
                    if (levelMainModal != null) {
                        if (!levelMainModal.getError()) {
                            compatitionLevelName = levelMainModal.getSingleLevelCompetition().getCompetitionLevelName();
                            compatitionLevelDetail = levelMainModal.getSingleLevelCompetition().getCompetitionLevelDescription();
                            compatitionLevelDuration = levelMainModal.getSingleLevelCompetition().getCompetitionLevelDuration();
                            compatitionLevelResult = levelMainModal.getSingleLevelCompetition().getCompetitionLevelResult();
                            compatitonLevelPaymentType = levelMainModal.getSingleLevelCompetition().getCompetitionLevelPaymentType();
                            compatitonLevelPaymentAmount = levelMainModal.getSingleLevelCompetition().getCompetitionLevelAmount();
                            compatitonLevelContentType = levelMainModal.getSingleLevelCompetition().getCompetitionLevelContentType();
                            compatitonLevelRules = levelMainModal.getSingleLevelCompetition().getCompetitionLevelRules();
                            setButtonVisibility(levelMainModal);
                            setData();
                        } else {
                            Alerts.show(mContext, levelMainModal.getMessage());
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

    private void setButtonVisibility(SingleLevelMainModal levelMainModal) {
        if (strType.equalsIgnoreCase("second")) {
            resultStatus = getIntent().getStringExtra("result_status");
            if (resultStatus.equalsIgnoreCase("win")) {
                btnApply.setVisibility(View.VISIBLE);
                txtMessage.setVisibility(View.GONE);
                btnRank.setVisibility(View.GONE);
            } else {
                txtMessage.setVisibility(View.GONE);
                btnRank.setVisibility(View.GONE);
                btnApply.setVisibility(View.GONE);
            }
        } else {
            btnApply.setVisibility(View.VISIBLE);
            txtMessage.setVisibility(View.GONE);
            btnRank.setVisibility(View.GONE);
        }

        SingleLevelParticipation participation = levelMainModal.getSingleLevelCompetition().getSingleLevelParticipation();
        if (participation.getParticipationStatus()) {
            if (participation.getAdminStatus().equalsIgnoreCase("active")) {
                if (participation.getPaymentStatus().equalsIgnoreCase("paid") ||
                        participation.getPaymentStatus().equalsIgnoreCase("free")) {
                    btnApply.setVisibility(View.VISIBLE);
                    txtMessage.setVisibility(View.GONE);
                    btnRank.setVisibility(View.GONE);

                    if (participation.getContentStatus()) {
                        txtMessage.setVisibility(View.GONE);
                        btnRank.setVisibility(View.VISIBLE);
                        btnApply.setVisibility(View.GONE);
                    } else {
                        txtMessage.setVisibility(View.GONE);
                        btnRank.setVisibility(View.GONE);
                        btnApply.setVisibility(View.GONE);
                        btnContentUpload.setVisibility(View.VISIBLE);
                    }
                } else {
                    txtMessage.setVisibility(View.GONE);
                    btnRank.setVisibility(View.GONE);
                    btnApply.setVisibility(View.GONE);
                    Alerts.show(mContext, "Your payment is under processing...!!!");
                }
            } else {
                txtMessage.setVisibility(View.VISIBLE);
                btnRank.setVisibility(View.GONE);
                btnApply.setVisibility(View.GONE);
            }
        } else {
            txtMessage.setVisibility(View.GONE);
            btnApply.setVisibility(View.VISIBLE);
            btnRank.setVisibility(View.GONE);
        }
    }

    private void setData() {
        backCompationDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        String s = compatitionLevelDuration;
        String[] data1 = s.split("-", 2);
        String fDate = " " + data1[0];
        String lDate = data1[1] + " ";
        Log.e("fDate", fDate);
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm aa", Locale.ENGLISH);

        try {
            Date strDate = sdf.parse(fDate);
            Date strEndDate = sdf.parse(lDate);

            /*if (System.currentTimeMillis() > strDate.getTime() && System.currentTimeMillis() > strEndDate.getTime()) {
                Log.e("close", "close ");
                btnApply.setVisibility(View.GONE);
            } else if (System.currentTimeMillis() < strDate.getTime()) {
                btnApply.setVisibility(View.GONE);
            } else if (System.currentTimeMillis() > strDate.getTime() && System.currentTimeMillis() < strEndDate.getTime()) {
                btnApply.setVisibility(View.VISIBLE);
            }*/
        } catch (ParseException e) {
            e.printStackTrace();
        }

        tvCompatitionLevelName.setText(compatitionLevelName);

        String inputPattern = "MM/dd/yyyy HH:mm aa";
        String outputPattern = "dd-MMM-yyyy h:mm a";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern, Locale.ENGLISH);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern, Locale.ENGLISH);

        try {
            Date date = inputFormat.parse(fDate);
            Date date1 = inputFormat.parse(lDate);

            sDate = outputFormat.format(date);
            eDate = outputFormat.format(date1);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        tvCompatitionLevelDuretion.setText("Start Date " + sDate + " \n\nEnd Date " + eDate);

        tvCompatitionLevelPaymentType.setText("Payment Type " + compatitonLevelPaymentType);
        if (compatitonLevelPaymentAmount.equals("0")) {
            llAmount.setVisibility(View.GONE);
        } else {
            tvCompatitionLevelPaymentAmount.setText(" " + compatitonLevelPaymentAmount);
        }
        //tvCompatitionLevelContantType.setText("Content Type " + compatitonLevelContentType);

        if (compatitonLevelContentType.equals("Video")) {
            content_Audio.setVisibility(View.GONE);
            content_Video.setVisibility(View.VISIBLE);
        } else if (compatitonLevelContentType.equals("Audio")) {
            content_Audio.setVisibility(View.VISIBLE);
            content_Video.setVisibility(View.GONE);
        } else if (compatitonLevelContentType.equals("Both")) {
            content_Audio.setVisibility(View.VISIBLE);
            content_Video.setVisibility(View.VISIBLE);
        } else {
            content_Audio.setVisibility(View.GONE);
            content_Video.setVisibility(View.GONE);
        }

        tvCompatitionLevelResult.setText(" " + compatitionLevelResult);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tvCompatitionLevelDetail.setText(Html.fromHtml("\n" + compatitionLevelDetail, Html.FROM_HTML_MODE_COMPACT));
        } else {
            tvCompatitionLevelDetail.setText(Html.fromHtml("\n" + compatitionLevelDetail));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tvCompatitionLevelRules.setText(Html.fromHtml("\n" + compatitonLevelRules, Html.FROM_HTML_MODE_COMPACT));
        } else {
            tvCompatitionLevelRules.setText(Html.fromHtml("\n" + compatitonLevelRules));
        }

        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                competitionApi();

            }
        });
        btnRank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //competitionApi();
                Intent intent = new Intent(mContext, RankLevelActivity.class);
                intent.putExtra("CompatitonLevelId", compatitionLevelId);
                startActivity(intent);
            }
        });
    }

    private void competitionApi() {
        cd = new ConnectionDetector(mContext);
        retrofitRxClient = RetrofitService.getRxClient();
        retrofitApiClient = RetrofitService.getRetrofit();
        String strId = AppPreference.getStringPreference(mContext, Constant.User_Id);
        Log.e("userid", strId);
        Log.e("compatitonLevelId", compatitionLevelId);
        Log.e("companyId", CompationId);
        Log.e("PaymentType", compatitonLevelPaymentType);
        if (cd.isNetworkAvailable()) {
            RetrofitService.getParticipation(new Dialog(mContext), retrofitApiClient.getParticipation(compatitionLevelId, CompationId, strId, compatitonLevelPaymentType), new WebResponse() {
                @Override
                public void onResponseSuccess(Response<?> result) {
                    TokenModel loginModal = (TokenModel) result.body();
                    assert loginModal != null;
                    if (!loginModal.getError()) {
                        btnApply.setVisibility(View.VISIBLE);
                        btnRank.setVisibility(View.GONE);
                        Intent intent = new Intent(mContext, ParticipationDetailFragment.class);
                        intent.putExtra("companyId", CompationId);
                        intent.putExtra("CompatitonLevelContentType", compatitonLevelContentType);
                        mContext.startActivity(intent);
                    } else {
                        Alerts.show(mContext, loginModal.getMessage());
                        btnApply.setVisibility(View.GONE);
                        btnRank.setVisibility(View.VISIBLE);
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

    /*************************************************************************************************************/
    /*
     * Audio video dialog
     * */
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnContentUpload:
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
                    Intent intent = new Intent(this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                }
                break;
        }
    }
}
