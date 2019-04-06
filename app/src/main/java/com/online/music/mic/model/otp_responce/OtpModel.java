
package com.online.music.mic.model.otp_responce;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OtpModel implements Parcelable
{

    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("user_type")
    @Expose
    private String userType;
    public final static Creator<OtpModel> CREATOR = new Creator<OtpModel>() {


        @SuppressWarnings({
            "unchecked"
        })
        public OtpModel createFromParcel(Parcel in) {
            return new OtpModel(in);
        }

        public OtpModel[] newArray(int size) {
            return (new OtpModel[size]);
        }

    }
    ;

    protected OtpModel(Parcel in) {
        this.error = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
        this.user = ((User) in.readValue((User.class.getClassLoader())));
        this.userType = ((String) in.readValue((String.class.getClassLoader())));
    }

    public OtpModel() {
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public OtpModel withError(Boolean error) {
        this.error = error;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public OtpModel withMessage(String message) {
        this.message = message;
        return this;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public OtpModel withUser(User user) {
        this.user = user;
        return this;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public OtpModel withUserType(String userType) {
        this.userType = userType;
        return this;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(error);
        dest.writeValue(message);
        dest.writeValue(user);
        dest.writeValue(userType);
    }

    public int describeContents() {
        return  0;
    }

}
