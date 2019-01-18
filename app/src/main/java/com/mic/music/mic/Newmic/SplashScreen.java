package com.mic.music.mic.Newmic;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.mic.music.mic.LoginActivity;
import com.mic.music.mic.MainActivity;
import com.mic.music.mic.R;
import com.mic.music.mic.retrofit_provider.RetrofitService;
import com.mic.music.mic.utils.BaseActivity;
import com.mic.music.mic.utils.ConnectionDetector;

public class SplashScreen extends BaseActivity {

    public static String MY_PREFS_NAME = "SRIL_APP";
    private static final int MY_PERMISSIONS_REQUEST_CODE = 123;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        mContext = this;
        cd = new ConnectionDetector(mContext);
        retrofitRxClient = RetrofitService.getRxClient();
        retrofitApiClient = RetrofitService.getRetrofit();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            checkPermission();
        }

       /* new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(i);
                // close this activity
                finish();
            }
        }, 6000);*/

    }

    protected void checkPermission(){
        if(ContextCompat.checkSelfPermission(mContext,Manifest.permission.CAMERA) + ContextCompat.checkSelfPermission(
                mContext,Manifest.permission.READ_CONTACTS)
                + ContextCompat.checkSelfPermission(
                mContext,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){

            // Do something, when permissions not granted
            if(ActivityCompat.shouldShowRequestPermissionRationale(
                    (Activity) mContext,Manifest.permission.CAMERA)
                    || ActivityCompat.shouldShowRequestPermissionRationale(
                    (Activity) mContext,Manifest.permission.READ_CONTACTS)
                    || ActivityCompat.shouldShowRequestPermissionRationale(
                    (Activity) mContext,Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                // If we should give explanation of requested permissions
                // Show an alert dialog here with request explanation
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setMessage("Camera, Read Contacts and Write External" +
                        " Storage permissions are required to do the task.");
                builder.setTitle("Please grant those permissions");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ActivityCompat.requestPermissions(
                                (Activity) mContext,
                                new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_CONTACTS,
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                                },
                                MY_PERMISSIONS_REQUEST_CODE
                        );
                    }
                });
                builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }else{
                // Directly request for required permissions, without explanation
                ActivityCompat.requestPermissions(
                        (Activity) mContext,
                        new String[]{
                                Manifest.permission.CAMERA,
                                Manifest.permission.READ_CONTACTS,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                        },
                        MY_PERMISSIONS_REQUEST_CODE
                );
            }
        }else {
            // Do something, when permissions are already granted
            Toast.makeText(mContext,"Permissions already granted",Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    // This method will be executed once the timer is over
                    // Start your app main activity
                    Intent i = new Intent(mContext, Mobile_Ragistration.class);
                    startActivity(i);
                    // close this activity
                    finish();
                }
            }, 3000);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
        switch (requestCode){
            case MY_PERMISSIONS_REQUEST_CODE:{
                // When request is cancelled, the results array are empty
                if(
                        (grantResults.length >0) &&
                                (grantResults[0]
                                        + grantResults[1]
                                        + grantResults[2]
                                        == PackageManager.PERMISSION_GRANTED
                                )
                        ){
                    // Permissions are granted
                    Toast.makeText(mContext,"Permissions granted.",Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(mContext, Mobile_Ragistration.class);
                    startActivity(i);
                    // close this activity
                    finish();
                }else {
                    // Permissions are denied
                    Toast.makeText(mContext,"Permissions denied.", Toast.LENGTH_SHORT).show();
                    finish();
                }
                return;
            }
        }
    }

}
