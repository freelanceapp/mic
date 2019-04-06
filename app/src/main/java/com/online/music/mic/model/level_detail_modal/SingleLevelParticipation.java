package com.online.music.mic.model.level_detail_modal;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SingleLevelParticipation implements Parcelable
{

    @SerializedName("participation_status")
    @Expose
    private Boolean participationStatus;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("admin_status")
    @Expose
    private String adminStatus;
    @SerializedName("payment_status")
    @Expose
    private String paymentStatus;
    @SerializedName("result_status")
    @Expose
    private String resultStatus;
    @SerializedName("content_status")
    @Expose
    private Boolean contentStatus;
    public final static Parcelable.Creator<SingleLevelParticipation> CREATOR = new Creator<SingleLevelParticipation>() {


        @SuppressWarnings({
                "unchecked"
        })
        public SingleLevelParticipation createFromParcel(Parcel in) {
            return new SingleLevelParticipation(in);
        }

        public SingleLevelParticipation[] newArray(int size) {
            return (new SingleLevelParticipation[size]);
        }

    }
            ;

    protected SingleLevelParticipation(Parcel in) {
        this.participationStatus = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.type = ((String) in.readValue((String.class.getClassLoader())));
        this.adminStatus = ((String) in.readValue((String.class.getClassLoader())));
        this.paymentStatus = ((String) in.readValue((String.class.getClassLoader())));
        this.resultStatus = ((String) in.readValue((String.class.getClassLoader())));
        this.contentStatus = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
    }

    public SingleLevelParticipation() {
    }

    public Boolean getParticipationStatus() {
        return participationStatus;
    }

    public void setParticipationStatus(Boolean participationStatus) {
        this.participationStatus = participationStatus;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAdminStatus() {
        return adminStatus;
    }

    public void setAdminStatus(String adminStatus) {
        this.adminStatus = adminStatus;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getResultStatus() {
        return resultStatus;
    }

    public void setResultStatus(String resultStatus) {
        this.resultStatus = resultStatus;
    }

    public Boolean getContentStatus() {
        return contentStatus;
    }

    public void setContentStatus(Boolean contentStatus) {
        this.contentStatus = contentStatus;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(participationStatus);
        dest.writeValue(id);
        dest.writeValue(type);
        dest.writeValue(adminStatus);
        dest.writeValue(paymentStatus);
        dest.writeValue(resultStatus);
        dest.writeValue(contentStatus);
    }

    public int describeContents() {
        return 0;
    }

}