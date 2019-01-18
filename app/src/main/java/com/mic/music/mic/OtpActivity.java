package com.mic.music.mic;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mic.music.mic.Api.RequestHandler;
import com.mic.music.mic.Api.URLs;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import static com.mic.music.mic.LoginActivity.number;
import static com.mic.music.mic.SplashScreen.MyApp;
import static com.mic.music.mic.SplashScreen.android_id;
import static com.mic.music.mic.SplashScreen.f_token;

public class OtpActivity extends AppCompatActivity {

    String m_number,otp,user_type;
    EditText et_otp;
    TextView otp_btn,signup_btn;
    ProgressBar progressBar;
    String P_id ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        et_otp = (EditText)findViewById(R.id.et_otp);
        otp_btn = (TextView)findViewById(R.id.otp_btn);
        progressBar = (ProgressBar)findViewById(R.id.otp_progress);

        SharedPreferences prefs = getSharedPreferences(MyApp, 0);
        m_number = prefs.getString("mobile", null);
        user_type = prefs.getString("user_type",null);

        otp_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                otp = et_otp.getText().toString();
                if (otp.equals(""))
                {
                    et_otp.setError("Please Enter OTP");
                }else if (otp.length() > 6 || number.length() < 6)
                {
                    et_otp.setError("Enter Vailed OTP");
                }
                else {

                    ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                    if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                            connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                        //we are connected to a network
                        OtpUser ru = new OtpUser();
                        ru.execute();
                    }else {
                        Toast.makeText(OtpActivity.this,"No Internet",Toast.LENGTH_SHORT).show();
                    }


                }
            }
        });

        signup_btn = (TextView)findViewById(R.id.signup_btn);
        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ResendOtp resendOtp = new ResendOtp();
                resendOtp.execute();
            }
        });

    }

    class OtpUser extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            //creating request handler object
            RequestHandler requestHandler = new RequestHandler();

            //creating request parameters
            HashMap<String, String> params = new HashMap<>();
            params.put("mobile_number", m_number);
            params.put("opt_number", otp);

            //returing the response
            return requestHandler.sendPostRequest(URLs.URL_OTP, params);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //displaying the progress bar while user registers on the server
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //hiding the progressbar after completion
            progressBar.setVisibility(View.GONE);

            try {
                //converting response to json object
                JSONObject obj = new JSONObject(s);
                //if no error in response
                if (!obj.getBoolean("error")) {

                   // Toast.makeText(OtpActivity.this, obj.getString("message"), Toast.LENGTH_SHORT).show();
                    Log.e("Login User ","..."+obj.getString("user_type"));

                    //getting the user from the response
                    JSONObject userJson = obj.getJSONObject("user");
                    userJson.getString("participant_id");
                    userJson.getString("participant_name");
                    userJson.getString("participant_gendar");
                    userJson.getString("participant_dob");
                    userJson.getString("participant_mobile_number");
                    userJson.getString("participant_registration_date");

                    P_id = userJson.getString("participant_id");
                    SharedPreferences pref = getApplicationContext().getSharedPreferences(MyApp, 0); // 0 - for private mode
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("id",userJson.getString("participant_id"));
                    editor.putString("name",userJson.getString("participant_name"));
                    editor.putString("gendar",userJson.getString("participant_gendar"));
                    editor.putString("dob",userJson.getString("participant_dob"));
                    editor.putString("register_date",userJson.getString("participant_registration_date"));
                    editor.putString("mobile", userJson.getString("participant_mobile_number")); // Storing string
                    editor.apply(); // commit changes

                    FbToken ru = new FbToken();
                    ru.execute();

                    if (user_type.equals("registered user"))
                    {
                        Intent intent = new Intent(OtpActivity.this,MainActivity.class);
                        intent.putExtra("User_id",userJson.getString("participant_id"));
                        startActivity(intent);
                        finish();
                    }else {
                        Intent intent = new Intent(OtpActivity.this, RagistrationActivity.class);
                        intent.putExtra("User_id", userJson.getString("participant_id"));
                        startActivity(intent);
                        finish();
                    }
                } else {
                    Toast.makeText(OtpActivity.this, obj.getString("message"), Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    class FbToken extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            //creating request handler object
            RequestHandler requestHandler = new RequestHandler();

            //creating request parameters
            HashMap<String, String> params = new HashMap<>();
            params.put("user_ip", android_id);
            params.put("token",f_token);
            params.put("participant_id",P_id);

            //returing the response
            return requestHandler.sendPostRequest(URLs.URL_TOKEN, params);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //displaying the progress bar while user registers on the server
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //hiding the progressbar after completion
            progressBar.setVisibility(View.GONE);

            try {
                //converting response to json object
                JSONObject obj = new JSONObject(s);
                //if no error in response
                if (!obj.getBoolean("error")) {

                    //Toast.makeText(OtpActivity.this, obj.getString("message"), Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(OtpActivity.this, obj.getString("message"), Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    class ResendOtp extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            //creating request handler object
            RequestHandler requestHandler = new RequestHandler();

            //creating request parameters
            HashMap<String, String> params = new HashMap<>();
            params.put("mobile_number", m_number);

            //returing the response
            return requestHandler.sendPostRequest(URLs.URL_LOGIN, params);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //displaying the progress bar while user registers on the server
            progressBar.setVisibility(View.VISIBLE);
            Toast.makeText(OtpActivity.this,"Resend Number "+m_number,Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //hiding the progressbar after completion
            progressBar.setVisibility(View.GONE);

            try {
                //converting response to json object
                JSONObject obj = new JSONObject(s);
                //if no error in response
                if (!obj.getBoolean("error")) {

                    Toast.makeText(OtpActivity.this, obj.getString("message"), Toast.LENGTH_SHORT).show();
                    Log.e("Login User ","..."+obj.getString("user"));
                } else {
                    Toast.makeText(OtpActivity.this, obj.getString("message"), Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
