package com.online.music.mic.AudioUpload;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.online.music.mic.Newmic.Activity.HomeActivity;
import com.online.music.mic.R;
import com.online.music.mic.Responce.VideoResponce;
import com.online.music.mic.constant.Constant;
import com.online.music.mic.retrofit_provider.RetrofitService;
import com.online.music.mic.retrofit_provider.WebResponse;
import com.online.music.mic.upload_with_progress.ProgressRequestBody;
import com.online.music.mic.utils.Alerts;
import com.online.music.mic.utils.AppPreference;
import com.online.music.mic.utils.BaseActivity;
import com.online.music.mic.utils.ConnectionDetector;
import com.wang.avi.AVLoadingIndicatorView;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static com.online.music.mic.Newmic.Activity.HomeActivity.user_id;

public class AudioRecordActivity extends BaseActivity implements ProgressRequestBody.UploadCallbacks {
    Button buttonStart, buttonStop, buttonPlayLastRecordAudio,
            buttonStopPlayingRecording ,button5 ;
    String AudioSavePathInDevice = null;
    MediaRecorder mediaRecorder ;
    Random random ;
    String RandomAudioFileName = "ABCDEFGHIJKLMNOP";
    public static final int RequestPermissionCode = 1;
    MediaPlayer mediaPlayer ;
    ImageView backABtn;
    TextView tvCount;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.audio_record_activity);

        mContext = this;
        cd = new ConnectionDetector(mContext);
        retrofitRxClient = RetrofitService.getRxClient();
        retrofitApiClient = RetrofitService.getRetrofit();
        progressDialog = new ProgressDialog(mContext);
        buttonStart = (Button) findViewById(R.id.start_record);
        buttonStop = (Button) findViewById(R.id.stop_record);
        buttonPlayLastRecordAudio = (Button) findViewById(R.id.button3);
        buttonStopPlayingRecording = (Button)findViewById(R.id.button4);
        button5 = (Button)findViewById(R.id.button5);
        backABtn = (ImageView)findViewById(R.id.backABtn);
        tvCount = (TextView) findViewById(R.id.tvCount);
        backABtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        buttonStop.setEnabled(false);
        buttonPlayLastRecordAudio.setEnabled(false);
        buttonStopPlayingRecording.setEnabled(false);

        random = new Random();

        buttonStart.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                if(checkPermission()) {

                    tvCount.setVisibility(View.GONE);

                    AudioSavePathInDevice = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + CreateRandomAudioFileName(5) + "MicAudioRecording.mp3";
                    MediaRecorderReady();
                    buttonStart.setBackground(getResources().getDrawable(R.drawable.yellow_background_shape));
                    buttonStart.setTextColor(Color.BLACK);
                    buttonStop.setBackground(getResources().getDrawable(R.drawable.buttonborder));
                    buttonStop.setTextColor(Color.parseColor("#f3C800"));
                    buttonPlayLastRecordAudio.setBackground(getResources().getDrawable(R.drawable.buttonborder));
                    buttonPlayLastRecordAudio.setTextColor(Color.parseColor("#f3C800"));
                    buttonStopPlayingRecording.setBackground(getResources().getDrawable(R.drawable.layout_back_b));
                    buttonStopPlayingRecording.setTextColor(Color.parseColor("#f3C800"));
                    button5.setBackground(getResources().getDrawable(R.drawable.layout_back_b));
                    button5.setTextColor(Color.parseColor("#f3C800"));

                    try {
                        mediaRecorder.prepare();
                        mediaRecorder.start();
                        ((AVLoadingIndicatorView)findViewById(R.id.aviProgressBar)).setVisibility(View.VISIBLE);



                    } catch (IllegalStateException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    buttonStart.setEnabled(false);
                    buttonStop.setEnabled(true);

                    Toast.makeText(AudioRecordActivity.this, "Recording started ", Toast.LENGTH_LONG).show();
                } else {
               //     buttonStart.setBackground(getResources().getDrawable(R.drawable.buttonborder));
                 //   buttonStart.setTextColor(R.color.yellow);
                    requestPermission();
                }

            }
        });

        buttonStop.setOnClickListener(new View.OnClickListener() {
            @SuppressLint({"ResourceAsColor", "ResourceType"})
            @Override
            public void onClick(View view) {
                mediaRecorder.stop();
                tvCount.setVisibility(View.GONE);

                buttonStop.setEnabled(false);
                buttonPlayLastRecordAudio.setEnabled(true);
                buttonStart.setEnabled(true);
                buttonStopPlayingRecording.setEnabled(false);
                ((AVLoadingIndicatorView)findViewById(R.id.aviProgressBar)).setVisibility(View.GONE);
                buttonStop.setBackground(getResources().getDrawable(R.drawable.yellow_background_shape));
                buttonStop.setTextColor(Color.BLACK);
                buttonStart.setBackground(getResources().getDrawable(R.drawable.buttonborder));
                buttonStart.setTextColor(Color.parseColor("#f3C800"));
                buttonPlayLastRecordAudio.setBackground(getResources().getDrawable(R.drawable.buttonborder));
                buttonPlayLastRecordAudio.setTextColor(Color.parseColor("#f3C800"));
                buttonStopPlayingRecording.setBackground(getResources().getDrawable(R.drawable.layout_back_b));
                buttonStopPlayingRecording.setTextColor(Color.parseColor("#f3C800"));
                button5.setBackground(getResources().getDrawable(R.drawable.layout_back_b));
                button5.setTextColor(Color.parseColor("#f3C800"));


                Toast.makeText(AudioRecordActivity.this, "Recording Completed", Toast.LENGTH_LONG).show();
            }
        });

        buttonPlayLastRecordAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) throws IllegalArgumentException,
                    SecurityException, IllegalStateException {

                buttonStop.setEnabled(false);
                buttonStart.setEnabled(false);
                buttonStopPlayingRecording.setEnabled(true);
                tvCount.setVisibility(View.GONE);

                buttonPlayLastRecordAudio.setBackground(getResources().getDrawable(R.drawable.yellow_background_shape));
                buttonPlayLastRecordAudio.setTextColor(Color.BLACK);
                buttonStart.setBackground(getResources().getDrawable(R.drawable.buttonborder));
                buttonStart.setTextColor(Color.parseColor("#f3C800"));
                buttonStop.setBackground(getResources().getDrawable(R.drawable.buttonborder));
                buttonStop.setTextColor(Color.parseColor("#f3C800"));
                buttonStopPlayingRecording.setBackground(getResources().getDrawable(R.drawable.layout_back_b));
                buttonStopPlayingRecording.setTextColor(Color.parseColor("#f3C800"));
                button5.setBackground(getResources().getDrawable(R.drawable.layout_back_b));
                button5.setTextColor(Color.parseColor("#f3C800"));

                mediaPlayer = new MediaPlayer();
                try {
                    mediaPlayer.setDataSource(AudioSavePathInDevice);
                    mediaPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                mediaPlayer.start();
                Toast.makeText(AudioRecordActivity.this, "Recording Playing", Toast.LENGTH_LONG).show();
            }
        });

        buttonStopPlayingRecording.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonStop.setEnabled(false);
                buttonStart.setEnabled(true);
                buttonStopPlayingRecording.setEnabled(false);
                buttonPlayLastRecordAudio.setEnabled(true);
                tvCount.setVisibility(View.GONE);

                buttonStopPlayingRecording.setBackground(getResources().getDrawable(R.drawable.layout_background_c));
                buttonStopPlayingRecording.setTextColor(Color.BLACK);
                buttonStart.setBackground(getResources().getDrawable(R.drawable.buttonborder));
                buttonStart.setTextColor(Color.parseColor("#f3C800"));
                buttonPlayLastRecordAudio.setBackground(getResources().getDrawable(R.drawable.buttonborder));
                buttonPlayLastRecordAudio.setTextColor(Color.parseColor("#f3C800"));
                buttonStop.setBackground(getResources().getDrawable(R.drawable.buttonborder));
                buttonStop.setTextColor(Color.parseColor("#f3C800"));
                button5.setBackground(getResources().getDrawable(R.drawable.layout_back_b));
                button5.setTextColor(Color.parseColor("#f3C800"));

                if(mediaPlayer != null){
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    MediaRecorderReady();
                }
            }
        });


        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                button5.setBackground(getResources().getDrawable(R.drawable.layout_background_c));
                button5.setTextColor(Color.BLACK);
                buttonStart.setBackground(getResources().getDrawable(R.drawable.buttonborder));
                buttonStart.setTextColor(Color.parseColor("#f3C800"));
                buttonPlayLastRecordAudio.setBackground(getResources().getDrawable(R.drawable.buttonborder));
                buttonPlayLastRecordAudio.setTextColor(Color.parseColor("#f3C800"));
                buttonStopPlayingRecording.setBackground(getResources().getDrawable(R.drawable.layout_back_b));
                buttonStopPlayingRecording.setTextColor(Color.parseColor("#f3C800"));
                buttonStop.setBackground(getResources().getDrawable(R.drawable.buttonborder));
                buttonStop.setTextColor(Color.parseColor("#f3C800"));

                newPostFeedApi();

            }
        });
    }

    public void MediaRecorderReady(){
        mediaRecorder=new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        mediaRecorder.setOutputFile(AudioSavePathInDevice);
    }

    public String CreateRandomAudioFileName(int string){
        StringBuilder stringBuilder = new StringBuilder( string );
        int i = 0 ;
        while(i < string ) {
            stringBuilder.append(RandomAudioFileName.charAt(random.nextInt(RandomAudioFileName.length())));
            i++ ;
        }
        return stringBuilder.toString();
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(AudioRecordActivity.this, new
                String[]{WRITE_EXTERNAL_STORAGE, RECORD_AUDIO}, RequestPermissionCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case RequestPermissionCode:
                if (grantResults.length> 0) {
                    boolean StoragePermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean RecordPermission = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if (StoragePermission && RecordPermission) {
                        Toast.makeText(AudioRecordActivity.this, "Permission Granted", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(AudioRecordActivity.this,"Permission Denied",Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }
    }

    public boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(),
                WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(),
                RECORD_AUDIO);
        return result == PackageManager.PERMISSION_GRANTED &&
                result1 == PackageManager.PERMISSION_GRANTED;

    }


    private void newPostFeedApi() {
        File file = new File(AudioSavePathInDevice);
        String strId = user_id;
        String strCompanyId = AppPreference.getStringPreference(getApplicationContext(), Constant.COMPANY_ID);
        String strLevelId = AppPreference.getStringPreference(getApplicationContext(), Constant.LEVEL_ID);

        if (cd.isNetworkAvailable()) {
            RequestBody competition = RequestBody.create(MediaType.parse("text/plain"), strCompanyId);
            RequestBody level = RequestBody.create(MediaType.parse("text/plain"), strLevelId);
            RequestBody participet = RequestBody.create(MediaType.parse("text/plain"), strId);
            RequestBody type = RequestBody.create(MediaType.parse("text/plain"), "audio");
            ProgressRequestBody fileBody = new ProgressRequestBody(file, "*/*", this);
            MultipartBody.Part videoFileUpload = MultipartBody.Part.createFormData("file", file.getName(), fileBody);

            progressDialog.setMessage("Uploading...");
            progressDialog.setCancelable(true);
            progressDialog.show();
            RetrofitService.getNewPostData(new Dialog(mContext), retrofitApiClient.getNewPostData(competition,level,participet,type,videoFileUpload), new WebResponse() {
                @Override
                public void onResponseSuccess(Response<?> result) {
                    VideoResponce responseBody = (VideoResponce) result.body();
                    assert responseBody != null;
                    if (!responseBody.getError())
                    {
                      //  Alerts.show(mContext, responseBody.getMessage());
                        Log.e("url", ".. "+ responseBody.getMessage());
                        Log.e("url", ".. "+ responseBody.getUrl());
                        AppPreference.setStringPreference(mContext, Constant.COMPANY_ID, "");
                        AppPreference.setStringPreference(mContext, Constant.FILE_TYPE, "");
                        AppPreference.setStringPreference(mContext, Constant.LEVEL_ID, "");
                        AppPreference.setStringPreference(mContext , Constant.COMPETITION_NAME, "");
                        AppPreference.setStringPreference(mContext , Constant.COMPETITION_NAME_LEVEL, "");
                        Intent intent = new Intent(AudioRecordActivity.this , HomeActivity.class);
                        startActivity(intent);
                        finish();

                    } else {
                        Alerts.show(mContext, responseBody.getMessage());
                        AppPreference.setStringPreference(mContext, Constant.COMPANY_ID, "");
                        AppPreference.setStringPreference(mContext, Constant.FILE_TYPE, "");
                    }
                    progressDialog.dismiss();
                }
                @Override
                public void onResponseFailed(String error) {
                    Alerts.show(mContext, error);
                    progressDialog.dismiss();

                }
            });
        } else {
            cd.show(mContext);
        }
    }


    @Override
    public void onProgressUpdate(int percentage) {
        tvCount.setVisibility(View.VISIBLE);
        tvCount.setText(""+percentage+"%");
        int val = Integer.parseInt(tvCount.getText().toString());
        if (val > 90 && val <100 )
        {
            tvCount.setText("Finish");
        }else {

        }
    }

    @Override
    public void onError() {

    }

    @Override
    public void onFinish() {

    }
}
