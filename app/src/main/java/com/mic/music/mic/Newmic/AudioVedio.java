package com.mic.music.mic.Newmic;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cleveroad.sy.cyclemenuwidget.CycleMenuWidget;
import com.cleveroad.sy.cyclemenuwidget.OnMenuItemClickListener;
import com.cleveroad.sy.cyclemenuwidget.OnStateChangedListener;
import com.mic.music.mic.AudioUpload.AudioListActivity;
import com.mic.music.mic.AudioUpload.AudioRecordActivity;
import com.mic.music.mic.R;
import com.mic.music.mic.VideoRecord.VideoRecordActivity;
import com.mic.music.mic.VideoUpload.VideoFolder;

public class AudioVedio extends AppCompatActivity implements OnMenuItemClickListener {
    LinearLayout audiodialog, videodialog;
    CycleMenuWidget itemCycleMenuWidget;
    RadioButton radioVideoButton, radioAudioButton;
    String videoSelect, audioSelect;
    public enum STATE {
        OPEN, CLOSED, IN_OPEN_PROCESS, IN_CLOSE_PROCESS
    }

    private STATE mState = STATE.CLOSED;
    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_vedio);

        audiodialog = findViewById(R.id.audio_dialog);
        videodialog = findViewById(R.id.video_dialog);
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
                if (sdk < Build.VERSION_CODES.JELLY_BEAN) {
                    itemCycleMenuWidget.setBackgroundDrawable(ContextCompat.getDrawable(AudioVedio.this, R.color.transparent_c));
                } else
                    itemCycleMenuWidget.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.color.transparent_c));
            }

            @Override
            public void onCloseComplete() {
                final int sdk = Build.VERSION.SDK_INT;
                if (sdk < Build.VERSION_CODES.JELLY_BEAN) {
                    itemCycleMenuWidget.setBackgroundDrawable(ContextCompat.getDrawable(AudioVedio.this, R.color.transparent));
                } else {
                    itemCycleMenuWidget.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.color.transparent));
                }
            }
        });

        audiodialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAudioDialog();
            }
        });
        videodialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showVideoDialog();
            }
        });
    }

    @Override
    public void onMenuItemClick(View view, int itemPosition) {
        switch (view.getId()) {
            case R.id.home:
                Intent intent1 = new Intent(AudioVedio.this, AudioVedio.class);
                startActivity(intent1);
                break;
            case R.id.profile:
                Intent intent2 = new Intent(AudioVedio.this, Profile.class);
                startActivity(intent2);
                break;
            case R.id.notification:
                Intent intent3 = new Intent(AudioVedio.this, Notification.class);
                startActivity(intent3);
                break;
            case R.id.competition:
                Intent intent4 = new Intent(AudioVedio.this, MicCompetitions.class);
                startActivity(intent4);
                break;

            case R.id.analytics:
                Intent intent5 = new Intent(AudioVedio.this, Performance.class);
                startActivity(intent5);
                break;
            case R.id.setting:
                Intent intent6 = new Intent(AudioVedio.this, Setting.class);
                startActivity(intent6);
                break;
        }
    }

    @Override
    public void onMenuItemLongClick(View view, int itemPosition) {

    }


    public void showAudioDialog() {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.activity_autdio_dialog);
        ImageView dialogButton = (ImageView) dialog.findViewById(R.id.cancleBtn);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        final RadioGroup rgAudio = (RadioGroup) dialog.findViewById(R.id.rgAudio);

        Button btnSelectAudio = (Button) dialog.findViewById(R.id.btnSelectAudio);
        btnSelectAudio.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                int selectedId = rgAudio.getCheckedRadioButtonId();

                // find the radiobutton by returned id
                radioAudioButton = (RadioButton) dialog.findViewById(selectedId);
                if (radioAudioButton.getText().equals("Record Audio"))
                {
                    Toast.makeText(context, "Select Option 1 "+radioAudioButton.getText(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context,AudioRecordActivity.class);
                    startActivity(intent);
                }else  if (radioAudioButton.getText().equals("Upload Audio"))
                {
                    Toast.makeText(context, "Select Option 1 "+radioAudioButton.getText(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, AudioListActivity.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(context, "Select Option any one option", Toast.LENGTH_SHORT).show();
                }


                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void showVideoDialog() {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.activity_video_dialog_box);

       /* TextView text = (TextView) dialog.findViewById(R.id.text_dialog);
        text.setText(msg);*/
        final RadioGroup rgVideo = (RadioGroup) dialog.findViewById(R.id.rgVideo);
        ImageView dialogButton = (ImageView) dialog.findViewById(R.id.cancleVideoBtn);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });



        Button btnSelectVideo = (Button) dialog.findViewById(R.id.btnSelectVideo);
        btnSelectVideo.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                // get selected radio button from radioGroup
                int selectedId = rgVideo.getCheckedRadioButtonId();
                // find the radiobutton by returned id
                radioVideoButton = (RadioButton) dialog.findViewById(selectedId);
                if (radioVideoButton.getText().equals("Record Video"))
                {
                    Toast.makeText(context, "Select Option 1 "+radioVideoButton.getText(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context,VideoRecordActivity.class);
                    startActivity(intent);
                }else  if (radioVideoButton.getText().equals("Upload Video"))
                {
                    Toast.makeText(context, "Select Option 1 "+radioVideoButton.getText(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context,VideoFolder.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(context, "Select Option any one option", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
