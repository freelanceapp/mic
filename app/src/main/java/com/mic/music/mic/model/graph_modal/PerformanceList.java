package com.mic.music.mic.model.graph_modal;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PerformanceList implements Parcelable
{

    @SerializedName("participant_name")
    @Expose
    private String participantName;
    @SerializedName("competition_id")
    @Expose
    private String competitionId;
    @SerializedName("participant_id")
    @Expose
    private String participantId;
    @SerializedName("participant_position")
    @Expose
    private Integer participantPosition;
    @SerializedName("time")
    @Expose
    private Integer time;
    public final static Parcelable.Creator<PerformanceList> CREATOR = new Creator<PerformanceList>() {


        @SuppressWarnings({
                "unchecked"
        })
        public PerformanceList createFromParcel(Parcel in) {
            return new PerformanceList(in);
        }

        public PerformanceList[] newArray(int size) {
            return (new PerformanceList[size]);
        }

    }
            ;

    protected PerformanceList(Parcel in) {
        this.participantName = ((String) in.readValue((String.class.getClassLoader())));
        this.competitionId = ((String) in.readValue((String.class.getClassLoader())));
        this.participantId = ((String) in.readValue((String.class.getClassLoader())));
        this.participantPosition = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.time = ((Integer) in.readValue((Integer.class.getClassLoader())));
    }

    public PerformanceList() {
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

    public String getParticipantId() {
        return participantId;
    }

    public void setParticipantId(String participantId) {
        this.participantId = participantId;
    }

    public Integer getParticipantPosition() {
        return participantPosition;
    }

    public void setParticipantPosition(Integer participantPosition) {
        this.participantPosition = participantPosition;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(participantName);
        dest.writeValue(competitionId);
        dest.writeValue(participantId);
        dest.writeValue(participantPosition);
        dest.writeValue(time);
    }

    public int describeContents() {
        return 0;
    }

}
