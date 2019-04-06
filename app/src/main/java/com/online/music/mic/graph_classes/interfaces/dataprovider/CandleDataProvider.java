package com.online.music.mic.graph_classes.interfaces.dataprovider;


import com.online.music.mic.graph_classes.data.CandleData;

public interface CandleDataProvider extends BarLineScatterCandleBubbleDataProvider {

    CandleData getCandleData();
}
