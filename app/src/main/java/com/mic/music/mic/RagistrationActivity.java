package com.mic.music.mic;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mic.music.mic.Api.RequestHandler;
import com.mic.music.mic.Api.URLs;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.mic.music.mic.SplashScreen.MyApp;

public class RagistrationActivity extends AppCompatActivity implements View.OnClickListener {
    TextView done_btn,et_dob;
    EditText et_name,et_email;
    RadioGroup grendar;
    private Pattern pattern;
    private Matcher matcher;
    String strGendar,email,dob,name,user_id;
    ProgressBar rg_progress;
    private DatePickerDialog fromDatePickerDialog;
    ImageView calender_btn;
    private SimpleDateFormat dateFormatter;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private static final String DATE_PATTERN =
            "(0?[1-9]|1[012]) [/.-] (0?[1-9]|[12][0-9]|3[01]) [/.-] ((20)\\d\\d)";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ragistration);
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        et_name = (EditText)findViewById(R.id.et_name);
        et_email = (EditText)findViewById(R.id.et_email);
        et_dob = (TextView)findViewById(R.id.et_dob);
        grendar = (RadioGroup)findViewById(R.id.gender);
        calender_btn = (ImageView)findViewById(R.id.calender_btn);
        rg_progress = (ProgressBar)findViewById(R.id.rg_progress);
        grendar.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    Toast.makeText(RagistrationActivity.this, rb.getText(), Toast.LENGTH_SHORT).show();
                    strGendar = rb.getText().toString();
                }
            }
        });

        calender_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fromDatePickerDialog.show();

            }
        });

        setDateTimeField();

        SharedPreferences prefs = getSharedPreferences(MyApp, 0);
        user_id = prefs.getString("id", "0");
        done_btn = (TextView)findViewById(R.id.done_btn);
        done_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = et_email.getText().toString();
                name = et_name.getText().toString();
                dob = et_dob.getText().toString();
                matcher = Pattern.compile(DATE_PATTERN).matcher(dob);

                if (!email.matches(emailPattern))
                {
                    et_email.setError("Enter Email ID");
                }
                else if (name.equals(""))
                {
                    et_name.setError("Enter user name");
                }else if (dob.equals(""))
                {
                    et_dob.setError("Enter DOB");
                }  /*else if (!matcher.matches()) {
                    Toast.makeText(RagistrationActivity.this,"Invalid Birthday!",Toast.LENGTH_SHORT).show();
                }*/
                else {

                    SharedPreferences pref = getApplicationContext().getSharedPreferences(MyApp, 0); // 0 - for private mode
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("name",et_name.getText().toString());
                    editor.putString("gendar",strGendar);
                    editor.putString("dob",et_dob.getText().toString());
                    editor.putString("email",email);
                    editor.apply(); // commit changes

                    ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                    if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                            connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                        //we are connected to a network
                        RagisterUser ragisterUser = new RagisterUser();
                        ragisterUser.execute();
                    }else {
                        Toast.makeText(RagistrationActivity.this,"No Internet",Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
    }

    private void setDateTimeField() {
        et_dob.setOnClickListener(this);
        String date = "1-1-2000";
        String parts[] = date.split("-");

        int day = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);
        int year = Integer.parseInt(parts[2]);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);

        fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                et_dob.setText(dateFormatter.format(newDate.getTime()));
            }

        },calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));


    }

    @Override
    public void onClick(View view) {
        fromDatePickerDialog.show();
    }


    class RagisterUser extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            //creating request handler object
            RequestHandler requestHandler = new RequestHandler();

            //creating request parameters
            HashMap<String, String> params = new HashMap<>();
            params.put("username", name);
            params.put("email", email);
            params.put("gendar", strGendar);
            params.put("user_id",user_id);
            params.put("user_dob", dob);
            //returing the response
            return requestHandler.sendPostRequest(URLs.URL_REGISTER, params);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //displaying the progress bar while user registers on the server
            rg_progress.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //hiding the progressbar after completion
            rg_progress.setVisibility(View.GONE);

            try {
                //converting response to json object
                JSONObject obj = new JSONObject(s);
                //if no error in response
                if (!obj.getBoolean("error")) {

                   // Toast.makeText(RagistrationActivity.this, obj.getString("message"), Toast.LENGTH_SHORT).show();
                    //Log.e("Login User ","..."+obj.getString("user"));
                    Intent intent = new Intent(RagistrationActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(RagistrationActivity.this, obj.getString("message"), Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
