package com.online.music.mic.Responce;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VideoResponce implements Parcelable {

    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("url")
    @Expose
    private String url;
    public final static Parcelable.Creator<VideoResponce> CREATOR = new Creator<VideoResponce>() {


        @SuppressWarnings({
                "unchecked"
        })
        public VideoResponce createFromParcel(Parcel in) {
            return new VideoResponce(in);
        }

        public VideoResponce[] newArray(int size) {
            return (new VideoResponce[size]);
        }

    };

    protected VideoResponce(Parcel in) {
        this.error = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
        this.url = ((String) in.readValue((String.class.getClassLoader())));
    }

    public VideoResponce() {
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(error);
        dest.writeValue(message);
        dest.writeValue(url);
    }

    public int describeContents() {
        return 0;
    }

}
