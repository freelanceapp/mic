package com.online.music.mic.utils;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.online.music.mic.Newmic.Activity.MainActivity;
import com.online.music.mic.interfaces.FragmentChangeListener;
import com.online.music.mic.interfaces.FragmentService;

import java.util.ArrayList;



public class FragmentUtils implements FragmentService {
    public static final int HOME_ID = 0;
    public static final int SEARCH_ID = 1;
    public static final int SHORTLISTED_ID = 2;
    public static final int MEMBERSHIP_ID = 3;

    public static final String HOME_FRAGMENT = "home";
    public static final String SEARCH_FRAGMENT = "Search";
    public static final String SHORTLISTED_FRAGMENT = "shortlisted";
    public static final String MEMBERSHIP_FRAGMENT = "membership";

    public static FragmentUtils mInstance;
    private static MainActivity mainActivity;
    public FragmentManager manager;
    private int lastCommit = -1;
    private RegisterFragmentHandler fragmentHandler;
    private Fragment fragmentArray[];
    private String[] tags = {HOME_FRAGMENT, SEARCH_FRAGMENT, SHORTLISTED_FRAGMENT, MEMBERSHIP_FRAGMENT};


    public static FragmentUtils initFragments(MainActivity act) {
        mInstance = new FragmentUtils();
        mainActivity = act;
        mInstance.showFragment(null, HOME_ID, HOME_FRAGMENT, false);

        return mInstance;
    }

    private void showFragment(Bundle bundle, int fragmentId, String tag, boolean isBackstack) {
        manager = mainActivity.getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();

        if (fragmentArray == null) {
            fragmentHandler = new RegisterFragmentHandler();

            fragmentArray = new Fragment[]{};

            for (int i = 0; i < fragmentArray.length; i++) {
                fragmentHandler.registerFragment((FragmentChangeListener) fragmentArray[i]);
                //ft.add(R.id.container_main, fragmentArray[i], tags[i]);
                ft.hide(fragmentArray[i]);
            }
        }
        if (lastCommit != -1)
            ft.hide(fragmentArray[lastCommit]);
        ft.show(fragmentArray[fragmentId]);
        lastCommit = fragmentId;

        if (isBackstack)
            ft.addToBackStack(tag);

        if (bundle != null)
            fragmentArray[fragmentId].setArguments(bundle);

        ft.commit();
        fragmentHandler.notifyFragment(fragmentId);
    }

    public void showChildFragment(Fragment fragment, Bundle bundle, int container) {
        FragmentTransaction ft = manager.beginTransaction();
        fragment.setArguments(bundle);
        ft.add(container, fragment, null);
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void inflateFragment(Bundle bundle, int fragmentId, String tag, boolean isBackstack) {
        showFragment(bundle, fragmentId, tag, isBackstack);
    }

    public class RegisterFragmentHandler {
        ArrayList<FragmentChangeListener> listeners = new ArrayList<>();

        public void registerFragment(FragmentChangeListener listener) {
            listeners.add(listener);
        }


        public void notifyFragment(int fragmentId) {
            listeners.get(fragmentId).onFragmentVisible("Fragment Visible");
        }

        public void notifyAllFragment(String tag) {
            for (FragmentChangeListener listener : listeners) {
                listener.onFragmentVisible(tag);
            }
        }
    }
}