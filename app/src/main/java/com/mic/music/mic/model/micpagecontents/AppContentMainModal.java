
package com.mic.music.mic.model.micpagecontents;

import java.util.ArrayList;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AppContentMainModal implements Parcelable
{

    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("page_content")
    @Expose
    private List<PageContent> pageContent = new ArrayList<PageContent>();
    public final static Creator<AppContentMainModal> CREATOR = new Creator<AppContentMainModal>() {


        @SuppressWarnings({
            "unchecked"
        })
        public AppContentMainModal createFromParcel(Parcel in) {
            return new AppContentMainModal(in);
        }

        public AppContentMainModal[] newArray(int size) {
            return (new AppContentMainModal[size]);
        }

    }
    ;

    protected AppContentMainModal(Parcel in) {
        this.error = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.pageContent, (PageContent.class.getClassLoader()));
    }

    public AppContentMainModal() {
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<PageContent> getPageContent() {
        return pageContent;
    }

    public void setPageContent(List<PageContent> pageContent) {
        this.pageContent = pageContent;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(error);
        dest.writeValue(message);
        dest.writeList(pageContent);
    }

    public int describeContents() {
        return  0;
    }

}
