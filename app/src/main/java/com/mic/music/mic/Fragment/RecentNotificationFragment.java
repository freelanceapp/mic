package com.mic.music.mic.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mic.music.mic.Adapter.JudgementAdapter;
import com.mic.music.mic.Adapter.NotificationAdapter;
import com.mic.music.mic.Api.RequestHandler;
import com.mic.music.mic.Api.URLs;
import com.mic.music.mic.LoginActivity;
import com.mic.music.mic.Newmic.Activity.Mobile_Ragistration;
import com.mic.music.mic.Newmic.Activity.VerificationActivity;
import com.mic.music.mic.OtpActivity;
import com.mic.music.mic.R;
import com.mic.music.mic.constant.Constant;
import com.mic.music.mic.model.judgement_responce.Judgement;
import com.mic.music.mic.model.judgement_responce.JudgementModel;
import com.mic.music.mic.model.login_responce.LoginModel1;
import com.mic.music.mic.model.notification;
import com.mic.music.mic.model.notification_responce.Notification;
import com.mic.music.mic.retrofit_provider.RetrofitService;
import com.mic.music.mic.retrofit_provider.WebResponse;
import com.mic.music.mic.utils.Alerts;
import com.mic.music.mic.utils.AppPreference;
import com.mic.music.mic.utils.BaseFragment;
import com.mic.music.mic.utils.ConnectionDetector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

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
