package com.mic.music.mic.Newmic;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mic.music.mic.Adapter.NotificationAdapter;
import com.mic.music.mic.Newmic.Adapter.CompetitionsAdapter;
import com.mic.music.mic.R;
import com.mic.music.mic.model.competition_responce.Competition;
import com.mic.music.mic.model.competition_responce.CompletionModel;
import com.mic.music.mic.model.token_responce.TokenModel;
import com.mic.music.mic.retrofit_provider.RetrofitService;
import com.mic.music.mic.retrofit_provider.WebResponse;
import com.mic.music.mic.utils.Alerts;
import com.mic.music.mic.utils.BaseFragment;
import com.mic.music.mic.utils.ConnectionDetector;

import java.util.ArrayList;

import retrofit2.Response;

public class MicCompetitions extends BaseFragment implements View.OnClickListener {

    private View view;
    private ArrayList<Competition> arrayList = new ArrayList<>();
    private CompetitionsAdapter adapter;
    private RecyclerView rvCompetitionList;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.activity_mic_competitions, container, false);

        init();
        return view;
    }

    public void init()
    {
        mContext = getActivity();
        activity = getActivity();
        cd = new ConnectionDetector(mContext);
        retrofitRxClient = RetrofitService.getRxClient();
        retrofitApiClient = RetrofitService.getRetrofit();

        rvCompetitionList = (RecyclerView)view.findViewById(R.id.rvCompetitionList);

        adapter = new CompetitionsAdapter(getActivity(),arrayList, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        rvCompetitionList.setLayoutManager(mLayoutManager);
        rvCompetitionList.setItemAnimator(new DefaultItemAnimator());
        rvCompetitionList.setAdapter(adapter);

        tokenApi();

    }

    private void tokenApi() {

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

    @Override
    public void onClick(View view) {

    }
}
