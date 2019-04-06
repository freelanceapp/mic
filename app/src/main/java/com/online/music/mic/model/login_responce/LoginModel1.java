package com.online.music.mic.model.login_responce;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginModel1 implements Parcelable
{

    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("user")
    @Expose
    private String userEmail;
    public final static Creator<LoginModel1> CREATOR = new Creator<LoginModel1>() {


        @SuppressWarnings({
                "unchecked"
        })
        public LoginModel1 createFromParcel(Parcel in) {
            return new LoginModel1(in);
        }

        public LoginModel1[] newArray(int size) {
            return (new LoginModel1[size]);
        }

    }
            ;

    protected LoginModel1(Parcel in) {
        this.error = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
        this.userEmail = ((String) in.readValue((String.class.getClassLoader())));
    }

    public LoginModel1() {
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public LoginModel1 withError(Boolean error) {
        this.error = error;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LoginModel1 withMessage(String message) {
        this.message = message;
        return this;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public LoginModel1 withUserEmail(String userEmail) {
        this.userEmail = userEmail;
        return this;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(error);
        dest.writeValue(message);
        dest.writeValue(userEmail);
    }

    public int describeContents() {
        return 0;
    }

}