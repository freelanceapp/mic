package com.online.music.mic.graph_classes.interfaces.dataprovider;


import com.online.music.mic.graph_classes.data.ScatterData;

public interface ScatterDataProvider extends BarLineScatterCandleBubbleDataProvider {

    ScatterData getScatterData();
}
