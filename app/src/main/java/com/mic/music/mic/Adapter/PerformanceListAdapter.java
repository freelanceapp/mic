package com.mic.music.mic.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mic.music.mic.R;
import com.mic.music.mic.model.graph_modal.PerformanceList;

import java.util.List;

public class PerformanceListAdapter extends RecyclerView.Adapter<PerformanceListAdapter.ViewHolder> {

    private List<PerformanceList> performanceLists;
    private Context context;

    public PerformanceListAdapter(Context context, List<PerformanceList> performanceLists) {
        this.performanceLists = performanceLists;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvSerialNo, tvPerformerName, tvPosition, tvTime;

        public ViewHolder(View v) {
            super(v);
            tvSerialNo = (TextView) v.findViewById(R.id.tvSerialNo);
            tvPerformerName = (TextView) v.findViewById(R.id.tvPerformerName);
            tvPosition = (TextView) v.findViewById(R.id.tvPosition);
            tvTime = (TextView) v.findViewById(R.id.tvTime);
        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_performance_list, parent, false);
        ViewHolder viewHolder1 = new ViewHolder(view);
        return viewHolder1;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        viewHolder.tvPerformerName.setText(performanceLists.get(position).getParticipantName());
        viewHolder.tvPosition.setText("" + performanceLists.get(position).getParticipantPosition());
        viewHolder.tvTime.setText("" + performanceLists.get(position).getTime());

        int serialNo = position + 1;
        viewHolder.tvSerialNo.setText("0" + serialNo);
    }

    @Override
    public int getItemCount() {
        return performanceLists.size();
    }
}
