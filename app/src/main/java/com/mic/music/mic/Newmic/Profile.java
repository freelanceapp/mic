package com.mic.music.mic.Newmic;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mic.music.mic.Newmic.Fragment.EditProfileFragment;
import com.mic.music.mic.R;
import com.mic.music.mic.constant.Constant;
import com.mic.music.mic.model.competition_responce.CompletionModel;
import com.mic.music.mic.model.user_responce.UserProfileModel;
import com.mic.music.mic.retrofit_provider.RetrofitService;
import com.mic.music.mic.retrofit_provider.WebResponse;
import com.mic.music.mic.utils.Alerts;
import com.mic.music.mic.utils.AppPreference;
import com.mic.music.mic.utils.BaseFragment;
import com.mic.music.mic.utils.ConnectionDetector;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Response;

import static com.mic.music.mic.Newmic.Activity.HomeActivity.user_id;

public class Profile extends BaseFragment {
    Fragment fragment;
    private View view;
    private ImageView editBtn;
    TextView singernamem,email,contact;
    CircleImageView circleImg;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.activity_profile, container, false);
        mContext = getActivity();
        activity = getActivity();
        cd = new ConnectionDetector(mContext);
        retrofitRxClient = RetrofitService.getRxClient();
        retrofitApiClient = RetrofitService.getRetrofit();
        init();

        return view;
    }

    private void init()
    {
        editBtn = (ImageView)view.findViewById(R.id.editBtn);
        singernamem = (TextView) view.findViewById(R.id.singernamem);
        email = (TextView) view.findViewById(R.id.email);
        contact = (TextView) view.findViewById(R.id.contact);
        circleImg = (CircleImageView) view.findViewById(R.id.circleImg);

        profileApi();

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment = new EditProfileFragment();
                loadFragment(fragment);
            }
        });

    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void profileApi() {

        if (cd.isNetworkAvailable()) {
            RetrofitService.getProfile(new Dialog(mContext), retrofitApiClient.getProfile(user_id), new WebResponse() {
                @Override
                public void onResponseSuccess(Response<?> result) {
                    UserProfileModel loginModal = (UserProfileModel) result.body();
                    assert loginModal != null;
                    if (!loginModal.getError()) {
                        Alerts.show(mContext, loginModal.getMessage());

                        singernamem.setText(loginModal.getUser().getParticipantName());
                        email.setText(loginModal.getUser().getParticipantGendar());
                        contact.setText(loginModal.getUser().getParticipantDob());
                        Glide.with(mContext).load(loginModal.getUser().getParticipantImage()).into(circleImg);
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
