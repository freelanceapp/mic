package com.mic.music.mic.utils;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.mic.music.mic.Newmic.SoundSetting;
import com.mic.music.mic.R;
import com.mic.music.mic.constant.Constant;

import java.util.HashMap;

import static com.mic.music.mic.Newmic.AudioVedio.formant;

public class ButtonSound
{
        private static final String TAG = ButtonSound.class.toString();

        private static final ButtonSound INSTANCE = new ButtonSound();

        Boolean chSound;
        // Sound ID can't be 0 (zero)
        public static final int SOUND_1 = 1;

        private ButtonSound() {

        }

        public static ButtonSound getInstance() {
            return INSTANCE;
        }

        private SoundPool soundPool;
        private HashMap<Integer, Integer> soundPoolMap;
        int priority = 1;
        int no_loop = 0;
        private int volume;
        float normal_playback_rate = 1f;

        private Context context;

        public void init(Context context) {
            this.context = context;
            soundPool = new SoundPool(2, AudioManager.STREAM_MUSIC, 50);
            soundPoolMap = new HashMap<Integer, Integer>();
            soundPoolMap.put(SOUND_1, soundPool.load(context, R.raw.button_sound, 1));
            AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            volume = audioManager.getStreamVolume(AudioManager.STREAM_SYSTEM);
        }

        public void playSound(final int soundId) {
            Log.i(TAG, "!!!!!!!!!!!!!! playSound_1 !!!!!!!!!!");
            chSound = AppPreference.getBooleanPreference(context, Constant.APP_SOUND);
            soundPool.play(soundId, volume, volume, priority, no_loop, normal_playback_rate);
            if (chSound.equals(true) ) {
                new Handler().postDelayed(new Runnable(){
                    @Override
                    public void run() {
                        soundPool.stop(soundId);
                    }
                }, 100);
            }else {
            }
        }
}
