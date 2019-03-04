
package com.mic.music.mic.model.compatition_level_rank_responce;

import java.util.ArrayList;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CompetitionLevel implements Parcelable
{

    @SerializedName("competition_id")
    @Expose
    private String competitionId;
    @SerializedName("competition_level")
    @Expose
    private String competitionLevel;
    @SerializedName("participant")
    @Expose
    private List<Participant> participant = new ArrayList<Participant>();
    public final static Creator<CompetitionLevel> CREATOR = new Creator<CompetitionLevel>() {


        @SuppressWarnings({
            "unchecked"
        })
        public CompetitionLevel createFromParcel(Parcel in) {
            return new CompetitionLevel(in);
        }

        public CompetitionLevel[] newArray(int size) {
            return (new CompetitionLevel[size]);
        }

    }
    ;

    protected CompetitionLevel(Parcel in) {
        this.competitionId = ((String) in.readValue((String.class.getClassLoader())));
        this.competitionLevel = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.participant, (Participant.class.getClassLoader()));
    }

    public CompetitionLevel() {
    }

    public String getCompetitionId() {
        return competitionId;
    }

    public void setCompetitionId(String competitionId) {
        this.competitionId = competitionId;
    }

    public CompetitionLevel withCompetitionId(String competitionId) {
        this.competitionId = competitionId;
        return this;
    }

    public String getCompetitionLevel() {
        return competitionLevel;
    }

    public void setCompetitionLevel(String competitionLevel) {
        this.competitionLevel = competitionLevel;
    }

    public CompetitionLevel withCompetitionLevel(String competitionLevel) {
        this.competitionLevel = competitionLevel;
        return this;
    }

    public List<Participant> getParticipant() {
        return participant;
    }

    public void setParticipant(List<Participant> participant) {
        this.participant = participant;
    }

    public CompetitionLevel withParticipant(List<Participant> participant) {
        this.participant = participant;
        return this;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(competitionId);
        dest.writeValue(competitionLevel);
        dest.writeList(participant);
    }

    public int describeContents() {
        return  0;
    }

}
