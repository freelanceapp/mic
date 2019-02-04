package com.mic.music.mic.VideoUpload;

import android.app.Dialog;
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
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.mic.music.mic.Api.ApiClient;
import com.mic.music.mic.Api.ApiService;
import com.mic.music.mic.MainActivity;
import com.mic.music.mic.Newmic.Activity.HomeActivity;
import com.mic.music.mic.R;
import com.mic.music.mic.RagistrationActivity;
import com.mic.music.mic.Responce.VideoResponce;
import com.mic.music.mic.VideoRecord.VideoRecordActivity;
import com.mic.music.mic.constant.Constant;
import com.mic.music.mic.retrofit_provider.RetrofitService;
import com.mic.music.mic.retrofit_provider.WebResponse;
import com.mic.music.mic.upload_with_progress.ProgressRequestBody;
import com.mic.music.mic.utils.Alerts;
import com.mic.music.mic.utils.AppPreference;
import com.mic.music.mic.utils.BaseActivity;
import com.mic.music.mic.utils.ConnectionDetector;


import java.io.File;
import java.io.IOException;

import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.mic.music.mic.MainActivity.u_id;

public class UploadVideoActivity extends BaseActivity implements ProgressRequestBody.UploadCallbacks {
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
        mContext = this;
        cd = new ConnectionDetector(mContext);
        retrofitRxClient = RetrofitService.getRxClient();
        retrofitApiClient = RetrofitService.getRetrofit();
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
                   newPostFeedApi();
                   // ApiCallkyc();
                } else {
                    Toast.makeText(UploadVideoActivity.this, "No Internet", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }


    private void newPostFeedApi() {
        File file = new File(video1);
        String strId = AppPreference.getStringPreference(getApplicationContext(), Constant.User_Id);
        String strCompanyId = AppPreference.getStringPreference(getApplicationContext(), Constant.COMPANY_ID);
        String strLevelId = AppPreference.getStringPreference(getApplicationContext(), Constant.LEVEL_ID);

        if (cd.isNetworkAvailable()) {
            RequestBody competition = RequestBody.create(MediaType.parse("text/plain"), strCompanyId);
            RequestBody level = RequestBody.create(MediaType.parse("text/plain"), strLevelId);
            RequestBody participet = RequestBody.create(MediaType.parse("text/plain"), strId);
            RequestBody type = RequestBody.create(MediaType.parse("text/plain"), "video");
            ProgressRequestBody fileBody = new ProgressRequestBody(file, "video/*", this);
            MultipartBody.Part videoFileUpload = MultipartBody.Part.createFormData("file", file.getName(), fileBody);

            RetrofitService.getNewPostData(new Dialog(mContext), retrofitApiClient.getNewPostData(competition,level,participet,type,videoFileUpload), new WebResponse() {
                @Override
                public void onResponseSuccess(Response<?> result) {
                    VideoResponce responseBody = (VideoResponce) result.body();
                    assert responseBody != null;
                    if (!responseBody.getError())
                        {
                            Alerts.show(mContext, responseBody.getMessage());
                            Log.e("url", ".. "+ responseBody.getMessage());
                            Log.e("url", ".. "+ responseBody.getUrl());
                            AppPreference.setStringPreference(mContext, Constant.COMPANY_ID, "");

                        } else {
                            Alerts.show(mContext, responseBody.getMessage());
                            //finish();
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




    @Override
    public void onProgressUpdate(int percentage) {
        submit_btn.setText(percentage + "");
        if (submit_btn.getText().equals("99"))
        {
            submit_btn.setText("Finish");
        }else {

        }

    }

    @Override
    public void onError() {

    }

    @Override
    public void onFinish() {
        submit_btn.setText("Finished");

    }
}
