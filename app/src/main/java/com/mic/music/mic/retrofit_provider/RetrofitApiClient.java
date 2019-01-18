package com.mic.music.mic.retrofit_provider;

import com.mic.music.mic.constant.Constant;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Url;
import rx.Observable;

public interface RetrofitApiClient {

    @Multipart
    @POST(Constant.USER_REGISTRATION)
    Call<RequestBody> userRegistration(@Part("name") RequestBody name, @Part MultipartBody.Part file,
                                      @Part("email") RequestBody email, @Part("password") RequestBody password,
                                      @Part("mobile_number") RequestBody mobile_number);




    @GET(Constant.OFFER_LIST)
    Call<RequestBody> getOfferList();


    @FormUrlEncoded
    @POST(Constant.FOROGOT_PASSWORD)
    Call<ResponseBody> getAllLikes(@Field("id") String id);

    @FormUrlEncoded
    @POST(Constant.FOROGOT_PASSWORD)
    Observable<ResponseBody> addShortedList(@Field("id") String id,
                                            @Field("like_id") String like_id);

    @FormUrlEncoded
    @POST(Constant.FOROGOT_PASSWORD)
    Call<ResponseBody> getShortedList(@Field("user_id") String user_id);

    //Ragister API
    @FormUrlEncoded
    @POST(Constant.FOROGOT_PASSWORD)
    Call<ResponseBody> signup(@Part("name") RequestBody name,
                              @Part("email") RequestBody email,
                              @Part("phone") RequestBody phone,
                              @Part("organisation") RequestBody organisation,
                              @Part("address") RequestBody address,
                              @Part("city") RequestBody city,
                              @Part("gender") RequestBody gender,
                              @Part("dateofbirth") RequestBody dateofbirth,
                              @Part MultipartBody.Part image);



    /*
     * Download image
     * */
    @GET
    Call<ResponseBody> getImageDetails(@Url String fileUrl);
}