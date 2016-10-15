package com.example.nhan.keephealthyver2.application;

import android.app.Application;
import android.content.SharedPreferences;

import com.example.nhan.keephealthyver2.constants.Constant;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Nhan on 10/14/2016.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(getApplicationContext())
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);

        SharedPreferences sharedPreferences = getSharedPreferences("SP", MODE_PRIVATE);
        Constant.isLoadedBreathExercise = sharedPreferences.getBoolean(Constant.keyLoadedBreathExercise, false);
        Constant.isLoadedPhysicalExercise = sharedPreferences.getBoolean(Constant.keyLoadedPhysicalExercise, false);
    }
}
