package com.mic.music.mic.Newmic.Activity;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mic.music.mic.Adapter.PerformanceListAdapter;
import com.mic.music.mic.R;
import com.mic.music.mic.graph_classes.charts.LineChart;
import com.mic.music.mic.graph_classes.components.Legend;
import com.mic.music.mic.graph_classes.components.XAxis;
import com.mic.music.mic.graph_classes.components.YAxis;
import com.mic.music.mic.graph_classes.data.Entry;
import com.mic.music.mic.graph_classes.data.LineData;
import com.mic.music.mic.graph_classes.data.LineDataSet;
import com.mic.music.mic.graph_classes.highlight.Highlight;
import com.mic.music.mic.graph_classes.listener.OnChartValueSelectedListener;
import com.mic.music.mic.graph_classes.utils.ColorTemplate;
import com.mic.music.mic.model.compatition_graph_responce.CompatitionGraphModel;
import com.mic.music.mic.model.compatition_graph_responce.CompetitionLevel;
import com.mic.music.mic.model.graph_modal.GraphMainModal;
import com.mic.music.mic.model.graph_modal.PerformanceGraph;
import com.mic.music.mic.model.graph_modal.PerformanceList;
import com.mic.music.mic.retrofit_provider.RetrofitService;
import com.mic.music.mic.retrofit_provider.WebResponse;
import com.mic.music.mic.utils.Alerts;
import com.mic.music.mic.utils.BaseActivity;
import com.mic.music.mic.utils.BaseFragment;
import com.mic.music.mic.utils.ConnectionDetector;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

import static com.mic.music.mic.Newmic.Activity.HomeActivity.user_id;

public class PerformanceActivity extends BaseActivity implements OnChartValueSelectedListener {

    private LineChart chart;
    private String compationId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfomance1);
        init();
    }

    private void init() {
        compationId = getIntent().getStringExtra("Compation_id");

        graphChartInit();
    }

    private void graphChartInit() {
        chart = findViewById(R.id.chart1);
        chart.setOnChartValueSelectedListener(this);
        // no description text
        chart.getDescription().setEnabled(false);
        // enable touch gestures
        chart.setTouchEnabled(true);
        chart.setDragDecelerationFrictionCoef(0.9f);
        // enable scaling and dragging
        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);
        chart.setDrawGridBackground(false);
        chart.setHighlightPerDragEnabled(true);
        // if disabled, scaling can be done on x- and y-axis separately
        chart.setPinchZoom(true);
        // set an alternative background color
        chart.setBackgroundColor(Color.TRANSPARENT);
        // add data
        chart.animateX(1500);
        // get the legend (only possible after setting data)
        Legend l = chart.getLegend();
        // modify the legend ...
        l.setForm(Legend.LegendForm.LINE);
        //l.setTypeface(tfLight);
        l.setTextSize(11f);
        l.setTextColor(Color.WHITE);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
//      l.setYOffset(11f);
        XAxis xAxis = chart.getXAxis();
        //xAxis.setTypeface(tfLight);
        xAxis.setTextSize(11f);
        xAxis.setTextColor(Color.WHITE);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        YAxis leftAxis = chart.getAxisLeft();
        //leftAxis.setTypeface(tfLight);
        leftAxis.setTextColor(ColorTemplate.getHoloBlue());
        leftAxis.setAxisMaximum(200f);
        leftAxis.setAxisMinimum(0f);
        leftAxis.setDrawGridLines(true);
        leftAxis.setGranularityEnabled(true);
        YAxis rightAxis = chart.getAxisRight();
        //rightAxis.setTypeface(tfLight);
        rightAxis.setTextColor(Color.RED);
        rightAxis.setAxisMaximum(900);
        rightAxis.setAxisMinimum(-200);
        rightAxis.setDrawGridLines(false);
        rightAxis.setDrawZeroLine(false);
        rightAxis.setGranularityEnabled(false);
        graphDataApi();
    }

    private void graphDataApi() {
        if (cd.isNetworkAvailable()) {
            RetrofitService.getCompatitionGraphData(new Dialog(mContext), retrofitApiClient.getCompatitionGraph(compationId,user_id), new WebResponse() {
                @Override
                public void onResponseSuccess(Response<?> result) {
                    CompatitionGraphModel graphMainModal = (CompatitionGraphModel) result.body();
                    if (graphMainModal != null) {
                        if (graphMainModal.getCompetitionLevel().size() > 0) {
                           // performanceLists.addAll(graphMainModal.getList());
                            setData(graphMainModal.getCompetitionLevel().size(), graphMainModal.getCompetitionLevel());
                            chart.invalidate();
                        } else {
                            Alerts.show(mContext, "No graph data!!!");
                        }
                    } else {
                        Alerts.show(mContext, "No data!!!");
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

    private void setData(int count, List<CompetitionLevel> graphDataList) {

        ArrayList<Entry> values1 = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            float val = Float.parseFloat(graphDataList.get(i).getScore());
            values1.add(new Entry(i, val));
        }
        //LineDataSet set1, set2, set3;
        LineDataSet set1;
        if (chart.getData() != null && chart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) chart.getData().getDataSetByIndex(0);
            set1.setValues(values1);
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(values1, "DataSet 1");
            set1.setAxisDependency(YAxis.AxisDependency.LEFT);
            set1.setColor(ColorTemplate.colorWithAlpha(Color.YELLOW, 200));
            set1.setCircleColor(Color.WHITE);
            set1.setLineWidth(2f);
            set1.setCircleRadius(3f);
            set1.setFillAlpha(65);
            set1.setFillColor(ColorTemplate.colorWithAlpha(Color.YELLOW, 200));
            set1.setHighLightColor(Color.rgb(244, 117, 117));
            set1.setDrawCircleHole(false);
            LineData data = new LineData(set1);
            data.setValueTextColor(Color.WHITE);
            data.setValueTextSize(9f);
            // set data
            chart.setData(data);
        }
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        Log.i("Entry selected", e.toString());
        chart.centerViewToAnimated(e.getX(), e.getY(),
                chart.getData().getDataSetByIndex(h.getDataSetIndex()).getAxisDependency(), 500);
    }

    @Override
    public void onNothingSelected() {
        Log.i("Nothing selected", "Nothing selected.");
    }
}
