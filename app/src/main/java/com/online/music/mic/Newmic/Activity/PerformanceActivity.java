package com.online.music.mic.Newmic.Activity;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import com.online.music.mic.R;
import com.online.music.mic.graph_classes.charts.LineChart;
import com.online.music.mic.graph_classes.components.AxisBase;
import com.online.music.mic.graph_classes.components.Legend;
import com.online.music.mic.graph_classes.components.XAxis;
import com.online.music.mic.graph_classes.components.YAxis;
import com.online.music.mic.graph_classes.data.Entry;
import com.online.music.mic.graph_classes.data.LineData;
import com.online.music.mic.graph_classes.data.LineDataSet;
import com.online.music.mic.graph_classes.formatter.ValueFormatter;
import com.online.music.mic.graph_classes.highlight.Highlight;
import com.online.music.mic.graph_classes.listener.OnChartValueSelectedListener;
import com.online.music.mic.graph_classes.utils.ColorTemplate;
import com.online.music.mic.model.compatition_graph_responce.CompatitionGraphModel;
import com.online.music.mic.model.compatition_graph_responce.CompetitionLevel;
import com.online.music.mic.retrofit_provider.RetrofitService;
import com.online.music.mic.retrofit_provider.WebResponse;
import com.online.music.mic.utils.Alerts;
import com.online.music.mic.utils.BaseActivity;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

import static com.online.music.mic.Newmic.Activity.HomeActivity.user_id;

public class PerformanceActivity extends BaseActivity implements OnChartValueSelectedListener {

    //private LineChart chart;
    private String compationId;
    private LineChart chart;
    final ArrayList<String> xAxisLabel = new ArrayList<>();
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
        xAxis.setGranularityEnabled(true);
        YAxis leftAxis = chart.getAxisLeft();
        xAxis.setValueFormatter(new ValueFormatter()
        {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                return xAxisLabel.get((int) value);
            }
        });

        //leftAxis.setTypeface(tfLight);
        leftAxis.setTextColor(ColorTemplate.getHoloBlue());
        leftAxis.setAxisMaximum(100f);
        leftAxis.setAxisMinimum(0f);
        leftAxis.setDrawGridLines(true);
        leftAxis.setGranularityEnabled(true);
        YAxis rightAxis = chart.getAxisRight();
        //rightAxis.setTypeface(tfLight);
        rightAxis.setTextColor(Color.RED);
        rightAxis.setAxisMaximum(100);
        rightAxis.setAxisMinimum(0);
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
            if (graphDataList.get(i).getScore() != null) {
                String valscore = graphDataList.get(i).getScore();
                if (valscore.isEmpty())
                {
                    valscore = "0";
                }
                float val = Float.parseFloat(valscore);

                values1.add(new Entry(i, val));
            }

            xAxisLabel.add(graphDataList.get(i).getCompetitionLevel());
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
