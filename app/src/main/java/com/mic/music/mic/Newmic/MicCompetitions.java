package com.mic.music.mic.Newmic;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mic.music.mic.Newmic.Adapter.CompetitionsAdapter;
import com.mic.music.mic.R;
import com.mic.music.mic.constant.Constant;
import com.mic.music.mic.model.Compatition1;
import com.mic.music.mic.model.competition_responce.Competition;
import com.mic.music.mic.model.competition_responce.CompletionModel;
import com.mic.music.mic.retrofit_provider.RetrofitService;
import com.mic.music.mic.retrofit_provider.WebResponse;
import com.mic.music.mic.utils.Alerts;
import com.mic.music.mic.utils.AppPreference;
import com.mic.music.mic.utils.BaseFragment;
import com.mic.music.mic.utils.ConnectionDetector;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Response;

public class MicCompetitions extends BaseFragment implements View.OnClickListener {

    private View view;
    private ArrayList<Competition> arrayList = new ArrayList<>();
    private ArrayList<Competition> onGoingList = new ArrayList<>();
    private ArrayList<Competition> upComingList = new ArrayList<>();
    private ArrayList<Competition> closeList = new ArrayList<>();
    private ArrayList<Competition> getdataList = new ArrayList<>();
    private CompetitionsAdapter adapter;
    private RecyclerView rvCompetitionList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.activity_mic_competitions, container, false);

        init();
        return view;
    }

    public void init() {
        mContext = getActivity();
        activity = getActivity();
        cd = new ConnectionDetector(mContext);
        retrofitRxClient = RetrofitService.getRxClient();
        retrofitApiClient = RetrofitService.getRetrofit();
        rvCompetitionList = (RecyclerView) view.findViewById(R.id.rvCompetitionList);
        adapter = new CompetitionsAdapter(getActivity(), getdataList, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        rvCompetitionList.setLayoutManager(mLayoutManager);
        rvCompetitionList.setItemAnimator(new DefaultItemAnimator());
        rvCompetitionList.setAdapter(adapter);

        RadioGroup rg = (RadioGroup) view.findViewById(R.id.rgCompatition);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                switch(checkedId)
                {
                    case R.id.rd_ongoing:
                        getdataList.clear();
                        getdataList.addAll(onGoingList);
                        adapter.notifyDataSetChanged();
                        break;
                    case R.id.rd_upcoming:
                        getdataList.clear();
                        getdataList.addAll(upComingList);
                        adapter.notifyDataSetChanged();
                        break;
                    case R.id.rd_close:
                        getdataList.clear();
                        getdataList.addAll(closeList);
                        adapter.notifyDataSetChanged();
                        break;
                }
            }
        });

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
                        Gson gson = new GsonBuilder().setLenient().create();
                        String data = gson.toJson(loginModal);
                        Log.e("Login", ".." + data);
                        AppPreference.setStringPreference(mContext, Constant.Comptition_Data, data);
                        Compatition1.setCompletionModel(loginModal);
                        //  Alerts.show(mContext, loginModal.getMessage());
                        arrayList.addAll(loginModal.getCompetition());

                        for (int i = 0; i < arrayList.size(); i++) {
                            String s = arrayList.get(i).getCompetitionDuration();
                            String[] data1 = s.split("-", 2);
                            String fDate = data1[0];
                            String lDate = data1[1];
                            Log.e("fDate", fDate);

                            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm aa");
                            Date strDate = new Date(), strEndDate = new Date();
                            try {
                                strDate = sdf.parse(fDate);
                                strEndDate = sdf.parse(lDate);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            if (System.currentTimeMillis() > strDate.getTime() && System.currentTimeMillis() > strEndDate.getTime()) {
                                Log.e("close", "close ");
                                closeList.add(arrayList.get(i));
                            } else if (System.currentTimeMillis() < strDate.getTime()) {
                                upComingList.add(arrayList.get(i));
                            } else if (System.currentTimeMillis() > strDate.getTime() && System.currentTimeMillis() < strEndDate.getTime()) {
                                onGoingList.add(arrayList.get(i));
                                getdataList.add(arrayList.get(i));
                            }
                        }

                        int closeSize = closeList.size();
                        int upcoming = upComingList.size();
                        int ongoing = onGoingList.size();

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
