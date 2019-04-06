package com.online.music.mic.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.online.music.mic.R;
import com.online.music.mic.model.event;

import java.util.ArrayList;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {

    ArrayList<event> eventArrayList;
    Context context;


    public EventAdapter() {
    }

    public EventAdapter(Context context, ArrayList<event> eventArrayList) {

        this.eventArrayList = eventArrayList;
        this.context = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView event_title,event_date,sevent_date,event_description,event_orgnization;
        public ViewHolder(View v) {
            super(v);

            event_title = (TextView)v.findViewById(R.id.event_title);
            event_date = (TextView)v.findViewById(R.id.event_time);
            event_description = (TextView)v.findViewById(R.id.event_description);
            sevent_date = (TextView)v.findViewById(R.id.sevent_time);
            event_orgnization = (TextView)v.findViewById(R.id.event_organizer);

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_event, parent, false);

        ViewHolder viewHolder1 = new ViewHolder(view);

        return viewHolder1;
    }

    @Override
    public void onBindViewHolder(final ViewHolder Vholder, final int position) {

        Vholder.event_title.setText(eventArrayList.get(position).getTitle());
        Vholder.event_date.setText(eventArrayList.get(position).getStart_time());
        Vholder.sevent_date.setText(eventArrayList.get(position).getEnd_time());
        Vholder.event_description.setText(eventArrayList.get(position).getDiscription());
        Vholder.event_orgnization.setText(eventArrayList.get(position).getOrganizer());

    }

    @Override
    public int getItemCount() {

        return eventArrayList.size();
    }
}
