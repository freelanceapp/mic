package com.mic.music.mic.VideoUpload;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.mic.music.mic.Api.AndroidMultiPartEntity;
import com.mic.music.mic.Newmic.Activity.HomeActivity;
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

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
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
        Constant.BASE_URL = "http://codeencrypt.in/sport/admin/api/";
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
                    //compressVideo(video1);
                    // ApiCallkyc();
                    newPostFeedApi(video1);
                    // new UploadFileToServer().execute();
                } else {
                    Toast.makeText(UploadVideoActivity.this, "No Internet", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /* private void compressVideo(String strOriginalVIdeo) {
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
 */
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
                        Intent intent = new Intent(UploadVideoActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Alerts.show(mContext, responseBody.getMessage());
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


    /**
     * Uploading the file to server
     */
    private class UploadFileToServer extends AsyncTask<Void, Integer, String> {
        String strId = user_id;
        String strCompanyId = AppPreference.getStringPreference(getApplicationContext(), Constant.COMPANY_ID);
        String strLevelId = AppPreference.getStringPreference(getApplicationContext(), Constant.LEVEL_ID);

        @Override
        protected void onPreExecute() {
            // setting progress bar to zero
            progressBar.setProgress(0);
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            // Making progress bar visible
            progressBar.setVisibility(View.VISIBLE);

            // updating progress bar value
            progressBar.setProgress(progress[0]);

            // updating percentage value
            txtPercentage.setText(String.valueOf(progress[0]) + "%");
        }

        @Override
        protected String doInBackground(Void... params) {
            return uploadFile();
        }

        @SuppressWarnings("deprecation")
        private String uploadFile() {
            String responseString = null;

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(Constant.FILE_UPLOAD1);

            try {
                AndroidMultiPartEntity entity = new AndroidMultiPartEntity(new AndroidMultiPartEntity.ProgressListener() {
                    @Override
                    public void transferred(long num) {
                        publishProgress((int) ((num / (float) totalSize) * 100));
                    }
                });

                File sourceFile = new File(video1);
                // Adding file data to http body
                entity.addPart("competition", new StringBody(strCompanyId));
                entity.addPart("competition_level", new StringBody(strLevelId));
                entity.addPart("participant", new StringBody(user_id));
                entity.addPart("type", new StringBody("video"));
                entity.addPart("file", new FileBody(sourceFile));

                totalSize = entity.getContentLength();
                httppost.setEntity(entity);

                // Making server call
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity r_entity = response.getEntity();

                int statusCode = response.getStatusLine().getStatusCode();
                Log.e("Responce", "..." + response.toString());
                if (statusCode == 200) {
                    // Server response
                    responseString = EntityUtils.toString(r_entity);
                } else {
                    responseString = "Error occurred! Http Status Code: " + statusCode;
                }

            } catch (ClientProtocolException e) {
                responseString = e.toString();
            } catch (IOException e) {
                responseString = e.toString();
            }
            return responseString;
        }

        @Override
        protected void onPostExecute(String result) {
            Log.e("Upload", "Response from server: " + result);

            // showing the server response in an alert dialog
            showAlert(result);

            super.onPostExecute(result);
        }

    }

    /**
     * Method to show alert dialog
     */
    private void showAlert(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message).setTitle("Response from Servers")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // do nothing
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

}
