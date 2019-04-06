
package com.online.music.mic.model.notification_responce;

import java.util.ArrayList;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NotificationModel implements Parcelable
{

    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("notification")
    @Expose
    private List<Notification> notification = new ArrayList<Notification>();
    public final static Creator<NotificationModel> CREATOR = new Creator<NotificationModel>() {


        @SuppressWarnings({
            "unchecked"
        })
        public NotificationModel createFromParcel(Parcel in) {
            return new NotificationModel(in);
        }

        public NotificationModel[] newArray(int size) {
            return (new NotificationModel[size]);
        }

    }
    ;

    protected NotificationModel(Parcel in) {
        this.error = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.notification, (Notification.class.getClassLoader()));
    }

    public NotificationModel() {
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public NotificationModel withError(Boolean error) {
        this.error = error;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public NotificationModel withMessage(String message) {
        this.message = message;
        return this;
    }

    public List<Notification> getNotification() {
        return notification;
    }

    public void setNotification(List<Notification> notification) {
        this.notification = notification;
    }

    public NotificationModel withNotification(List<Notification> notification) {
        this.notification = notification;
        return this;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(error);
        dest.writeValue(message);
        dest.writeList(notification);
    }

    public int describeContents() {
        return  0;
    }

}
