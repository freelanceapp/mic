package com.mic.music.mic.VideoRecord;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;


import com.mic.music.mic.R;
import com.mic.music.mic.Responce.VideoResponce;
import com.mic.music.mic.VideoUpload.Activity_galleryview;
import com.mic.music.mic.constant.Constant;
import com.mic.music.mic.retrofit_provider.RetrofitService;
import com.mic.music.mic.retrofit_provider.WebResponse;
import com.mic.music.mic.upload_with_progress.ProgressRequestBody;
import com.mic.music.mic.utils.Alerts;
import com.mic.music.mic.utils.AppPreference;
import com.mic.music.mic.utils.BaseActivity;
import com.mic.music.mic.utils.ConnectionDetector;
import com.mic.music.mic.video_compressor_classes.SiliCompressor;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;

import static com.mic.music.mic.Newmic.Activity.HomeActivity.user_id;

public class SelectPictureActivity extends BaseActivity implements ProgressRequestBody.UploadCallbacks{

    public static final String LOG_TAG = SelectPictureActivity.class.getSimpleName();

    public static final String FILE_PROVIDER_AUTHORITY = "com.mic.music.mic.provider";
    private static final int REQUEST_TAKE_CAMERA_PHOTO = 1;
    private static final int MY_PERMISSIONS_REQUEST_WRITE_STORAGE = 1;
    private static final int MY_PERMISSIONS_REQUEST_WRITE_STORAGE_VID = 2;
    private static final int REQUEST_TAKE_VIDEO = 200;
    private static final int TYPE_IMAGE = 1;
    private static final int TYPE_VIDEO = 2;

