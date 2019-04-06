package com.online.music.mic.Fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.online.music.mic.Api.RequestHandler;
import com.online.music.mic.Api.URLs;
import com.online.music.mic.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import static com.online.music.mic.MainActivity.u_id;

public class RatingFragment extends Fragment {
    ProgressBar rating_progress;
    String score;
    TextView rating_TV;
    GraphView graph;
    public RatingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rating, container, false);
        rating_progress = (ProgressBar)view.findViewById(R.id.rating_progress);
        rating_TV = (TextView)view.findViewById(R.id.rating_TV);
        graph = (GraphView) view.findViewById(R.id.graph);
        Rating rating = new Rating();
        rating.execute();
        return view;
    }


    class Rating extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            //creating request handler object
            RequestHandler requestHandler = new RequestHandler();

            //creating request parameters
            HashMap<String, String> params = new HashMap<>();
            params.put("participant_id",u_id );

            //returing the response
            return requestHandler.sendPostRequest(URLs.URL_RATING, params);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //displaying the progress bar while user registers on the server
            rating_progress.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //hiding the progressbar after completion
            rating_progress.setVisibility(View.GONE);

            try {
                //converting response to json object
                JSONObject obj = new JSONObject(s);
                //if no error in response
                if (!obj.getBoolean("error")) {

                    Toast.makeText(getActivity(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                    JSONArray jsonArray = obj.getJSONArray("score");
                    for (int i = 0 ; i<jsonArray.length() ; i++)
                    {
                        JSONObject object = jsonArray.getJSONObject(i);
                        score = object.getString("score");
                        rating_TV.setText(score);
                    }
                    JSONArray jsonArray1 = obj.getJSONArray("numUser");
                    for (int i = 0 ; i<jsonArray1.length() ; i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String point1 = jsonObject.getString("user");
                        String point2 = jsonObject.getString("age");

                        int p1 = Integer.parseInt(point1);
                        int p2 = Integer.parseInt(point2);

                        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[]{
                                new DataPoint(p1, p2)

                        });
                        graph.addSeries(series);
                    }
                } else {
                    Toast.makeText(getActivity(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
