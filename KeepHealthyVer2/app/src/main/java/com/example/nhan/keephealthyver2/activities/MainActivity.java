package com.example.nhan.keephealthyver2.activities;

import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.nhan.keephealthyver2.R;
import com.example.nhan.keephealthyver2.fragments.FragmentHome;
import com.example.nhan.keephealthyver2.fragments.FragmentSetting;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        openFragment(new FragmentHome());
    }

    private void openFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment)
                .addToBackStack(fragment.getClass().getName())
                .commit();
    }
    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences("sharePref", MODE_PRIVATE);
        FragmentSetting.voiceInstructor = sharedPreferences.getBoolean("voiceInstructor", true);
        FragmentSetting.randomOrder = sharedPreferences.getBoolean("randomOrder", false);
        Log.d("abcd", "random " + FragmentSetting.randomOrder);
        FragmentSetting.timeExercise = sharedPreferences.getInt("timeExercise", 10);
        FragmentSetting.timeRest = sharedPreferences.getInt("timeRest", 5);
    }
}
