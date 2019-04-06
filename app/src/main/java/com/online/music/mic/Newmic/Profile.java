package com.online.music.mic.Newmic;

import android.app.Dialog;
import android.app.DownloadManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.online.music.mic.Newmic.Activity.Mobile_Ragistration;
import com.online.music.mic.Newmic.Adapter.MyVideoAdapter;
import com.online.music.mic.Newmic.Fragment.EditProfileFragment;
import com.online.music.mic.R;
import com.online.music.mic.constant.Constant;
import com.online.music.mic.model.login_responce.LoginModel;
import com.online.music.mic.model.otp_responce.OtpModel;
import com.online.music.mic.model.user_responce.CompetitionContent;
import com.online.music.mic.model.user_responce.UserProfileModel;
import com.online.music.mic.retrofit_provider.RetrofitService;
import com.online.music.mic.retrofit_provider.WebResponse;
import com.online.music.mic.utils.Alerts;
import com.online.music.mic.utils.AppPreference;
import com.online.music.mic.utils.BaseFragment;
import com.online.music.mic.utils.ConnectionDetector;
import com.online.music.mic.utils.MyStringRandomGen;

import java.io.IOException;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Response;

import static com.online.music.mic.Newmic.Activity.HomeActivity.user_id;

public class Profile extends BaseFragment implements View.OnClickListener {

    private Uri Download_Uri;
    private DownloadManager downloadManager;
    private long refid;
    private Fragment fragment;
    private View rootView;
    private ImageView editBtn, btnAudio, btnVideo, logoutBtn;
    private TextView singernamem, email, contact;
    private CircleImageView circleImg;
    private MyVideoAdapter adapter;
    private ArrayList<CompetitionContent> competitionContentArrayList = new ArrayList<>();
    private ArrayList<CompetitionContent> allAudioVideoList = new ArrayList<>();
    private ArrayList<CompetitionContent> videoList = new ArrayList<>();
    private ArrayList<CompetitionContent> audioList = new ArrayList<>();
    private RecyclerView recylerviewgrid, recylerviewvideo;
    private TextView btnVarify;
    private String emailOtp1;
    private MediaPlayer mPlayer;
    private MyStringRandomGen myStringRandomGen;

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
        myStringRandomGen = new MyStringRandomGen();
        downloadManager = (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);
        mContext.registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        editBtn = rootView.findViewById(R.id.editBtn);
        singernamem = rootView.findViewById(R.id.singernamem);
        email = rootView.findViewById(R.id.email);
        contact = rootView.findViewById(R.id.contact);
        circleImg = rootView.findViewById(R.id.circleImg);
        recylerviewgrid = rootView.findViewById(R.id.recylerviewgrid);
        recylerviewvideo = rootView.findViewById(R.id.recylerviewvideo);
        btnAudio = rootView.findViewById(R.id.btnAudio);
        btnVideo = rootView.findViewById(R.id.btnVideo);
        logoutBtn = rootView.findViewById(R.id.logoutBtn);
        btnVarify = rootView.findViewById(R.id.btnVarify);
        editBtn = rootView.findViewById(R.id.editBtn);
        singernamem = rootView.findViewById(R.id.singernamem);
        email = rootView.findViewById(R.id.email);
        contact = rootView.findViewById(R.id.contact);
        circleImg = rootView.findViewById(R.id.circleImg);
        recylerviewgrid = rootView.findViewById(R.id.recylerviewgrid);
        rootView.findViewById(R.id.btnAudio).setOnClickListener(this);
        rootView.findViewById(R.id.btnVideo).setOnClickListener(this);
        btnVarify.setOnClickListener(this);
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
        recylerviewgrid.setItemAnimator(new DefaultItemAnimator());
        recylerviewgrid.setAdapter(adapter);

