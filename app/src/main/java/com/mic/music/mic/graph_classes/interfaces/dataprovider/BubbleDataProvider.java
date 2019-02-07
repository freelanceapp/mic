package com.mic.music.mic.graph_classes.interfaces.dataprovider;


import com.mic.music.mic.graph_classes.data.BubbleData;

public interface BubbleDataProvider extends BarLineScatterCandleBubbleDataProvider {

    BubbleData getBubbleData();
}
