package com.mic.music.mic.Newmic.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mic.music.mic.Newmic.Activity.CompationDetailActivity;
import com.mic.music.mic.Newmic.AudioVedio;
import com.mic.music.mic.R;
import com.mic.music.mic.model.compation_level_responce.CompetitionLevel;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class SectionListDataAdapter extends RecyclerView.Adapter<SectionListDataAdapter.SingleItemRowHolder> {
    private View.OnClickListener onClickListener;

    private ArrayList<CompetitionLevel> itemsList;
    private Context mContext;

    public SectionListDataAdapter(Context context, ArrayList<CompetitionLevel> itemsList, View.OnClickListener onClickListener) {
        this.itemsList = itemsList;
        this.mContext = context;
        this.onClickListener = onClickListener;
    }

    public SectionListDataAdapter(Context context, ArrayList<CompetitionLevel> itemsList) {
        this.itemsList = itemsList;
        this.mContext = context;
    }

    @Override
    public SingleItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_single_card, null);
        SingleItemRowHolder mh = new SingleItemRowHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(SingleItemRowHolder holder, int i) {

        CompetitionLevel singleItem = itemsList.get(i);
        holder.tvLevelName.setText(singleItem.getCompetitionLevelName());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            holder.tvLevelDetail.setText(Html.fromHtml(singleItem.getCompetitionLevelDescription(), Html.FROM_HTML_MODE_COMPACT));
        } else {
            holder.tvLevelDetail.setText(Html.fromHtml(singleItem.getCompetitionLevelDescription()));
        }
        //holder.tvLevelDetail.setText(singleItem.getCompetitionLevelDescription());
        holder.tvLevelDuretion.setText(singleItem.getCompetitionLevelDuration());
        holder.tvLevelAmount.setText(singleItem.getCompetitionLevelAmount());
        holder.tvLevelAmountType.setText(singleItem.getCompetitionLevelPaymentType());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            holder.tvLevelRules.setText(Html.fromHtml(singleItem.getCompetitionLevelRules(), Html.FROM_HTML_MODE_COMPACT));
        } else {
            holder.tvLevelRules.setText(Html.fromHtml(singleItem.getCompetitionLevelRules()));
        }
        //holder.tvLevelRules.setText(singleItem.getCompetitionLevelRules());
        holder.tvLevelResult.setText(singleItem.getCompetitionLevelResult());
        holder.tvApply.setTag(i);
        holder.tvApply.setOnClickListener(onClickListener);
    }

    @Override
    public int getItemCount() {
        return (null != itemsList ? itemsList.size() : 0);
    }

    public class SingleItemRowHolder extends RecyclerView.ViewHolder  {
        protected TextView tvLevelName, tvLevelDetail, tvLevelDuretion, tvLevelAmount, tvLevelAmountType, tvLevelRules, tvLevelResult, tvApply;
        protected LinearLayout levelBtn;

        public SingleItemRowHolder(View view) {
            super(view);
            this.levelBtn = (LinearLayout) view.findViewById(R.id.levelBtn);
            this.tvLevelName = (TextView) view.findViewById(R.id.tvLevelName);
            this.tvLevelDetail = (TextView) view.findViewById(R.id.tvLevelDetail);
            this.tvLevelDuretion = (TextView) view.findViewById(R.id.tvLevelDuretion);
            this.tvLevelAmount = (TextView) view.findViewById(R.id.tvLevelAmount);
            this.tvLevelAmountType = (TextView) view.findViewById(R.id.tvLevelAmountType);
            this.tvLevelRules = (TextView) view.findViewById(R.id.tvLevelRules);
            this.tvLevelResult = (TextView) view.findViewById(R.id.tvLevelResult);
            this.tvApply = (TextView) view.findViewById(R.id.tvApply);

          /*  tvApply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, CompationDetailActivity.class);
                    intent.putExtra("CompatitonLevelId", itemsList.get(getAdapterPosition()).getCompetitionLevelId());
                    intent.putExtra("CompatitonLevelName", itemsList.get(getAdapterPosition()).getCompetitionLevelName());
                    intent.putExtra("CompatitonLevelDetail", itemsList.get(getAdapterPosition()).getCompetitionLevelDescription());
                    intent.putExtra("CompatitonLevelDuretion", itemsList.get(getAdapterPosition()).getCompetitionLevelDuration());
                    intent.putExtra("CompatitonLevelResult", itemsList.get(getAdapterPosition()).getCompetitionLevelResult());
                    intent.putExtra("CompatitonLevelPaymentType", itemsList.get(getAdapterPosition()).getCompetitionLevelPaymentType());
                    intent.putExtra("CompatitonLevelPaymentAmount", itemsList.get(getAdapterPosition()).getCompetitionLevelAmount());
                    intent.putExtra("CompatitonLevelPaymentContentType", itemsList.get(getAdapterPosition()).getCompetitionLevelContentType());
                    intent.putExtra("CompatitonLevelPaymentContentRules", itemsList.get(getAdapterPosition()).getCompetitionLevelRules());

                    mContext.startActivity(intent);
                }
            });*/
        }

    }
}
