package com.mic.music.mic.AudioUpload;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mic.music.mic.Adapter.AudioListAdapter;
import com.mic.music.mic.Adapter.EventAdapter;
import com.mic.music.mic.R;
import com.mic.music.mic.Responce.VideoResponce;
import com.mic.music.mic.constant.Constant;
import com.mic.music.mic.model.AudioModel;
import com.mic.music.mic.retrofit_provider.RetrofitService;
import com.mic.music.mic.retrofit_provider.WebResponse;
import com.mic.music.mic.upload_with_progress.ProgressRequestBody;
import com.mic.music.mic.utils.Alerts;
import com.mic.music.mic.utils.AppPreference;
import com.mic.music.mic.utils.BaseActivity;
import com.mic.music.mic.utils.ConnectionDetector;

import java.io.File;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;

public class AudioListActivity extends BaseActivity implements View.OnClickListener, ProgressRequestBody.UploadCallbacks {
    private Context mContext;
    private Activity mActivity;
    AlertDialog.Builder builder;
    private String urlAudio = "";
    private LinearLayout mRootLayout;
    private Button mButtonPlay;
    private TextView mResult;
    private ArrayList<AudioModel> audioModelArrayList = new ArrayList<>();
    private static final int MY_PERMISSION_REQUEST_CODE = 123;
    private AudioListAdapter adapter;
    private RecyclerView rvAudioList;
    private ImageView backBtn;
    private ProgressDialog progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.audio_list_activity);

        mContext = this;
        cd = new ConnectionDetector(mContext);
        retrofitRxClient = RetrofitService.getRxClient();
        retrofitApiClient = RetrofitService.getRetrofit();
        progressBar = new ProgressDialog(mContext);
        // Get the application context
        mContext = getApplicationContext();
        mActivity = AudioListActivity.this;
        builder = new AlertDialog.Builder(mContext);
        // Get the widget reference from xml layout
        mRootLayout = findViewById(R.id.root_layout);
        mButtonPlay = findViewById(R.id.btn_task);
        mResult = findViewById(R.id.result);
        rvAudioList = findViewById(R.id.rvAudioList);
        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        // Make text view scrollable
        mResult.setMovementMethod(new ScrollingMovementMethod());

        // Custom method to check permission at run time
        checkPermission();

        // Set a click listener for button
        mButtonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    // Custom method to get all audio files list from external storage
    protected void getMediaFileList(){
        mResult.setText("");
        // Get the content resolver
        ContentResolver contentResolver = mContext.getContentResolver();

        // Get the external storage uri of media store audio
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        mResult.append("URI : " + uri.toString());

        Cursor cursor = contentResolver.query(
                uri, // Uri
                null, // Projection
                null, // Selection
                null, // Selection args
                null // Sor order
        );

        if (cursor == null) {
            mResult.append("\n" +"Query failed, handle error.");
        } else if (!cursor.moveToFirst()) {
            // no media on the device
            mResult.append("\n" +"Nno music found on the sd card.");
        } else {

            int title = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int id = cursor.getColumnIndex(MediaStore.Audio.Media._ID);
            int url = cursor.getColumnIndex(MediaStore.Audio.Media.DATA);
            int album = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM);
            int artist = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            // Loop through the musics
            do {
                // Get the current audio file id
                long thisId = cursor.getLong(id);
                mResult.append("\n\n" +thisId);

                // Get the current audio file id
                String thisPath = cursor.getString(url);
                String thisAlbum = cursor.getString(album);
                String thisArtist = cursor.getString(artist);
                String thisTitle = cursor.getString(title);

                AudioModel audioModel = new AudioModel();
                audioModel.setaPath(thisPath);
                audioModel.setaName("Name : "+thisTitle);
                audioModel.setaArtist("Artist : "+thisArtist);
                audioModel.setaAlbum("Album : "+thisAlbum);
                audioModelArrayList.add(audioModel);
                //mResult.append("\n" +thisTitle);
                // Process current music here
            } while (cursor.moveToNext());


            adapter = new AudioListAdapter(AudioListActivity.this, audioModelArrayList, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = Integer.parseInt(view.getTag().toString());
                    AudioModel audioModel = audioModelArrayList.get(pos);
                    urlAudio = audioModel.getaPath();
                    Log.e("Url", "..."+urlAudio);
                    newPostFeedApi();
                }
            });
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(AudioListActivity.this);
            rvAudioList.setLayoutManager(mLayoutManager);
            rvAudioList.setItemAnimator(new DefaultItemAnimator());
            rvAudioList.setAdapter(adapter);
        }
    }

    protected void checkPermission(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                if(shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)){
                    // Show an alert dialog
                    AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
                    builder.setMessage("Read external storage permission is required.");
                    builder.setTitle("Please grant permission");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ActivityCompat.requestPermissions(
                                    mActivity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSION_REQUEST_CODE
                            );
                        }
                    });
                    builder.setNeutralButton("Cancel",null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }else {
                    // Request permission
                    ActivityCompat.requestPermissions(
                            mActivity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSION_REQUEST_CODE
                    );
                }
            }else {
                // Permission already granted
                getMediaFileList();


            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
        switch(requestCode){
            case MY_PERMISSION_REQUEST_CODE:{
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    // Permission granted
                    getMediaFileList();

                }else {
                    // Permission denied
                }
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {

        }
    }


    private void newPostFeedApi() {
        File file = new File(urlAudio);
        String strId = AppPreference.getStringPreference(getApplicationContext(), Constant.User_Id);
        String strCompanyId = AppPreference.getStringPreference(getApplicationContext(), Constant.COMPANY_ID);
        String strLevelId = AppPreference.getStringPreference(getApplicationContext(), Constant.LEVEL_ID);

        if (cd.isNetworkAvailable()) {
            RequestBody competition = RequestBody.create(MediaType.parse("text/plain"), strCompanyId);
            RequestBody level = RequestBody.create(MediaType.parse("text/plain"), strLevelId);
            RequestBody participet = RequestBody.create(MediaType.parse("text/plain"), strId);
            RequestBody type = RequestBody.create(MediaType.parse("text/plain"), "audio");
            ProgressRequestBody fileBody = new ProgressRequestBody(file, "video/*", this);
            MultipartBody.Part videoFileUpload = MultipartBody.Part.createFormData("file", file.getName(), fileBody);

            progressBar.setMessage("Uploading...");
            progressBar.setCancelable(true);
            progressBar.show();
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
                        AppPreference.setStringPreference(mContext, Constant.COMPANY_ID, "");

                        //finish();
                    }
                    progressBar.dismiss();
                }
                @Override
                public void onResponseFailed(String error) {
                    Alerts.show(mContext, error);
                    progressBar.dismiss();

                }
            });
        } else {
            cd.show(mContext);
        }
    }

    @Override
    public void onProgressUpdate(int percentage) {

      // Log.e("Loading..."," "+percentage);

    }

    @Override
    public void onError() {

    }

    @Override
    public void onFinish() {

    }
}