        email.setHorizontallyScrolling(true);
        email.setFocusable(true);
        email.setMarqueeRepeatLimit(-1);
        email.setSelected(true);
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
                    //  allAudioVideoList.clear();
                    competitionContentArrayList.clear();
                    assert loginModal != null;
                    if (!loginModal.getError()) {
                        // Alerts.show(mContext, loginModal.getMessage());
                        singernamem.setText(loginModal.getUser().getParticipantName());
                        email.setText(loginModal.getUser().getParticipantEmail());
                        contact.setText(loginModal.getUser().getParticipantMobileNumber());
                        Glide.with(mContext).load(loginModal.getUser().getParticipantImage()).into(circleImg);
                        //Log.e("Url ", ".."+loginModal.getUser().getParticipantImage());
                        if (loginModal.getUser().getParticipantEmailVerificationStatus() != null) {
                            if (loginModal.getUser().getParticipantEmailVerificationStatus().equals("Verified")) {
                                btnVarify.setText("Verified");
                                email.setFocusable(false);
                                btnVarify.setClickable(false);
                            } else {
                                btnVarify.setText("Verify");
                            }
                        }
                        // competitionContentArrayList.addAll(loginModal.getCompetitionContent());
                        Log.e("Email Varification", ".." + loginModal.getUser().getParticipantEmailVerificationStatus());
                        allAudioVideoList.clear();
                        audioList.clear();
                        videoList.clear();
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
                            competitionContentArrayList.addAll(audioList);
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
                String forment1 = competitionContentArrayList.get(pos).getCompetitionContentType();
                if (forment1.equals("audio")) {
                    String url = Constant.AUDIO_URL + competitionContentArrayList.get(pos).getCompetitionContentUrl();
                    getSongUrl(url);
                } else {
                    String url = Constant.VIDEO_URL + competitionContentArrayList.get(pos).getCompetitionContentUrl();
                    showDialog(url);
                }
                break;
            case R.id.btnAudio:
                btnAudio.setColorFilter(ContextCompat.getColor(mContext, (R.color.colorYellow)));
                btnVideo.setColorFilter(ContextCompat.getColor(mContext, (R.color.gray_b)));
                competitionContentArrayList.clear();
                competitionContentArrayList.addAll(audioList);
                adapter.notifyDataSetChanged();
                break;
            case R.id.btnVideo:
                btnAudio.setColorFilter(ContextCompat.getColor(mContext, (R.color.gray_b)));
                btnVideo.setColorFilter(ContextCompat.getColor(mContext, (R.color.colorYellow)));
                competitionContentArrayList.clear();
                competitionContentArrayList.addAll(videoList);
                adapter.notifyDataSetChanged();
                break;
            case R.id.btnVarify:
                getEmail();
                break;
            case R.id.logoutBtn:
                AppPreference.setBooleanPreference(mContext, Constant.Is_Login, false);
                Intent intent = new Intent(getActivity(), Mobile_Ragistration.class);
                startActivity(intent);
                getActivity().finish();
                break;
        }
    }

    private void getEmail() {
        if (cd.isNetworkAvailable()) {
            RetrofitService.getEmaillogin(new Dialog(mContext), retrofitApiClient.getLogin1(email.getText().toString()), new WebResponse() {
                @Override
                public void onResponseSuccess(Response<?> result) {
                    LoginModel loginModal = (LoginModel) result.body();
                    assert loginModal != null;
                    if (!loginModal.getError()) {
                        Alerts.show(mContext, loginModal.getMessage());
                        showDialog();
                    } else {
                        Alerts.show(mContext, loginModal.getMessage());
                    }
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

    public void showDialog() {
        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_email_otp);

        TextView text = dialog.findViewById(R.id.emailTv);
        text.setText(email.getText().toString());

        final EditText emailOtp = dialog.findViewById(R.id.emailOtpET);

        Button dialogButton = dialog.findViewById(R.id.emailOTPVarificationBtn);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailOtp1 = emailOtp.getText().toString();
                if (emailOtp1.length() == 6) {
                    otpVarification1();
                } else {
                    emailOtp.setError("Please Enter OTP");
                }
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void otpVarification1() {
        if (cd.isNetworkAvailable()) {
            RetrofitService.getOtp(new Dialog(mContext), retrofitApiClient.getOtp1(email.getText().toString(), emailOtp1), new WebResponse() {
                @Override
                public void onResponseSuccess(Response<?> result) {
                    OtpModel loginModal = (OtpModel) result.body();
                    assert loginModal != null;
                    if (!loginModal.getError()) {
                        btnVarify.setText("Verified");
                        email.setFocusable(false);
                        btnVarify.setClickable(false);
                    } else {
                        Alerts.show(mContext, loginModal.getMessage());
                    }
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

    public void showDialog(String video) {
        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_video_upload);
        dialog.setCancelable(false);

        MediaController mediaController = new MediaController(mContext);
        final VideoView video1 = dialog.findViewById(R.id.video);
        final ProgressDialog pd = new ProgressDialog(mContext);

        pd.setMessage("Buffering video please wait...");
        pd.show();

        Uri uri = Uri.parse(video);
        video1.setVideoURI(uri);
        mediaController.setAnchorView(video1);
        video1.setMediaController(mediaController);
        video1.setVideoURI(uri);
        video1.requestFocus();
        video1.start();

        Uri myUri = Uri.parse(video); // initialize Uri here
        final MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaPlayer.setDataSource(mContext, myUri);
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mediaPlayer.start();

        video1.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                //close the progress dialog when buffering is done
                pd.dismiss();
            }
        });

        dialog.findViewById(R.id.imgDismis).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // player.stop();
                video1.stopPlayback();
                video1.pause();
                mediaPlayer.stop();
                dialog.dismiss();
            }
        });

        // Prepare the player with the source.
        //player.prepare(videoSource);
        dialog.show();
    }

    private void initializePlayer() {
    }

    private void getSongUrl(String videourl) {
        Download_Uri = Uri.parse(videourl);
        downLoadManagerSong(videourl);
    }

    private void downLoadManagerSong(String video) {
        DownloadManager.Request request = new DownloadManager.Request(Download_Uri);
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        request.setAllowedOverRoaming(false);
        request.setTitle("Mic");
        request.setDescription("Downloading ");
        request.setVisibleInDownloadsUi(true);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "/mic/" + "mic_song_"+myStringRandomGen.generateRandomString());
        refid = downloadManager.enqueue(request);
        Log.e("OUT", "" + refid);

    }

    BroadcastReceiver onComplete = new BroadcastReceiver() {
        public void onReceive(Context ctxt, Intent intent) {
            long referenceId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            Log.e("IN", "" + referenceId);
            Alerts.show(getActivity(), "Downloading Complete");
            PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, intent, PendingIntent.FLAG_ONE_SHOT);
           /* list.remove(referenceId);
            if (list.isEmpty()) {
                Log.e("INSIDE", "" + referenceId);
                NotificationCompat.Builder mBuilder =
                        new NotificationCompat.Builder(ctx.getApplicationContext())
                                .setSmallIcon(R.mipmap.ic_launcher)
                                .setContentTitle("Clean Sys")
                               *//* .setContentIntent(pendingIntent)*//*;

                NotificationManager notificationManager = (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(455, mBuilder.build());
            }*/

            Intent intent1 = new Intent(Intent.ACTION_GET_CONTENT);
            Uri uri = Uri.parse("/Download/mic/"); // a directory
            intent1.setDataAndType(uri, "*/*");
            showNotification(intent1, "Mic", "Complete");

        }
    };


    private void showNotification(Intent intent, String title, String message) {
        int notifyID = 1;
        String CHANNEL_ID = "my_channel_01";// The id of the channel.
        CharSequence name = "MIC ";// The user-visible name of the channel.
        int importance = NotificationManager.IMPORTANCE_HIGH;

        Notification notification;
        NotificationChannel mChannel = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
        }

        PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0,
                intent, PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(mContext)
                .setSmallIcon(R.drawable.logo)
                .setTicker(title)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri).setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(mChannel);
        } else {
            notificationManager.notify(0, notificationBuilder.build());
        }
    }

}
