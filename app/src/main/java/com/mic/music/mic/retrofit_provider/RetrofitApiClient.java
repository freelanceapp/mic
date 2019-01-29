package com.mic.music.mic.retrofit_provider;

import com.mic.music.mic.constant.Constant;
import com.mic.music.mic.model.appversion_responce.AppVersion;
import com.mic.music.mic.model.competition_responce.CompletionModel;
import com.mic.music.mic.model.login_responce.LoginModel;
import com.mic.music.mic.model.notification_responce.Notification;
import com.mic.music.mic.model.notification_responce.NotificationModel;
import com.mic.music.mic.model.otp_responce.OtpModel;
import com.mic.music.mic.model.token_responce.TokenModel;
import com.mic.music.mic.model.user_responce.UserProfileModel;

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



    @FormUrlEncoded
    @POST(Constant.FIREBSE_TOKEN)
    Call<TokenModel> gettoken(@Field("user_ip") String user_ip,
                                 @Field("token") String token,
                                 @Field("participant_id") String participant_id);


    @FormUrlEncoded
    @POST(Constant.MY_PROFILE)
    Call<UserProfileModel> getProfile(@Field("user_id") String user_id);


    @GET(Constant.APP_VERSION)
    Call<AppVersion> getapp();

    @GET(Constant.NOTIFICATION_LIST)
    Call<NotificationModel> getnotification();

    @GET(Constant.COMPETITIONS_LIST)
    Call<CompletionModel> getcompetition();


    @FormUrlEncoded
    @POST(Constant.FOROGOT_PASSWORD)
    Call<ResponseBody> getAllLikes(@Field("id") String id);

    @FormUrlEncoded
    @POST(Constant.USER_LOGIN)
    Call<LoginModel> getLogin(@Field("mobile_number") String mobile_number);

    @FormUrlEncoded
    @POST(Constant.RESEND_PASSWORD)
    Call<TokenModel> getResend(@Field("email") String email);


    @FormUrlEncoded
    @POST(Constant.USER_LOGIN)
    Call<LoginModel> getLogin1(@Field("email") String email);

    @FormUrlEncoded
    @POST(Constant.OTP_VARIFICATION)
    Call<OtpModel> getOtp(@Field("mobile_number") String mobile_number,
                          @Field("otp_number") String opt_number);

    @FormUrlEncoded
    @POST(Constant.OTP_EMAIL_VARIFICATION)
    Call<OtpModel> getOtp1(@Field("email") String email,
                          @Field("otp_number") String opt_number);

  /*  @FormUrlEncoded
    @POST(Constant.APP_VERSION)
    Observable<ResponseBody> addShortedList();*/



    //Ragister API
    @Multipart
    @POST(Constant.PROFILE_IMAGE)
    Call<TokenModel> profileimage(@Part("user_id") RequestBody user_id,
                                  @Part MultipartBody.Part file);


    //Ragister API
    @FormUrlEncoded
    @POST(Constant.PROFILE_UPDATE)
    Call<TokenModel> updateProfile1(@Field("username") String username,
                                   @Field("email") String email,
                                   @Field("gendar") String gendar,
                                   @Field("user_id") String user_id,
                                   @Field("user_dob") String user_dob,
                                   @Field("participant_organization") String participant_organization,
                                   @Field("participant_address") String participant_address,
                                   @Field("participant_city") String participant_city,
                                   @Field("participant_state") String participant_state,
                                   @Field("participant_country") String participant_country
    );

    /*
     * Download image
     * */
    @GET
    Call<ResponseBody> getImageDetails(@Url String fileUrl);
}