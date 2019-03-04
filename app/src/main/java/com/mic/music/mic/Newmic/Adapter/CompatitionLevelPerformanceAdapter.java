package com.mic.music.mic.Newmic.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mic.music.mic.R;
import com.mic.music.mic.model.compatition_level_rank_responce.Participant;
import com.mic.music.mic.model.graph_modal.PerformanceList;

import java.util.List;

public class CompatitionLevelPerformanceAdapter extends RecyclerView.Adapter<CompatitionLevelPerformanceAdapter.ViewHolder> {

    private List<Participant> performanceLists;
    private Context context;

    public CompatitionLevelPerformanceAdapter(Context context, List<Participant> performanceLists) {
        this.performanceLists = performanceLists;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView  tvCPerformerName, tvCPosition, tvCTime;

        public ViewHolder(View v) {
            super(v);
            tvCPerformerName = (TextView) v.findViewById(R.id.tvCPerformerName);
            tvCPosition = (TextView) v.findViewById(R.id.tvCPosition);
            tvCTime = (TextView) v.findViewById(R.id.tvCTime);
        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_compatition_performance_list, parent, false);
        ViewHolder viewHolder1 = new ViewHolder(view);
        return viewHolder1;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        viewHolder.tvCPerformerName.setText(performanceLists.get(position).getParticipantName());
        viewHolder.tvCPosition.setText("" + performanceLists.get(position).getScore());
        viewHolder.tvCTime.setText("" + performanceLists.get(position).getContentUploadedDate());
    }

    @Override
    public int getItemCount() {
        return performanceLists.size();
    }
}
