package com.mic.music.mic.Newmic.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mic.music.mic.Newmic.Adapter.SectionListDataAdapter;
import com.mic.music.mic.R;
import com.mic.music.mic.model.compation_level_responce.CompatitionLevelModel;
import com.mic.music.mic.model.compation_level_responce.CompetitionLevel;
import com.mic.music.mic.retrofit_provider.RetrofitService;
import com.mic.music.mic.retrofit_provider.WebResponse;
import com.mic.music.mic.utils.Alerts;
import com.mic.music.mic.utils.BaseActivity;
import com.mic.music.mic.utils.ConnectionDetector;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import retrofit2.Response;

public class CompatitonLevelActivity extends BaseActivity implements View.OnClickListener {

    private SectionListDataAdapter sectionListDataAdapter;
    private ArrayList<CompetitionLevel> competitionLevelArrayList = new ArrayList<>();
    private RecyclerView rvCompationLevel;
    String compationId = "";
    private ImageView backCompationLevel;
    private TextView tvCompatitionName;
    private TextView tvCompatitionDetail;
    private TextView tvCompatitionDuration;
    private TextView tvCompatitionRules;
    private Button btnShowGraph;
    private String sDate, eDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compatiton_level);

        compationId = getIntent().getStringExtra("Compation_id");
        Log.e("Compation_Id", compationId);
        mContext = this;
        cd = new ConnectionDetector(mContext);
        retrofitRxClient = RetrofitService.getRxClient();
        retrofitApiClient = RetrofitService.getRetrofit();

        rvCompationLevel = (RecyclerView) findViewById(R.id.rvCompationLevel);
        tvCompatitionName = (TextView) findViewById(R.id.tvCompatitionName);
        tvCompatitionDetail = (TextView) findViewById(R.id.tvCompatitionDetail);
        tvCompatitionDuration = (TextView) findViewById(R.id.tvCompatitionDuration);
        tvCompatitionRules = (TextView) findViewById(R.id.tvCompatitionRules);
        backCompationLevel = (ImageView) findViewById(R.id.backCompationLevel);
        btnShowGraph = (Button) findViewById(R.id.btnShowGraph);
        backCompationLevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tokenApi();

        btnShowGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CompatitonLevelActivity.this, PerformanceActivity.class);
                intent.putExtra("Compation_id", compationId);
                startActivity(intent);
            }
        });
        sectionListDataAdapter = new SectionListDataAdapter(CompatitonLevelActivity.this, competitionLevelArrayList, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(CompatitonLevelActivity.this);
        rvCompationLevel.setLayoutManager(mLayoutManager);
        rvCompationLevel.setItemAnimator(new DefaultItemAnimator());
        rvCompationLevel.setAdapter(sectionListDataAdapter);
    }

    private void tokenApi() {
        if (cd.isNetworkAvailable()) {
            RetrofitService.getCompetitionLevel(new Dialog(mContext), retrofitApiClient.getCompationLevel(compationId), new WebResponse() {
                @Override
                public void onResponseSuccess(Response<?> result) {
                    CompatitionLevelModel loginModal = (CompatitionLevelModel) result.body();
                    assert loginModal != null;
                    competitionLevelArrayList.clear();
                    if (!loginModal.getError()) {
                        Gson gson = new GsonBuilder().setLenient().create();
                        String data = gson.toJson(loginModal);
                        Log.e("Login", ".." + data);
                        //  Alerts.show(mContext, loginModal.getMessage());
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            tvCompatitionName.setText(Html.fromHtml(loginModal.getCompetition().get(0).getCompetitionName(), Html.FROM_HTML_MODE_COMPACT));
                        } else {
                            tvCompatitionName.setText(Html.fromHtml(loginModal.getCompetition().get(0).getCompetitionName()));
                        }
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            tvCompatitionDetail.setText(Html.fromHtml(loginModal.getCompetition().get(0).getCompetitionDescription(), Html.FROM_HTML_MODE_COMPACT));
                        } else {
                            tvCompatitionDetail.setText(Html.fromHtml(loginModal.getCompetition().get(0).getCompetitionDescription()));
                        }
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            tvCompatitionRules.setText(Html.fromHtml(loginModal.getCompetition().get(0).getCompetitionRules(), Html.FROM_HTML_MODE_COMPACT));
                        } else {
                            tvCompatitionRules.setText(Html.fromHtml(loginModal.getCompetition().get(0).getCompetitionRules()));
                        }

                        String s = loginModal.getCompetition().get(0).getCompetitionDuration();
                        String[] data1 = s.split("-", 2);
                        String fDate = data1[0];
                        String lDate = data1[1];

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

                        tvCompatitionDuration.setText("Start Date " + sDate + " \n\nEnd Date " + eDate);

                        competitionLevelArrayList.addAll(loginModal.getCompetition().get(0).getCompetitionLevel());

                    } else {
                        Alerts.show(mContext, loginModal.getMessage());
                    }
                    sectionListDataAdapter.notifyDataSetChanged();
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
            case R.id.tvApply:
                int pos = Integer.parseInt(view.getTag().toString());
                CompetitionLevel competitionLevel = competitionLevelArrayList.get(pos);
                Intent intent = new Intent(CompatitonLevelActivity.this, CompationDetailActivity.class);
                intent.putExtra("CompatitonLevel", (Parcelable) competitionLevel);
                intent.putExtra("CompationId", compationId);
                intent.putExtra("CompatitonLevelId", competitionLevelArrayList.get(pos).getCompetitionLevelId());
                intent.putExtra("CompatitonLevelName", competitionLevelArrayList.get(pos).getCompetitionLevelName());
                intent.putExtra("CompatitonLevelDetail", competitionLevelArrayList.get(pos).getCompetitionLevelDescription());
                intent.putExtra("CompatitonLevelDuretion", competitionLevelArrayList.get(pos).getCompetitionLevelDuration());
                intent.putExtra("CompatitonLevelResult", competitionLevelArrayList.get(pos).getCompetitionLevelResult());
                intent.putExtra("CompatitonLevelPaymentType", competitionLevelArrayList.get(pos).getCompetitionLevelPaymentType());
                intent.putExtra("CompatitonLevelPaymentAmount", competitionLevelArrayList.get(pos).getCompetitionLevelAmount());
                intent.putExtra("CompatitonLevelPaymentContentType", competitionLevelArrayList.get(pos).getCompetitionLevelContentType());
                intent.putExtra("CompatitonLevelPaymentContentRules", competitionLevelArrayList.get(pos).getCompetitionLevelRules());
                startActivity(intent);
                break;
        }
    }
}
