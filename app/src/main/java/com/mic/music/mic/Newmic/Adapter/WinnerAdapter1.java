package com.mic.music.mic.Newmic.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mic.music.mic.R;
import com.mic.music.mic.model.user_responce.CompetitionContent;
import com.mic.music.mic.model.winner_responce.Participation;

import java.util.ArrayList;

public class WinnerAdapter1 extends RecyclerView.Adapter<WinnerAdapter1.SingleItemRowHolder> {
    private View.OnClickListener onClickListener;

    private ArrayList<Participation> itemsList;
    private Context mContext;

    public WinnerAdapter1(Context context, ArrayList<Participation> itemsList, View.OnClickListener onClickListener) {
        this.itemsList = itemsList;
        this.mContext = context;
        this.onClickListener = onClickListener;
    }

    public WinnerAdapter1(Context context, ArrayList<Participation> itemsList) {
        this.itemsList = itemsList;
        this.mContext = context;
    }

    @Override
    public SingleItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_my_video, null);
        SingleItemRowHolder mh = new SingleItemRowHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(SingleItemRowHolder holder, int i) {

        Participation singleItem = itemsList.get(i);

       /* Glide.with(mContext).load(singleItem.getCompetitionContentId()).into(holder.ivVideo);

        holder.videoBtn.setTag(i);
        holder.videoBtn.setOnClickListener(onClickListener);*/


        holder.tvName.setText(singleItem.getName());
        holder.tvCompetitionName.setText(singleItem.getCompetition());
        holder.tvRank.setText(singleItem.getResultStatus());
    }

    @Override
    public int getItemCount() {
        return (null != itemsList ? itemsList.size() : 0);
    }

    public class SingleItemRowHolder extends RecyclerView.ViewHolder {
        public TextView tvName,tvCompetitionName,tvRank;
        public SingleItemRowHolder(View v) {
            super(v);
            tvName = (TextView)v.findViewById(R.id.tvName1);
            tvCompetitionName = (TextView)v.findViewById(R.id.tvCompetitionName1);
            tvRank = (TextView)v.findViewById(R.id.tvRank1);

        }
    }
}
