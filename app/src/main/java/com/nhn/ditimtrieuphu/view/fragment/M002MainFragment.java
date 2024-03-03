package com.nhn.ditimtrieuphu.view.fragment;


import static com.nhn.ditimtrieuphu.view.dialog.SimpleMessageDialog.TAG_DIALOG_MESSAGE;
import static com.nhn.ditimtrieuphu.view.fragment.HighScoreFragment.KEY_SHOW_HIGH_SCORE_FRAGMENT;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.nhn.ditimtrieuphu.BackgroundService;
import com.nhn.ditimtrieuphu.OnActionCallBack;
import com.nhn.ditimtrieuphu.R;
import com.nhn.ditimtrieuphu.common.CommonUtils;
import com.nhn.ditimtrieuphu.view.act.BaseAct;
import com.nhn.ditimtrieuphu.view.dialog.ChangeUserNameDialog;
import com.nhn.ditimtrieuphu.view.dialog.CustomDialogInfo;
import com.nhn.ditimtrieuphu.view.dialog.SettingsDialog;
import com.nhn.ditimtrieuphu.view.dialog.SimpleMessageDialog;
import com.nhn.ditimtrieuphu.viewmodel.MainFragViewModel;

public class M002MainFragment extends BaseFragment<MainFragViewModel> implements ChangeUserNameDialog.OnCallBack {

    public static final String KEY_SHOW_PLAY_FRAGMENT = "KEY_SHOW_PLAY_FRAGMENT";
    public static final String KEY_SHOW_PROFILE_FRAGMENT = "KEY_SHOW_PROFILE_FRAGMENT";

    private OnActionCallBack callBack;
    private ImageView ivPlayNow;
    private ImageView ivTutorial;
    private ImageView ivLogout;
    private ImageView ivMusic;
    private ImageView ivHighScore;
    // Minh: them view - start
    private ImageView mAvatarImageView;
    private ImageView ivProfile;
    private TextView mPlayerNameTextView;
    private TextView mPlayerLevelTextView;

    public boolean musicIsOn = true;

    protected BackgroundService mBackgroundService;

    private AdView mAdView;

    @Override
    protected void initViews() {
         if (!CommonUtils.getInstance().isExistPref("first_open")) {
             CommonUtils.getInstance().savePref("first_open","first_open");
             ChangeUserNameDialog changeUserNameDialog = new ChangeUserNameDialog();
             changeUserNameDialog.setOnCallBack(this);
             changeUserNameDialog.show(getParentFragmentManager(), ChangeUserNameDialog.TAG_DIALOG_CHANGE_USER_NAME);
         }
         mAdView = findViewById(R.id.adView);
         AdRequest adRequest = new AdRequest.Builder().build();
         mAdView.loadAd(adRequest);
         ivPlayNow=findViewById(R.id.iv_play_now,this);
         ivTutorial=findViewById(R.id.iv_info, this);
         ivMusic=findViewById(R.id.iv_music, this);
         ivHighScore=findViewById(R.id.iv_highScore, this);
         ivLogout=findViewById(R.id.iv_logout, this);
         ivProfile=findViewById(R.id.iv_bg_avatar_title, this);
         // Minh: them phan hien thi ten va avatar - start
         mPlayerNameTextView = findViewById(R.id.tv_player_name);
         mPlayerLevelTextView = findViewById(R.id.tv_player_level);

        if (CommonUtils.getInstance().isExistPref("username")) {
            String username = CommonUtils.getInstance().getPref("username");
            mPlayerNameTextView.setText(username);
        }
        if (CommonUtils.getInstance().isExistPref("score")) {
            String score = CommonUtils.getInstance().getScore("score");
            mPlayerLevelTextView.setText(score);
        } else {
            mPlayerLevelTextView.setText("0");
        }
         // Minh: sua dung service choi bg music
        if (mBackgroundService != null) {
            musicIsOn = mBackgroundService.isBgMusicUserSetting();
            if (musicIsOn) {
                ivMusic.setImageDrawable(getActivity().getDrawable(R.drawable.ic_sound));
            } else {
                ivMusic.setImageDrawable(getActivity().getDrawable(R.drawable.ic_sound_off));
            }
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.iv_play_now) {
            // show dialog chon dung vat pham truoc khi choi
            showPlayFragment();
        }
        if(v.getId()==R.id.iv_info) {
            showTutorial();
        }
        if(v.getId()==R.id.iv_music){
            //OnOrOffMusic();
            final SettingsDialog dialog = new SettingsDialog(musicIsOn);
            dialog.setListenerBackgroundMusicSwitch((compoundButton, b) -> {
                onOrOffMusic();
            });

            dialog.show(getChildFragmentManager(), SettingsDialog.TAG_DIALOG_SETTINGS);
        }
        if(v.getId()==R.id.iv_highScore){
            callBack.onCallBack(KEY_SHOW_HIGH_SCORE_FRAGMENT);
        }
        if(v.getId()==R.id.iv_logout) {
            musicIsOn = false;
            // Minh: sua dung service phat nhac
            if (mBackgroundService != null) {
                mBackgroundService.pauseBackgroundMusic();
                mBackgroundService.stopBackgroundMusic();
            }
            getActivity().onBackPressed();
        }
        if (v.getId() == R.id.iv_bg_avatar_title) {
            callBack.onCallBack(KEY_SHOW_PROFILE_FRAGMENT, null);
        }
    }
    public void setBackgroundService(BackgroundService service) {
        this.mBackgroundService = service;
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
        callBack.onCallBack(KEY_SHOW_PLAY_FRAGMENT);
    }

    private void showTutorial() {
        CustomDialogInfo customDialogInfo = new CustomDialogInfo();
        customDialogInfo.show(getParentFragmentManager(), "tutorial_Fragment");
    }

    private void onOrOffMusic() {
        if(musicIsOn) {
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

    public void showMessageDialog(Object...messages) {
        if (messages != null && messages.length > 0) {
            String message = (String) messages[0];
            SimpleMessageDialog simpleMessageDialog = new SimpleMessageDialog();
            simpleMessageDialog.setDialogMessage(message);
            simpleMessageDialog.show(getParentFragmentManager(), TAG_DIALOG_MESSAGE);
        }
    }

    @Override
    public void onClickConfirm() {
        String username = CommonUtils.getInstance().getPref("username");
        mPlayerNameTextView.setText(username);
    }
}
