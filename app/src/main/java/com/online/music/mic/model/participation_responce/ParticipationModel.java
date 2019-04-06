
package com.online.music.mic.model.participation_responce;

import java.util.ArrayList;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ParticipationModel implements Parcelable
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
    public final static Creator<ParticipationModel> CREATOR = new Creator<ParticipationModel>() {


        @SuppressWarnings({
            "unchecked"
        })
        public ParticipationModel createFromParcel(Parcel in) {
            return new ParticipationModel(in);
        }

        public ParticipationModel[] newArray(int size) {
            return (new ParticipationModel[size]);
        }

    }
    ;

    protected ParticipationModel(Parcel in) {
        this.error = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.participation, (Participation.class.getClassLoader()));
    }

    public ParticipationModel() {
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public ParticipationModel withError(Boolean error) {
        this.error = error;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ParticipationModel withMessage(String message) {
        this.message = message;
        return this;
    }

    public List<Participation> getParticipation() {
        return participation;
    }

    public void setParticipation(List<Participation> participation) {
        this.participation = participation;
    }

    public ParticipationModel withParticipation(List<Participation> participation) {
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
