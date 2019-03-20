package com.mic.music.mic.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.mic.music.mic.R;
import com.mic.music.mic.model.judgement_responce.Judgement;
import com.mic.music.mic.model.notification_responce.Notification;

import java.util.ArrayList;

public class JudgementAdapter extends RecyclerView.Adapter<JudgementAdapter.ViewHolder> {

    public static ArrayList<Judgement> notificationArrayList;
    Context context;

    public  String strCompatition;
    public  String strCompatitionLevel;
    public  String strJugmentScore;
    public  String strJugmentComment;
    public  String strJugmentName;
    public  String strJugmentDate;
    public  String strImage;

    public JudgementAdapter() {
    }

    public JudgementAdapter(Context context, ArrayList<Judgement> notificationArrayList) {

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
            strCompatition = notificationArrayList.get(getAdapterPosition()).getCompetition();
            strCompatitionLevel = notificationArrayList.get(getAdapterPosition()).getCompetitionLevel();
            strJugmentScore = notificationArrayList.get(getAdapterPosition()).getJudgementJudgeScore();
            strJugmentComment = notificationArrayList.get(getAdapterPosition()).getJudgementJudgeComment();
            strJugmentName = notificationArrayList.get(getAdapterPosition()).getJudge();
            strJugmentDate = notificationArrayList.get(getAdapterPosition()).getJudgementDate();
            //strDetail = notificationArrayList.get(getAdapterPosition()).getNotificationMessage();
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
        Judgement notification = notificationArrayList.get(position);
        Vholder.title_tv.setText(notification.getCompetitionLevel());
        Vholder.discription_tv.setText(notification.getJudgementJudgeComment());

    }


    @Override
    public int getItemCount() {

        return notificationArrayList.size();
    }


    public  void showDialog() {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_judgement);

        TextView tvDialogCompatitionName = (TextView)dialog.findViewById(R.id.tvDialogCompatitionName);
        TextView tvDialogCompatitionLevelName = (TextView)dialog.findViewById(R.id.tvDialogCompatitionLevelName);
        TextView tvJugmentScore = (TextView)dialog.findViewById(R.id.tvJugmentScore);
        TextView tvJugmentComment = (TextView)dialog.findViewById(R.id.tvJugmentComment);
        TextView tvJugmentName = (TextView)dialog.findViewById(R.id.tvJugmentName);
        TextView tvJugmentDate = (TextView)dialog.findViewById(R.id.tvJugmentDate);


        ImageView btnNotificationCancle = (ImageView)dialog.findViewById(R.id.btnNotificationCancle);

        tvDialogCompatitionName.setText(strCompatition);
        tvDialogCompatitionLevelName.setText(strCompatitionLevel);
        tvJugmentScore.setText("Score : "+strJugmentScore);
        tvJugmentComment.setText("Comment : "+strJugmentComment);
        tvJugmentName.setText(strJugmentName);
        tvJugmentDate.setText(strJugmentDate);
       // Picasso.get().load(strImage).placeholder(R.drawable.notification1).error(R.drawable.notification1).into(ivNotification);

        btnNotificationCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });
        dialog.show();
    }

}
