package com.mic.music.mic.graph_classes.interfaces.dataprovider;


import com.mic.music.mic.graph_classes.components.YAxis;
import com.mic.music.mic.graph_classes.data.BarLineScatterCandleBubbleData;
import com.mic.music.mic.graph_classes.utils.Transformer;

public interface BarLineScatterCandleBubbleDataProvider extends ChartInterface {

    Transformer getTransformer(YAxis.AxisDependency axis);
    boolean isInverted(YAxis.AxisDependency axis);
    
    float getLowestVisibleX();
    float getHighestVisibleX();

    BarLineScatterCandleBubbleData getData();
}