    String mCurrentPhotoPath;
    Uri capturedUri = null;
    Uri compressUri = null;
    ImageView imageView;
    TextView picDescription;
    private ImageView videoImageView;
    LinearLayout compressionMsg;
    VideoView VideoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_picture);
        mContext = this;
        cd = new ConnectionDetector(mContext);
        retrofitRxClient = RetrofitService.getRxClient();
        retrofitApiClient = RetrofitService.getRetrofit();
        imageView = (ImageView) findViewById(R.id.photo);
        videoImageView = (ImageView) findViewById(R.id.videoImageView);
        picDescription = (TextView) findViewById(R.id.pic_description);
        compressionMsg = (LinearLayout) findViewById(R.id.compressionMsg);
        VideoView = (VideoView) findViewById(R.id.VideoView);


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPermissions(TYPE_IMAGE);
            }
        });

        videoImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestPermissions(TYPE_VIDEO);
            }
        });
    }

    /**
     * Request Permission for writing to External Storage in 6.0 and up
     */
    private void requestPermissions(int mediaType) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (mediaType == TYPE_IMAGE) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_WRITE_STORAGE);
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_WRITE_STORAGE_VID);
            }

        } else {
            if (mediaType == TYPE_IMAGE) {
                // Want to compress an image
                dispatchTakePictureIntent();
            } else if (mediaType == TYPE_VIDEO) {
                // Want to compress a video
                dispatchTakeVideoIntent();
            }

        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WRITE_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    dispatchTakePictureIntent();
                } else {
                    Toast.makeText(this, "You need to enable the permission for External Storage Write" +
                            " to test out this library.", Toast.LENGTH_LONG).show();
                    return;
                }
                break;
            }
            case MY_PERMISSIONS_REQUEST_WRITE_STORAGE_VID: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    dispatchTakeVideoIntent();
                } else {
                    Toast.makeText(this, "You need to enable the permission for External Storage Write" +
                            " to test out this library.", Toast.LENGTH_LONG).show();
                    return;
                }
                break;
            }
            default:
        }
    }

    private File createMediaFile(int type) throws IOException {

        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fileName = (type == TYPE_IMAGE) ? "JPEG_" + timeStamp + "_" : "VID_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                type == TYPE_IMAGE ? Environment.DIRECTORY_PICTURES : Environment.DIRECTORY_MOVIES);
        File file = File.createTempFile(
                fileName,  /* prefix */
                type == TYPE_IMAGE ? ".jpg" : ".mp4",         /* suffix */
                storageDir      /* directory */
        );

        // Get the path of the file created
        mCurrentPhotoPath = file.getAbsolutePath();
        Log.d(LOG_TAG, "mCurrentPhotoPath: " + mCurrentPhotoPath);
        return file;
    }

    private void dispatchTakePictureIntent() {
        /*Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");*/
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePictureIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createMediaFile(TYPE_IMAGE);
            } catch (IOException ex) {
                // Error occurred while creating the File
                Log.d(LOG_TAG, "Error occurred while creating the file");

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {

                // Get the content URI for the image file
                capturedUri = FileProvider.getUriForFile(this, FILE_PROVIDER_AUTHORITY, photoFile);

                Log.d(LOG_TAG, "Log1: " + String.valueOf(capturedUri));

                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, capturedUri);

                startActivityForResult(takePictureIntent, REQUEST_TAKE_CAMERA_PHOTO);

            }
        }
    }


    private void dispatchTakeVideoIntent() {
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        takeVideoIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
            try {
                takeVideoIntent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 120);
                takeVideoIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
                capturedUri = FileProvider.getUriForFile(this, FILE_PROVIDER_AUTHORITY, createMediaFile(TYPE_VIDEO));
                takeVideoIntent.putExtra(MediaStore.EXTRA_OUTPUT, capturedUri);
                Log.e(LOG_TAG, "VideoUri: " + capturedUri.toString());
                startActivityForResult(takeVideoIntent, REQUEST_TAKE_VIDEO);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }


    }

    // Method which will process the captured image
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //verify if the image was gotten successfully
        if (requestCode == REQUEST_TAKE_CAMERA_PHOTO && resultCode == Activity.RESULT_OK) {
            new ImageCompressionAsyncTask(this).execute(mCurrentPhotoPath,
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/Silicompressor/images");


        } else if (requestCode == REQUEST_TAKE_VIDEO && resultCode == RESULT_OK) {
            if (data.getData() != null) {
                //create destination directory
                File f = new File( Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES) + "/Silicompressor/videos");
                if (f.mkdirs() || f.isDirectory())
                    //compress and output new video specs
                    new VideoCompressAsyncTask(this).execute(mCurrentPhotoPath, f.getPath());

            }
        }

    }

    class ImageCompressionAsyncTask extends AsyncTask<String, Void, String> {

        Context mContext;

        public ImageCompressionAsyncTask(Context context) {
            mContext = context;
        }

        @Override
        protected String doInBackground(String... params) {

            String filePath = SiliCompressor.with(mContext).compress(params[0], new File(params[1]));
            return filePath;


            /*
            Bitmap compressBitMap = null;
            try {
                compressBitMap = SiliCompressor.with(mContext).getCompressBitmap(params[0], true);
                return compressBitMap;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return compressBitMap;

            */
        }

        @Override
        protected void onPostExecute(String s) {
            /*
            if (null != s){
                imageView.setImageBitmap(s);
                int compressHieght = s.getHeight();
                int compressWidth = s.getWidth();
                float length = s.getByteCount() / 1024f; // Size in KB;

                String text = String.format("Name: %s\nSize: %fKB\nWidth: %d\nHeight: %d", "ff", length, compressWidth, compressHieght);
                picDescription.setVisibility(View.VISIBLE);
                picDescription.setText(text);
            }
            */

            File imageFile = new File(s);
            compressUri = Uri.fromFile(imageFile);
            //FileProvider.getUriForFile(mContext, mContext.getApplicationContext().getPackageName()+ FILE_PROVIDER_EXTENTION, imageFile);


            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), compressUri);
                imageView.setImageBitmap(bitmap);

                String name = imageFile.getName();
                float length = imageFile.length() / 1024f; // Size in KB
                int compressWidth = bitmap.getWidth();
                int compressHieght = bitmap.getHeight();
                String text = String.format(Locale.US, "Name: %s\nSize: %fKB\nWidth: %d\nHeight: %d", name, length, compressWidth, compressHieght);
                picDescription.setVisibility(View.VISIBLE);
                picDescription.setText(text);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


    class VideoCompressAsyncTask extends AsyncTask<String, String, String> {

        Context mContext;

        public VideoCompressAsyncTask(Context context) {
            mContext = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
           // imageView.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_photo_camera_white_48px));
            compressionMsg.setVisibility(View.VISIBLE);
            picDescription.setVisibility(View.GONE);
        }

        @Override
        protected String doInBackground(String... paths) {
            String filePath = null;
            try {

                filePath = SiliCompressor.with(mContext).compressVideo(paths[0], paths[1]);

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
            String text = String.format(Locale.US, "%s\nName: %s\nSize: %s", getString(R.string.video_compression_complete), compressedFilePath, value);
            compressionMsg.setVisibility(View.GONE);
            picDescription.setVisibility(View.VISIBLE);
            picDescription.setText(text);
            Log.e("Silicompressor", "Path: " + compressedFilePath);
            MediaController mediaController= new MediaController(mContext);

            Uri uri = Uri.parse(compressedFilePath);
            VideoView.setVideoURI(uri);
            mediaController.setAnchorView(VideoView);
            VideoView.setMediaController(mediaController);
            VideoView.setVideoURI(uri);
            VideoView.requestFocus();
            VideoView.start();

            newPostFeedApi(compressedFilePath);

           /* Intent intent_gallery = new Intent(SelectPictureActivity.this, Activity_galleryview.class);
            intent_gallery.putExtra("video",compressedFilePath);
            startActivity(intent_gallery);
            finish();*/

        }
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
        picDescription.setText(percentage + "");
        if (picDescription.getText().equals("99")) {
            picDescription.setText("Finish");
        } else {

        }

    }

    @Override
    public void onError() {

    }

    @Override
    public void onFinish() {
        picDescription.setText("Finished");
    }

}
