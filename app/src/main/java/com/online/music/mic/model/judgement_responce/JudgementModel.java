
package com.online.music.mic.model.judgement_responce;

import java.util.ArrayList;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JudgementModel implements Parcelable
{

    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("judgement")
    @Expose
    private List<Judgement> judgement = new ArrayList<Judgement>();
    public final static Creator<JudgementModel> CREATOR = new Creator<JudgementModel>() {


        @SuppressWarnings({
            "unchecked"
        })
        public JudgementModel createFromParcel(Parcel in) {
            return new JudgementModel(in);
        }

        public JudgementModel[] newArray(int size) {
            return (new JudgementModel[size]);
        }

    }
    ;

    protected JudgementModel(Parcel in) {
        this.error = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.judgement, (Judgement.class.getClassLoader()));
    }

    public JudgementModel() {
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public JudgementModel withError(Boolean error) {
        this.error = error;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public JudgementModel withMessage(String message) {
        this.message = message;
        return this;
    }

    public List<Judgement> getJudgement() {
        return judgement;
    }

    public void setJudgement(List<Judgement> judgement) {
        this.judgement = judgement;
    }

    public JudgementModel withJudgement(List<Judgement> judgement) {
        this.judgement = judgement;
        return this;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(error);
        dest.writeValue(message);
        dest.writeList(judgement);
    }

    public int describeContents() {
        return  0;
    }

}
