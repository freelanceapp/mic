
package com.online.music.mic.model.compatition_level_rank_responce;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class Participant implements Parcelable
{

    @SerializedName("participant_id")
    @Expose
    private String participantId;
    @SerializedName("participant_name")
    @Expose
    private String participantName;
    @SerializedName("score")
    @Expose
    private String score;
    @SerializedName("content_uploaded_date")
    @Expose
    private String contentUploadedDate;
    public final static Creator<Participant> CREATOR = new Creator<Participant>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Participant createFromParcel(Parcel in) {
            return new Participant(in);
        }

        public Participant[] newArray(int size) {
            return (new Participant[size]);
        }

    }
    ;

    protected Participant(Parcel in) {
        this.participantId = ((String) in.readValue((String.class.getClassLoader())));
        this.participantName = ((String) in.readValue((String.class.getClassLoader())));
        this.score = ((String) in.readValue((String.class.getClassLoader())));
        this.contentUploadedDate = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Participant() {
    }

    public String getParticipantId() {
        return participantId;
    }

    public void setParticipantId(String participantId) {
        this.participantId = participantId;
    }

    public Participant withParticipantId(String participantId) {
        this.participantId = participantId;
        return this;
    }

    public String getParticipantName() {
        return participantName;
    }

    public void setParticipantName(String participantName) {
        this.participantName = participantName;
    }

    public Participant withParticipantName(String participantName) {
        this.participantName = participantName;
        return this;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public Participant withScore(String score) {
        this.score = score;
        return this;
    }

    public String getContentUploadedDate() {
        return contentUploadedDate;
    }

    public void setContentUploadedDate(String contentUploadedDate) {
        this.contentUploadedDate = contentUploadedDate;
    }

    public Participant withContentUploadedDate(String contentUploadedDate) {
        this.contentUploadedDate = contentUploadedDate;
        return this;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(participantId);
        dest.writeValue(participantName);
        dest.writeValue(score);
        dest.writeValue(contentUploadedDate);
    }

    public int describeContents() {
        return  0;
    }


    public static Comparator<Participant> hightolowComparator = new Comparator<Participant>() {
        @Override
        public int compare(Participant jc1, Participant jc2) {
            return (Integer.parseInt(jc1.getScore()) > Integer.parseInt(jc2.getScore()) ? -1 :
                    (jc1.getScore() == jc2.getScore() ? 0 : 1));
        }
    };



}
