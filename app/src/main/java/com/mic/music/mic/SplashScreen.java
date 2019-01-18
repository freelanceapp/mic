package com.mic.music.mic;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.mic.music.mic.Api.RequestHandler;
import com.mic.music.mic.Api.URLs;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission_group.CAMERA;


public class SplashScreen extends AppCompatActivity {
    public static String f_token;
    public static String android_id;
    String id = "0";
    public static String MyApp = "MicApp";
    ImageView s_logo;
    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 100;
    public static final String ALLOW_KEY = "ALLOWED";
    public static final String CAMERA_PREF = "camera_pref";
    private View view;
    String virsion = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
       /* s_logo = (ImageView)findViewById(R.id.s_logo);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.flade);
        s_logo.startAnimation(animation);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
        {
            if (getFromPref(this, ALLOW_KEY)) {
                showSettingsAlert();
            } else if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.CAMERA)) {
                    showAlert();
                } else {
                    // No explanation needed, we can request the permission.
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.CAMERA},
                            MY_PERMISSIONS_REQUEST_CAMERA);
                }
            }
        } else {
           // openCamera();
            new Handler().postDelayed(new Runnable(){
                @Override
                public void run() {
                    SharedPreferences prefs1 = getSharedPreferences(MyApp, 0);
                    virsion = prefs1.getString("version", "0");
                    if (virsion.equals("5")) {
                       // Toast.makeText(SplashScreen.this, "MIC App", Toast.LENGTH_LONG).show();
                        f_token = FirebaseInstanceId.getInstance().getToken();
                        // Toast.makeText(SplashScreenActivity.this, "Current token ["+f_token+"]", Toast.LENGTH_LONG).show();
                        Log.e("App", "Token ["+f_token+"]");
                        android_id = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
                        Log.e("App", "Device ["+android_id+"]");
                        SharedPreferences prefs = getSharedPreferences(MyApp, 0);
                        id = prefs.getString("id", "0");
                        if (id.equals("0"))
                        {
                            Intent mainIntent = new Intent(SplashScreen.this,LoginActivity.class);
                            SplashScreen.this.startActivity(mainIntent);
                            SplashScreen.this.finish();
                        }else {
                            Intent mainIntent = new Intent(SplashScreen.this,MainActivity.class);
                            SplashScreen.this.startActivity(mainIntent);
                            SplashScreen.this.finish();
                        }

                    }else {
                        // Toast.makeText(SplashScreen.this, "Please Update MIC App", Toast.LENGTH_LONG).show();
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(SplashScreen.this);
                        builder1.setMessage("Please Update MIC App");
                        builder1.setCancelable(true);

                        builder1.setPositiveButton(
                                "Yes",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.mic.music.mic")));
                                        dialog.cancel();
                                    }
                                });

                        builder1.setNegativeButton(
                                "No",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        finish();
                                        dialog.cancel();
                                    }
                                });

                        AlertDialog alert11 = builder1.create();
                        alert11.show();
                    }


                     //Create an Intent that will start the Menu-Activity.

                }
            }, 2500);
        }
        AppVersion version = new AppVersion();
        version.execute();



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
                        ActivityCompat.requestPermissions(SplashScreen.this,
                                new String[]{Manifest.permission.CAMERA},
                                MY_PERMISSIONS_REQUEST_CAMERA);

                        new Handler().postDelayed(new Runnable(){
                            @Override
                            public void run() {
                                SharedPreferences prefs1 = getSharedPreferences(MyApp, 0);
                                virsion = prefs1.getString("version", "0");
                                if (virsion.equals("3")) {
                                    Toast.makeText(SplashScreen.this, "MIC App", Toast.LENGTH_LONG).show();
                                    f_token = FirebaseInstanceId.getInstance().getToken();
                                    // Toast.makeText(SplashScreenActivity.this, "Current token ["+f_token+"]", Toast.LENGTH_LONG).show();
                                    Log.e("App", "Token ["+f_token+"]");
                                    android_id = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
                                    Log.e("App", "Device ["+android_id+"]");
                                    SharedPreferences prefs = getSharedPreferences(MyApp, 0);
                                    id = prefs.getString("id", "0");
                                    if (id.equals("0"))
                                    {
                                        Intent mainIntent = new Intent(SplashScreen.this,LoginActivity.class);
                                        SplashScreen.this.startActivity(mainIntent);
                                        SplashScreen.this.finish();
                                    }else {
                                        Intent mainIntent = new Intent(SplashScreen.this,MainActivity.class);
                                        SplashScreen.this.startActivity(mainIntent);
                                        SplashScreen.this.finish();
                                    }

                                }else {
                                   // Toast.makeText(SplashScreen.this, "Please Update MIC App", Toast.LENGTH_LONG).show();
                                    AlertDialog.Builder builder1 = new AlertDialog.Builder(SplashScreen.this);
                                    builder1.setMessage("Please Update MIC App");
                                    builder1.setCancelable(true);

                                    builder1.setPositiveButton(
                                            "Yes",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.mic.music.mic")));
                                                    dialog.cancel();
                                                }
                                            });

                                    builder1.setNegativeButton(
                                            "No",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    finish();
                                                    dialog.cancel();
                                                }
                                            });

                                    AlertDialog alert11 = builder1.create();
                                    alert11.show();
                                }


                                // Create an Intent that will start the Menu-Activity.

                            }
                        }, 2500);


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
                        startInstalledAppDetailsActivity(SplashScreen.this);


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
                            saveToPreferences(SplashScreen.this, ALLOW_KEY, true);
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

    class AppVersion extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            //creating request handler object
            RequestHandler requestHandler = new RequestHandler();
            //creating request parameters
            HashMap<String, String> params = new HashMap<>();
            //returing the response
            return requestHandler.sendPostRequest(URLs.URL_VIRSION, params);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //displaying the progress bar while user registers on the server
            //progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //hiding the progressbar after completion
            //progressBar.setVisibility(View.GONE);

            try {
                //converting response to json object
                JSONObject obj = new JSONObject(s);
                //if no error in response
                  //  Toast.makeText(SplashScreen.this, obj.getString("version"), Toast.LENGTH_SHORT).show();
                    Log.e("Login User ","..."+obj.getString("version"));
                SharedPreferences pref = getApplicationContext().getSharedPreferences(MyApp, 0); // 0 - for private mode
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("version",obj.getString("version"));
                editor.apply(); // commit changes


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }*/
    }

}
