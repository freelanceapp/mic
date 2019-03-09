package com.mic.music.mic.Newmic.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mic.music.mic.R;
import com.mic.music.mic.constant.Constant;
import com.mic.music.mic.model.Compatition1;
import com.mic.music.mic.model.competition_responce.CompetitionLevel;
import com.mic.music.mic.model.competition_responce.CompletionModel;
import com.mic.music.mic.model.participation_responce.Participation;
import com.mic.music.mic.model.participation_responce.ParticipationModel;
import com.mic.music.mic.utils.AppPreference;

import java.util.ArrayList;

public class ParticipationListAdapter extends RecyclerView.Adapter<ParticipationListAdapter.SingleItemRowHolder> {
    private View.OnClickListener onClickListener;

    private ArrayList<Participation> itemsList;
    private Context mContext;

    public ParticipationListAdapter(Context context, ArrayList<Participation> itemsList, View.OnClickListener onClickListener) {
        this.itemsList = itemsList;
        this.mContext = context;
        this.onClickListener = onClickListener;
    }

    public ParticipationListAdapter(Context context, ArrayList<Participation> itemsList) {
        this.itemsList = itemsList;
        this.mContext = context;
    }

    @Override
    public SingleItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_participation, null);
        SingleItemRowHolder mh = new SingleItemRowHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(SingleItemRowHolder holder, int i) {

        Participation singleItem = itemsList.get(i);

        Gson gson = new Gson();
        String data = AppPreference.getStringPreference(mContext, Constant.Comptition_Data);
        Log.e("Profile ", "..."+data);
        CompletionModel loginModal = gson.fromJson(data, CompletionModel.class);
        Compatition1.setCompletionModel(loginModal);

        CompletionModel completionModel = Compatition1.getCompletionModel();
        for (int k = 0 ; k < completionModel.getCompetition().size() ; k++)
        {
            for (int j = 0 ; j < completionModel.getCompetition().get(k).getCompetitionLevel().size() ; j++)
            {
                if (completionModel.getCompetition().get(k).getCompetitionLevel().get(j).getCompetitionLevelId().equals(singleItem.getCompetitionLevel()))
                {
                    Log.e("Level ID ", ".."+ completionModel.getCompetition().get(k).getCompetitionLevel().get(j).getCompetitionLevelName());
                    holder.tvLevelName.setText(completionModel.getCompetition().get(k).getCompetitionLevel().get(j).getCompetitionLevelName());
                }
            }

        }

        if (singleItem.getType().equals("Free"))
        {
            holder.tvPaymentStatus.setVisibility(View.GONE);
        }

        holder.tvPaymentStatus.setText("Payment Status "+singleItem.getPaymentStatus());
        holder.tvPaymentType.setText("Payment Type "+singleItem.getType());
        holder.tvAdminStatus.setText(singleItem.getAdminStatus());
        holder.tvAdminStatus.setTag(i);
        holder.tvAdminStatus.setOnClickListener(onClickListener);
        holder.tvShowRank.setTag(i);
        holder.tvShowRank.setOnClickListener(onClickListener);
    }

    @Override
    public int getItemCount() {
        return (null != itemsList ? itemsList.size() : 0);
    }

    public class SingleItemRowHolder extends RecyclerView.ViewHolder {
        protected TextView tvLevelName, tvPaymentStatus,  tvAdminStatus , tvPaymentType , tvShowRank;
        protected LinearLayout levelBtn;

        public SingleItemRowHolder(View view) {
            super(view);
            this.tvLevelName = (TextView) view.findViewById(R.id.tvLevelName);
            this.tvPaymentStatus = (TextView) view.findViewById(R.id.tvPaymentStatus);
            this.tvAdminStatus = (TextView) view.findViewById(R.id.tvAdminStatus);
            this.tvPaymentType = (TextView) view.findViewById(R.id.tvPaymentType);
            this.tvShowRank = (TextView) view.findViewById(R.id.tvShowRank);

        }
    }
}
