package com.example.nhan.keephealthyver2.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.speech.tts.TextToSpeech;

import java.util.Locale;

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
}
