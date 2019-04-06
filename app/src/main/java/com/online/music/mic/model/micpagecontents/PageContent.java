
package com.online.music.mic.model.micpagecontents;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PageContent implements Parcelable
{

    @SerializedName("page_title")
    @Expose
    private String pageTitle;
    @SerializedName("page_content")
    @Expose
    private String pageContent;
    public final static Creator<PageContent> CREATOR = new Creator<PageContent>() {


        @SuppressWarnings({
            "unchecked"
        })
        public PageContent createFromParcel(Parcel in) {
            return new PageContent(in);
        }

        public PageContent[] newArray(int size) {
            return (new PageContent[size]);
        }

    };

    protected PageContent(Parcel in) {
        this.pageTitle = ((String) in.readValue((String.class.getClassLoader())));
        this.pageContent = ((String) in.readValue((String.class.getClassLoader())));
    }

    public PageContent() {
    }

    public String getPageTitle() {
        return pageTitle;
    }

    public void setPageTitle(String pageTitle) {
        this.pageTitle = pageTitle;
    }

    public String getPageContent() {
        return pageContent;
    }

    public void setPageContent(String pageContent) {
        this.pageContent = pageContent;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(pageTitle);
        dest.writeValue(pageContent);
    }

    public int describeContents() {
        return  0;
    }

}
