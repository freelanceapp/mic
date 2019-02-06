package com.mic.music.mic.graph_classes.interfaces.dataprovider;


import com.mic.music.mic.graph_classes.data.ScatterData;

public interface ScatterDataProvider extends BarLineScatterCandleBubbleDataProvider {

    ScatterData getScatterData();
}
