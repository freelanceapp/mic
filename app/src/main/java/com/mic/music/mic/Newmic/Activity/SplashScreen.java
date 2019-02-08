package com.mic.music.mic.Newmic.Activity;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.mic.music.mic.R;
import com.mic.music.mic.constant.Constant;
import com.mic.music.mic.model.User;
import com.mic.music.mic.model.appversion_responce.AppVersion;
import com.mic.music.mic.model.otp_responce.OtpModel;
import com.mic.music.mic.retrofit_provider.RetrofitService;
import com.mic.music.mic.retrofit_provider.WebResponse;
import com.mic.music.mic.utils.Alerts;
import com.mic.music.mic.utils.AppPreference;
import com.mic.music.mic.utils.BaseActivity;
import com.mic.music.mic.utils.ConnectionDetector;

import retrofit2.Response;

public class SplashScreen extends BaseActivity {
    private static final int MY_PERMISSIONS_REQUEST_CODE = 123;
    public String f_token;
    public String android_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        f_token = FirebaseInstanceId.getInstance().getToken();
        android_id = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        mContext = this;
        cd = new ConnectionDetector(mContext);
        retrofitRxClient = RetrofitService.getRxClient();
        retrofitApiClient = RetrofitService.getRetrofit();
        tokenApi();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkPermission();
        }else {
            testHandler();
        }
    }

    protected void checkPermission() {
        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA)
                + ContextCompat.checkSelfPermission(mContext, Manifest.permission.READ_CONTACTS)
                + ContextCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                + ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Do something, when permissions not granted
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) mContext, Manifest.permission.CAMERA) ||
                    ActivityCompat.shouldShowRequestPermissionRationale((Activity) mContext, Manifest.permission.READ_CONTACTS)
                    || ActivityCompat.shouldShowRequestPermissionRationale((Activity) mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    || ActivityCompat.shouldShowRequestPermissionRationale((Activity) mContext, Manifest.permission.ACCESS_FINE_LOCATION)) {
                // If we should give explanation of requested permissions
                // Show an alert dialog here with request explanation
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setMessage("Camera, Read Contacts, Write External and Location" +
                        " Storage permissions are required to do the task.");
                builder.setTitle("Please grant those permissions");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ActivityCompat.requestPermissions(
                                (Activity) mContext,
                                new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_CONTACTS,
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION
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
            } else {
                // Directly request for required permissions, without explanation
                ActivityCompat.requestPermissions(
                        (Activity) mContext,
                        new String[]{
                                Manifest.permission.CAMERA, Manifest.permission.READ_CONTACTS,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION
                        },
                        MY_PERMISSIONS_REQUEST_CODE
                );
            }
        } else {
            if (cd.isNetworkAvailable()) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (AppPreference.getBooleanPreference(mContext, Constant.Is_Login)) {
                            Gson gson = new Gson();
                            String userData = AppPreference.getStringPreference(mContext, Constant.User_Data);
                            OtpModel loginModal = gson.fromJson(userData, OtpModel.class);
                            User.setUser(loginModal);
                            Intent i = new Intent(SplashScreen.this, HomeActivity.class);
                            startActivity(i);
                            finish();
                        } else {
                            Intent i = new Intent(SplashScreen.this, Mobile_Ragistration.class);
                            startActivity(i);
                            finish();
                        }
                    }
                }, 3000);
            } else {
                cd.show(mContext);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CODE: {
                // When request is cancelled, the results array are empty
                if ((grantResults.length > 0) &&
                                (grantResults[0]
                                        + grantResults[1]
                                        + grantResults[2]
                                        + grantResults[3]
                                        == PackageManager.PERMISSION_GRANTED)) {
                    // Permissions are granted
                    //  Toast.makeText(mContext, "Permissions granted.", Toast.LENGTH_SHORT).show();
                    testHandler();
                    // close this activity
                } else {
                    // Permissions are denied
                    Toast.makeText(mContext, "Permissions denied.", Toast.LENGTH_SHORT).show();
                    finish();
                }
                return;
            }
        }
    }

    private void testHandler(){
        if (cd.isNetworkAvailable()) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (AppPreference.getBooleanPreference(mContext, Constant.Is_Login)) {
                        Gson gson = new Gson();
                        String userData = AppPreference.getStringPreference(mContext, Constant.User_Data);
                        OtpModel loginModal = gson.fromJson(userData, OtpModel.class);
                        User.setUser(loginModal);
                        Intent i = new Intent(SplashScreen.this, HomeActivity.class);
                        startActivity(i);
                        finish();

                    } else {
                        Intent i = new Intent(SplashScreen.this, Mobile_Ragistration.class);
                        startActivity(i);
                        finish();
                    }
                }
            }, 3000);
        } else {
            cd.show(mContext);
        }
    }

    private void tokenApi() {
        if (cd.isNetworkAvailable()) {
            RetrofitService.appversion(new Dialog(mContext), retrofitApiClient.getapp(), new WebResponse() {
                @Override
                public void onResponseSuccess(Response<?> result) {
                    AppVersion loginModal = (AppVersion) result.body();
                    assert loginModal != null;
                    if (loginModal.getVersion().equals("1")) {
                       // Alerts.show(mContext, loginModal.getVersion());
                    } else {
                        Alerts.show(mContext, "Update App");
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
}
