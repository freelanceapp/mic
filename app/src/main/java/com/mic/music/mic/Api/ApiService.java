package com.mic.music.mic.Api;

import com.mic.music.mic.Responce.VideoResponce;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiService {
    @Multipart
    @POST("user-video-upload.php")
    Call<VideoResponce> uploadVideo(@Part("competition") RequestBody competition,
                                   @Part("competition_level") RequestBody competition_level,
                                   @Part("participant") RequestBody participant,
                                   @Part MultipartBody.Part file);

}
