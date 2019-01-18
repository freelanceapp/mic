package com.mic.music.mic.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mic.music.mic.Adapter.EventAdapter;
import com.mic.music.mic.Adapter.NotificationAdapter;
import com.mic.music.mic.Api.RequestHandler;
import com.mic.music.mic.Api.URLs;
import com.mic.music.mic.R;
import com.mic.music.mic.model.event;
import com.mic.music.mic.model.notification;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;


public class CalenderFragment extends Fragment {
    ArrayList<event> events = new ArrayList<>();
    EventAdapter adapter;
    TextView date_tv,day_tv,year_tv;
    RecyclerView event_detail;
    String date3;
    ProgressBar notification_bar;
    public CalenderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calender, container, false);
        CalendarView cv = (CalendarView)view.findViewById(R.id.cv1);
        date_tv = (TextView)view.findViewById(R.id.date_tv);
        day_tv = (TextView)view.findViewById(R.id.day_tv);
        year_tv = (TextView)view.findViewById(R.id.year_tv);
        notification_bar = (ProgressBar)view.findViewById(R.id.notification_bar);
        Calendar c = Calendar.getInstance();
        SimpleDateFormat ss = new SimpleDateFormat("dd-MMM-yyyy");
        Date date = new Date();
        String currentdate= ss.format(date);

        String date5 = currentdate;
        String[] dateParts = date5.split("-");
        String day = dateParts[0];
        String month = dateParts[1];
        String year = dateParts[2];
        date_tv.setText(""+day);
        day_tv.setText(""+month);
        year_tv.setText(""+year);

        //Toast.makeText(getActivity(),currentdate,Toast.LENGTH_SHORT).show();
        EventUser eventUser = new EventUser();
        eventUser.execute();
        cv.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                //Toast.makeText(getActivity(),i+"-"+i1+"-"+i2,Toast.LENGTH_SHORT).show();
                date3 = i2+"-"+i1+"-"+i;
                day_tv.setText(""+i1);
                date_tv.setText(""+i2);
                year_tv.setText(""+i);

            }
        });

        event_detail = (RecyclerView)view.findViewById(R.id.event_detail);

        return view;
    }


    class EventUser extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            //creating request handler object
            RequestHandler requestHandler = new RequestHandler();

            //creating request parameters
            HashMap<String, String> params = new HashMap<>();

            //returing the response
            return requestHandler.sendPostRequest(URLs.URL_EVENT, params);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //displaying the progress bar while user registers on the server
            notification_bar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //hiding the progressbar after completion
            notification_bar.setVisibility(View.GONE);

            try {
                //converting response to json object
                JSONObject obj = new JSONObject(s);
                //if no error in response
                if (!obj.getBoolean("error")) {

                    Toast.makeText(getActivity(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                    JSONArray jsonArray = obj.getJSONArray("event");
                    for (int i = 0 ; i<jsonArray.length() ; i++)
                    {
                        JSONObject object = jsonArray.getJSONObject(i);

                        String stime = object.getString("start_event");
                        String etime = object.getString("end_event");

                        String inputPattern = "yyyy-MM-dd HH:mm:ss";
                        String outputPattern = "dd-MMM h:mm a";
                        String outputTime = "h:mm a";
                        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
                        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(outputTime);

                        Date date = null;
                        String str = null;
                        String str1 = null;
                        Date date1 = null;
                        try {
                            date = inputFormat.parse(stime);
                            date1 = inputFormat.parse(etime);
                            str = outputFormat.format(date);
                            str1 = outputFormat.format(date1);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                            event n = new event();
                            n.setTitle(object.getString("title"));
                            n.setStart_time(str1);
                            n.setEnd_time(str);
                            n.setDiscription(object.getString("description"));
                            n.setOrganizer(object.getString("organizer"));
                            events.add(n);


                    }

                    adapter = new EventAdapter(getActivity(),events);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                    event_detail.setLayoutManager(mLayoutManager);
                    event_detail.setItemAnimator(new DefaultItemAnimator());
                    event_detail.setAdapter(adapter);

                } else {
                    Toast.makeText(getActivity(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
