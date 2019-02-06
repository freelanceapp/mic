
package com.mic.music.mic.model.user_responce;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CompetitionContent implements Parcelable
{

    @SerializedName("competition_content_id")
    @Expose
    private String competitionContentId;
    @SerializedName("competition_content_url")
    @Expose
    private String competitionContentUrl;
    @SerializedName("competition_content_type")
    @Expose
    private String competitionContentType;
    @SerializedName("competition_content_competition_id")
    @Expose
    private String competitionContentCompetitionId;
    @SerializedName("competition_content_competition_level_id")
    @Expose
    private String competitionContentCompetitionLevelId;
    @SerializedName("competition_content_participant_id")
    @Expose
    private String competitionContentParticipantId;
    @SerializedName("competition_content_status")
    @Expose
    private String competitionContentStatus;
    @SerializedName("competition_content_date")
    @Expose
    private String competitionContentDate;
    @SerializedName("competition_content_update_date")
    @Expose
    private String competitionContentUpdateDate;
    public final static Creator<CompetitionContent> CREATOR = new Creator<CompetitionContent>() {


        @SuppressWarnings({
            "unchecked"
        })
        public CompetitionContent createFromParcel(Parcel in) {
            return new CompetitionContent(in);
        }

        public CompetitionContent[] newArray(int size) {
            return (new CompetitionContent[size]);
        }

    }
    ;

    protected CompetitionContent(Parcel in) {
        this.competitionContentId = ((String) in.readValue((String.class.getClassLoader())));
        this.competitionContentUrl = ((String) in.readValue((String.class.getClassLoader())));
        this.competitionContentType = ((String) in.readValue((String.class.getClassLoader())));
        this.competitionContentCompetitionId = ((String) in.readValue((String.class.getClassLoader())));
        this.competitionContentCompetitionLevelId = ((String) in.readValue((String.class.getClassLoader())));
        this.competitionContentParticipantId = ((String) in.readValue((String.class.getClassLoader())));
        this.competitionContentStatus = ((String) in.readValue((String.class.getClassLoader())));
        this.competitionContentDate = ((String) in.readValue((String.class.getClassLoader())));
        this.competitionContentUpdateDate = ((String) in.readValue((String.class.getClassLoader())));
    }

    public CompetitionContent() {
    }

    public String getCompetitionContentId() {
        return competitionContentId;
    }

    public void setCompetitionContentId(String competitionContentId) {
        this.competitionContentId = competitionContentId;
    }

    public CompetitionContent withCompetitionContentId(String competitionContentId) {
        this.competitionContentId = competitionContentId;
        return this;
    }

    public String getCompetitionContentUrl() {
        return competitionContentUrl;
    }

    public void setCompetitionContentUrl(String competitionContentUrl) {
        this.competitionContentUrl = competitionContentUrl;
    }

    public CompetitionContent withCompetitionContentUrl(String competitionContentUrl) {
        this.competitionContentUrl = competitionContentUrl;
        return this;
    }

    public String getCompetitionContentType() {
        return competitionContentType;
    }

    public void setCompetitionContentType(String competitionContentType) {
        this.competitionContentType = competitionContentType;
    }

    public CompetitionContent withCompetitionContentType(String competitionContentType) {
        this.competitionContentType = competitionContentType;
        return this;
    }

    public String getCompetitionContentCompetitionId() {
        return competitionContentCompetitionId;
    }

    public void setCompetitionContentCompetitionId(String competitionContentCompetitionId) {
        this.competitionContentCompetitionId = competitionContentCompetitionId;
    }

    public CompetitionContent withCompetitionContentCompetitionId(String competitionContentCompetitionId) {
        this.competitionContentCompetitionId = competitionContentCompetitionId;
        return this;
    }

    public String getCompetitionContentCompetitionLevelId() {
        return competitionContentCompetitionLevelId;
    }

    public void setCompetitionContentCompetitionLevelId(String competitionContentCompetitionLevelId) {
        this.competitionContentCompetitionLevelId = competitionContentCompetitionLevelId;
    }

    public CompetitionContent withCompetitionContentCompetitionLevelId(String competitionContentCompetitionLevelId) {
        this.competitionContentCompetitionLevelId = competitionContentCompetitionLevelId;
        return this;
    }

    public String getCompetitionContentParticipantId() {
        return competitionContentParticipantId;
    }

    public void setCompetitionContentParticipantId(String competitionContentParticipantId) {
        this.competitionContentParticipantId = competitionContentParticipantId;
    }

    public CompetitionContent withCompetitionContentParticipantId(String competitionContentParticipantId) {
        this.competitionContentParticipantId = competitionContentParticipantId;
        return this;
    }

    public String getCompetitionContentStatus() {
        return competitionContentStatus;
    }

    public void setCompetitionContentStatus(String competitionContentStatus) {
        this.competitionContentStatus = competitionContentStatus;
    }

    public CompetitionContent withCompetitionContentStatus(String competitionContentStatus) {
        this.competitionContentStatus = competitionContentStatus;
        return this;
    }

    public String getCompetitionContentDate() {
        return competitionContentDate;
    }

    public void setCompetitionContentDate(String competitionContentDate) {
        this.competitionContentDate = competitionContentDate;
    }

    public CompetitionContent withCompetitionContentDate(String competitionContentDate) {
        this.competitionContentDate = competitionContentDate;
        return this;
    }

    public String getCompetitionContentUpdateDate() {
        return competitionContentUpdateDate;
    }

    public void setCompetitionContentUpdateDate(String competitionContentUpdateDate) {
        this.competitionContentUpdateDate = competitionContentUpdateDate;
    }

    public CompetitionContent withCompetitionContentUpdateDate(String competitionContentUpdateDate) {
        this.competitionContentUpdateDate = competitionContentUpdateDate;
        return this;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(competitionContentId);
        dest.writeValue(competitionContentUrl);
        dest.writeValue(competitionContentType);
        dest.writeValue(competitionContentCompetitionId);
        dest.writeValue(competitionContentCompetitionLevelId);
        dest.writeValue(competitionContentParticipantId);
        dest.writeValue(competitionContentStatus);
        dest.writeValue(competitionContentDate);
        dest.writeValue(competitionContentUpdateDate);
    }

    public int describeContents() {
        return  0;
    }

}
