package com.mic.music.mic.Responce;

import com.google.gson.annotations.SerializedName;

public class VideoResponce {
    @SerializedName("error")
    String error;
    @SerializedName("message")
    String message;
    @SerializedName("url")
    String url;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
