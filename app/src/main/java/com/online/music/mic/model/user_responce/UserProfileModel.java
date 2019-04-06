
package com.online.music.mic.model.user_responce;

import java.util.ArrayList;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserProfileModel implements Parcelable
{

    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("user_type")
    @Expose
    private String userType;
    @SerializedName("competition_content")
    @Expose
    private List<CompetitionContent> competitionContent = new ArrayList<CompetitionContent>();
    public final static Creator<UserProfileModel> CREATOR = new Creator<UserProfileModel>() {


        @SuppressWarnings({
            "unchecked"
        })
        public UserProfileModel createFromParcel(Parcel in) {
            return new UserProfileModel(in);
        }

        public UserProfileModel[] newArray(int size) {
            return (new UserProfileModel[size]);
        }

    }
    ;

    protected UserProfileModel(Parcel in) {
        this.error = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
        this.user = ((User) in.readValue((User.class.getClassLoader())));
        this.userType = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.competitionContent, (CompetitionContent.class.getClassLoader()));
    }

    public UserProfileModel() {
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public UserProfileModel withError(Boolean error) {
        this.error = error;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UserProfileModel withMessage(String message) {
        this.message = message;
        return this;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserProfileModel withUser(User user) {
        this.user = user;
        return this;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public UserProfileModel withUserType(String userType) {
        this.userType = userType;
        return this;
    }

    public List<CompetitionContent> getCompetitionContent() {
        return competitionContent;
    }

    public void setCompetitionContent(List<CompetitionContent> competitionContent) {
        this.competitionContent = competitionContent;
    }

    public UserProfileModel withCompetitionContent(List<CompetitionContent> competitionContent) {
        this.competitionContent = competitionContent;
        return this;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(error);
        dest.writeValue(message);
        dest.writeValue(user);
        dest.writeValue(userType);
        dest.writeList(competitionContent);
    }

    public int describeContents() {
        return  0;
    }

}
