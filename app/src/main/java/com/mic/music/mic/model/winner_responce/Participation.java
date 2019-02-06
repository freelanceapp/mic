
package com.mic.music.mic.model.winner_responce;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Participation implements Parcelable
{

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("result_status")
    @Expose
    private String resultStatus;
    @SerializedName("competition")
    @Expose
    private String competition;
    @SerializedName("competition_level")
    @Expose
    private String competitionLevel;
    public final static Creator<Participation> CREATOR = new Creator<Participation>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Participation createFromParcel(Parcel in) {
            return new Participation(in);
        }

        public Participation[] newArray(int size) {
            return (new Participation[size]);
        }

    }
    ;

    protected Participation(Parcel in) {
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.image = ((String) in.readValue((String.class.getClassLoader())));
        this.resultStatus = ((String) in.readValue((String.class.getClassLoader())));
        this.competition = ((String) in.readValue((String.class.getClassLoader())));
        this.competitionLevel = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Participation() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Participation withId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Participation withName(String name) {
        this.name = name;
        return this;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Participation withImage(String image) {
        this.image = image;
        return this;
    }

    public String getResultStatus() {
        return resultStatus;
    }

    public void setResultStatus(String resultStatus) {
        this.resultStatus = resultStatus;
    }

    public Participation withResultStatus(String resultStatus) {
        this.resultStatus = resultStatus;
        return this;
    }

    public String getCompetition() {
        return competition;
    }

    public void setCompetition(String competition) {
        this.competition = competition;
    }

    public Participation withCompetition(String competition) {
        this.competition = competition;
        return this;
    }

    public String getCompetitionLevel() {
        return competitionLevel;
    }

    public void setCompetitionLevel(String competitionLevel) {
        this.competitionLevel = competitionLevel;
    }

    public Participation withCompetitionLevel(String competitionLevel) {
        this.competitionLevel = competitionLevel;
        return this;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(name);
        dest.writeValue(image);
        dest.writeValue(resultStatus);
        dest.writeValue(competition);
        dest.writeValue(competitionLevel);
    }

    public int describeContents() {
        return  0;
    }

}
