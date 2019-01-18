package com.mic.music.mic.VideoUpload;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.mic.music.mic.Api.ApiClient;
import com.mic.music.mic.Api.ApiService;
import com.mic.music.mic.MainActivity;
import com.mic.music.mic.R;
import com.mic.music.mic.RagistrationActivity;
import com.mic.music.mic.Responce.VideoResponce;
import com.mic.music.mic.VideoRecord.VideoRecordActivity;


import java.io.File;
import java.io.IOException;

import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.mic.music.mic.MainActivity.u_id;

public class UploadVideoActivity extends AppCompatActivity {
    private TextView txtPercentage;
    VideoView videoView;
    String video1;
    TextView submit_btn;
    private ProgressBar progressBar;
    long totalSize = 0;
    ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_video);
        txtPercentage = (TextView) findViewById(R.id.txtPercentage);
        videoView = (VideoView) findViewById(R.id.video_view);
        video1 = getIntent().getStringExtra("video1");
        Log.e("Path", "..." + video1);
        pDialog = new ProgressDialog(this);
        videoView.setVideoURI(Uri.parse(video1));
        videoView.start();
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        submit_btn = (TextView) findViewById(R.id.submit_btn);
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                        connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                    //we are connected to a network
                    ApiCallkyc();
                } else {
                    Toast.makeText(UploadVideoActivity.this, "No Internet", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    public void ApiCallkyc() {

        File file = new File(video1);
        RequestBody competition = RequestBody.create(MediaType.parse("text/plain"), "music");
        RequestBody level = RequestBody.create(MediaType.parse("text/plain"), "1");
        RequestBody participet = RequestBody.create(MediaType.parse("text/plain"), u_id);
        RequestBody r_video = RequestBody.create(MediaType.parse("video/*"), file);
        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part video2 = MultipartBody.Part.createFormData("file", file.getName(), r_video);
        // add another part within the multipart request
        ApiService apiInterface1 = ApiClient.getClient().create(ApiService.class);
        Call<VideoResponce> call = apiInterface1.uploadVideo(competition, level, participet, video2);
        Log.e("Response", "...." + competition + "..........");
        Log.e("Response lat", "...." + file.getName() + "..........");

        progressBar.setVisibility(View.VISIBLE);
        pDialog.setMessage("Upload...");
        pDialog.show();
        pDialog.setCancelable(true);
        call.enqueue(new Callback<VideoResponce>() {
            @Override
            public void onResponse(Call<VideoResponce> call, Response<VideoResponce> response) {
                VideoResponce imageResponce = response.body();
                Log.e("...Massage...", "..." + imageResponce.getMessage());
                Log.e("...URI...", "..." + imageResponce.getUrl());
                if (imageResponce.getMessage().equals("Video Uploaded Successfull")) {
                    Toast.makeText(UploadVideoActivity.this, imageResponce.getMessage(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(UploadVideoActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else if (imageResponce.getMessage().equals("User Already uploaded File")) {
                    Toast.makeText(UploadVideoActivity.this, imageResponce.getMessage(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(UploadVideoActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(UploadVideoActivity.this, "Internet speed slow try again", Toast.LENGTH_SHORT).show();
                }
                pDialog.dismiss();
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<VideoResponce> call, Throwable t) {
                Log.e("...Throwable...", "..." + t.getMessage());
                Log.e("...Throwable...", "..." + t.toString());
                progressBar.setVisibility(View.GONE);
                Toast.makeText(UploadVideoActivity.this, "Video not Upload Please try Again", Toast.LENGTH_SHORT).show();
                pDialog.dismiss();
            }
        });
    }
}
