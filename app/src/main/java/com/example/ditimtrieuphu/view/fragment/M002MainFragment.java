package com.example.ditimtrieuphu.view.fragment;


import android.content.Intent;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.ditimtrieuphu.OnActionCallBack;
import com.example.ditimtrieuphu.R;
import com.example.ditimtrieuphu.view.dialog.CustomDialogInfo;
import com.example.ditimtrieuphu.viewmodel.MainFragViewModel;

public class M002MainFragment extends BaseFragment<MainFragViewModel> {

    public static M002MainFragment m002MainFragment;
    public static final String KEY_SHOW_PLAY_FRAGMENT = "KEY_SHOW_PLAY_FRAGMENT";

    private OnActionCallBack callBack;
    private ImageView ivPlayNow;
    private ImageView ivTutorial;
    private ImageView ivMusic;
    private ImageView ivHighScore;

    public boolean musicIsOn = true;

    @Override
    protected void initViews() {
         m002MainFragment = this;

         ivPlayNow=findViewById(R.id.iv_play_now,this);
         ivTutorial=findViewById(R.id.iv_info, this);
         ivMusic=findViewById(R.id.iv_music, this);
         ivHighScore=findViewById(R.id.iv_highScore, this);

         // Minh: sua dung service choi bg music
        if (mBackgroundService != null) {
            musicIsOn = mBackgroundService.isBgMusicUserSetting();
            if (musicIsOn) {
                ivMusic.setImageDrawable(getActivity().getDrawable(R.drawable.ic_sound));
            } else {
                ivMusic.setImageDrawable(getActivity().getDrawable(R.drawable.ic_sound));
            }
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.iv_play_now){
            showPlayFragment();
        }
        if(v.getId()==R.id.iv_info){
            showTutorial();
        }
        if(v.getId()==R.id.iv_music){
            OnOrOffMusic();
        }
        if(v.getId()==R.id.iv_highScore){
            showHighScore();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Minh: sua dung service de phat nhac
        if(musicIsOn) {
            mBackgroundService.startBackgroundMusic(R.raw.background_music, true);
        }
    }

    private void showPlayFragment() {
        callBack.onCallBack(KEY_SHOW_PLAY_FRAGMENT,null);
    }

    private void showTutorial() {
        CustomDialogInfo customDialogInfo = new CustomDialogInfo();
        customDialogInfo.show(getActivity().getFragmentManager(), "tutorial_Fragment");
    }

    private  void showHighScore() {
        // Minh: sua goi open fragment highscore
        callBack.onCallBack(KEY_SHOW_HIGH_SCORE_FRAGMENT,null);
    }

    private void OnOrOffMusic() {
        if(musicIsOn == true) {
            ivMusic.setImageDrawable(getActivity().getDrawable(R.drawable.ic_sound_off));
            musicIsOn = false;
            // Minh: sua dung service phat nhac
            if (mBackgroundService != null) {
                mBackgroundService.pauseBackgroundMusic();
            }
        }
        else {
            ivMusic.setImageDrawable(getActivity().getDrawable(R.drawable.ic_sound));
            musicIsOn = true;
            // Minh: sua dung service phat nhac
            if (mBackgroundService != null) {
                mBackgroundService.startBackgroundMusic(R.raw.background_music, true);
            }
        }
        // Minh: set setting user
        mBackgroundService.setBgMusicUserSetting(musicIsOn);
    }

    @Override
    protected Class<MainFragViewModel> getClassViewModel() {
        return MainFragViewModel.class;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.m002_main_fragment;
    }

    public void setCallBack(OnActionCallBack callBack) {
        this.callBack = callBack;
    }

}
