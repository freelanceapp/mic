package com.online.music.mic.retrofit_provider;

import com.online.music.mic.Responce.VideoResponce;
import com.online.music.mic.constant.Constant;
import com.online.music.mic.model.appversion_responce.AppVersion;
import com.online.music.mic.model.compation_level_responce.CompatitionLevelModel;
import com.online.music.mic.model.compatition_graph_responce.CompatitionGraphModel;
import com.online.music.mic.model.compatition_level_rank_responce.CompatitionLevelRankModel;
import com.online.music.mic.model.competition_responce.CompletionModel;
import com.online.music.mic.model.graph_modal.GraphMainModal;
import com.online.music.mic.model.judgement_responce.JudgementModel;
import com.online.music.mic.model.level_detail_modal.SingleLevelMainModal;
import com.online.music.mic.model.login_responce.LoginModel;
import com.online.music.mic.model.login_responce.LoginModel1;
import com.online.music.mic.model.micpagecontents.AppContentMainModal;
import com.online.music.mic.model.notification_responce.NotificationModel;
import com.online.music.mic.model.otp_responce.OtpModel;
import com.online.music.mic.model.participation_responce.ParticipationModel;
import com.online.music.mic.model.token_responce.TokenModel;
import com.online.music.mic.model.user_responce.UserProfileModel;
import com.online.music.mic.model.winner_responce.WinnersModel;

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
    @POST(Constant.USER_PARTICIPATION)
    Call<TokenModel> getParticipation(@Field("competition_level") String competition_level,
                                      @Field("competition") String competition,
                                      @Field("user_id") String user_id,
                                      @Field("type") String type);


    @FormUrlEncoded
    @POST(Constant.SELECT_PARTICIPATION)
    Call<ParticipationModel> getSelectParticipation(@Field("competition") String competition,
                                                    @Field("user_id") String user_id);


    @FormUrlEncoded
    @POST(Constant.MY_PROFILE)
    Call<UserProfileModel> getProfile(@Field("user_id") String user_id);


    @GET(Constant.APP_VERSION)
    Call<AppVersion> getapp();

    @GET(Constant.NOTIFICATION_LIST)
    Call<NotificationModel> getnotification();

    @GET(Constant.COMPETITIONS_LIST)
    Call<CompletionModel> getcompetition();

    @GET(Constant.WINNER_API)
    Call<WinnersModel> getwinner();

    @FormUrlEncoded
    @POST(Constant.FOROGOT_PASSWORD)
    Call<ResponseBody> getAllLikes(@Field("id") String id);

    @FormUrlEncoded
    @POST(Constant.COMPATITION_LEVEL)
    Call<CompatitionLevelModel> getCompationLevel(@Field("user_id") String user_id,@Field("competition") String competition);

    @FormUrlEncoded
    @POST(Constant.USER_LOGIN)
    Call<LoginModel1> getLogin(@Field("mobile_number") String mobile_number,
                               @Field("participant_country_code") String participant_country_code);

    @FormUrlEncoded
    @POST(Constant.RESEND_PASSWORD)
    Call<TokenModel> getResend(@Field("email") String email);

    @FormUrlEncoded
    @POST(Constant.RESEND_PASSWORD)
    Call<LoginModel1> getResendMobile(@Field("mobile_number") String email);


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

    //Ragister API
    @Multipart
    @POST(Constant.PROFILE_IMAGE)
    Call<TokenModel> profileimage(@Part("user_id") RequestBody user_id,
                                  @Part MultipartBody.Part file);

    //Ragister API
    @Multipart
    @POST(Constant.FILE_UPLOAD)
    //@POST("post_feed.php")
    Call<VideoResponce> getNewPostData(@Part("competition") RequestBody competition,
                                       @Part("competition_level") RequestBody competition_level,
                                       @Part("participant") RequestBody participant,
                                       @Part("type") RequestBody type,
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
                                    @Field("participant_country") String participant_country);


    @FormUrlEncoded
    @POST(Constant.COMPATITION_GRAPH)
    Call<CompatitionGraphModel> getCompatitionGraph(@Field("competition") String competition,
                                                    @Field("user_id") String user_id);


    @FormUrlEncoded
    @POST(Constant.COMPATITION_LEVEL_RANK)
    Call<CompatitionLevelRankModel> getCompatitionRank(@Field("competition_level") String competition_level,
                                                       @Field("user_id") String user_id);


    @FormUrlEncoded
    @POST(Constant.JUGMENT_COMMENT)
    Call<JudgementModel> getJugment(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST(Constant.LevelDetailApi)
    Call<SingleLevelMainModal> levelDetail(@Field("user_id") String user_id,
                                           @Field("competition_level") String competition_level);

    /*
     * Download image
     * */
    @GET
    Call<ResponseBody> getImageDetails(@Url String fileUrl);

    // Page content API
    @GET(Constant.PAGE_CONTENT)
    Call<AppContentMainModal> getPageContent();

    @GET(Constant.GRAPH_URL)
    Call<GraphMainModal> getGraphData();


}