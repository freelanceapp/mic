
package com.online.music.mic.model.winner_responce;

import java.util.ArrayList;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WinnersModel implements Parcelable
{

    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("participation")
    @Expose
    private List<Participation> participation = new ArrayList<Participation>();
    public final static Creator<WinnersModel> CREATOR = new Creator<WinnersModel>() {


        @SuppressWarnings({
            "unchecked"
        })
        public WinnersModel createFromParcel(Parcel in) {
            return new WinnersModel(in);
        }

        public WinnersModel[] newArray(int size) {
            return (new WinnersModel[size]);
        }

    }
    ;

    protected WinnersModel(Parcel in) {
        this.error = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.participation, (Participation.class.getClassLoader()));
    }

    public WinnersModel() {
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public WinnersModel withError(Boolean error) {
        this.error = error;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public WinnersModel withMessage(String message) {
        this.message = message;
        return this;
    }

    public List<Participation> getParticipation() {
        return participation;
    }

    public void setParticipation(List<Participation> participation) {
        this.participation = participation;
    }

    public WinnersModel withParticipation(List<Participation> participation) {
        this.participation = participation;
        return this;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(error);
        dest.writeValue(message);
        dest.writeList(participation);
    }

    public int describeContents() {
        return  0;
    }

}
