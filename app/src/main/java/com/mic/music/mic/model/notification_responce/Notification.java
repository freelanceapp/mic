
package com.mic.music.mic.model.notification_responce;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Notification implements Parcelable
{

    @SerializedName("notification_id")
    @Expose
    private String notificationId;
    @SerializedName("notification_title")
    @Expose
    private String notificationTitle;
    @SerializedName("notification_message")
    @Expose
    private String notificationMessage;
    @SerializedName("notification_image")
    @Expose
    private String notificationImage;
    @SerializedName("notification_image_status")
    @Expose
    private String notification_image_status;
    public final static Creator<Notification> CREATOR = new Creator<Notification>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Notification createFromParcel(Parcel in) {
            return new Notification(in);
        }

        public Notification[] newArray(int size) {
            return (new Notification[size]);
        }

    }
    ;

    protected Notification(Parcel in) {
        this.notificationId = ((String) in.readValue((String.class.getClassLoader())));
        this.notificationTitle = ((String) in.readValue((String.class.getClassLoader())));
        this.notificationMessage = ((String) in.readValue((String.class.getClassLoader())));
        this.notificationImage = ((String) in.readValue((Object.class.getClassLoader())));
        this.notification_image_status = ((String) in.readValue((Object.class.getClassLoader())));
    }

    public Notification() {
    }

    public String getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(String notificationId) {
        this.notificationId = notificationId;
    }

    public Notification withNotificationId(String notificationId) {
        this.notificationId = notificationId;
        return this;
    }

    public String getNotificationTitle() {
        return notificationTitle;
    }

    public void setNotificationTitle(String notificationTitle) {
        this.notificationTitle = notificationTitle;
    }

    public Notification withNotificationTitle(String notificationTitle) {
        this.notificationTitle = notificationTitle;
        return this;
    }

    public String getNotificationMessage() {
        return notificationMessage;
    }

    public void setNotificationMessage(String notificationMessage) {
        this.notificationMessage = notificationMessage;
    }

    public Notification withNotificationMessage(String notificationMessage) {
        this.notificationMessage = notificationMessage;
        return this;
    }

    public String getNotificationImage() {
        return notificationImage;
    }

    public void setNotificationImage(String notificationImage) {
        this.notificationImage = notificationImage;
    }

    public String getNotification_image_status() {
        return notification_image_status;
    }

    public void setNotification_image_status(String notification_image_status) {
        this.notification_image_status = notification_image_status;
    }

    public Notification withNotificationImage(String notificationImage) {
        this.notificationImage = notificationImage;
        return this;
    }

    public Notification withNotification_image_status(String notification_image_status) {
        this.notification_image_status = notification_image_status;
        return this;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(notificationId);
        dest.writeValue(notificationTitle);
        dest.writeValue(notificationMessage);
        dest.writeValue(notificationImage);
        dest.writeValue(notification_image_status);
    }

    public int describeContents() {
        return  0;
    }

}
