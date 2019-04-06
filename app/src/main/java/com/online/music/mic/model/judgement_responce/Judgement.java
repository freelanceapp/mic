
package com.online.music.mic.model.judgement_responce;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Judgement implements Parcelable
{

    @SerializedName("judgement_id")
    @Expose
    private String judgementId;
    @SerializedName("judgement_judge_comment")
    @Expose
    private String judgementJudgeComment;
    @SerializedName("judgement_judge_score")
    @Expose
    private String judgementJudgeScore;
    @SerializedName("judgement_date")
    @Expose
    private String judgementDate;
    @SerializedName("judge")
    @Expose
    private String judge;
    @SerializedName("competition")
    @Expose
    private String competition;
    @SerializedName("competition_level")
    @Expose
    private String competitionLevel;
    public final static Creator<Judgement> CREATOR = new Creator<Judgement>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Judgement createFromParcel(Parcel in) {
            return new Judgement(in);
        }

        public Judgement[] newArray(int size) {
            return (new Judgement[size]);
        }

    }
    ;

    protected Judgement(Parcel in) {
        this.judgementId = ((String) in.readValue((String.class.getClassLoader())));
        this.judgementJudgeComment = ((String) in.readValue((String.class.getClassLoader())));
        this.judgementJudgeScore = ((String) in.readValue((String.class.getClassLoader())));
        this.judgementDate = ((String) in.readValue((String.class.getClassLoader())));
        this.judge = ((String) in.readValue((String.class.getClassLoader())));
        this.competition = ((String) in.readValue((String.class.getClassLoader())));
        this.competitionLevel = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Judgement() {
    }

    public String getJudgementId() {
        return judgementId;
    }

    public void setJudgementId(String judgementId) {
        this.judgementId = judgementId;
    }

    public Judgement withJudgementId(String judgementId) {
        this.judgementId = judgementId;
        return this;
    }

    public String getJudgementJudgeComment() {
        return judgementJudgeComment;
    }

    public void setJudgementJudgeComment(String judgementJudgeComment) {
        this.judgementJudgeComment = judgementJudgeComment;
    }

    public Judgement withJudgementJudgeComment(String judgementJudgeComment) {
        this.judgementJudgeComment = judgementJudgeComment;
        return this;
    }

    public String getJudgementJudgeScore() {
        return judgementJudgeScore;
    }

    public void setJudgementJudgeScore(String judgementJudgeScore) {
        this.judgementJudgeScore = judgementJudgeScore;
    }

    public Judgement withJudgementJudgeScore(String judgementJudgeScore) {
        this.judgementJudgeScore = judgementJudgeScore;
        return this;
    }

    public String getJudgementDate() {
        return judgementDate;
    }

    public void setJudgementDate(String judgementDate) {
        this.judgementDate = judgementDate;
    }

    public Judgement withJudgementDate(String judgementDate) {
        this.judgementDate = judgementDate;
        return this;
    }

    public String getJudge() {
        return judge;
    }

    public void setJudge(String judge) {
        this.judge = judge;
    }

    public Judgement withJudge(String judge) {
        this.judge = judge;
        return this;
    }

    public String getCompetition() {
        return competition;
    }

    public void setCompetition(String competition) {
        this.competition = competition;
    }

    public Judgement withCompetition(String competition) {
        this.competition = competition;
        return this;
    }

    public String getCompetitionLevel() {
        return competitionLevel;
    }

    public void setCompetitionLevel(String competitionLevel) {
        this.competitionLevel = competitionLevel;
    }

    public Judgement withCompetitionLevel(String competitionLevel) {
        this.competitionLevel = competitionLevel;
        return this;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(judgementId);
        dest.writeValue(judgementJudgeComment);
        dest.writeValue(judgementJudgeScore);
        dest.writeValue(judgementDate);
        dest.writeValue(judge);
        dest.writeValue(competition);
        dest.writeValue(competitionLevel);
    }

    public int describeContents() {
        return  0;
    }

}
