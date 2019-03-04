
package com.mic.music.mic.model.compatition_level_rank_responce;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CompatitionLevelRankModel implements Parcelable
{

    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("competition_level")
    @Expose
    private CompetitionLevel competitionLevel;
    public final static Creator<CompatitionLevelRankModel> CREATOR = new Creator<CompatitionLevelRankModel>() {


        @SuppressWarnings({
            "unchecked"
        })
        public CompatitionLevelRankModel createFromParcel(Parcel in) {
            return new CompatitionLevelRankModel(in);
        }

        public CompatitionLevelRankModel[] newArray(int size) {
            return (new CompatitionLevelRankModel[size]);
        }

    }
    ;

    protected CompatitionLevelRankModel(Parcel in) {
        this.error = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
        this.competitionLevel = ((CompetitionLevel) in.readValue((CompetitionLevel.class.getClassLoader())));
    }

    public CompatitionLevelRankModel() {
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public CompatitionLevelRankModel withError(Boolean error) {
        this.error = error;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public CompatitionLevelRankModel withMessage(String message) {
        this.message = message;
        return this;
    }

    public CompetitionLevel getCompetitionLevel() {
        return competitionLevel;
    }

    public void setCompetitionLevel(CompetitionLevel competitionLevel) {
        this.competitionLevel = competitionLevel;
    }

    public CompatitionLevelRankModel withCompetitionLevel(CompetitionLevel competitionLevel) {
        this.competitionLevel = competitionLevel;
        return this;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(error);
        dest.writeValue(message);
        dest.writeValue(competitionLevel);
    }

    public int describeContents() {
        return  0;
    }

}
