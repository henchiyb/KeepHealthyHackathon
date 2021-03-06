package com.example.nhan.keephealthyver2.fragments;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.nhan.keephealthyver2.R;
import com.example.nhan.keephealthyver2.constants.Constant;
import com.example.nhan.keephealthyver2.utils.Utils;

/**
 * Created by Nhan on 10/14/2016.
 */

public class FragmentHome extends Fragment implements View.OnClickListener {
    private Button btClassicExercise;
    private Button btSetting;
    private Button btBreathExercise;
    private MediaPlayer mediaPlayer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        btBreathExercise = (Button)view.findViewById(R.id.bt_breath_exercise);
        btClassicExercise = (Button)view.findViewById(R.id.bt_classic_exercise);
        btSetting = (Button)view.findViewById(R.id.bt_setting);
        btClassicExercise.setOnClickListener(this);
        btSetting.setOnClickListener(this);
        btBreathExercise.setOnClickListener(this);
        return view;
    }
    private void openFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment)
                .addToBackStack(fragment.getClass().getName())
                .commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_classic_exercise:
                openFragment(new FragmentChoosePhysicalExercise());
                break;
            case R.id.bt_setting:
                openFragment(new FragmentChooseOfficeExercise());
                break;
            case R.id.bt_breath_exercise:
                openFragment(new FragmentSetting());
                break;
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        mediaPlayer = new MediaPlayer();
        Utils.setDataSourceForMediaPlayer(this.getContext(), mediaPlayer, Constant.MUSIC_HOME);
        mediaPlayer.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        mediaPlayer.release();
    }
}
