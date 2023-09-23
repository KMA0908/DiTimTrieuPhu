package com.example.ditimtrieuphu.view.fragment;


import static com.example.ditimtrieuphu.view.dialog.SimpleMessageDialog.TAG_DIALOG_MESSAGE;
import static com.example.ditimtrieuphu.view.dialog.WaitingLoadingBlurDialog.TAG_DIALOG_BLUR;
import static com.example.ditimtrieuphu.view.fragment.HighScoreFragment.KEY_SHOW_HIGH_SCORE_FRAGMENT;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ditimtrieuphu.OnActionCallBack;
import com.example.ditimtrieuphu.R;
import com.example.ditimtrieuphu.view.dialog.ChangeUserNameDialog;
import com.example.ditimtrieuphu.view.dialog.CustomDialogInfo;
import com.example.ditimtrieuphu.view.dialog.SimpleMessageDialog;
import com.example.ditimtrieuphu.view.dialog.WaitingLoadingBlurDialog;
import com.example.ditimtrieuphu.viewmodel.MainFragViewModel;

public class M002MainFragment extends BaseFragment<MainFragViewModel> {

    public static M002MainFragment m002MainFragment;
    public static final String KEY_SHOW_PLAY_FRAGMENT = "KEY_SHOW_PLAY_FRAGMENT";

    private OnActionCallBack callBack;
    private ImageView ivPlayNow;
    private ImageView ivTutorial;
    private ImageView ivMusic;
    private ImageView ivHighScore;
    private ImageView mAvatarImageView;
    private TextView mPlayerNameTextView;
    private TextView mPlayerLevelTextView;

    public boolean musicIsOn = true;

    @Override
    protected void initViews() {
         m002MainFragment = this;

         ivPlayNow=findViewById(R.id.iv_play_now,this);
         ivTutorial=findViewById(R.id.iv_info, this);
         ivMusic=findViewById(R.id.iv_music, this);
         ivHighScore=findViewById(R.id.iv_highScore, this);
         // Minh: them phan hien thi ten va avatar - start
        mAvatarImageView = findViewById(R.id.iv_person, this);
        mPlayerNameTextView = findViewById(R.id.tv_player_name);
        mPlayerLevelTextView = findViewById(R.id.tv_player_level);
        // Minh: them phan hien thi ten va avatar - end.

         // Minh: sua dung service choi bg music
        if (mBackgroundService != null) {
            musicIsOn = mBackgroundService.isBgMusicUserSetting();
            if (musicIsOn) {
                ivMusic.setImageDrawable(getActivity().getDrawable(R.drawable.ic_sound));
            } else {
                ivMusic.setImageDrawable(getActivity().getDrawable(R.drawable.ic_sound_off));
            }
        }

        mModel.getUserNameLiveData().observe(this, s -> {
            mPlayerNameTextView.setText(s);
        });
        //TODO khi vao main can check display name cua user co chua, neu chua co can yeu cau nguoi choi dat ten
        if (TextUtils.isEmpty(mModel.getUserNameLiveData().getValue())) {
            final ChangeUserNameDialog dialog = new ChangeUserNameDialog();
            dialog.setButtonCallBack(objects -> {
                if (objects != null && objects.length > 0) {
                    String userName = (String) objects[0];
                    if (TextUtils.isEmpty(userName)) {
                        return;
                    }
                    // show blur
                    final WaitingLoadingBlurDialog blurDialog = new WaitingLoadingBlurDialog();
                    blurDialog.show(getParentFragmentManager(), TAG_DIALOG_BLUR);

                    mModel.updateUserDisplayName(userName, objects1 -> {
                        dialog.dismiss();
                        blurDialog.dismiss();
                    }, objects1 -> {
                        // dang nhap fail hien dialog
                        blurDialog.dismiss();
                        if (objects1 != null && objects1.length > 0) {
                            String message = (String) objects[0];
                            SimpleMessageDialog simpleMessageDialog = new SimpleMessageDialog();
                            simpleMessageDialog.setDialogMessage(message);
                            simpleMessageDialog.show(getParentFragmentManager(), TAG_DIALOG_MESSAGE);
                        }
                    });
                }
            });
            dialog.show(getParentFragmentManager(), ChangeUserNameDialog.TAG_DIALOG_CHANGE_USER_NAME);
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
        callBack.onCallBack(KEY_SHOW_PLAY_FRAGMENT);
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

}
