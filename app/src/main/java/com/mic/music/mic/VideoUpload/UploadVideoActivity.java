package com.mic.music.mic.VideoUpload;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.mic.music.mic.R;
import com.mic.music.mic.Responce.VideoResponce;
import com.mic.music.mic.constant.Constant;
import com.mic.music.mic.retrofit_provider.RetrofitService;
import com.mic.music.mic.retrofit_provider.WebResponse;
import com.mic.music.mic.upload_with_progress.ProgressRequestBody;
import com.mic.music.mic.utils.Alerts;
import com.mic.music.mic.utils.AppPreference;
import com.mic.music.mic.utils.BaseActivity;
import com.mic.music.mic.utils.ConnectionDetector;
import com.mic.music.mic.video_compressor_classes.VideoCompress;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;

import static com.mic.music.mic.Newmic.Activity.HomeActivity.user_id;

public class UploadVideoActivity extends BaseActivity implements ProgressRequestBody.UploadCallbacks {

    private TextView txtPercentage;
    private VideoView videoView;
    private String video1;
    private TextView submit_btn;
    private ProgressBar progressBar;
    private long totalSize = 0;
    private ProgressDialog pDialog;
    private String destPath;
    private Dialog dialogCompressProgress;
    private String outputDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();

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
                    compressVideo(video1);
                    // ApiCallkyc();
                } else {
                    Toast.makeText(UploadVideoActivity.this, "No Internet", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void compressVideo(String strOriginalVIdeo) {
        destPath = outputDir + File.separator + "VID_" +
                new SimpleDateFormat("yyyyMMdd_HHmmss", getLocale()).format(new Date()) + ".mp4";
        VideoCompress.compressVideoMedium(strOriginalVIdeo, destPath, new VideoCompress.CompressListener() {
            @Override
            public void onStart() {
                dialogCompressProgress = new Dialog(mContext);
                dialogCompressProgress.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialogCompressProgress.setContentView(R.layout.dialog_compress_progress);

                dialogCompressProgress.setCanceledOnTouchOutside(true);
                dialogCompressProgress.setCancelable(true);
                if (dialogCompressProgress.getWindow() != null)
                    dialogCompressProgress.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                Window window = dialogCompressProgress.getWindow();
                window.setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                dialogCompressProgress.show();
            }

            @Override
            public void onSuccess() {
                dialogCompressProgress.dismiss();
                newPostFeedApi(destPath);
            }

            @Override
            public void onFail() {
                Toast.makeText(mContext, "Compress fail", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onProgress(float percent) {
                if (dialogCompressProgress != null) {
                    ((TextView) dialogCompressProgress.findViewById(R.id.tvProgress))
                            .setText("Progress " + String.valueOf(percent) + " %");
                }
            }
        });
    }


    private void newPostFeedApi(String strFilePath) {
        File file = new File(strFilePath);
        String strId = user_id;
        String strCompanyId = AppPreference.getStringPreference(getApplicationContext(), Constant.COMPANY_ID);
        String strLevelId = AppPreference.getStringPreference(getApplicationContext(), Constant.LEVEL_ID);

        if (cd.isNetworkAvailable()) {
            RequestBody competition = RequestBody.create(MediaType.parse("text/plain"), strCompanyId);
            RequestBody level = RequestBody.create(MediaType.parse("text/plain"), strLevelId);
            RequestBody participet = RequestBody.create(MediaType.parse("text/plain"), strId);
            RequestBody type = RequestBody.create(MediaType.parse("text/plain"), "video");
            ProgressRequestBody fileBody = new ProgressRequestBody(file, "video/*", this);
            MultipartBody.Part videoFileUpload = MultipartBody.Part.createFormData("file", file.getName(), fileBody);

            RetrofitService.getNewPostData(new Dialog(mContext), retrofitApiClient.getNewPostData(competition, level, participet, type, videoFileUpload), new WebResponse() {
                @Override
                public void onResponseSuccess(Response<?> result) {
                    VideoResponce responseBody = (VideoResponce) result.body();
                    assert responseBody != null;
                    if (!responseBody.getError()) {
                        Alerts.show(mContext, responseBody.getMessage());
                        Log.e("url", ".. " + responseBody.getMessage());
                        Log.e("url", ".. " + responseBody.getUrl());
                        AppPreference.setStringPreference(mContext, Constant.COMPANY_ID, "");

                    } else {
                        Alerts.show(mContext, responseBody.getMessage());
                        //finish();
                        AppPreference.setStringPreference(mContext, Constant.COMPANY_ID, "");

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
        if (submit_btn.getText().equals("99")) {
            submit_btn.setText("Finish");
        } else {

        }

    }

    @Override
    public void onError() {

    }

    @Override
    public void onFinish() {
        submit_btn.setText("Finished");
    }

    /****************************************************************************************************/
    /*
     * Video compress methods
     * */
    private Locale getLocale() {
        Configuration config = getResources().getConfiguration();
        Locale sysLocale = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            sysLocale = getSystemLocale(config);
        } else {
            sysLocale = getSystemLocaleLegacy(config);
        }

        return sysLocale;
    }

    public static Locale getSystemLocaleLegacy(Configuration config) {
        return config.locale;
    }

    @TargetApi(Build.VERSION_CODES.N)
    public static Locale getSystemLocale(Configuration config) {
        return config.getLocales().get(0);
    }
}
