package com.example.ditimtrieuphu.view.fragment;


import android.content.Intent;
import android.media.MediaPlayer;
import android.view.View;
import android.widget.ImageView;

import com.example.ditimtrieuphu.OnActionCallBack;
import com.example.ditimtrieuphu.R;
import com.example.ditimtrieuphu.view.act.HighScoreActivity;
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

    private MediaPlayer backGoundMusic;

    @Override
    protected void initViews() {
         m002MainFragment = this;

         ivPlayNow=findViewById(R.id.iv_play_now,this);
         ivTutorial=findViewById(R.id.iv_info, this);
         ivMusic=findViewById(R.id.iv_music, this);
         ivHighScore=findViewById(R.id.iv_highScore, this);

         backGoundMusic = MediaPlayer.create(getActivity(), R.raw.background_music);
         backGoundMusic.setLooping(true);
         backGoundMusic.start();
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
        backGoundMusic.pause();
    }

    @Override
    public void onStart() {
        super.onStart();
        if(!backGoundMusic.isPlaying() && musicIsOn) {
            backGoundMusic.start();
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
        Intent intent = new Intent(getActivity(), HighScoreActivity.class);
        startActivity(intent);
    }

    private void OnOrOffMusic() {
        if(musicIsOn == true) {
            ivMusic.setImageDrawable(getActivity().getDrawable(R.drawable.ic_sound_off));
            musicIsOn = false;
            backGoundMusic.pause();
        }
        else {
            ivMusic.setImageDrawable(getActivity().getDrawable(R.drawable.ic_sound));
            musicIsOn = true;
            backGoundMusic.start();
        }
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
