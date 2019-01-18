package com.mic.music.mic.Fragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.mic.music.mic.MainActivity;
import com.mic.music.mic.R;
import com.mic.music.mic.RagistrationActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.mic.music.mic.MainActivity.u_dob;
import static com.mic.music.mic.MainActivity.u_email;
import static com.mic.music.mic.MainActivity.u_gender;
import static com.mic.music.mic.MainActivity.u_id;
import static com.mic.music.mic.MainActivity.u_mobile;
import static com.mic.music.mic.MainActivity.u_name;
import static com.mic.music.mic.SplashScreen.MyApp;


public class EditProfile extends Fragment {
    TextView done_btn;
    EditText et_name1,et_email1;
    private Pattern pattern;
    private Matcher matcher;
    TextView et_mobile1,et_dob;
    RadioGroup grendar1;
    String strGendar,email,dob,name,user_id;
    ProgressBar rg_progress;
    private DatePickerDialog fromDatePickerDialog;
    private DatePickerDialog toDatePickerDialog;
    ImageView calender_btn;
    private SimpleDateFormat dateFormatter;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private static final String DATE_PATTERN =
            "(0?[1-9]|1[012]) [/.-] (0?[1-9]|[12][0-9]|3[01]) [/.-] ((20)\\d\\d)";
    public EditProfile() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        et_name1 = (EditText)view.findViewById(R.id.et_name1);
        et_email1 = (EditText)view.findViewById(R.id.et_email1);
        et_dob = (TextView)view.findViewById(R.id.et_dob1);
        et_mobile1 = (TextView)view.findViewById(R.id.et_mobile1);
        grendar1 = (RadioGroup)view.findViewById(R.id.gender1);
        calender_btn = (ImageView)view.findViewById(R.id.calender_btn1);
        rg_progress = (ProgressBar)view.findViewById(R.id.rg_progress);

        et_name1.setText(u_name);
        et_email1.setText(u_email);
        et_dob.setText(u_dob);
        et_mobile1.setText(u_mobile);
        user_id = u_id;
        strGendar = u_gender;

        grendar1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    Toast.makeText(getActivity(), rb.getText(), Toast.LENGTH_SHORT).show();
                    strGendar = rb.getText().toString();
                }
            }
        });
        done_btn = (TextView)view.findViewById(R.id.done_btn);
        done_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                email = et_email1.getText().toString();
                name = et_name1.getText().toString();
                dob = et_dob.getText().toString();
                matcher = Pattern.compile(DATE_PATTERN).matcher(dob);

                if (!email.matches(emailPattern))
                {
                    et_email1.setError("Enter Email ID");
                }
                else if (name.equals(""))
                {
                    et_name1.setError("Enter user name");
                }else if (dob.equals(""))
                {

                    et_dob.setError("Enter DOB");
                }else if (strGendar.equals(""))
                {
                    Toast.makeText(getActivity(),"Please Click Gender",Toast.LENGTH_SHORT).show();
                }/*else if (!matcher.matches())  {
                    Toast.makeText(getActivity(),"Invalid Birthday!",Toast.LENGTH_SHORT).show();
                }*/
                else {


                    RagisterUser1 ragisterUser = new RagisterUser1();
                    ragisterUser.execute();


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


        SharedPreferences prefs = getActivity().getSharedPreferences(MyApp, 0);
        user_id = prefs.getString("id", "0");

        return view;
    }
    private void setDateTimeField() {
        //et_dob.setOnClickListener(this);
        String date = "1-1-2000";
        String parts[] = date.split("-");

        int day = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);
        int year = Integer.parseInt(parts[2]);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);

        fromDatePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                et_dob.setText(dateFormatter.format(newDate.getTime()));
            }

        },calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));


    }

    class RagisterUser1 extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            //creating request handler object
            RequestHandler requestHandler = new RequestHandler();
            //creating request parameters
            HashMap<String, String> params = new HashMap<>();
            params.put("username", name);
            params.put("email", email);
            params.put("gendar", strGendar);
            params.put("user_id",u_id);
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
                    Toast.makeText(getActivity(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                    SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences(MyApp, 0); // 0 - for private mode
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("name",name);
                    editor.putString("gendar",strGendar);
                    editor.putString("dob",et_dob.getText().toString());
                    editor.putString("email",email);
                    editor.apply(); // commit changes
                    Intent intent = new Intent(getActivity(),MainActivity.class);
                    getActivity().startActivity(intent);
                    getActivity().finish();
                } else {
                    Toast.makeText(getActivity(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
