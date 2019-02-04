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
import android.widget.ImageView;

import com.google.gson.Gson;
import com.mic.music.mic.Newmic.Activity.HomeActivity;
import com.mic.music.mic.Newmic.Adapter.CompetitionsAdapter;
import com.mic.music.mic.Newmic.Adapter.ParticipationListAdapter;
import com.mic.music.mic.R;
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


public class ParticipationDetailFragment extends BaseActivity implements View.OnClickListener {

    private View view;
    private RecyclerView rvParticipationList;
    private ParticipationListAdapter adapter;
    ArrayList<Participation> participationArrayList = new ArrayList<>();
    private String companyId;
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

    private void init(){

        companyId = getIntent().getStringExtra("companyId");
        rvParticipationList = (RecyclerView)findViewById(R.id.rvParticipationList);

        mContext = this;
        cd = new ConnectionDetector(mContext);
        retrofitRxClient = RetrofitService.getRxClient();
        retrofitApiClient = RetrofitService.getRetrofit();

        selectParticipationApi();

        ivBackBtn = (ImageView)findViewById(R.id.ivBackBtn);
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
        if (cd.isNetworkAvailable()) {
            RetrofitService.getSelectParticipation(new Dialog(mContext), retrofitApiClient.getSelectParticipation("16", "2"), new WebResponse() {
                @Override
                public void onResponseSuccess(Response<?> result) {
                    ParticipationModel loginModal = (ParticipationModel) result.body();
                    assert loginModal != null;
                    if (!loginModal.getError()) {
                        Alerts.show(mContext, loginModal.getMessage());
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
        switch (view.getId())
        {
            case R.id.tvAdminStatus :
                int pos = Integer.parseInt(view.getTag().toString());
                AppPreference.setStringPreference(mContext, Constant.COMPANY_ID, companyId);
                AppPreference.setStringPreference(mContext,Constant.LEVEL_ID, participationArrayList.get(pos).getCompetitionLevel());

                if (participationArrayList.get(pos).getAdminStatus().equals("Active")) {
                    Intent i = new Intent(mContext, HomeActivity.class);
                    startActivity(i);
                    finish();
                }else {
                    Alerts.show(mContext,"Yor are not selected");
                    finish();
                }
                break;
        }
    }
}
