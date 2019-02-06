package com.mic.music.mic.VideoRecord;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.mic.music.mic.R;
import com.mic.music.mic.VideoUpload.UploadVideoActivity;

import life.knowledge4.videotrimmer.K4LVideoTrimmer;
import life.knowledge4.videotrimmer.interfaces.OnTrimVideoListener;


public class TrimVideoActivity extends Activity implements OnTrimVideoListener {

    String str_video;
    // VideoView vv_video;
    String mCurrentPhotoPath;
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
        mVideoTrimmer.setMaxDuration(90);
        str_video = getIntent().getStringExtra("filePath");
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
                //Toast.makeText(TrimVideoActivity.this, getString(R.string.video_saved_at, uri.getPath()), Toast.LENGTH_SHORT).show();
            }
        });

        //mCurrentPhotoPath = uri.getPath();
        //new VideoCompressAsyncTask(this).execute(mCurrentPhotoPath, uri.getPath());
        Log.e("Video Path ", "..." + uri.getPath());
        Intent intent_gallery = new Intent(TrimVideoActivity.this, UploadVideoActivity.class);
        intent_gallery.putExtra("video1", uri.getPath());
        startActivity(intent_gallery);
        finish();


       /* Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.setDataAndType(uri, "video/mp4");
        startActivity(intent);*/

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


   /* class VideoCompressAsyncTask extends AsyncTask<String, String, String> {
        Context mContext;
        public VideoCompressAsyncTask(Context context) {
            mContext = context;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
           // imageView.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_photo_camera_white_48px));
            *//*compressionMsg.setVisibility(View.VISIBLE);
            picDescription.setVisibility(View.GONE);*//*
        }
        @Override
        protected String doInBackground(String... paths) {
            String filePath = null;
            try {
                filePath = KplCompressor.with(mContext).compressVideo(paths[0], paths[1]);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            return filePath;
        }
        @Override
        protected void onPostExecute(String compressedFilePath) {
            super.onPostExecute(compressedFilePath);
            File imageFile = new File(compressedFilePath);
            float length = imageFile.length() / 1024f; // Size in KB
            String value;
            if (length >= 1024)
                value = length / 1024f + " MB";
            else
                value = length + " KB";
            String text = String.format(Locale.US, "%s\nName: %s\nSize: %s", getString(R.string.video_compression_complete), imageFile.getName(), value);
            *//*compressionMsg.setVisibility(View.GONE);
            picDescription.setVisibility(View.VISIBLE);
            picDescription.setText(text);*//*
            Log.i("Silicompressor", "Path: " + compressedFilePath);

        }
    }*/

}
