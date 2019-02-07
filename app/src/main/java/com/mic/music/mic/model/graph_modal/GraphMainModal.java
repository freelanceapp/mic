package com.mic.music.mic.model.graph_modal;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GraphMainModal implements Parcelable {

    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("list")
    @Expose
    private java.util.List<PerformanceList> list = new ArrayList<PerformanceList>();
    @SerializedName("graph")
    @Expose
    private java.util.List<PerformanceGraph> graph = new ArrayList<PerformanceGraph>();
    public final static Parcelable.Creator<GraphMainModal> CREATOR = new Creator<GraphMainModal>() {


        @SuppressWarnings({
                "unchecked"
        })
        public GraphMainModal createFromParcel(Parcel in) {
            return new GraphMainModal(in);
        }

        public GraphMainModal[] newArray(int size) {
            return (new GraphMainModal[size]);
        }

    };

    protected GraphMainModal(Parcel in) {
        this.error = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.list, (PerformanceList.class.getClassLoader()));
        in.readList(this.graph, (PerformanceGraph.class.getClassLoader()));
    }

    public GraphMainModal() {
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public java.util.List<PerformanceList> getList() {
        return list;
    }

    public void setList(java.util.List<PerformanceList> list) {
        this.list = list;
    }

    public java.util.List<PerformanceGraph> getGraph() {
        return graph;
    }

    public void setGraph(java.util.List<PerformanceGraph> graph) {
        this.graph = graph;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(error);
        dest.writeValue(message);
        dest.writeList(list);
        dest.writeList(graph);
    }

    public int describeContents() {
        return 0;
    }

}