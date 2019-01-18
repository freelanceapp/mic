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

import com.googlecode.mp4parser.authoring.Edit;
import com.mic.music.mic.Api.RequestHandler;
import com.mic.music.mic.Api.URLs;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import static com.mic.music.mic.SplashScreen.MyApp;

public class LoginActivity extends AppCompatActivity {
    TextView login_btn,signup_btn;
    ProgressBar progressBar;
    EditText et_number;
    public static String number;
    boolean connected = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        login_btn = (TextView)findViewById(R.id.login_btn);
        signup_btn = (TextView)findViewById(R.id.signup_btn);
        et_number = (EditText) findViewById(R.id.et_number);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                number = et_number.getText().toString();
                if (number.equals(""))
                {
                    et_number.setError("Enter Vailed Number");
                }else if (number.length() > 10 || number.length() < 10)
                {
                    et_number.setError("Enter Vailed Number");
                }
                else {
                    ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                    if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                            connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                        //we are connected to a network
                        LoginUser ru = new LoginUser();
                        ru.execute();
                    }else {
                        Toast.makeText(LoginActivity.this,"No Internet",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,RagistrationActivity.class);
                startActivity(intent);
            }
        });
    }

    class LoginUser extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            //creating request handler object
            RequestHandler requestHandler = new RequestHandler();

            //creating request parameters
            HashMap<String, String> params = new HashMap<>();
            params.put("mobile_number", number);

            //returing the response
            return requestHandler.sendPostRequest(URLs.URL_LOGIN, params);
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

                   // Toast.makeText(LoginActivity.this, obj.getString("message"), Toast.LENGTH_SHORT).show();
                    Log.e("Login User ","..."+obj.getString("user"));
                    SharedPreferences pref = getApplicationContext().getSharedPreferences(MyApp, 0); // 0 - for private mode
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("mobile", number); // Storing string
                    editor.putString("user_type", obj.getString("user"));// Storing integer
                    editor.apply(); // commit changes
                    Intent intent = new Intent(LoginActivity.this,OtpActivity.class);
                    intent.putExtra("number",number);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, obj.getString("message"), Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
