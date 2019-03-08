package com.mic.music.mic.Newmic.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.mic.music.mic.R;
import com.mic.music.mic.constant.Constant;
import com.mic.music.mic.model.user_responce.CompetitionContent;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyVideoAdapter extends RecyclerView.Adapter<MyVideoAdapter.SingleItemRowHolder> {
    private View.OnClickListener onClickListener;

    private ArrayList<CompetitionContent> itemsList;
    private Context mContext;

    public MyVideoAdapter(Context context, ArrayList<CompetitionContent> itemsList, View.OnClickListener onClickListener) {
        this.itemsList = itemsList;
        this.mContext = context;
        this.onClickListener = onClickListener;
    }

    public MyVideoAdapter(Context context, ArrayList<CompetitionContent> itemsList) {
        this.itemsList = itemsList;
        this.mContext = context;
    }

    @Override
    public SingleItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_my_video, null);
        SingleItemRowHolder mh = new SingleItemRowHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(SingleItemRowHolder holder, int i) {
        CompetitionContent singleItem = itemsList.get(i);
        if (singleItem.getCompetitionContentType().equals("video")) {
            Picasso.get()
                    .load(Constant.VIDEO_URL + itemsList.get(i).getCompetitionContentUrl())
                    .networkPolicy(NetworkPolicy.NO_CACHE).into(holder.ivVideo);
        } else {
            holder.ivVideo.setImageResource(R.drawable.hmike);
        }
        holder.videoBtn.setTag(i);
        holder.videoBtn.setOnClickListener(onClickListener);
    }

    @Override
    public int getItemCount() {
        return (null != itemsList ? itemsList.size() : 0);
    }

    public class SingleItemRowHolder extends RecyclerView.ViewHolder {
        protected ImageView ivVideo;
        RelativeLayout videoBtn;

        public SingleItemRowHolder(View view) {
            super(view);
            this.ivVideo = (ImageView) view.findViewById(R.id.ivVideo);
            this.videoBtn = (RelativeLayout) view.findViewById(R.id.videoBtn);

        }
    }
}
