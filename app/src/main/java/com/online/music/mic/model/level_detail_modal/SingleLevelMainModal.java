package com.online.music.mic.model.level_detail_modal;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SingleLevelMainModal implements Parcelable {

    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("competition")
    @Expose
    private SingleLevelCompetition singleLevelCompetition;
    public final static Parcelable.Creator<SingleLevelMainModal> CREATOR = new Creator<SingleLevelMainModal>() {


        @SuppressWarnings({
                "unchecked"
        })
        public SingleLevelMainModal createFromParcel(Parcel in) {
            return new SingleLevelMainModal(in);
        }

        public SingleLevelMainModal[] newArray(int size) {
            return (new SingleLevelMainModal[size]);
        }

    };

    protected SingleLevelMainModal(Parcel in) {
        this.error = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
        this.singleLevelCompetition = ((SingleLevelCompetition) in.readValue((SingleLevelCompetition.class.getClassLoader())));
    }

    public SingleLevelMainModal() {
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

    public SingleLevelCompetition getSingleLevelCompetition() {
        return singleLevelCompetition;
    }

    public void setSingleLevelCompetition(SingleLevelCompetition singleLevelCompetition) {
        this.singleLevelCompetition = singleLevelCompetition;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(error);
        dest.writeValue(message);
        dest.writeValue(singleLevelCompetition);
    }

    public int describeContents() {
        return 0;
    }

}