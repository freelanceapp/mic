package com.mic.music.mic.model.graph_modal;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PerformanceGraph implements Parcelable {

    @SerializedName("participant_name")
    @Expose
    private String participantName;
    @SerializedName("competition_id")
    @Expose
    private String competitionId;
    @SerializedName("competition_level_id")
    @Expose
    private String competitionLevelId;
    @SerializedName("point")
    @Expose
    private Integer point;
    @SerializedName("total_judge")
    @Expose
    private Integer totalJudge;
    public final static Parcelable.Creator<PerformanceGraph> CREATOR = new Creator<PerformanceGraph>() {


        @SuppressWarnings({
                "unchecked"
        })
        public PerformanceGraph createFromParcel(Parcel in) {
            return new PerformanceGraph(in);
        }

        public PerformanceGraph[] newArray(int size) {
            return (new PerformanceGraph[size]);
        }

    };

    protected PerformanceGraph(Parcel in) {
        this.participantName = ((String) in.readValue((String.class.getClassLoader())));
        this.competitionId = ((String) in.readValue((String.class.getClassLoader())));
        this.competitionLevelId = ((String) in.readValue((String.class.getClassLoader())));
        this.point = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.totalJudge = ((Integer) in.readValue((Integer.class.getClassLoader())));
    }

    public PerformanceGraph() {
    }

    public String getParticipantName() {
        return participantName;
    }

    public void setParticipantName(String participantName) {
        this.participantName = participantName;
    }

    public String getCompetitionId() {
        return competitionId;
    }

    public void setCompetitionId(String competitionId) {
        this.competitionId = competitionId;
    }

    public String getCompetitionLevelId() {
        return competitionLevelId;
    }

    public void setCompetitionLevelId(String competitionLevelId) {
        this.competitionLevelId = competitionLevelId;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public Integer getTotalJudge() {
        return totalJudge;
    }

    public void setTotalJudge(Integer totalJudge) {
        this.totalJudge = totalJudge;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(participantName);
        dest.writeValue(competitionId);
        dest.writeValue(competitionLevelId);
        dest.writeValue(point);
        dest.writeValue(totalJudge);
    }

    public int describeContents() {
        return 0;
    }

}