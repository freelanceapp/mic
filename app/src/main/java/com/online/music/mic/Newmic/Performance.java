package com.online.music.mic.Newmic;

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

import com.online.music.mic.Adapter.PerformanceListAdapter;
import com.online.music.mic.R;
import com.online.music.mic.graph_classes.charts.LineChart;
import com.online.music.mic.graph_classes.components.Legend;
import com.online.music.mic.graph_classes.components.XAxis;
import com.online.music.mic.graph_classes.components.YAxis;
import com.online.music.mic.graph_classes.data.Entry;
import com.online.music.mic.graph_classes.data.LineData;
import com.online.music.mic.graph_classes.data.LineDataSet;
import com.online.music.mic.graph_classes.highlight.Highlight;
import com.online.music.mic.graph_classes.listener.OnChartValueSelectedListener;
import com.online.music.mic.graph_classes.utils.ColorTemplate;
import com.online.music.mic.model.graph_modal.GraphMainModal;
import com.online.music.mic.model.graph_modal.PerformanceGraph;
import com.online.music.mic.model.graph_modal.PerformanceList;
import com.online.music.mic.retrofit_provider.RetrofitService;
import com.online.music.mic.retrofit_provider.WebResponse;
import com.online.music.mic.utils.Alerts;
import com.online.music.mic.utils.BaseFragment;
import com.online.music.mic.utils.ConnectionDetector;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

public class Performance extends BaseFragment implements OnChartValueSelectedListener {

    private LineChart chart;
    private View view;
    private RecyclerView recyclerViewPerformanceList;
    private PerformanceListAdapter performanceListAdapter;
    private List<PerformanceList> performanceLists = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.activity_performance, container, false);
        mContext = getActivity();
        activity = getActivity();
        cd = new ConnectionDetector(mContext);
        retrofitApiClient = RetrofitService.getRetrofit();

        init();
        return view;
    }

    private void init() {
        recyclerViewPerformanceList = view.findViewById(R.id.recyclerViewPerformanceList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        recyclerViewPerformanceList.setLayoutManager(mLayoutManager);
        recyclerViewPerformanceList.setItemAnimator(new DefaultItemAnimator());
        performanceListAdapter = new PerformanceListAdapter(mContext, performanceLists);
        recyclerViewPerformanceList.setAdapter(performanceListAdapter);
        graphChartInit();
    }

    private void graphChartInit() {
        chart = view.findViewById(R.id.chart1);
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
//        l.setYOffset(11f);
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
            RetrofitService.getGraphData(new Dialog(mContext), retrofitApiClient.getGraphData(), new WebResponse() {
                @Override
                public void onResponseSuccess(Response<?> result) {
                    GraphMainModal graphMainModal = (GraphMainModal) result.body();
                    performanceLists.clear();
                    if (graphMainModal != null) {
                        if (graphMainModal.getGraph().size() > 0) {
                            performanceLists.addAll(graphMainModal.getList());
                            setData(graphMainModal.getGraph().size(), graphMainModal.getGraph());
                            chart.invalidate();
                        } else {
                            Alerts.show(mContext, "No graph data!!!");
                        }
                    } else {
                        Alerts.show(mContext, "No data!!!");
                    }
                    performanceListAdapter.notifyDataSetChanged();
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

    private void setData(int count, java.util.List<PerformanceGraph> graphDataList) {

        ArrayList<Entry> values1 = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            float val = graphDataList.get(i).getPoint();
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