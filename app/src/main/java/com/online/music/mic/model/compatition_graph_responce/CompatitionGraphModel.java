
package com.online.music.mic.model.compatition_graph_responce;

import java.util.ArrayList;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CompatitionGraphModel implements Parcelable
{

    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("competition_level")
    @Expose
    private List<CompetitionLevel> competitionLevel = new ArrayList<CompetitionLevel>();
    public final static Parcelable.Creator<CompatitionGraphModel> CREATOR = new Creator<CompatitionGraphModel>() {


        @SuppressWarnings({
                "unchecked"
        })
        public CompatitionGraphModel createFromParcel(Parcel in) {
            return new CompatitionGraphModel(in);
        }

        public CompatitionGraphModel[] newArray(int size) {
            return (new CompatitionGraphModel[size]);
        }

    }
            ;

    protected CompatitionGraphModel(Parcel in) {
        this.error = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.competitionLevel, (com.online.music.mic.model.compatition_graph_responce.CompetitionLevel.class.getClassLoader()));
    }

    public CompatitionGraphModel() {
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public CompatitionGraphModel withError(Boolean error) {
        this.error = error;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public CompatitionGraphModel withMessage(String message) {
        this.message = message;
        return this;
    }

    public List<CompetitionLevel> getCompetitionLevel() {
        return competitionLevel;
    }

    public void setCompetitionLevel(List<CompetitionLevel> competitionLevel) {
        this.competitionLevel = competitionLevel;
    }

    public CompatitionGraphModel withCompetitionLevel(List<CompetitionLevel> competitionLevel) {
        this.competitionLevel = competitionLevel;
        return this;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(error);
        dest.writeValue(message);
        dest.writeList(competitionLevel);
    }

    public int describeContents() {
        return 0;
    }

}
