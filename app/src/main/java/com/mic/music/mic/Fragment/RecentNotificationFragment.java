package com.mic.music.mic.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mic.music.mic.Adapter.NotificationAdapter;
import com.mic.music.mic.Api.RequestHandler;
import com.mic.music.mic.Api.URLs;
import com.mic.music.mic.LoginActivity;
import com.mic.music.mic.OtpActivity;
import com.mic.music.mic.R;
import com.mic.music.mic.model.notification;
import com.mic.music.mic.model.notification_responce.Notification;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class RecentNotificationFragment extends Fragment {
    RecyclerView recent_list;
    ArrayList<Notification> notifications = new ArrayList<>();
    NotificationAdapter adapter;
    ProgressBar notification_progress;
    public RecentNotificationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recent_notification, container, false);
        recent_list = (RecyclerView)view.findViewById(R.id.recent_list);
        notification_progress = (ProgressBar)view.findViewById(R.id.notification_progress);
        NotificationUser notificationUser = new NotificationUser();
        notificationUser.execute();
        return view;
    }

    class NotificationUser extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            //creating request handler object
            RequestHandler requestHandler = new RequestHandler();
            //creating request parameters
            HashMap<String, String> params = new HashMap<>();
            //returing the response
            return requestHandler.sendPostRequest(URLs.URL_NOTIFICATION, params);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //displaying the progress bar while user registers on the server
            notification_progress.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //hiding the progressbar after completion
            notification_progress.setVisibility(View.GONE);
            try {
                //converting response to json object
                JSONObject obj = new JSONObject(s);
                //if no error in response
                if (!obj.getBoolean("error")) {
                //    Toast.makeText(getActivity(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                    JSONArray jsonArray = obj.getJSONArray("notification");
                    for (int i = 0 ; i<10 ; i++)
                    {
                        JSONObject object = jsonArray.getJSONObject(i);
                        Notification n = new Notification();
                        n.setNotificationTitle(object.getString("notification_title"));
                        n.setNotificationMessage(object.getString("notification_message"));
                        notifications.add(n);
                    }
                    adapter = new NotificationAdapter(getActivity(),notifications);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                    recent_list.setLayoutManager(mLayoutManager);
                    recent_list.setItemAnimator(new DefaultItemAnimator());
                    recent_list.setAdapter(adapter);

                } else {
                    Toast.makeText(getActivity(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
