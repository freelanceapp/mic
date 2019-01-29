
package com.mic.music.mic.model.user_responce;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User implements Parcelable
{

    @SerializedName("participant_id")
    @Expose
    private String participantId;
    @SerializedName("participant_name")
    @Expose
    private String participantName;
    @SerializedName("participant_gendar")
    @Expose
    private String participantGendar;
    @SerializedName("participant_dob")
    @Expose
    private String participantDob;
    @SerializedName("participant_organization")
    @Expose
    private Object participantOrganization;
    @SerializedName("participant_address")
    @Expose
    private Object participantAddress;
    @SerializedName("participant_city")
    @Expose
    private Object participantCity;
    @SerializedName("participant_state")
    @Expose
    private String participantState;
    @SerializedName("participant_country")
    @Expose
    private String participantCountry;
    @SerializedName("participant_country_code")
    @Expose
    private String participantCountryCode;
    @SerializedName("participant_image")
    @Expose
    private String participantImage;
    @SerializedName("participant_email_verification_status")
    @Expose
    private String participantEmailVerificationStatus;
    @SerializedName("participant_email_otp")
    @Expose
    private String participantEmailOtp;
    @SerializedName("participant_registration_date")
    @Expose
    private String participantRegistrationDate;
    public final static Creator<User> CREATOR = new Creator<User>() {


        @SuppressWarnings({
            "unchecked"
        })
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        public User[] newArray(int size) {
            return (new User[size]);
        }

    }
    ;

    protected User(Parcel in) {
        this.participantId = ((String) in.readValue((String.class.getClassLoader())));
        this.participantName = ((String) in.readValue((String.class.getClassLoader())));
        this.participantGendar = ((String) in.readValue((String.class.getClassLoader())));
        this.participantDob = ((String) in.readValue((String.class.getClassLoader())));
        this.participantOrganization = ((Object) in.readValue((Object.class.getClassLoader())));
        this.participantAddress = ((Object) in.readValue((Object.class.getClassLoader())));
        this.participantCity = ((Object) in.readValue((Object.class.getClassLoader())));
        this.participantState = ((String) in.readValue((String.class.getClassLoader())));
        this.participantCountry = ((String) in.readValue((String.class.getClassLoader())));
        this.participantCountryCode = ((String) in.readValue((String.class.getClassLoader())));
        this.participantImage = ((String) in.readValue((String.class.getClassLoader())));
        this.participantEmailVerificationStatus = ((String) in.readValue((String.class.getClassLoader())));
        this.participantEmailOtp = ((String) in.readValue((String.class.getClassLoader())));
        this.participantRegistrationDate = ((String) in.readValue((String.class.getClassLoader())));
    }

    public User() {
    }

    public String getParticipantId() {
        return participantId;
    }

    public void setParticipantId(String participantId) {
        this.participantId = participantId;
    }

    public User withParticipantId(String participantId) {
        this.participantId = participantId;
        return this;
    }

    public String getParticipantName() {
        return participantName;
    }

    public void setParticipantName(String participantName) {
        this.participantName = participantName;
    }

    public User withParticipantName(String participantName) {
        this.participantName = participantName;
        return this;
    }

    public String getParticipantGendar() {
        return participantGendar;
    }

    public void setParticipantGendar(String participantGendar) {
        this.participantGendar = participantGendar;
    }

    public User withParticipantGendar(String participantGendar) {
        this.participantGendar = participantGendar;
        return this;
    }

    public String getParticipantDob() {
        return participantDob;
    }

    public void setParticipantDob(String participantDob) {
        this.participantDob = participantDob;
    }

    public User withParticipantDob(String participantDob) {
        this.participantDob = participantDob;
        return this;
    }

    public Object getParticipantOrganization() {
        return participantOrganization;
    }

    public void setParticipantOrganization(Object participantOrganization) {
        this.participantOrganization = participantOrganization;
    }

    public User withParticipantOrganization(Object participantOrganization) {
        this.participantOrganization = participantOrganization;
        return this;
    }

    public Object getParticipantAddress() {
        return participantAddress;
    }

    public void setParticipantAddress(Object participantAddress) {
        this.participantAddress = participantAddress;
    }

    public User withParticipantAddress(Object participantAddress) {
        this.participantAddress = participantAddress;
        return this;
    }

    public Object getParticipantCity() {
        return participantCity;
    }

    public void setParticipantCity(Object participantCity) {
        this.participantCity = participantCity;
    }

    public User withParticipantCity(Object participantCity) {
        this.participantCity = participantCity;
        return this;
    }

    public String getParticipantState() {
        return participantState;
    }

    public void setParticipantState(String participantState) {
        this.participantState = participantState;
    }

    public User withParticipantState(String participantState) {
        this.participantState = participantState;
        return this;
    }

    public String getParticipantCountry() {
        return participantCountry;
    }

    public void setParticipantCountry(String participantCountry) {
        this.participantCountry = participantCountry;
    }

    public User withParticipantCountry(String participantCountry) {
        this.participantCountry = participantCountry;
        return this;
    }

    public String getParticipantCountryCode() {
        return participantCountryCode;
    }

    public void setParticipantCountryCode(String participantCountryCode) {
        this.participantCountryCode = participantCountryCode;
    }

    public User withParticipantCountryCode(String participantCountryCode) {
        this.participantCountryCode = participantCountryCode;
        return this;
    }

    public String getParticipantImage() {
        return participantImage;
    }

    public void setParticipantImage(String participantImage) {
        this.participantImage = participantImage;
    }

    public User withParticipantImage(String participantImage) {
        this.participantImage = participantImage;
        return this;
    }

    public String getParticipantEmailVerificationStatus() {
        return participantEmailVerificationStatus;
    }

    public void setParticipantEmailVerificationStatus(String participantEmailVerificationStatus) {
        this.participantEmailVerificationStatus = participantEmailVerificationStatus;
    }

    public User withParticipantEmailVerificationStatus(String participantEmailVerificationStatus) {
        this.participantEmailVerificationStatus = participantEmailVerificationStatus;
        return this;
    }

    public String getParticipantEmailOtp() {
        return participantEmailOtp;
    }

    public void setParticipantEmailOtp(String participantEmailOtp) {
        this.participantEmailOtp = participantEmailOtp;
    }

    public User withParticipantEmailOtp(String participantEmailOtp) {
        this.participantEmailOtp = participantEmailOtp;
        return this;
    }

    public String getParticipantRegistrationDate() {
        return participantRegistrationDate;
    }

    public void setParticipantRegistrationDate(String participantRegistrationDate) {
        this.participantRegistrationDate = participantRegistrationDate;
    }

    public User withParticipantRegistrationDate(String participantRegistrationDate) {
        this.participantRegistrationDate = participantRegistrationDate;
        return this;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(participantId);
        dest.writeValue(participantName);
        dest.writeValue(participantGendar);
        dest.writeValue(participantDob);
        dest.writeValue(participantOrganization);
        dest.writeValue(participantAddress);
        dest.writeValue(participantCity);
        dest.writeValue(participantState);
        dest.writeValue(participantCountry);
        dest.writeValue(participantCountryCode);
        dest.writeValue(participantImage);
        dest.writeValue(participantEmailVerificationStatus);
        dest.writeValue(participantEmailOtp);
        dest.writeValue(participantRegistrationDate);
    }

    public int describeContents() {
        return  0;
    }

}
