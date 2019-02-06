package com.mic.music.mic.VideoUpload;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import android.widget.VideoView;

import com.mic.music.mic.R;
import life.knowledge4.videotrimmer.K4LVideoTrimmer;
import life.knowledge4.videotrimmer.interfaces.OnTrimVideoListener;


public class Activity_galleryview extends Activity implements OnTrimVideoListener {

    String str_video;
   // VideoView vv_video;
    private K4LVideoTrimmer mVideoTrimmer;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galleryview);
        init();
    }

    private void init() {

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setMessage("Start progress");


        mVideoTrimmer = ((K4LVideoTrimmer) findViewById(R.id.timeLine));
        str_video = getIntent().getStringExtra("video");
       /* vv_video.setVideoPath(str_video);
        vv_video.start();*/

        if (mVideoTrimmer != null) {
            mVideoTrimmer.setMaxDuration(90);
            mVideoTrimmer.setOnTrimVideoListener(this);
            mVideoTrimmer.getDrawingTime();
            //mVideoTrimmer.setOnK4LVideoListener(this);
            //mVideoTrimmer.setDestinationPath("/storage/emulated/0/DCIM/CameraCustom/");
            mVideoTrimmer.setVideoURI(Uri.parse(str_video));
            //mVideoTrimmer.setVideoInformationVisibility(true);
        }

    }

   /* @Override
    public void onTrimStarted() {
        mProgressDialog.show();
    }*/

    @Override
    public void getResult(final Uri uri) {
        mProgressDialog.cancel();

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
               // Toast.makeText(Activity_galleryview.this, getString(R.string.video_saved_at, uri.getPath()), Toast.LENGTH_SHORT).show();
            }
        });
        Log.e("Video Path ","..."+uri.getPath());
        Intent intent_gallery = new Intent(Activity_galleryview.this,UploadVideoActivity.class);
        intent_gallery.putExtra("video1",uri.getPath());
        startActivity(intent_gallery);

       /* Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.setDataAndType(uri, "video/mp4");
        startActivity(intent);*/
        finish();
    }

    @Override
    public void cancelAction() {
        mProgressDialog.cancel();
        mVideoTrimmer.destroy();
        finish();
    }

    /*@Override
    public void onError(final String message) {
        mProgressDialog.cancel();

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(Activity_galleryview.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onVideoPrepared() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(Activity_galleryview.this, "onVideoPrepared", Toast.LENGTH_SHORT).show();
            }
        });
    }*/

}
