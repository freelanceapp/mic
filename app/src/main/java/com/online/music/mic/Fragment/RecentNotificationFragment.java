package com.online.music.mic.Fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.online.music.mic.Adapter.JudgementAdapter;
import com.online.music.mic.R;
import com.online.music.mic.constant.Constant;
import com.online.music.mic.model.judgement_responce.Judgement;
import com.online.music.mic.model.judgement_responce.JudgementModel;
import com.online.music.mic.retrofit_provider.RetrofitService;
import com.online.music.mic.retrofit_provider.WebResponse;
import com.online.music.mic.utils.Alerts;
import com.online.music.mic.utils.AppPreference;
import com.online.music.mic.utils.BaseFragment;
import com.online.music.mic.utils.ConnectionDetector;

import java.util.ArrayList;

import retrofit2.Response;

public class RecentNotificationFragment extends BaseFragment {
    RecyclerView recent_list;
    ArrayList<Judgement> judgementArrayList = new ArrayList<>();
    JudgementAdapter adapter;
    ProgressBar notification_progress;
    public RecentNotificationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recent_notification, container, false);
        recent_list = (RecyclerView)view.findViewById(R.id.recent_list);
        notification_progress = (ProgressBar)view.findViewById(R.id.notification_progress);

        mContext = getActivity();
        activity = getActivity();
        cd = new ConnectionDetector(mContext);
        retrofitRxClient = RetrofitService.getRxClient();
        retrofitApiClient = RetrofitService.getRetrofit();

        adapter = new JudgementAdapter(getActivity(),judgementArrayList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recent_list.setLayoutManager(mLayoutManager);
        recent_list.setItemAnimator(new DefaultItemAnimator());
        recent_list.setAdapter(adapter);

        getNotificatio();
        return view;
    }

    private void getNotificatio() {
        if (cd.isNetworkAvailable()) {

            String strUserIs = AppPreference.getStringPreference(mContext, Constant.User_Id);
            RetrofitService.getJugment(new Dialog(mContext), retrofitApiClient.getJugment(strUserIs), new WebResponse() {
                @Override
                public void onResponseSuccess(Response<?> result) {
                    JudgementModel loginModal = (JudgementModel) result.body();
                    assert loginModal != null;

                    judgementArrayList.clear();
                    if (!loginModal.getError()) {
                        Alerts.show(mContext, loginModal.getMessage());

                        judgementArrayList.addAll(loginModal.getJudgement());
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
