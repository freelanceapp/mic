package com.mic.music.mic.model.appversion_responce;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AppVersion implements Parcelable
{

    @SerializedName("version")
    @Expose
    private String version;
    public final static Parcelable.Creator<AppVersion> CREATOR = new Creator<AppVersion>() {


        @SuppressWarnings({
                "unchecked"
        })
        public AppVersion createFromParcel(Parcel in) {
            return new AppVersion(in);
        }

        public AppVersion[] newArray(int size) {
            return (new AppVersion[size]);
        }

    }
            ;

    protected AppVersion(Parcel in) {
        this.version = ((String) in.readValue((String.class.getClassLoader())));
    }

    public AppVersion() {
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public AppVersion withVersion(String version) {
        this.version = version;
        return this;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(version);
    }

    public int describeContents() {
        return 0;
    }

}