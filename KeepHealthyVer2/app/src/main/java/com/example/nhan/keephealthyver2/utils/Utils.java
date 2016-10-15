package com.example.nhan.keephealthyver2.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.speech.tts.TextToSpeech;

import com.example.nhan.keephealthyver2.constants.Constant;

import java.io.IOException;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Nhan on 10/12/2016.
 */

public class Utils {
    private static TextToSpeech t1;
    public static TextToSpeech textToSpeech(Context context){
        t1 = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.ENGLISH);
                    t1.setSpeechRate(1.2f);
                }
            }
        });
        return t1;
    }

    public static void setLoadData(Context context, String key, boolean isLoaded) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("SP", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, isLoaded);
        editor.commit();
    }

    public static void setDataSourceForMediaPlayer(Context context, MediaPlayer mediaPlayer, String fileName){
        try {
            AssetFileDescriptor descriptor = context.getAssets().openFd("sounds/" + fileName);
            mediaPlayer.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (Utils.getIntFromPreference(context, Constant.MUSIC_NAME_PREF) == 0){
            mediaPlayer.setVolume(1.0f, 1.0f);
        } else {
            mediaPlayer.setVolume(0, 0);
        }
    }

    public static void saveIntToPreference(Context context, String namePref, int value){
        SharedPreferences preferences = context.getSharedPreferences(namePref, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(namePref, value);
        editor.apply();
    }

    public static int getIntFromPreference(Context context, String namePref){
        SharedPreferences preferences = context.getSharedPreferences(namePref, MODE_PRIVATE);
        return preferences.getInt(namePref, 0);
    }
}
