package com.mic.music.mic.Newmic;

import android.app.Dialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.mic.music.mic.Newmic.Adapter.MyVideoAdapter;
import com.mic.music.mic.Newmic.Fragment.EditProfileFragment;
import com.mic.music.mic.R;
import com.mic.music.mic.model.user_responce.CompetitionContent;
import com.mic.music.mic.model.user_responce.UserProfileModel;
import com.mic.music.mic.retrofit_provider.RetrofitService;
import com.mic.music.mic.retrofit_provider.WebResponse;
import com.mic.music.mic.utils.Alerts;
import com.mic.music.mic.utils.BaseFragment;
import com.mic.music.mic.utils.ConnectionDetector;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Response;

import static com.mic.music.mic.Newmic.Activity.HomeActivity.user_id;
import static com.mic.music.mic.constant.Constant.VIDEO_URL;

public class Profile extends BaseFragment implements View.OnClickListener {

    Fragment fragment;
    private View rootView;
    private ImageView editBtn;
    TextView singernamem, email, contact;
    CircleImageView circleImg;
    MyVideoAdapter adapter;
    ArrayList<CompetitionContent> competitionContentArrayList = new ArrayList<>();
    ArrayList<CompetitionContent> allAudioVideoList = new ArrayList<>();
    ArrayList<CompetitionContent> videoList = new ArrayList<>();
    ArrayList<CompetitionContent> audioList = new ArrayList<>();
    RecyclerView recylerviewgrid;
    SimpleExoPlayer player;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.activity_profile, container, false);
        mContext = getActivity();
        activity = getActivity();
        cd = new ConnectionDetector(mContext);
        retrofitRxClient = RetrofitService.getRxClient();
        retrofitApiClient = RetrofitService.getRetrofit();
        init();

        return rootView;
    }

    private void init() {
        editBtn = (ImageView) rootView.findViewById(R.id.editBtn);
        singernamem = (TextView) rootView.findViewById(R.id.singernamem);
        email = (TextView) rootView.findViewById(R.id.email);
        contact = (TextView) rootView.findViewById(R.id.contact);
        circleImg = (CircleImageView) rootView.findViewById(R.id.circleImg);
        recylerviewgrid = (RecyclerView) rootView.findViewById(R.id.recylerviewgrid);
        rootView.findViewById(R.id.btnAudio).setOnClickListener(this);
        rootView.findViewById(R.id.btnVideo).setOnClickListener(this);

        profileApi();

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment = new EditProfileFragment();
                loadFragment(fragment);
            }
        });


        adapter = new MyVideoAdapter(mContext, competitionContentArrayList, this);
        RecyclerView.LayoutManager recyclerViewLayoutManager = new GridLayoutManager(mContext, 2);
        recylerviewgrid.setLayoutManager(recyclerViewLayoutManager);
        /*RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        recylerviewgrid.setLayoutManager(mLayoutManager);*/
        recylerviewgrid.setItemAnimator(new DefaultItemAnimator());
        recylerviewgrid.setAdapter(adapter);
    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void profileApi() {
        if (cd.isNetworkAvailable()) {
            RetrofitService.getProfile(new Dialog(mContext), retrofitApiClient.getProfile(user_id), new WebResponse() {
                @Override
                public void onResponseSuccess(Response<?> result) {
                    UserProfileModel loginModal = (UserProfileModel) result.body();
                    assert loginModal != null;
                    allAudioVideoList.clear();
                    competitionContentArrayList.clear();
                    if (!loginModal.getError()) {
                        Alerts.show(mContext, loginModal.getMessage());

                        singernamem.setText(loginModal.getUser().getParticipantName());
                        email.setText(loginModal.getUser().getParticipantEmail());
                        contact.setText(loginModal.getUser().getParticipantMobileNumber());
                        Glide.with(mContext).load(loginModal.getUser().getParticipantImage()).into(circleImg);

                        allAudioVideoList.addAll(loginModal.getCompetitionContent());
                        Log.e("Email Varification", ".." + loginModal.getUser().getParticipantEmailVerificationStatus());

                        /* Separate audio and video */
                        if (allAudioVideoList.size() > 0) {
                            for (int i = 0; i < allAudioVideoList.size(); i++) {
                                if (allAudioVideoList.get(i).getCompetitionContentType().equals("audio")) {
                                    audioList.add(allAudioVideoList.get(i));
                                } else {
                                    videoList.add(allAudioVideoList.get(i));
                                }
                            }
                            competitionContentArrayList.addAll(videoList);

                            ((TextView) rootView.findViewById(R.id.tvCount)).setText("Total" + " " + videoList.size() + " " + "videos");
                        }
                    } else {
                        Alerts.show(mContext, loginModal.getMessage());
                    }
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onResponseFailed(String error) {
                    Alerts.show(mContext, error);
                }
            });

        } else {
            cd.show(mContext);
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.videoBtn:
                int pos = Integer.parseInt(view.getTag().toString());
                String url = VIDEO_URL + competitionContentArrayList.get(pos).getCompetitionContentUrl();
                Toast.makeText(mContext, url, Toast.LENGTH_SHORT).show();
                showDialog(url);
                break;
            case R.id.btnAudio:
                competitionContentArrayList.clear();
                competitionContentArrayList.addAll(audioList);
                adapter.notifyDataSetChanged();
                ((TextView) rootView.findViewById(R.id.tvCount)).setText("Total" + " " + audioList.size() + " " + "audios");
                break;
            case R.id.btnVideo:
                competitionContentArrayList.clear();
                competitionContentArrayList.addAll(videoList);
                adapter.notifyDataSetChanged();
                ((TextView) rootView.findViewById(R.id.tvCount)).setText("Total" + " " + videoList.size() + " " + "videos");
                break;
        }
    }


    public void showDialog(String video) {
        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_video_upload);
        dialog.setCancelable(false);

        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector =
                new DefaultTrackSelector(videoTrackSelectionFactory);

        //Initialize the player
        player = ExoPlayerFactory.newSimpleInstance(mContext, trackSelector);

        //Initialize simpleExoPlayerView
        SimpleExoPlayerView simpleExoPlayerView = dialog.findViewById(R.id.video_view);
        simpleExoPlayerView.setPlayer(player);

        // Produces DataSource instances through which media data is loaded.
        DataSource.Factory dataSourceFactory =
                new DefaultDataSourceFactory(mContext, Util.getUserAgent(mContext, "CloudinaryExoplayer"));

        // Produces Extractor instances for parsing the media data.
        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();

        // This is the MediaSource representing the media to be played.
        Uri videoUri = Uri.parse(video);
        MediaSource videoSource = new ExtractorMediaSource(videoUri, dataSourceFactory, extractorsFactory, null, null);

        ((ImageView) dialog.findViewById(R.id.imgDismis)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.stop();
                dialog.dismiss();
            }
        });

        // Prepare the player with the source.
        player.prepare(videoSource);
        dialog.show();
    }


    private void initializePlayer() {
        // Create a default TrackSelector


    }

}
