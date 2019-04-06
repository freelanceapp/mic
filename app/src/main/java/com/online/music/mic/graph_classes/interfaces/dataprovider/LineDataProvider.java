package com.online.music.mic.graph_classes.interfaces.dataprovider;

import com.online.music.mic.graph_classes.components.YAxis;
import com.online.music.mic.graph_classes.data.LineData;

public interface LineDataProvider extends BarLineScatterCandleBubbleDataProvider {

    LineData getLineData();

    YAxis getAxis(YAxis.AxisDependency dependency);
}
