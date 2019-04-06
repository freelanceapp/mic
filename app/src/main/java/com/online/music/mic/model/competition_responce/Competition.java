
package com.online.music.mic.model.competition_responce;

import java.util.ArrayList;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Competition implements Parcelable
{

    @SerializedName("competition_id")
    @Expose
    private String competitionId;
    @SerializedName("competition_name")
    @Expose
    private String competitionName;
    @SerializedName("competition_description")
    @Expose
    private String competitionDescription;
    @SerializedName("competition_duration")
    @Expose
    private String competitionDuration;
    @SerializedName("competition_rules")
    @Expose
    private String competitionRules;
    @SerializedName("competition_level")
    @Expose
    private List<CompetitionLevel> competitionLevel = new ArrayList<CompetitionLevel>();
    public final static Creator<Competition> CREATOR = new Creator<Competition>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Competition createFromParcel(Parcel in) {
            return new Competition(in);
        }

        public Competition[] newArray(int size) {
            return (new Competition[size]);
        }

    }
    ;

    protected Competition(Parcel in) {
        this.competitionId = ((String) in.readValue((String.class.getClassLoader())));
        this.competitionName = ((String) in.readValue((String.class.getClassLoader())));
        this.competitionDescription = ((String) in.readValue((String.class.getClassLoader())));
        this.competitionDuration = ((String) in.readValue((String.class.getClassLoader())));
        this.competitionRules = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.competitionLevel, (com.online.music.mic.model.competition_responce.CompetitionLevel.class.getClassLoader()));
    }

    public Competition() {
    }

    public String getCompetitionId() {
        return competitionId;
    }

    public void setCompetitionId(String competitionId) {
        this.competitionId = competitionId;
    }

    public Competition withCompetitionId(String competitionId) {
        this.competitionId = competitionId;
        return this;
    }

    public String getCompetitionName() {
        return competitionName;
    }

    public void setCompetitionName(String competitionName) {
        this.competitionName = competitionName;
    }

    public Competition withCompetitionName(String competitionName) {
        this.competitionName = competitionName;
        return this;
    }

    public String getCompetitionDescription() {
        return competitionDescription;
    }

    public void setCompetitionDescription(String competitionDescription) {
        this.competitionDescription = competitionDescription;
    }

    public Competition withCompetitionDescription(String competitionDescription) {
        this.competitionDescription = competitionDescription;
        return this;
    }

    public String getCompetitionDuration() {
        return competitionDuration;
    }

    public void setCompetitionDuration(String competitionDuration) {
        this.competitionDuration = competitionDuration;
    }

    public Competition withCompetitionDuration(String competitionDuration) {
        this.competitionDuration = competitionDuration;
        return this;
    }

    public String getCompetitionRules() {
        return competitionRules;
    }

    public void setCompetitionRules(String competitionRules) {
        this.competitionRules = competitionRules;
    }

    public Competition withCompetitionRules(String competitionRules) {
        this.competitionRules = competitionRules;
        return this;
    }

    public List<CompetitionLevel> getCompetitionLevel() {
        return competitionLevel;
    }

    public void setCompetitionLevel(List<CompetitionLevel> competitionLevel) {
        this.competitionLevel = competitionLevel;
    }

    public Competition withCompetitionLevel(List<CompetitionLevel> competitionLevel) {
        this.competitionLevel = competitionLevel;
        return this;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(competitionId);
        dest.writeValue(competitionName);
        dest.writeValue(competitionDescription);
        dest.writeValue(competitionDuration);
        dest.writeValue(competitionRules);
        dest.writeList(competitionLevel);
    }

    public int describeContents() {
        return  0;
    }

}
