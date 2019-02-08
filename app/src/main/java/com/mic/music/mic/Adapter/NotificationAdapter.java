package com.mic.music.mic.Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mic.music.mic.Newmic.Activity.MainActivity;
import com.mic.music.mic.R;
import com.mic.music.mic.VideoUpload.Activity_galleryview;
import com.mic.music.mic.VideoUpload.Adapter_VideoFolder;
import com.mic.music.mic.VideoUpload.Model_Video;
import com.mic.music.mic.model.notification;
import com.mic.music.mic.model.notification_responce.Notification;

import java.util.ArrayList;

public class NotificationAdapter  extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    public static ArrayList<Notification> notificationArrayList;
    Context context;

    public  String strName;
    public  String strDetail;

    public NotificationAdapter() {
    }

    public NotificationAdapter(Context context, ArrayList<Notification> notificationArrayList) {

        this.notificationArrayList = notificationArrayList;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView title_tv,discription_tv;
        public ViewHolder(View v) {
            super(v);

            title_tv = (TextView)v.findViewById(R.id.title_tv);
            discription_tv = (TextView)v.findViewById(R.id.title_description);

            v.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            strName = notificationArrayList.get(getAdapterPosition()).getNotificationTitle();
            strDetail = notificationArrayList.get(getAdapterPosition()).getNotificationMessage();
            showDialog();
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
        Notification notification = notificationArrayList.get(position);
        Vholder.title_tv.setText(notification.getNotificationTitle());
        Vholder.discription_tv.setText(notification.getNotificationMessage());

    }


    @Override
    public int getItemCount() {

        return notificationArrayList.size();
    }


    public  void showDialog() {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_notification);

        TextView tvNotificationName = (TextView)dialog.findViewById(R.id.tvNotificationName);
        TextView tvNotificatioDatail = (TextView)dialog.findViewById(R.id.tvNotificatioDatail);
        ImageView btnNotificationCancle = (ImageView)dialog.findViewById(R.id.btnNotificationCancle);

        tvNotificationName.setText(strName);
        tvNotificatioDatail.setText(strDetail);

        btnNotificationCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });
        dialog.show();
    }

}
