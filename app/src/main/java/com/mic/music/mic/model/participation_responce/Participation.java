
package com.mic.music.mic.model.participation_responce;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Participation implements Parcelable
{

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
    @SerializedName("competition_level")
    @Expose
    private String competitionLevel;
    @SerializedName("content_status")
    @Expose
    private String content_status;
    public final static Creator<Participation> CREATOR = new Creator<Participation>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Participation createFromParcel(Parcel in) {
            return new Participation(in);
        }

        public Participation[] newArray(int size) {
            return (new Participation[size]);
        }

    }
    ;

    protected Participation(Parcel in) {
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.type = ((String) in.readValue((String.class.getClassLoader())));
        this.adminStatus = ((String) in.readValue((String.class.getClassLoader())));
        this.paymentStatus = ((String) in.readValue((String.class.getClassLoader())));
        this.resultStatus = ((String) in.readValue((String.class.getClassLoader())));
        this.competitionLevel = ((String) in.readValue((String.class.getClassLoader())));
        this.content_status = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Participation() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Participation withId(String id) {
        this.id = id;
        return this;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Participation withType(String type) {
        this.type = type;
        return this;
    }

    public String getAdminStatus() {
        return adminStatus;
    }

    public void setAdminStatus(String adminStatus) {
        this.adminStatus = adminStatus;
    }

    public Participation withAdminStatus(String adminStatus) {
        this.adminStatus = adminStatus;
        return this;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Participation withPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
        return this;
    }

    public String getResultStatus() {
        return resultStatus;
    }

    public void setResultStatus(String resultStatus) {
        this.resultStatus = resultStatus;
    }

    public Participation withResultStatus(String resultStatus) {
        this.resultStatus = resultStatus;
        return this;
    }

    public String getCompetitionLevel() {
        return competitionLevel;
    }

    public void setCompetitionLevel(String competitionLevel) {
        this.competitionLevel = competitionLevel;
    }

    public String getContent_status() {
        return content_status;
    }

    public void setContent_status(String content_status) {
        this.content_status = content_status;
    }

    public Participation withCompetitionLevel(String competitionLevel) {
        this.competitionLevel = competitionLevel;
        return this;
    }

    public Participation withContent_status(String content_status) {
        this.content_status = content_status;
        return this;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(type);
        dest.writeValue(adminStatus);
        dest.writeValue(paymentStatus);
        dest.writeValue(resultStatus);
        dest.writeValue(competitionLevel);
        dest.writeValue(content_status);
    }

    public int describeContents() {
        return  0;
    }

}
