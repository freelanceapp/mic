package com.online.music.mic.Newmic;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;

import com.online.music.mic.Newmic.Activity.Mobile_Ragistration;
import com.online.music.mic.Newmic.Activity.NotificationActivity;
import com.online.music.mic.R;
import com.online.music.mic.constant.Constant;
import com.online.music.mic.model.micpagecontents.AppContentMainModal;
import com.online.music.mic.retrofit_provider.RetrofitService;
import com.online.music.mic.retrofit_provider.WebResponse;
import com.online.music.mic.utils.Alerts;
import com.online.music.mic.utils.AppPreference;
import com.online.music.mic.utils.BaseFragment;
import com.online.music.mic.utils.ConnectionDetector;

import retrofit2.Response;

public class Setting extends BaseFragment implements View.OnClickListener {

    private LinearLayout about, reachus, termsofuse, privatepolicy, refundpolicy, soundsetting,
            notificationsetting, ourteam, performance, logoutBtn, bankDetailbtn;
    private String pageTitle, pageContent;
    private View view;
    private AppContentMainModal appContentMainModal;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.activity_setting, container, false);
        mContext = getActivity();
        activity = getActivity();
        cd = new ConnectionDetector(mContext);
        retrofitApiClient = RetrofitService.getRetrofit();
        init();

        return view;
    }

    private void init() {
        performance = view.findViewById(R.id.performance);
        ourteam = view.findViewById(R.id.ourteam);
        about = view.findViewById(R.id.about);
        reachus = view.findViewById(R.id.reachus);
        termsofuse = view.findViewById(R.id.termsofuse);
        privatepolicy = view.findViewById(R.id.privacypolicy);
        refundpolicy = view.findViewById(R.id.refundpolicy);
        soundsetting = view.findViewById(R.id.soundsetting);
        notificationsetting = view.findViewById(R.id.notification);
        bankDetailbtn = view.findViewById(R.id.bankDetailbtn);
        bankDetailbtn.setOnClickListener(this);

        // performance.setOnClickListener(this);
        ourteam.setOnClickListener(this);
        about.setOnClickListener(this);
        reachus.setOnClickListener(this);
        termsofuse.setOnClickListener(this);
        privatepolicy.setOnClickListener(this);
        refundpolicy.setOnClickListener(this);
        soundsetting.setOnClickListener(this);
        notificationsetting.setOnClickListener(this);
        logoutBtn = view.findViewById(R.id.logoutBtn);
        logoutBtn.setOnClickListener(this);
        getPageContent();
    }

    private void getPageContent() {
        if (cd.isNetworkAvailable()) {
            RetrofitService.appContentPage(new Dialog(mContext), retrofitApiClient.getPageContent(), new WebResponse() {
                @Override
                public void onResponseSuccess(Response<?> result) {
                    appContentMainModal = (AppContentMainModal) result.body();
                    if (!appContentMainModal.getError()) {
                        //   Alerts.show(mContext, appContentMainModal.getMessage());
                    } else {
                        Alerts.show(mContext, appContentMainModal.getMessage());
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

    public void showVideoDialog() {
        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.layout_logout_box);
        final Button btnyes = (Button) dialog.findViewById(R.id.btn_yes);
        Button btncencel = (Button) dialog.findViewById(R.id.btn_no);
        btncencel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnyes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppPreference.setBooleanPreference(mContext, Constant.Is_Login, false);
                AppPreference.setStringPreference(mContext, Constant.FILE_TYPE, "");
                AppPreference.setStringPreference(mContext, Constant.COMPANY_ID, "");
                AppPreference.setStringPreference(mContext, Constant.LEVEL_ID, "");
                Intent intent = new Intent(mContext, Mobile_Ragistration.class);
                startActivity(intent);
                getActivity().finish();
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.performance:
                Intent intent = new Intent(getActivity(), Performance.class);
                startActivity(intent);
                break;
            case R.id.ourteam:
                Intent intent1 = new Intent(getActivity(), OurTeam.class);
                startActivity(intent1);
                break;
            case R.id.about:
                if (cd.isNetworkAvailable()) {
                    String strAboutData = appContentMainModal.getPageContent().get(2).getPageContent();
                    Intent intent2 = new Intent(getActivity(), About.class);
                    intent2.putExtra("pagetitile", "About Us");
                    intent2.putExtra("pagecontent", strAboutData);
                    startActivity(intent2);
                } else {
                    cd.show(mContext);
                }
                break;
            case R.id.reachus:
                Intent intent3 = new Intent(getActivity(), ReachUs.class);
                startActivity(intent3);
                break;
            case R.id.termsofuse:
                if (cd.isNetworkAvailable()) {
                    String strTermsConditionData = appContentMainModal.getPageContent().get(1).getPageContent();
                    Intent intent4 = new Intent(getActivity(), About.class);
                    intent4.putExtra("pagetitile", "Terms of use");
                    intent4.putExtra("pagecontent", strTermsConditionData);
                    startActivity(intent4);
                } else {
                    cd.show(mContext);
                }
                break;
            case R.id.privacypolicy:
                if (cd.isNetworkAvailable()) {
                    String strPrivacyPolicyData = appContentMainModal.getPageContent().get(0).getPageContent();
                    Intent intent5 = new Intent(getActivity(), About.class);
                    intent5.putExtra("pagetitile", "Privacy Policy");
                    intent5.putExtra("pagecontent", strPrivacyPolicyData);
                    startActivity(intent5);
                } else {
                    cd.show(mContext);
                }

                break;
            case R.id.refundpolicy:
                if (cd.isNetworkAvailable()) {
                    String strReturnPolicyData = appContentMainModal.getPageContent().get(3).getPageContent();
                    Intent intent6 = new Intent(getActivity(), About.class);
                    intent6.putExtra("pagetitile", "Refund Policy");
                    intent6.putExtra("pagecontent", strReturnPolicyData);
                    startActivity(intent6);
                } else {
                    cd.show(mContext);
                }
                break;
            case R.id.soundsetting:
                Intent intent7 = new Intent(getActivity(), SoundSetting.class);
                startActivity(intent7);
                break;
            case R.id.notification:
                Intent intent8 = new Intent(getActivity(), NotificationActivity.class);
                startActivity(intent8);
                break;
            case R.id.bankDetailbtn:
                String strPaymentMethod = appContentMainModal.getPageContent().get(4).getPageContent();
                Intent intent9 = new Intent(getActivity(), About.class);
                intent9.putExtra("pagetitile", "Payment Method");
                intent9.putExtra("pagecontent", strPaymentMethod);
                startActivity(intent9);
                break;
            case R.id.logoutBtn:
                showVideoDialog();
                break;
        }
    }
}