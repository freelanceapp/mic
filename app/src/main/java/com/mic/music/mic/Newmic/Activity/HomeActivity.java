package com.mic.music.mic.Newmic.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.cleveroad.sy.cyclemenuwidget.CycleMenuWidget;
import com.cleveroad.sy.cyclemenuwidget.OnMenuItemClickListener;
import com.cleveroad.sy.cyclemenuwidget.OnStateChangedListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mic.music.mic.Fragment.NotificationFragment;
import com.mic.music.mic.Newmic.AudioVedio;
import com.mic.music.mic.Newmic.Fragment.AllNotificationFragment;
import com.mic.music.mic.Newmic.MicCompetitions;
import com.mic.music.mic.Newmic.Notification;
import com.mic.music.mic.Newmic.Performance;
import com.mic.music.mic.Newmic.Profile;
import com.mic.music.mic.Newmic.Setting;
import com.mic.music.mic.R;
import com.mic.music.mic.constant.Constant;
import com.mic.music.mic.model.Compatition1;
import com.mic.music.mic.model.User;
import com.mic.music.mic.model.competition_responce.CompletionModel;
import com.mic.music.mic.model.otp_responce.OtpModel;
import com.mic.music.mic.model.token_responce.TokenModel;
import com.mic.music.mic.retrofit_provider.RetrofitService;
import com.mic.music.mic.retrofit_provider.WebResponse;
import com.mic.music.mic.utils.Alerts;
import com.mic.music.mic.utils.AppPreference;
import com.mic.music.mic.utils.BaseActivity;
import com.mic.music.mic.utils.ButtonSound;
import com.mic.music.mic.utils.ConnectionDetector;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class HomeActivity extends BaseActivity implements OnMenuItemClickListener {
    CycleMenuWidget itemCycleMenuWidget;
    Fragment fragment;
    public String f_token;
    public String android_id;
    public static String user_id;

    public static HomeActivity homeActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mContext = this;
        homeActivity = this;
        cd = new ConnectionDetector(mContext);
        retrofitRxClient = RetrofitService.getRxClient();
        retrofitApiClient = RetrofitService.getRetrofit();
        f_token = FirebaseInstanceId.getInstance().getToken();
        android_id = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        //user_id = AppPreference.getStringPreference(mContext, Constant.User_Id);

        Gson gson = new Gson();
        String data = AppPreference.getStringPreference(mContext, Constant.User_Data);
        Log.e("Profile ", "..."+data);
        OtpModel loginModal = gson.fromJson(data, OtpModel.class);
        User.setUser(loginModal);


        OtpModel completionModel = User.getUser();
        user_id = completionModel.getUser().getParticipantId();
        AppPreference.setStringPreference(mContext, Constant.User_Id , completionModel.getUser().getParticipantId());

       /* Log.e("token", f_token);
        Log.e("Device", android_id);*/

        tokenApi();
        fragment = new AudioVedio();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.commit();

        itemCycleMenuWidget = findViewById(R.id.itemCycleMenuWidget);
        itemCycleMenuWidget.setMenuRes(R.menu.cycle_menu);
        itemCycleMenuWidget.setOnMenuItemClickListener(this);

        itemCycleMenuWidget.setStateChangeListener(new OnStateChangedListener() {
            @Override
            public void onStateChanged(CycleMenuWidget.STATE state) {

            }

            @Override
            public void onOpenComplete() {
                final int sdk = Build.VERSION.SDK_INT;
                ButtonSound.getInstance().playSound(ButtonSound.SOUND_1);
                if (sdk < Build.VERSION_CODES.JELLY_BEAN) {
                    itemCycleMenuWidget.setBackgroundDrawable(ContextCompat.getDrawable(HomeActivity.this, R.color.transparent_c));
                } else
                    itemCycleMenuWidget.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.color.transparent_c));
            }

            @Override
            public void onCloseComplete() {
                final int sdk = Build.VERSION.SDK_INT;
                ButtonSound.getInstance().playSound(ButtonSound.SOUND_1);
                if (sdk < Build.VERSION_CODES.JELLY_BEAN) {
                    itemCycleMenuWidget.setBackgroundDrawable(ContextCompat.getDrawable(HomeActivity.this, R.color.transparent));
                } else {
                    itemCycleMenuWidget.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.color.transparent));
                }
            }
        });
    }

    @Override
    public void onMenuItemClick(View view, int itemPosition) {
        switch (view.getId()) {
            case R.id.home:
                fragment = new AudioVedio();
                loadFragment(fragment);
                ButtonSound.getInstance().playSound(ButtonSound.SOUND_1);
                ButtonSound.getInstance().vibration(mContext);
                break;
            case R.id.profile:
                fragment = new Profile();
                loadFragment(fragment);
                ButtonSound.getInstance().playSound(ButtonSound.SOUND_1);
                ButtonSound.getInstance().vibration(mContext);
                break;
            case R.id.notification:
                fragment = new NotificationFragment();
                loadFragment(fragment);
                ButtonSound.getInstance().playSound(ButtonSound.SOUND_1);
                ButtonSound.getInstance().vibration(mContext);
                break;
            case R.id.competition:
                fragment = new MicCompetitions();
                loadFragment(fragment);
                ButtonSound.getInstance().playSound(ButtonSound.SOUND_1);
                ButtonSound.getInstance().vibration(mContext);
                break;

           /* case R.id.analytics:
                fragment = new Performance();
                loadFragment(fragment);
                ButtonSound.getInstance().playSound(ButtonSound.SOUND_1);
                ButtonSound.getInstance().vibration(mContext);
                break;*/
            case R.id.setting:
                fragment = new Setting();
                loadFragment(fragment);
                ButtonSound.getInstance().playSound(ButtonSound.SOUND_1);
                ButtonSound.getInstance().vibration(mContext);
                break;
        }
        itemCycleMenuWidget.close(true);
    }

    @Override
    public void onMenuItemLongClick(View view, int itemPosition) {

    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void tokenApi() {
        if (cd.isNetworkAvailable()) {
            RetrofitService.gettoken(new Dialog(mContext), retrofitApiClient.gettoken(android_id,f_token,user_id), new WebResponse() {
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //finish();
    }
}
