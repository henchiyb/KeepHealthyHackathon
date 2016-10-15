package com.example.nhan.keephealthyver2.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.nhan.keephealthyver2.R;
import com.example.nhan.keephealthyver2.constants.Constant;
import com.example.nhan.keephealthyver2.utils.Utils;

/**
 * Created by Nhan on 10/14/2016.
 */

public class FragmentSetting extends Fragment implements View.OnClickListener {
    private ToggleButton tgVoice, tgMusic;
    private TextView tvTimeExercise, tvTimeRest;
    private ImageView ivMinusEx, ivMinusRest, ivPlusEx, ivPlusRest;

    public static boolean voiceInstructor;
    public static boolean randomOrder;
    public static int timeExercise;
    public static int timeRest;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private MediaPlayer mediaPlayer;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        initLayout(view);
        initData();
        addListeners();
        return view;
    }

    private void initLayout(View view) {
        tgVoice = (ToggleButton) view.findViewById(R.id.toggle_voice);
        tgMusic = (ToggleButton) view.findViewById(R.id.toggle_music);
        tvTimeExercise = (TextView) view.findViewById(R.id.tv_time_ex);
        tvTimeRest = (TextView) view.findViewById(R.id.tv_time_rest);
        ivMinusEx = (ImageView) view.findViewById(R.id.iv_minus_ex);
        ivMinusRest = (ImageView) view.findViewById(R.id.iv_minus_rest);
        ivPlusEx = (ImageView) view.findViewById(R.id.iv_plus_ex);
        ivPlusRest = (ImageView) view.findViewById(R.id.iv_plus_rest);

    }

    private void initData() {
        mediaPlayer = new MediaPlayer();
        sharedPreferences = getActivity().getSharedPreferences("sharePref", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        if (Utils.getIntFromPreference(getContext(), Constant.MUSIC_NAME_PREF) == 0){
            tgMusic.setChecked(true);
            mediaPlayer.setVolume(1.0f, 1.0f);
        } else {
            tgMusic.setChecked(false);
            mediaPlayer.setVolume(0, 0);
        }
        tgVoice.setChecked(voiceInstructor);
        tgMusic.setChecked(randomOrder);
        tvTimeExercise.setText(timeExercise+"s");
        tvTimeRest.setText(timeRest+"s");
    }

    private void addListeners() {
        tgVoice.setOnClickListener(this);
        tgMusic.setOnClickListener(this);
        ivMinusRest.setOnClickListener(this);
        ivPlusRest.setOnClickListener(this);
        ivMinusEx.setOnClickListener(this);
        ivPlusEx.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toggle_voice: {
                Log.d("abcd", "tgvoice");
                editor.putBoolean("voiceInstructor", tgVoice.isChecked());
                editor.apply();
                break;
            }
            case R.id.toggle_music: {
                if(tgMusic.isChecked()){
                    Utils.saveIntToPreference(getContext(), Constant.MUSIC_NAME_PREF, 0);
                    mediaPlayer.setVolume(1.0f, 1.0f);
                } else {
                    Utils.saveIntToPreference(getContext(), Constant.MUSIC_NAME_PREF, 1);
                    mediaPlayer.setVolume(0, 0);
                }
                break;
            }
            case R.id.iv_minus_ex: {
                if (timeExercise > 10) {
                    timeExercise--;
                }
                editor.putInt("timeExercise", timeExercise);
                editor.commit();
                tvTimeExercise.setText(timeExercise+"s");
                break;
            }
            case R.id.iv_minus_rest: {
                if (timeRest > 5) {
                    timeRest--;
                }
                editor.putInt("timeRest", timeRest);
                editor.commit();
                tvTimeRest.setText(timeRest+"s");
                break;
            }
            case R.id.iv_plus_ex: {
                if (timeExercise < 60) {
                    timeExercise++;
                }
                editor.putInt("timeExercise", timeExercise);
                editor.commit();
                tvTimeExercise.setText(timeExercise+"s");
                break;
            }
            case R.id.iv_plus_rest: {
                if (timeRest < 30) {
                    timeRest++;
                }
                editor.putInt("timeRest", timeRest);
                editor.commit();
                tvTimeRest.setText(timeRest+"s");
                break;
            }
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        Utils.setDataSourceForMediaPlayer(this.getContext(), mediaPlayer, Constant.MUSIC_HOME);
        mediaPlayer.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        mediaPlayer.release();
    }
}
