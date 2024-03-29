package com.online.music.mic.Newmic.Adapter;

import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.online.music.mic.R;
import com.online.music.mic.model.compation_level_responce.CompetitionLevel;

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
        }

    }
}
