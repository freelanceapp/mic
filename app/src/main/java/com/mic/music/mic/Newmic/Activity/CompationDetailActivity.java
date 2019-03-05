package com.mic.music.mic.Newmic.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mic.music.mic.Newmic.Fragment.ParticipationDetailFragment;
import com.mic.music.mic.R;
import com.mic.music.mic.constant.Constant;
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

import retrofit2.Response;

public class CompationDetailActivity extends BaseActivity {

    private String CompationId, compatitionLevelId, compatitionLevelName, compatitionLevelDetail, compatitionLevelDuration, compatitionLevelResult,
            compatitonLevelPaymentType, compatitonLevelPaymentAmount, compatitonLevelContentType, compatitonLevelRules;

    private TextView tvCompatitionLevelName;
    private TextView tvCompatitionLevelDetail;
    private TextView tvCompatitionLevelDuretion;
    private TextView tvCompatitionLevelRules;
    private TextView tvCompatitionLevelResult;
    private TextView tvCompatitionLevelPaymentType;
    private TextView tvCompatitionLevelPaymentAmount;
    private TextView tvCompatitionLevelContantType;
    private Button btnApply, btnRank;
    private ImageView backCompationDetail;
    private String sDate,eDate,rDate;
    LinearLayout llAmount;
    private ImageView content_Video,content_Audio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compation_detail);

        mContext = this;
        cd = new ConnectionDetector(mContext);
        retrofitRxClient = RetrofitService.getRxClient();
        retrofitApiClient = RetrofitService.getRetrofit();

        CompationId = getIntent().getStringExtra("CompationId");
        compatitionLevelId = getIntent().getStringExtra("CompatitonLevelId");
        compatitionLevelName = getIntent().getStringExtra("CompatitonLevelName");
        compatitionLevelDetail = getIntent().getStringExtra("CompatitonLevelDetail");
        compatitionLevelDuration = getIntent().getStringExtra("CompatitonLevelDuretion");
        compatitionLevelResult = getIntent().getStringExtra("CompatitonLevelResult");
        compatitonLevelPaymentType = getIntent().getStringExtra("CompatitonLevelPaymentType");
        compatitonLevelPaymentAmount = getIntent().getStringExtra("CompatitonLevelPaymentAmount");
        compatitonLevelContentType = getIntent().getStringExtra("CompatitonLevelPaymentContentType");
        compatitonLevelRules = getIntent().getStringExtra("CompatitonLevelPaymentContentRules");

        tvCompatitionLevelName = (TextView) findViewById(R.id.tvCompetitionLevelName);
        tvCompatitionLevelDetail = (TextView) findViewById(R.id.tvCompetitionLevelDetail1);
        tvCompatitionLevelDuretion = (TextView) findViewById(R.id.tvCompetitionDuretion);
        tvCompatitionLevelPaymentType = (TextView) findViewById(R.id.tvCompetitionPaymentType);
        tvCompatitionLevelPaymentAmount = (TextView) findViewById(R.id.tvCompetitionPrice);
        tvCompatitionLevelRules = (TextView) findViewById(R.id.tvCompetitionRules);
        tvCompatitionLevelContantType = (TextView) findViewById(R.id.tvCompetitionType);
        tvCompatitionLevelResult = (TextView) findViewById(R.id.tvCompetitionResult);
        btnApply = (Button) findViewById(R.id.btnApply);
        btnRank = (Button) findViewById(R.id.btnRank);
        llAmount = (LinearLayout) findViewById(R.id.llAmount);
        content_Video = (ImageView) findViewById(R.id.content_Video);
        content_Audio = (ImageView) findViewById(R.id.content_Audio);

        backCompationDetail = (ImageView) findViewById(R.id.backCompationDetail);
        backCompationDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        String s = compatitionLevelDuration;
        String[] data1 = s.split("-", 2);
        String fDate = data1[0];
        String lDate = data1[1];
        Log.e("fDate", fDate);
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm aa");
        Date strDate = null, strEndDate = null;
        try {
            strDate = sdf.parse(fDate);
            strEndDate = sdf.parse(lDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (System.currentTimeMillis() > strDate.getTime() && System.currentTimeMillis() > strEndDate.getTime()) {
            Log.e("close", "close ");
            btnApply.setVisibility(View.GONE);
        } else if (System.currentTimeMillis() < strDate.getTime()) {
            btnApply.setVisibility(View.GONE);
        } else if (System.currentTimeMillis() > strDate.getTime() && System.currentTimeMillis() < strEndDate.getTime()) {
            btnApply.setVisibility(View.VISIBLE);
        }

        tvCompatitionLevelName.setText(compatitionLevelName);

        String s1 = compatitionLevelDuration;
        String[] data11 = s1.split("-", 2);
        String fDate1 = data11[0];
        String lDate1 = data11[1];

        String inputPattern = "MM/dd/yyyy HH:mm aa";
        String outputPattern = "dd-MMM-yyyy h:mm a";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
        Date date = null , strResultDate = null;
        Date date1 = null;
        sDate = null;
        eDate = null;
        rDate = null;
        try {
            date = inputFormat.parse(fDate1);
            sDate = outputFormat.format(date);
            date1 = inputFormat.parse(lDate1);
            eDate = outputFormat.format(date1);
            strResultDate = inputFormat.parse(compatitionLevelResult);
            rDate = outputFormat.format(strResultDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        tvCompatitionLevelDuretion.setText("Start Date "+ sDate +" \n\nEnd Date "+ eDate);

        tvCompatitionLevelPaymentType.setText("Payment Type " + compatitonLevelPaymentType);
        if (compatitonLevelPaymentAmount.equals("0"))
        {
            llAmount.setVisibility(View.GONE);
        }else {
            tvCompatitionLevelPaymentAmount.setText(" " + compatitonLevelPaymentAmount);
        }
        //tvCompatitionLevelContantType.setText("Content Type " + compatitonLevelContentType);

        if (compatitonLevelContentType.equals("Video"))
        {
            content_Audio.setVisibility(View.GONE);
            content_Video.setVisibility(View.VISIBLE);
        }else if (compatitonLevelContentType.equals("Audio"))
        {
            content_Audio.setVisibility(View.VISIBLE);
            content_Video.setVisibility(View.GONE);
        }else if (compatitonLevelContentType.equals("Both")){
            content_Audio.setVisibility(View.VISIBLE);
            content_Video.setVisibility(View.VISIBLE);
        }else {
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
        if (cd.isNetworkAvailable()) {
            RetrofitService.getParticipation(new Dialog(mContext), retrofitApiClient.getParticipation(compatitionLevelId, CompationId, strId, compatitonLevelPaymentType), new WebResponse() {
                @Override
                public void onResponseSuccess(Response<?> result) {
                    TokenModel loginModal = (TokenModel) result.body();
                    assert loginModal != null;
                    if (!loginModal.getError()) {
                       // Alerts.show(mContext, loginModal.getMessage());
                        Log.e("message ", "..." + loginModal.getMessage());
                        Intent intent = new Intent(mContext, ParticipationDetailFragment.class);
                        intent.putExtra("companyId", CompationId);
                        intent.putExtra("CompatitonLevelContentType", compatitonLevelContentType);
                        mContext.startActivity(intent);

                    } else {
                        Alerts.show(mContext, loginModal.getMessage());
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
}
