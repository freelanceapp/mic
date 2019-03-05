package com.mic.music.mic.Newmic.Activity;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.mic.music.mic.Adapter.PerformanceListAdapter;
import com.mic.music.mic.Newmic.Adapter.CompatitionLevelPerformanceAdapter;
import com.mic.music.mic.R;
import com.mic.music.mic.model.compatition_level_rank_responce.CompatitionLevelRankModel;
import com.mic.music.mic.model.compatition_level_rank_responce.Participant;
import com.mic.music.mic.model.graph_modal.GraphMainModal;
import com.mic.music.mic.model.graph_modal.PerformanceList;
import com.mic.music.mic.retrofit_provider.RetrofitService;
import com.mic.music.mic.retrofit_provider.WebResponse;
import com.mic.music.mic.utils.Alerts;
import com.mic.music.mic.utils.BaseActivity;
import com.mic.music.mic.utils.ConnectionDetector;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

import static com.mic.music.mic.Newmic.Activity.HomeActivity.user_id;

public class RankLevelActivity extends BaseActivity {

    String compatitionLevelId, compatitonLevelContentType;
    RecyclerView recyclerViewPerformanceList;
    CompatitionLevelPerformanceAdapter adapter;
    private List<Participant> performanceLists = new ArrayList<>();

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
        adapter = new CompatitionLevelPerformanceAdapter(mContext, performanceLists);
        recyclerViewPerformanceList.setAdapter(adapter);

        graphDataApi();
    }


    private void graphDataApi() {
        if (cd.isNetworkAvailable()) {
            RetrofitService.getCompatitionLevelData(new Dialog(mContext), retrofitApiClient.getCompatitionRank(compatitionLevelId , user_id), new WebResponse() {
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
                    adapter.notifyDataSetChanged();
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

}
