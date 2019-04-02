package com.mic.music.mic.model.level_detail_modal;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SingleLevelCompetition implements Parcelable
{

    @SerializedName("competition_level_id")
    @Expose
    private String competitionLevelId;
    @SerializedName("competition_level_name")
    @Expose
    private String competitionLevelName;
    @SerializedName("competition_level_description")
    @Expose
    private String competitionLevelDescription;
    @SerializedName("competition_level_duration")
    @Expose
    private String competitionLevelDuration;
    @SerializedName("competition_level_result")
    @Expose
    private String competitionLevelResult;
    @SerializedName("competition_level_payment_type")
    @Expose
    private String competitionLevelPaymentType;
    @SerializedName("competition_level_amount")
    @Expose
    private String competitionLevelAmount;
    @SerializedName("competition_level_content_type")
    @Expose
    private String competitionLevelContentType;
    @SerializedName("competition_level_rules")
    @Expose
    private String competitionLevelRules;
    @SerializedName("participation")
    @Expose
    private SingleLevelParticipation singleLevelParticipation;
    public final static Parcelable.Creator<SingleLevelCompetition> CREATOR = new Creator<SingleLevelCompetition>() {


        @SuppressWarnings({
                "unchecked"
        })
        public SingleLevelCompetition createFromParcel(Parcel in) {
            return new SingleLevelCompetition(in);
        }

        public SingleLevelCompetition[] newArray(int size) {
            return (new SingleLevelCompetition[size]);
        }

    }
            ;

    protected SingleLevelCompetition(Parcel in) {
        this.competitionLevelId = ((String) in.readValue((String.class.getClassLoader())));
        this.competitionLevelName = ((String) in.readValue((String.class.getClassLoader())));
        this.competitionLevelDescription = ((String) in.readValue((String.class.getClassLoader())));
        this.competitionLevelDuration = ((String) in.readValue((String.class.getClassLoader())));
        this.competitionLevelResult = ((String) in.readValue((String.class.getClassLoader())));
        this.competitionLevelPaymentType = ((String) in.readValue((String.class.getClassLoader())));
        this.competitionLevelAmount = ((String) in.readValue((String.class.getClassLoader())));
        this.competitionLevelContentType = ((String) in.readValue((String.class.getClassLoader())));
        this.competitionLevelRules = ((String) in.readValue((String.class.getClassLoader())));
        this.singleLevelParticipation = ((SingleLevelParticipation) in.readValue((SingleLevelParticipation.class.getClassLoader())));
    }

    public SingleLevelCompetition() {
    }

    public String getCompetitionLevelId() {
        return competitionLevelId;
    }

    public void setCompetitionLevelId(String competitionLevelId) {
        this.competitionLevelId = competitionLevelId;
    }

    public String getCompetitionLevelName() {
        return competitionLevelName;
    }

    public void setCompetitionLevelName(String competitionLevelName) {
        this.competitionLevelName = competitionLevelName;
    }

    public String getCompetitionLevelDescription() {
        return competitionLevelDescription;
    }

    public void setCompetitionLevelDescription(String competitionLevelDescription) {
        this.competitionLevelDescription = competitionLevelDescription;
    }

    public String getCompetitionLevelDuration() {
        return competitionLevelDuration;
    }

    public void setCompetitionLevelDuration(String competitionLevelDuration) {
        this.competitionLevelDuration = competitionLevelDuration;
    }

    public String getCompetitionLevelResult() {
        return competitionLevelResult;
    }

    public void setCompetitionLevelResult(String competitionLevelResult) {
        this.competitionLevelResult = competitionLevelResult;
    }

    public String getCompetitionLevelPaymentType() {
        return competitionLevelPaymentType;
    }

    public void setCompetitionLevelPaymentType(String competitionLevelPaymentType) {
        this.competitionLevelPaymentType = competitionLevelPaymentType;
    }

    public String getCompetitionLevelAmount() {
        return competitionLevelAmount;
    }

    public void setCompetitionLevelAmount(String competitionLevelAmount) {
        this.competitionLevelAmount = competitionLevelAmount;
    }

    public String getCompetitionLevelContentType() {
        return competitionLevelContentType;
    }

    public void setCompetitionLevelContentType(String competitionLevelContentType) {
        this.competitionLevelContentType = competitionLevelContentType;
    }

    public String getCompetitionLevelRules() {
        return competitionLevelRules;
    }

    public void setCompetitionLevelRules(String competitionLevelRules) {
        this.competitionLevelRules = competitionLevelRules;
    }

    public SingleLevelParticipation getSingleLevelParticipation() {
        return singleLevelParticipation;
    }

    public void setSingleLevelParticipation(SingleLevelParticipation singleLevelParticipation) {
        this.singleLevelParticipation = singleLevelParticipation;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(competitionLevelId);
        dest.writeValue(competitionLevelName);
        dest.writeValue(competitionLevelDescription);
        dest.writeValue(competitionLevelDuration);
        dest.writeValue(competitionLevelResult);
        dest.writeValue(competitionLevelPaymentType);
        dest.writeValue(competitionLevelAmount);
        dest.writeValue(competitionLevelContentType);
        dest.writeValue(competitionLevelRules);
        dest.writeValue(singleLevelParticipation);
    }

    public int describeContents() {
        return 0;
    }

}