
package com.online.music.mic.model.competition_responce;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CompetitionLevel implements Parcelable
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
    public final static Creator<CompetitionLevel> CREATOR = new Creator<CompetitionLevel>() {


        @SuppressWarnings({
            "unchecked"
        })
        public CompetitionLevel createFromParcel(Parcel in) {
            return new CompetitionLevel(in);
        }

        public CompetitionLevel[] newArray(int size) {
            return (new CompetitionLevel[size]);
        }

    }
    ;

    protected CompetitionLevel(Parcel in) {
        this.competitionLevelId = ((String) in.readValue((String.class.getClassLoader())));
        this.competitionLevelName = ((String) in.readValue((String.class.getClassLoader())));
        this.competitionLevelDescription = ((String) in.readValue((String.class.getClassLoader())));
        this.competitionLevelDuration = ((String) in.readValue((String.class.getClassLoader())));
        this.competitionLevelResult = ((String) in.readValue((String.class.getClassLoader())));
        this.competitionLevelPaymentType = ((String) in.readValue((String.class.getClassLoader())));
        this.competitionLevelAmount = ((String) in.readValue((String.class.getClassLoader())));
        this.competitionLevelContentType = ((String) in.readValue((String.class.getClassLoader())));
        this.competitionLevelRules = ((String) in.readValue((String.class.getClassLoader())));
    }

    public CompetitionLevel() {
    }

    public String getCompetitionLevelId() {
        return competitionLevelId;
    }

    public void setCompetitionLevelId(String competitionLevelId) {
        this.competitionLevelId = competitionLevelId;
    }

    public CompetitionLevel withCompetitionLevelId(String competitionLevelId) {
        this.competitionLevelId = competitionLevelId;
        return this;
    }

    public String getCompetitionLevelName() {
        return competitionLevelName;
    }

    public void setCompetitionLevelName(String competitionLevelName) {
        this.competitionLevelName = competitionLevelName;
    }

    public CompetitionLevel withCompetitionLevelName(String competitionLevelName) {
        this.competitionLevelName = competitionLevelName;
        return this;
    }

    public String getCompetitionLevelDescription() {
        return competitionLevelDescription;
    }

    public void setCompetitionLevelDescription(String competitionLevelDescription) {
        this.competitionLevelDescription = competitionLevelDescription;
    }

    public CompetitionLevel withCompetitionLevelDescription(String competitionLevelDescription) {
        this.competitionLevelDescription = competitionLevelDescription;
        return this;
    }

    public String getCompetitionLevelDuration() {
        return competitionLevelDuration;
    }

    public void setCompetitionLevelDuration(String competitionLevelDuration) {
        this.competitionLevelDuration = competitionLevelDuration;
    }

    public CompetitionLevel withCompetitionLevelDuration(String competitionLevelDuration) {
        this.competitionLevelDuration = competitionLevelDuration;
        return this;
    }

    public String getCompetitionLevelResult() {
        return competitionLevelResult;
    }

    public void setCompetitionLevelResult(String competitionLevelResult) {
        this.competitionLevelResult = competitionLevelResult;
    }

    public CompetitionLevel withCompetitionLevelResult(String competitionLevelResult) {
        this.competitionLevelResult = competitionLevelResult;
        return this;
    }

    public String getCompetitionLevelPaymentType() {
        return competitionLevelPaymentType;
    }

    public void setCompetitionLevelPaymentType(String competitionLevelPaymentType) {
        this.competitionLevelPaymentType = competitionLevelPaymentType;
    }

    public CompetitionLevel withCompetitionLevelPaymentType(String competitionLevelPaymentType) {
        this.competitionLevelPaymentType = competitionLevelPaymentType;
        return this;
    }

    public String getCompetitionLevelAmount() {
        return competitionLevelAmount;
    }

    public void setCompetitionLevelAmount(String competitionLevelAmount) {
        this.competitionLevelAmount = competitionLevelAmount;
    }

    public CompetitionLevel withCompetitionLevelAmount(String competitionLevelAmount) {
        this.competitionLevelAmount = competitionLevelAmount;
        return this;
    }

    public String getCompetitionLevelContentType() {
        return competitionLevelContentType;
    }

    public void setCompetitionLevelContentType(String competitionLevelContentType) {
        this.competitionLevelContentType = competitionLevelContentType;
    }

    public CompetitionLevel withCompetitionLevelContentType(String competitionLevelContentType) {
        this.competitionLevelContentType = competitionLevelContentType;
        return this;
    }

    public String getCompetitionLevelRules() {
        return competitionLevelRules;
    }

    public void setCompetitionLevelRules(String competitionLevelRules) {
        this.competitionLevelRules = competitionLevelRules;
    }

    public CompetitionLevel withCompetitionLevelRules(String competitionLevelRules) {
        this.competitionLevelRules = competitionLevelRules;
        return this;
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
    }

    public int describeContents() {
        return  0;
    }

}
