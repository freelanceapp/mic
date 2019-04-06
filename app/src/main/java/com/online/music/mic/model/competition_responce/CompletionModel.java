
package com.online.music.mic.model.competition_responce;

import java.util.ArrayList;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CompletionModel implements Parcelable
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
    public final static Creator<CompletionModel> CREATOR = new Creator<CompletionModel>() {


        @SuppressWarnings({
            "unchecked"
        })
        public CompletionModel createFromParcel(Parcel in) {
            return new CompletionModel(in);
        }

        public CompletionModel[] newArray(int size) {
            return (new CompletionModel[size]);
        }

    }
    ;

    protected CompletionModel(Parcel in) {
        this.error = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.competition, (Competition.class.getClassLoader()));
    }

    public CompletionModel() {
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public CompletionModel withError(Boolean error) {
        this.error = error;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public CompletionModel withMessage(String message) {
        this.message = message;
        return this;
    }

    public List<Competition> getCompetition() {
        return competition;
    }

    public void setCompetition(List<Competition> competition) {
        this.competition = competition;
    }

    public CompletionModel withCompetition(List<Competition> competition) {
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
