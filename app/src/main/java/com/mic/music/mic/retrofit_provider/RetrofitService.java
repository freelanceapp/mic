package com.mic.music.mic.retrofit_provider;

import android.app.Dialog;


import com.mic.music.mic.Responce.VideoResponce;
import com.mic.music.mic.constant.Constant;
import com.mic.music.mic.model.appversion_responce.AppVersion;
import com.mic.music.mic.model.competition_responce.CompletionModel;
import com.mic.music.mic.model.login_responce.LoginModel;
import com.mic.music.mic.model.login_responce.LoginModel1;
import com.mic.music.mic.model.notification_responce.Notification;
import com.mic.music.mic.model.notification_responce.NotificationModel;
import com.mic.music.mic.model.otp_responce.OtpModel;
import com.mic.music.mic.model.participation_responce.ParticipationModel;
import com.mic.music.mic.model.token_responce.TokenModel;
import com.mic.music.mic.model.user_responce.UserProfileModel;
import com.mic.music.mic.model.winner_responce.WinnersModel;
import com.mic.music.mic.utils.AppProgressDialog;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {

    public static RetrofitApiClient client;
    final OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .readTimeout(10, TimeUnit.MINUTES)
            .connectTimeout(10, TimeUnit.MINUTES)
            .build();

    public RetrofitService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        client = retrofit.create(RetrofitApiClient.class);
    }

    public static RetrofitApiClient getRxClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constant.BASE_URL)
                .build();
        return retrofit.create(RetrofitApiClient.class);
    }

    public static RetrofitApiClient getRetrofit() {
        if (client == null)
            new RetrofitService();

        return client;
    }

    public static void getResponse(final Dialog dialog, final Call<ResponseBody> method, final WebResponse webResponse) {
        if (dialog != null)
            AppProgressDialog.show(dialog);

        method.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (dialog != null)
                    AppProgressDialog.hide(dialog);
                WebServiceResponse.handleResponse(response, webResponse);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                if (dialog != null)
                    AppProgressDialog.hide(dialog);
                webResponse.onResponseFailed(throwable.getMessage());
            }
        });
    }

    //token Update
    public static void gettoken(final Dialog dialog, final Call<TokenModel> method, final WebResponse webResponse) {
        if (dialog != null)
            AppProgressDialog.show(dialog);

        method.enqueue(new Callback<TokenModel>() {
            @Override
            public void onResponse(Call<TokenModel> call, Response<TokenModel> response) {
                if (dialog != null)
                    AppProgressDialog.hide(dialog);
                WebServiceResponse.handleResponse(response, webResponse);
            }

            @Override
            public void onFailure(Call<TokenModel> call, Throwable throwable) {
                if (dialog != null)
                    AppProgressDialog.hide(dialog);
                webResponse.onResponseFailed(throwable.getMessage());
            }
        });
    }

    //Competition level
    public static void getCompetition(final Dialog dialog, final Call<CompletionModel> method, final WebResponse webResponse) {
        if (dialog != null)
            AppProgressDialog.show(dialog);

        method.enqueue(new Callback<CompletionModel>() {
            @Override
            public void onResponse(Call<CompletionModel> call, Response<CompletionModel> response) {
                if (dialog != null)
                    AppProgressDialog.hide(dialog);
                WebServiceResponse.handleResponse(response, webResponse);
            }

            @Override
            public void onFailure(Call<CompletionModel> call, Throwable throwable) {
                if (dialog != null)
                    AppProgressDialog.hide(dialog);
                webResponse.onResponseFailed(throwable.getMessage());
            }
        });
    }



    //winner api
    public static void getwinner(final Dialog dialog, final Call<WinnersModel> method, final WebResponse webResponse) {
        if (dialog != null)
            AppProgressDialog.show(dialog);

        method.enqueue(new Callback<WinnersModel>() {
            @Override
            public void onResponse(Call<WinnersModel> call, Response<WinnersModel> response) {
                if (dialog != null)
                    AppProgressDialog.hide(dialog);
                WebServiceResponse.handleResponse(response, webResponse);
            }

            @Override
            public void onFailure(Call<WinnersModel> call, Throwable throwable) {
                if (dialog != null)
                    AppProgressDialog.hide(dialog);
                webResponse.onResponseFailed(throwable.getMessage());
            }
        });
    }

    //Select Participation
    public static void getSelectParticipation(final Dialog dialog, final Call<ParticipationModel> method, final WebResponse webResponse) {
        if (dialog != null)
            AppProgressDialog.show(dialog);

        method.enqueue(new Callback<ParticipationModel>() {
            @Override
            public void onResponse(Call<ParticipationModel> call, Response<ParticipationModel> response) {
                if (dialog != null)
                    AppProgressDialog.hide(dialog);
                WebServiceResponse.handleResponse(response, webResponse);
            }

            @Override
            public void onFailure(Call<ParticipationModel> call, Throwable throwable) {
                if (dialog != null)
                    AppProgressDialog.hide(dialog);
                webResponse.onResponseFailed(throwable.getMessage());
            }
        });
    }


    //video upload
    public static void getNewPostData(final Dialog dialog, final Call<VideoResponce> method, final WebResponse webResponse) {
        if (dialog != null)
            AppProgressDialog.show(dialog);

        method.enqueue(new Callback<VideoResponce>() {
            @Override
            public void onResponse(Call<VideoResponce> call, Response<VideoResponce> response) {
                if (dialog != null)
                    AppProgressDialog.hide(dialog);
                WebServiceResponse.handleResponse(response, webResponse);
            }

            @Override
            public void onFailure(Call<VideoResponce> call, Throwable throwable) {
                if (dialog != null)
                    AppProgressDialog.hide(dialog);
                webResponse.onResponseFailed(throwable.getMessage());
            }
        });
    }

    //My Profile
    public static void getProfile(final Dialog dialog, final Call<UserProfileModel> method, final WebResponse webResponse) {
        if (dialog != null)
            AppProgressDialog.show(dialog);

        method.enqueue(new Callback<UserProfileModel>() {
            @Override
            public void onResponse(Call<UserProfileModel> call, Response<UserProfileModel> response) {
                if (dialog != null)
                    AppProgressDialog.hide(dialog);
                WebServiceResponse.handleResponse(response, webResponse);
            }

            @Override
            public void onFailure(Call<UserProfileModel> call, Throwable throwable) {
                if (dialog != null)
                    AppProgressDialog.hide(dialog);
                webResponse.onResponseFailed(throwable.getMessage());
            }
        });
    }

    //Email Resend Otp
    public static void getResend(final Dialog dialog, final Call<TokenModel> method, final WebResponse webResponse) {
        if (dialog != null)
            AppProgressDialog.show(dialog);

        method.enqueue(new Callback<TokenModel>() {
            @Override
            public void onResponse(Call<TokenModel> call, Response<TokenModel> response) {
                if (dialog != null)
                    AppProgressDialog.hide(dialog);
                WebServiceResponse.handleResponse(response, webResponse);
            }

            @Override
            public void onFailure(Call<TokenModel> call, Throwable throwable) {
                if (dialog != null)
                    AppProgressDialog.hide(dialog);
                webResponse.onResponseFailed(throwable.getMessage());
            }
        });
    }

    //Email Resend Otp
    public static void getParticipation(final Dialog dialog, final Call<TokenModel> method, final WebResponse webResponse) {
        if (dialog != null)
            AppProgressDialog.show(dialog);

        method.enqueue(new Callback<TokenModel>() {
            @Override
            public void onResponse(Call<TokenModel> call, Response<TokenModel> response) {
                if (dialog != null)
                    AppProgressDialog.hide(dialog);
                WebServiceResponse.handleResponse(response, webResponse);
            }

            @Override
            public void onFailure(Call<TokenModel> call, Throwable throwable) {
                if (dialog != null)
                    AppProgressDialog.hide(dialog);
                webResponse.onResponseFailed(throwable.getMessage());
            }
        });
    }


    //app version
    public static void appversion(final Dialog dialog, final Call<AppVersion> method, final WebResponse webResponse) {
        if (dialog != null)
            AppProgressDialog.show(dialog);

        method.enqueue(new Callback<AppVersion>() {
            @Override
            public void onResponse(Call<AppVersion> call, Response<AppVersion> response) {
                if (dialog != null)
                    AppProgressDialog.hide(dialog);
                WebServiceResponse.handleResponse(response, webResponse);
            }

            @Override
            public void onFailure(Call<AppVersion> call, Throwable throwable) {
                if (dialog != null)
                    AppProgressDialog.hide(dialog);
                webResponse.onResponseFailed(throwable.getMessage());
            }
        });
    }


    //app notification list
    public static void getnotification(final Dialog dialog, final Call<NotificationModel> method, final WebResponse webResponse) {
        if (dialog != null)
            AppProgressDialog.show(dialog);

        method.enqueue(new Callback<NotificationModel>() {
            @Override
            public void onResponse(Call<NotificationModel> call, Response<NotificationModel> response) {
                if (dialog != null)
                    AppProgressDialog.hide(dialog);
                WebServiceResponse.handleResponse(response, webResponse);
            }

            @Override
            public void onFailure(Call<NotificationModel> call, Throwable throwable) {
                if (dialog != null)
                    AppProgressDialog.hide(dialog);
                webResponse.onResponseFailed(throwable.getMessage());
            }
        });
    }


    //app login
    public static void getlogin(final Dialog dialog, final Call<LoginModel1> method, final WebResponse webResponse) {
        if (dialog != null)
            AppProgressDialog.show(dialog);

        method.enqueue(new Callback<LoginModel1>() {
            @Override
            public void onResponse(Call<LoginModel1> call, Response<LoginModel1> response) {
                if (dialog != null)
                    AppProgressDialog.hide(dialog);
                WebServiceResponse.handleResponse(response, webResponse);
            }

            @Override
            public void onFailure(Call<LoginModel1> call, Throwable throwable) {
                if (dialog != null)
                    AppProgressDialog.hide(dialog);
                webResponse.onResponseFailed(throwable.getMessage());
            }
        });
    }

    //app login email
    public static void getEmaillogin(final Dialog dialog, final Call<LoginModel> method, final WebResponse webResponse) {
        if (dialog != null)
            AppProgressDialog.show(dialog);

        method.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                if (dialog != null)
                    AppProgressDialog.hide(dialog);
                WebServiceResponse.handleResponse(response, webResponse);
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable throwable) {
                if (dialog != null)
                    AppProgressDialog.hide(dialog);
                webResponse.onResponseFailed(throwable.getMessage());
            }
        });
    }

    //app otp
    public static void getOtp(final Dialog dialog, final Call<OtpModel> method, final WebResponse webResponse) {
        if (dialog != null)
            AppProgressDialog.show(dialog);

        method.enqueue(new Callback<OtpModel>() {
            @Override
            public void onResponse(Call<OtpModel> call, Response<OtpModel> response) {
                if (dialog != null)
                    AppProgressDialog.hide(dialog);
                WebServiceResponse.handleResponse(response, webResponse);
            }

            @Override
            public void onFailure(Call<OtpModel> call, Throwable throwable) {
                if (dialog != null)
                    AppProgressDialog.hide(dialog);
                webResponse.onResponseFailed(throwable.getMessage());
            }
        });
    }

    //profile update
    public static void updateProfile(final Dialog dialog, final Call<TokenModel> method, final WebResponse webResponse) {
        if (dialog != null)
            AppProgressDialog.show(dialog);

        method.enqueue(new Callback<TokenModel>() {
            @Override
            public void onResponse(Call<TokenModel> call, Response<TokenModel> response) {
                if (dialog != null)
                    AppProgressDialog.hide(dialog);
                WebServiceResponse.handleResponse(response, webResponse);
            }

            @Override
            public void onFailure(Call<TokenModel> call, Throwable throwable) {
                if (dialog != null)
                    AppProgressDialog.hide(dialog);
                webResponse.onResponseFailed(throwable.getMessage());
            }
        });
    }

    //profile image
    public static void profileimage(final Dialog dialog, final Call<TokenModel> method, final WebResponse webResponse) {
        if (dialog != null)
            AppProgressDialog.show(dialog);

        method.enqueue(new Callback<TokenModel>() {
            @Override
            public void onResponse(Call<TokenModel> call, Response<TokenModel> response) {
                if (dialog != null)
                    AppProgressDialog.hide(dialog);
                WebServiceResponse.handleResponse(response, webResponse);
            }

            @Override
            public void onFailure(Call<TokenModel> call, Throwable throwable) {
                if (dialog != null)
                    AppProgressDialog.hide(dialog);
                webResponse.onResponseFailed(throwable.getMessage());
            }
        });
    }


}