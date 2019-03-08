package com.mic.music.mic.Newmic.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mic.music.mic.Adapter.NotificationAdapter;
import com.mic.music.mic.Api.RequestHandler;
import com.mic.music.mic.Api.URLs;
import com.mic.music.mic.R;
import com.mic.music.mic.model.appversion_responce.AppVersion;
import com.mic.music.mic.model.notification;
import com.mic.music.mic.model.notification_responce.Notification;
import com.mic.music.mic.model.notification_responce.NotificationModel;
import com.mic.music.mic.retrofit_provider.RetrofitService;
import com.mic.music.mic.retrofit_provider.WebResponse;
import com.mic.music.mic.utils.Alerts;
import com.mic.music.mic.utils.BaseFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Response;

public class AllNotificationFragment extends BaseFragment {
    RecyclerView all_list;
    ArrayList<Notification> notifications = new ArrayList<>();
    NotificationAdapter adapter;
    private SharedPreferences sharedPreferences;

    public AllNotificationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_notification, container, false);

        all_list = (RecyclerView)view.findViewById(R.id.all_list);

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
           // notification_progress.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //hiding the progressbar after completion
            //notification_progress.setVisibility(View.GONE);

            try {
                //converting response to json object
                JSONObject obj = new JSONObject(s);
                //if no error in response
                if (!obj.getBoolean("error")) {

                  //  Toast.makeText(getActivity(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                    JSONArray jsonArray = obj.getJSONArray("notification");
                    for (int i = 0 ; i<jsonArray.length() ; i++)
                    {
                        JSONObject object = jsonArray.getJSONObject(i);
                        Notification n = new Notification();


                        n.setNotificationTitle(object.getString("notification_title"));
                        n.setNotificationMessage(object.getString("notification_message"));
                        n.setNotificationImage(object.getString("notification_image"));
                        n.setNotification_image_status(object.getString("notification_image_status"));
                        notifications.add(n);
                    }

                    adapter = new NotificationAdapter(getActivity(),notifications);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                    all_list.setLayoutManager(mLayoutManager);
                    all_list.setItemAnimator(new DefaultItemAnimator());
                    all_list.setAdapter(adapter);


                } else {
                    Toast.makeText(getActivity(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
