package com.example.nhan.keephealthyver2.activities;

import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

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
    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (!doubleBackToExitPressedOnce && count > 1) {
            super.onBackPressed();
            return;
        } else if (!doubleBackToExitPressedOnce && count == 1) {
            Log.d("test", getSupportFragmentManager().getBackStackEntryCount() + "");
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        } else if(doubleBackToExitPressedOnce && count == 1){
            finish();
        }
    }
}
