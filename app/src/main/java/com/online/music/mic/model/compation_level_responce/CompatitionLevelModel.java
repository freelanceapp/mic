
package com.online.music.mic.model.compation_level_responce;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CompatitionLevelModel implements Parcelable
{

    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("competition")
    @Expose
    private Competition competition;
    public final static Parcelable.Creator<CompatitionLevelModel> CREATOR = new Creator<CompatitionLevelModel>() {


        @SuppressWarnings({
                "unchecked"
        })
        public CompatitionLevelModel createFromParcel(Parcel in) {
            return new CompatitionLevelModel(in);
        }

        public CompatitionLevelModel[] newArray(int size) {
            return (new CompatitionLevelModel[size]);
        }

    }
            ;

    protected CompatitionLevelModel(Parcel in) {
        this.error = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
        this.competition = ((Competition) in.readValue((Competition.class.getClassLoader())));
    }

    public CompatitionLevelModel() {
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

    public Competition getCompetition() {
        return competition;
    }

    public void setCompetition(Competition competition) {
        this.competition = competition;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(error);
        dest.writeValue(message);
        dest.writeValue(competition);
    }

    public int describeContents() {
        return 0;
    }

}