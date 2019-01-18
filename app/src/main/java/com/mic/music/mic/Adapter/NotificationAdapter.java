package com.mic.music.mic.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mic.music.mic.R;
import com.mic.music.mic.VideoUpload.Activity_galleryview;
import com.mic.music.mic.VideoUpload.Adapter_VideoFolder;
import com.mic.music.mic.VideoUpload.Model_Video;
import com.mic.music.mic.model.notification;

import java.util.ArrayList;

public class NotificationAdapter  extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    ArrayList<notification> notificationArrayList;
    Context context;


    public NotificationAdapter() {
    }

    public NotificationAdapter(Context context, ArrayList<notification> notificationArrayList) {

        this.notificationArrayList = notificationArrayList;
        this.context = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView title_tv,discription_tv;
        public ViewHolder(View v) {
            super(v);

            title_tv = (TextView)v.findViewById(R.id.title_tv);
            discription_tv = (TextView)v.findViewById(R.id.title_description);

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_notification, parent, false);

        ViewHolder viewHolder1 = new ViewHolder(view);

        return viewHolder1;
    }

    @Override
    public void onBindViewHolder(final ViewHolder Vholder, final int position) {

        Vholder.title_tv.setText(notificationArrayList.get(position).getTitle());
        Vholder.discription_tv.setText(notificationArrayList.get(position).getDescription());

    }

    @Override
    public int getItemCount() {

        return notificationArrayList.size();
    }
}
