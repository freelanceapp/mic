package com.mic.music.mic.Notification;

import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Looper;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.mic.music.mic.Newmic.Activity.SplashScreen;
import com.mic.music.mic.R;
import com.mic.music.mic.constant.Constant;
import com.mic.music.mic.model.token_responce.TokenModel;
import com.mic.music.mic.retrofit_provider.RetrofitApiClient;
import com.mic.music.mic.retrofit_provider.RetrofitService;
import com.mic.music.mic.retrofit_provider.WebResponse;
import com.mic.music.mic.utils.Alerts;
import com.mic.music.mic.utils.AppPreference;
import com.mic.music.mic.utils.ConnectionDetector;
import com.mic.music.mic.utils.NotificationHelper;

import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import retrofit2.Response;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "FirebaseMessageService";
    private Bitmap bitmap;
    public Context mContext;
    private NotificationHelper notificationHelper;
    public RetrofitApiClient retrofitApiClient;
    public ConnectionDetector cd;

    @Override
    public void onNewToken(String s) {

        mContext = this;
        cd = new ConnectionDetector(mContext);
        retrofitApiClient = RetrofitService.getRetrofit();

        Log.d(TAG, "Refreshed token: " + s);
        AppPreference.setStringPreference(getApplicationContext(), Constant.TOKEN, s);

        if (!(AppPreference.getStringPreference(mContext, Constant.User_Id)).isEmpty()) {
            tokenApi(s);
        }
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        mContext = this;
        notificationHelper = new NotificationHelper(mContext);

        Log.d(TAG, "From: " + remoteMessage.getFrom());
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
        }
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }

        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Message data payload: " + remoteMessage.getData());
            String strPayload = "" + remoteMessage.getData();
            Log.e(TAG, "StrPayload: " + strPayload);

            try {
                JSONObject json = new JSONObject(remoteMessage.getData().toString());
                JSONObject jsonObject = json.getJSONObject("message");
                String strTitle = jsonObject.getString("title");

                String message = jsonObject.getString("body");
                String imageUri = jsonObject.getString("image_url");
                String TrueOrFlase = "true";
                bitmap = getBitmapfromUrl(imageUri);

                notificationHelper.createNotification(strTitle, message, bitmap, TrueOrFlase);
            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }
        }
        //sendNotification(message, bitmap, TrueOrFlase);
    }

    private void sendNotification(String messageBody, Bitmap image, String TrueOrFalse) {
        Intent intent = new Intent(this, SplashScreen.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("AnotherActivity", TrueOrFalse);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent, PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setLargeIcon(image)/*Notification icon image*/
                .setSmallIcon(R.drawable.logo2)
                .setContentTitle(messageBody)
                .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(image))/*Notification with Image*/
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

    public Bitmap getBitmapfromUrl(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(input);
            return bitmap;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    private void tokenApi(String strToken) {
        Looper.prepare();
        String android_id = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        String strId = AppPreference.getStringPreference(mContext, Constant.User_Id);
        if (cd.isNetworkAvailable()) {
            RetrofitService.gettoken(new Dialog(mContext), retrofitApiClient.gettoken(android_id, strToken, strId), new WebResponse() {
                @Override
                public void onResponseSuccess(Response<?> result) {
                    TokenModel loginModal = (TokenModel) result.body();
                    assert loginModal != null;
                    if (!loginModal.getError()) {
                        //Alerts.show(mContext, loginModal.getMessage());
                    } else {
                        Alerts.show(mContext, loginModal.getMessage());
                    }
                }

                @Override
                public void onResponseFailed(String error) {
                    Alerts.show(mContext, error);
                }
            });
        } else {
            cd.show(mContext);
        }
    }

}
