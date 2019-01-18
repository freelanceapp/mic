package com.mic.music.mic.VideoRecord;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Config;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.mic.music.mic.Api.ApiClient;
import com.mic.music.mic.Api.ApiService;
import com.mic.music.mic.R;
import com.mic.music.mic.Responce.VideoResponce;
import com.mic.music.mic.VideoUpload.Activity_galleryview;
import com.mic.music.mic.VideoUpload.UploadVideoActivity;
import com.mic.music.mic.VideoUpload.VideoFolder;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO;
import static com.mic.music.mic.MainActivity.u_id;
import static com.mic.music.mic.SplashScreen.ALLOW_KEY;
import static com.mic.music.mic.SplashScreen.CAMERA_PREF;
import static com.mic.music.mic.SplashScreen.MY_PERMISSIONS_REQUEST_CAMERA;

public class VideoRecordActivity extends AppCompatActivity implements View.OnClickListener {
    VideoView videoView;
    Uri videoFileUri;
    public static int VIDEO_CAPTURED = 1;
    ProgressBar progressBar;
    Button captureVideoButton,playVideoButton,captureWithoutDataVideoButton;
    TextView submit_btn;
    ProgressDialog pDialog;
    String videoname;
    File file;
    private static final int REQUEST_PERMISSIONS = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_record);
        playVideoButton = (Button) this.findViewById(R.id.PlayVideoButton);
        videoView = (VideoView) this.findViewById(R.id.VideoView);
        progressBar = (ProgressBar)this.findViewById(R.id.record_progress);
        submit_btn = (TextView)this.findViewById(R.id.submit_btn);
        pDialog = new ProgressDialog(this);
        Intent captureVideoIntent =new Intent(android.provider.MediaStore.ACTION_VIDEO_CAPTURE);
        startActivityForResult(captureVideoIntent,VIDEO_CAPTURED);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (getFromPref(this, ALLOW_KEY)) {
                showSettingsAlert();
                } else if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    showAlert();
                } else {
                    // No explanation needed, we can request the permission.
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            MY_PERMISSIONS_REQUEST_CAMERA);
                }
            }
        } else {
           /* Intent captureVideoIntent1 =new Intent(android.provider.MediaStore.ACTION_VIDEO_CAPTURE);
            startActivityForResult(captureVideoIntent1,VIDEO_CAPTURED);*/
        }

        playVideoButton.setOnClickListener(this);
        submit_btn.setOnClickListener(this);
    }


    public static void saveToPreferences(Context context, String key,
                                         Boolean allowed) {
        SharedPreferences myPrefs = context.getSharedPreferences
                (CAMERA_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = myPrefs.edit();
        prefsEditor.putBoolean(key, allowed);
        prefsEditor.commit();
    }

    public static Boolean getFromPref(Context context, String key) {
        SharedPreferences myPrefs = context.getSharedPreferences
                (CAMERA_PREF, Context.MODE_PRIVATE);
        return (myPrefs.getBoolean(key, false));
    }

    private void showAlert() {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("App needs to access the Camera.");
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "DONT ALLOW",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "ALLOW",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        ActivityCompat.requestPermissions(VideoRecordActivity.this,
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                MY_PERMISSIONS_REQUEST_CAMERA);

                    }
                });
        alertDialog.show();
    }

    private void showSettingsAlert() {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("App needs to access the Camera.");
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "DONT ALLOW",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        //finish();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "SETTINGS",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        startInstalledAppDetailsActivity(VideoRecordActivity.this);

                    }
                });
        alertDialog.show();
    }


    @Override
    public void onRequestPermissionsResult
            (int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA: {
                for (int i = 0, len = permissions.length; i < len; i++) {
                    String permission = permissions[i];
                    if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                        boolean showRationale =
                                ActivityCompat.shouldShowRequestPermissionRationale
                                        (this, permission);
                        if (showRationale) {
                            showAlert();
                        } else if (!showRationale) {
                            // user denied flagging NEVER ASK AGAIN
                            // you can either enable some fall back,
                            // disable features of your app
                            // or open another dialog explaining
                            // again the permission and directing to
                            // the app setting
                            saveToPreferences(VideoRecordActivity.this, ALLOW_KEY, true);
                        }
                    }
                }
            }

            // other 'case' lines to check for other
            // permissions this app might request

        }
    }

    public static void startInstalledAppDetailsActivity(final Activity context) {
        if (context == null) {
            return;
        }
        final Intent i = new Intent();
        i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        i.addCategory(Intent.CATEGORY_DEFAULT);
        i.setData(Uri.parse("package:" + context.getPackageName()));
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        context.startActivity(i);
    }

    private void openCamera() {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        startActivity(intent);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == VIDEO_CAPTURED) {
            videoFileUri = data.getData();
            Log.e("Url","..."+videoFileUri);
            videoname = getPath(videoFileUri);
            file = new File(getPath(videoFileUri));
            Log.e("Url Path...","###..."+videoname);
            playVideoButton.setEnabled(true);
        }
    }
    public String getPath(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = getContentResolver().query(
                android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
        cursor.close();

        return path;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.PlayVideoButton :
                Log.e("Url Path","..."+videoname);
                //videoView.setVideoURI(videoFileUri);
                videoView.setVideoPath(videoname);
                videoView.start();
                break;

            case R.id.submit_btn :
                //ApiCallkyc();

                Intent intent_gallery = new Intent(VideoRecordActivity.this,Activity_galleryview.class);
                intent_gallery.putExtra("video",videoname);
                startActivity(intent_gallery);
                break;
            }

        }

    public void ApiCallkyc() {

        RequestBody competition = RequestBody.create(MediaType.parse("text/plain"), "music");
        RequestBody level = RequestBody.create(MediaType.parse("text/plain"), "1");
        RequestBody participet = RequestBody.create(MediaType.parse("text/plain"), u_id);
        RequestBody r_video = RequestBody.create(MediaType.parse("video/mp4"), file);

        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part video2 = MultipartBody.Part.createFormData("file", file.getName(),r_video);
        // add another part within the multipart request

        ApiService apiInterface1 = ApiClient.getClient().create(ApiService.class);
        Call<VideoResponce> call = apiInterface1.uploadVideo(competition,level,participet,video2);
        Log.e("Response", "...." + competition + "..........");
        Log.e("Response lat", "...." + video2.toString() + "..........");
        Log.e("Response log", "...." + participet.toString() + "..........");

        progressBar.setVisibility(View.VISIBLE);
        pDialog.setMessage("Doing something, please wait.");
        pDialog.show();
        call.enqueue(new Callback<VideoResponce>() {
            @Override
            public void onResponse(Call<VideoResponce> call, Response<VideoResponce> response) {
                VideoResponce imageResponce = response.body();
                Log.e("...Massage...", "..." + imageResponce.getMessage());
                Log.e("...URI...", "..." + imageResponce.getUrl());
                //Toast.makeText(VideoRecordActivity.this,imageResponce.getMessage(),Toast.LENGTH_SHORT).show();

                progressBar.setVisibility(View.GONE);
                pDialog.dismiss();
            }
            @Override
            public void onFailure(Call<VideoResponce> call, Throwable t) {
                Log.e("...Throwable...", "..." + t.getMessage());
                Log.e("...Throwable...", "..." + t.toString());
                progressBar.setVisibility(View.GONE);
                pDialog.dismiss();
            }
        });
    }


}
