package com.mic.music.mic.Newmic.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.mic.music.mic.R;
import com.mic.music.mic.model.winner_responce.Participation;

import java.util.ArrayList;

public class WinnersAdapter extends RecyclerView.Adapter<WinnersAdapter.ViewHolder> {

    public static ArrayList<Participation> notificationArrayList;
    Context context;

    public WinnersAdapter() {
    }

    public WinnersAdapter(Context context, ArrayList<Participation> notificationArrayList) {

        this.notificationArrayList = notificationArrayList;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView tvName,tvCompetitionName,tvRank;
        public ViewHolder(View v) {
            super(v);

            tvName = (TextView)v.findViewById(R.id.tvName1);
            tvCompetitionName = (TextView)v.findViewById(R.id.tvCompetitionName1);
            tvRank = (TextView)v.findViewById(R.id.tvRank1);
        }

        @Override
        public void onClick(View view) {

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_ranking_list, parent, false);

        ViewHolder viewHolder1 = new ViewHolder(view);

        return viewHolder1;
    }

    @Override
    public void onBindViewHolder(final ViewHolder Vholder, final int position) {

        Vholder.tvName.setText(notificationArrayList.get(position).getName());
        Vholder.tvCompetitionName.setText(notificationArrayList.get(position).getCompetition());
        Vholder.tvRank.setText(notificationArrayList.get(position).getResultStatus());

    }

    @Override
    public int getItemCount() {

        return notificationArrayList.size();
    }

}
