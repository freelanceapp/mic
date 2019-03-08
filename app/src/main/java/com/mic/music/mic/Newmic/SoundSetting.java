package com.mic.music.mic.Newmic;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.widget.CompoundButton;

import com.mic.music.mic.R;
import com.mic.music.mic.constant.Constant;
import com.mic.music.mic.utils.AppPreference;

public class SoundSetting extends AppCompatActivity implements
        CompoundButton.OnCheckedChangeListener {

    SwitchCompat scSound, scVibrition;
    Boolean chSound, chVibration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound_setting);

        scSound = findViewById(R.id.scSound1);
        scVibrition = findViewById(R.id.scVibrition);

        chSound = AppPreference.getBooleanPreference(SoundSetting.this, Constant.APP_SOUND);

        if (chSound.equals(true)) {
            scSound.setChecked(true);
        }

        chVibration = AppPreference.getBooleanPreference(SoundSetting.this, Constant.APP_VIBRATION);

        if (chVibration.equals(true)) {
            scVibrition.setChecked(true);
        }

        scSound.setOnCheckedChangeListener(this);
        scVibrition.setOnCheckedChangeListener(this);

    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()) {
            case R.id.scSound1:
                if (b == true) {
                    AppPreference.setBooleanPreference(SoundSetting.this, Constant.APP_SOUND, true);
                } else {
                    AppPreference.setBooleanPreference(SoundSetting.this, Constant.APP_SOUND, false);
                }

                Log.e("switch_compat sound", b + "");
                break;
            case R.id.scVibrition:
                if (b == true) {
                    AppPreference.setBooleanPreference(SoundSetting.this, Constant.APP_VIBRATION, true);
                } else {
                    AppPreference.setBooleanPreference(SoundSetting.this, Constant.APP_VIBRATION, false);
                }

                Log.e("switch_compat vibrit", b + "");
                break;
        }
    }
}
