package com.mic.music.mic.Newmic.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mic.music.mic.Newmic.Activity.CompatitonLevelActivity;
import com.mic.music.mic.Newmic.AudioVedio;
import com.mic.music.mic.Newmic.Fragment.ParticipationDetailFragment;
import com.mic.music.mic.R;
import com.mic.music.mic.constant.Constant;
import com.mic.music.mic.model.competition_responce.Competition;
import com.mic.music.mic.model.competition_responce.CompetitionLevel;
import com.mic.music.mic.model.token_responce.TokenModel;
import com.mic.music.mic.retrofit_provider.RetrofitApiClient;
import com.mic.music.mic.retrofit_provider.RetrofitService;
import com.mic.music.mic.retrofit_provider.WebResponse;
import com.mic.music.mic.utils.Alerts;
import com.mic.music.mic.utils.AppPreference;
import com.mic.music.mic.utils.ButtonSound;
import com.mic.music.mic.utils.ConnectionDetector;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Response;

public class CompetitionsAdapter extends RecyclerView.Adapter<CompetitionsAdapter.ViewHolder> {
    private View.OnClickListener onClickListener;
    ArrayList<Competition> competitionArrayList;
    public RetrofitApiClient retrofitApiClient;
    public RetrofitApiClient retrofitRxClient;
    public ConnectionDetector cd;
    Context context;
    int num1 = 0;
    String competitionId, competitionLevelId, paymentType, UserId;
    String strPayment;

    public CompetitionsAdapter() {
    }

    public CompetitionsAdapter(Context context, ArrayList<Competition> competitionArrayList, View.OnClickListener onClickListener) {
        this.competitionArrayList = competitionArrayList;
        this.context = context;
        this.onClickListener = onClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvCompetitionName, tvCompetitionDetail, tvExpDate, tvCompetitionLevelDetail;
        public ImageView imgArrowA;
        public LinearLayout competitionll,ll_rclv_competitions;
        protected RecyclerView recycler_view_list;
        public ViewHolder(View v) {
            super(v);
            this.recycler_view_list = (RecyclerView) v.findViewById(R.id.recycler_view_list);
            ll_rclv_competitions = (LinearLayout) v.findViewById(R.id.ll_rclv_competition);
            tvCompetitionName = (TextView) v.findViewById(R.id.tvCompetitionName);
            tvCompetitionDetail = (TextView) v.findViewById(R.id.tvCompetitionDetail);
            tvExpDate = (TextView) v.findViewById(R.id.tvExpDate);
            tvCompetitionLevelDetail = (TextView) v.findViewById(R.id.tvCompetitionLevelDetail);
            imgArrowA = (ImageView) v.findViewById(R.id.imgArrowA);
            ll_rclv_competitions.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                        Intent intent = new Intent(context, CompatitonLevelActivity.class);
                        intent.putExtra("Compation_id", competitionArrayList.get(getAdapterPosition()).getCompetitionId());
                        context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public CompetitionsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_competition_list, parent, false);
        CompetitionsAdapter.ViewHolder viewHolder1 = new CompetitionsAdapter.ViewHolder(view);
        return viewHolder1;
    }

    @Override
    public void onBindViewHolder(final CompetitionsAdapter.ViewHolder Vholder, final int position) {
        Vholder.tvCompetitionName.setText(competitionArrayList.get(position).getCompetitionName());


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Vholder.tvCompetitionDetail.setText(Html.fromHtml(competitionArrayList.get(position).getCompetitionDescription(), Html.FROM_HTML_MODE_COMPACT));
        } else {
            Vholder.tvCompetitionDetail.setText(Html.fromHtml(competitionArrayList.get(position).getCompetitionDescription()));
        }

        //Vholder.tvCompetitionDetail.setText(competitionArrayList.get(position).getCompetitionDescription());
        Vholder.tvExpDate.setText(competitionArrayList.get(position).getCompetitionDuration());

    }

    @Override
    public int getItemCount() {
        return competitionArrayList.size();
    }



}
