package com.online.music.mic.Newmic.Activity;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.online.music.mic.Newmic.Adapter.CompatitionLevelPerformanceAdapter;
import com.online.music.mic.R;
import com.online.music.mic.model.compatition_level_rank_responce.CompatitionLevelRankModel;
import com.online.music.mic.model.compatition_level_rank_responce.Participant;
import com.online.music.mic.retrofit_provider.RetrofitService;
import com.online.music.mic.retrofit_provider.WebResponse;
import com.online.music.mic.utils.Alerts;
import com.online.music.mic.utils.BaseActivity;
import com.online.music.mic.utils.ConnectionDetector;
import com.online.music.mic.utils.PriceProductSorter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import retrofit2.Response;

import static com.online.music.mic.Newmic.Activity.HomeActivity.user_id;

public class RankLevelActivity extends BaseActivity {

    private String compatitionLevelId;
    private RecyclerView recyclerViewPerformanceList;
    private CompatitionLevelPerformanceAdapter adapter;
    private List<Participant> performanceLists = new ArrayList<>();
    private List<Participant> filterLists = new ArrayList<>();
    private List<Participant> shortLists = new ArrayList<>();
    private ImageView iv_top;
    SwipeRefreshLayout mSwipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank_level);

        compatitionLevelId = getIntent().getStringExtra("CompatitonLevelId");
        mContext = this;
        cd = new ConnectionDetector(mContext);
        retrofitRxClient = RetrofitService.getRxClient();
        retrofitApiClient = RetrofitService.getRetrofit();

        recyclerViewPerformanceList = findViewById(R.id.recyclerViewPerformanceList1);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        recyclerViewPerformanceList.setLayoutManager(mLayoutManager);
        recyclerViewPerformanceList.setItemAnimator(new DefaultItemAnimator());
        adapter = new CompatitionLevelPerformanceAdapter(mContext, filterLists);
        recyclerViewPerformanceList.setAdapter(adapter);
        iv_top = findViewById(R.id.iv_top);
        Glide.with(this).load(R.drawable.giphy).into(iv_top);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeToRefresh);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                graphDataApi();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
        graphDataApi();
    }

    private void graphDataApi() {
        if (cd.isNetworkAvailable()) {
            RetrofitService.getCompatitionLevelData(new Dialog(mContext), retrofitApiClient.getCompatitionRank(compatitionLevelId, user_id), new WebResponse() {
                @Override
                public void onResponseSuccess(Response<?> result) {
                    CompatitionLevelRankModel graphMainModal = (CompatitionLevelRankModel) result.body();
                    performanceLists.clear();
                    if (graphMainModal != null) {
                        if (graphMainModal.getCompetitionLevel().getParticipant().size() > 0) {
                            performanceLists.addAll(graphMainModal.getCompetitionLevel().getParticipant());

                        } else {
                            Alerts.show(mContext, "No graph data!!!");
                        }
                    } else {
                        Alerts.show(mContext, "No data!!!");
                    }

                    filterData();
                }

                @Override
                public void onResponseFailed(String error) {
                    //Alerts.show(mContext, error);
                }
            });
        } else {
            cd.show(mContext);
        }
    }

    private void filterData() {
        filterLists.clear();
        if (performanceLists.size() > 0) {
            if (performanceLists.size() < 4) {
                filterLists.addAll(performanceLists);
            } else {
                for (int i = 0; i < 3; i++) {
                    filterLists.add(performanceLists.get(i));
                }
            }

            for (int j = 0; j < filterLists.size(); j++) {
                if (!user_id.equals(filterLists.get(j).getParticipantId())) {
                    for (int k = 0; k < performanceLists.size(); k++) {
                        if (user_id.equals(performanceLists.get(k).getParticipantId())) {
                            filterLists.add(performanceLists.get(k));
                            break;
                        }
                    }
                    break;
                }
                break;
            }
        }

        HashSet<Participant> hashSet = new HashSet<>();
        hashSet.addAll(filterLists);
        filterLists.clear();
        filterLists.addAll(hashSet);

        System.out.println("-----Sorted JobCandidate by name: Ascending-----");
        PriceProductSorter nameProductSorter = new PriceProductSorter(filterLists);
        List<Participant> sortedJobCandidate = nameProductSorter.getSortedProductByHightoLow();
        shortLists.clear();
        for (Participant jobCandidate : sortedJobCandidate) {
            Log.e("Name", jobCandidate.getParticipantName());
            Log.e("Price", jobCandidate.getScore());
            shortLists.add(jobCandidate);
        }
        filterLists.clear();
        filterLists.addAll(shortLists);



        adapter.notifyDataSetChanged();
    }


}
