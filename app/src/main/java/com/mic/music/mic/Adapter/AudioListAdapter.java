package com.mic.music.mic.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mic.music.mic.R;
import com.mic.music.mic.model.AudioModel;
import com.mic.music.mic.model.event;

import java.util.ArrayList;

public class AudioListAdapter extends RecyclerView.Adapter<AudioListAdapter.ViewHolder> {

    ArrayList<AudioModel> eventArrayList;
    Context context;


    public AudioListAdapter() {
    }

    public AudioListAdapter(Context context, ArrayList<AudioModel> eventArrayList) {

        this.eventArrayList = eventArrayList;
        this.context = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvName,tvPath,tvAlbum,tvArtist;
        public ViewHolder(View v) {
            super(v);

            tvName = (TextView)v.findViewById(R.id.tvName);
            tvPath = (TextView)v.findViewById(R.id.tvPath);
            tvAlbum = (TextView)v.findViewById(R.id.tvAlbum);
            tvArtist = (TextView)v.findViewById(R.id.tvArtist);

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_audio_layout, parent, false);

        ViewHolder viewHolder1 = new ViewHolder(view);

        return viewHolder1;
    }

    @Override
    public void onBindViewHolder(final ViewHolder Vholder, final int position) {

        Vholder.tvName.setText(eventArrayList.get(position).getaName());
        Vholder.tvPath.setText(eventArrayList.get(position).getaPath());
        Vholder.tvAlbum.setText(eventArrayList.get(position).getaAlbum());
        Vholder.tvArtist.setText(eventArrayList.get(position).getaArtist());

    }

    @Override
    public int getItemCount() {

        return eventArrayList.size();
    }
}
