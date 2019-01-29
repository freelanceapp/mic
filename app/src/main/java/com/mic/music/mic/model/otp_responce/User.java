
package com.mic.music.mic.model.otp_responce;

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
    private Object participantName;
    @SerializedName("participant_gendar")
    @Expose
    private Object participantGendar;
    @SerializedName("participant_dob")
    @Expose
    private Object participantDob;
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
    private Object participantState;
    @SerializedName("participant_country")
    @Expose
    private Object participantCountry;
    @SerializedName("participant_country_code")
    @Expose
    private Object participantCountryCode;
    @SerializedName("participant_image")
    @Expose
    private Object participantImage;
    @SerializedName("participant_mobile_verification_status")
    @Expose
    private Object participantMobileVerificationStatus;
    @SerializedName("participant_email_verification_status")
    @Expose
    private Object participantEmailVerificationStatus;
    @SerializedName("participant_email_otp")
    @Expose
    private Object participantEmailOtp;
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
        this.participantName = ((Object) in.readValue((Object.class.getClassLoader())));
        this.participantGendar = ((Object) in.readValue((Object.class.getClassLoader())));
        this.participantDob = ((Object) in.readValue((Object.class.getClassLoader())));
        this.participantOrganization = ((Object) in.readValue((Object.class.getClassLoader())));
        this.participantAddress = ((Object) in.readValue((Object.class.getClassLoader())));
        this.participantCity = ((Object) in.readValue((Object.class.getClassLoader())));
        this.participantState = ((Object) in.readValue((Object.class.getClassLoader())));
        this.participantCountry = ((Object) in.readValue((Object.class.getClassLoader())));
        this.participantCountryCode = ((Object) in.readValue((Object.class.getClassLoader())));
        this.participantImage = ((Object) in.readValue((Object.class.getClassLoader())));
        this.participantMobileVerificationStatus = ((Object) in.readValue((Object.class.getClassLoader())));
        this.participantEmailVerificationStatus = ((Object) in.readValue((Object.class.getClassLoader())));
        this.participantEmailOtp = ((Object) in.readValue((Object.class.getClassLoader())));
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

    public Object getParticipantName() {
        return participantName;
    }

    public void setParticipantName(Object participantName) {
        this.participantName = participantName;
    }

    public User withParticipantName(Object participantName) {
        this.participantName = participantName;
        return this;
    }

    public Object getParticipantGendar() {
        return participantGendar;
    }

    public void setParticipantGendar(Object participantGendar) {
        this.participantGendar = participantGendar;
    }

    public User withParticipantGendar(Object participantGendar) {
        this.participantGendar = participantGendar;
        return this;
    }

    public Object getParticipantDob() {
        return participantDob;
    }

    public void setParticipantDob(Object participantDob) {
        this.participantDob = participantDob;
    }

    public User withParticipantDob(Object participantDob) {
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

    public Object getParticipantState() {
        return participantState;
    }

    public void setParticipantState(Object participantState) {
        this.participantState = participantState;
    }

    public User withParticipantState(Object participantState) {
        this.participantState = participantState;
        return this;
    }

    public Object getParticipantCountry() {
        return participantCountry;
    }

    public void setParticipantCountry(Object participantCountry) {
        this.participantCountry = participantCountry;
    }

    public User withParticipantCountry(Object participantCountry) {
        this.participantCountry = participantCountry;
        return this;
    }

    public Object getParticipantCountryCode() {
        return participantCountryCode;
    }

    public void setParticipantCountryCode(Object participantCountryCode) {
        this.participantCountryCode = participantCountryCode;
    }

    public User withParticipantCountryCode(Object participantCountryCode) {
        this.participantCountryCode = participantCountryCode;
        return this;
    }

    public Object getParticipantImage() {
        return participantImage;
    }

    public void setParticipantImage(Object participantImage) {
        this.participantImage = participantImage;
    }

    public User withParticipantImage(Object participantImage) {
        this.participantImage = participantImage;
        return this;
    }

    public Object getParticipantMobileVerificationStatus() {
        return participantMobileVerificationStatus;
    }

    public void setParticipantMobileVerificationStatus(Object participantMobileVerificationStatus) {
        this.participantMobileVerificationStatus = participantMobileVerificationStatus;
    }

    public User withParticipantMobileVerificationStatus(Object participantMobileVerificationStatus) {
        this.participantMobileVerificationStatus = participantMobileVerificationStatus;
        return this;
    }

    public Object getParticipantEmailVerificationStatus() {
        return participantEmailVerificationStatus;
    }

    public void setParticipantEmailVerificationStatus(Object participantEmailVerificationStatus) {
        this.participantEmailVerificationStatus = participantEmailVerificationStatus;
    }

    public User withParticipantEmailVerificationStatus(Object participantEmailVerificationStatus) {
        this.participantEmailVerificationStatus = participantEmailVerificationStatus;
        return this;
    }

    public Object getParticipantEmailOtp() {
        return participantEmailOtp;
    }

    public void setParticipantEmailOtp(Object participantEmailOtp) {
        this.participantEmailOtp = participantEmailOtp;
    }

    public User withParticipantEmailOtp(Object participantEmailOtp) {
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
        dest.writeValue(participantMobileVerificationStatus);
        dest.writeValue(participantEmailVerificationStatus);
        dest.writeValue(participantEmailOtp);
        dest.writeValue(participantRegistrationDate);
    }

    public int describeContents() {
        return  0;
    }

}
