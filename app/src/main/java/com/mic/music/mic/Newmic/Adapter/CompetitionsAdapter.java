package com.mic.music.mic.Newmic.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mic.music.mic.Adapter.NotificationAdapter;
import com.mic.music.mic.R;
import com.mic.music.mic.model.competition_responce.Competition;
import com.mic.music.mic.model.notification_responce.Notification;

import java.util.ArrayList;

public class CompetitionsAdapter extends RecyclerView.Adapter<CompetitionsAdapter.ViewHolder> {
    private View.OnClickListener onClickListener;

    ArrayList<Competition> competitionArrayList;
    Context context;
    int num1 = 0;
    public CompetitionsAdapter() {
    }

    public CompetitionsAdapter(Context context, ArrayList<Competition> competitionArrayList , View.OnClickListener onClickListener) {
        this.competitionArrayList = competitionArrayList;
        this.context = context;
        this.onClickListener = onClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvCompetitionName,tvCompetitionDetail, tvExpDate, tvCompetitionLevelDetail;
        public ImageView imgArrowA;
        public LinearLayout competitionll;
        public ViewHolder(View v) {
            super(v);
            tvCompetitionName = (TextView)v.findViewById(R.id.tvCompetitionName);
            tvCompetitionDetail = (TextView)v.findViewById(R.id.tvCompetitionDetail);
            tvExpDate = (TextView)v.findViewById(R.id.tvExpDate);
            tvCompetitionLevelDetail = (TextView)v.findViewById(R.id.tvCompetitionLevelDetail);
            imgArrowA = (ImageView)v.findViewById(R.id.imgArrowA);
            competitionll = (LinearLayout)v.findViewById(R.id.competitionll);
            imgArrowA.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (num1 == 0)
                    {
                        competitionll.setVisibility(View.VISIBLE);
                        num1 = 1;
                        imgArrowA.setImageResource(R.drawable.ic_open);
                    }else {
                        competitionll.setVisibility(View.GONE);
                        num1 = 0;
                        imgArrowA.setImageResource(R.drawable.ic_close);
                    }
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
        Vholder.tvCompetitionDetail.setText(competitionArrayList.get(position).getCompetitionDescription());
        Vholder.tvExpDate.setText(competitionArrayList.get(position).getCompetitionDuration());

        for (int i= 0 ; i <competitionArrayList.get(position).getCompetitionLevel().size(); i++)
        {
            Vholder.tvCompetitionLevelDetail.setText("Lavel Name "+competitionArrayList.get(position).getCompetitionLevel().get(i).getCompetitionLevelName()+"\n"+
                    "Lavel Detail "+competitionArrayList.get(position).getCompetitionLevel().get(i).getCompetitionLevelDescription()+"\n"+
                    "Lavel Duration "+competitionArrayList.get(position).getCompetitionLevel().get(i).getCompetitionLevelDuration()+"\n"+
                    "Lavel Result "+competitionArrayList.get(position).getCompetitionLevel().get(i).getCompetitionLevelResult()+"\n"+
                    "Amount "+competitionArrayList.get(position).getCompetitionLevel().get(i).getCompetitionLevelAmount()+"\n"+
                    "Lavel Rules "+competitionArrayList.get(position).getCompetitionLevel().get(i).getCompetitionLevelRules()+"\n\n");
        }

        Vholder.tvCompetitionName.setTag(position);
        Vholder.tvCompetitionName.setOnClickListener(onClickListener);
    }

    @Override
    public int getItemCount() {
        return competitionArrayList.size();
    }
}
