
package com.mic.music.mic.model.compation_level_responce;

import java.util.ArrayList;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
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
    private List<Competition> competition = new ArrayList<Competition>();
    public final static Creator<CompatitionLevelModel> CREATOR = new Creator<CompatitionLevelModel>() {


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
        in.readList(this.competition, (com.mic.music.mic.model.compation_level_responce.Competition.class.getClassLoader()));
    }

    public CompatitionLevelModel() {
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public CompatitionLevelModel withError(Boolean error) {
        this.error = error;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public CompatitionLevelModel withMessage(String message) {
        this.message = message;
        return this;
    }

    public List<Competition> getCompetition() {
        return competition;
    }

    public void setCompetition(List<Competition> competition) {
        this.competition = competition;
    }

    public CompatitionLevelModel withCompetition(List<Competition> competition) {
        this.competition = competition;
        return this;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(error);
        dest.writeValue(message);
        dest.writeList(competition);
    }

    public int describeContents() {
        return  0;
    }

}
