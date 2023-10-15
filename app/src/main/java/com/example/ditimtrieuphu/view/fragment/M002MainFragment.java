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
import com.example.ditimtrieuphu.common.GameConstant;
import com.example.ditimtrieuphu.entity.BonusItem;
import com.example.ditimtrieuphu.view.dialog.ChangeUserNameDialog;
import com.example.ditimtrieuphu.view.dialog.CustomDialogInfo;
import com.example.ditimtrieuphu.view.dialog.SettingsDialog;
import com.example.ditimtrieuphu.view.dialog.SimpleMessageDialog;
import com.example.ditimtrieuphu.view.dialog.UseItemPlayDialog;
import com.example.ditimtrieuphu.view.dialog.WaitingLoadingBlurDialog;
import com.example.ditimtrieuphu.viewmodel.MainFragViewModel;

import java.util.List;

public class M002MainFragment extends BaseFragment<MainFragViewModel> {

    public static M002MainFragment m002MainFragment;
    public static final String KEY_SHOW_PLAY_FRAGMENT = "KEY_SHOW_PLAY_FRAGMENT";

    private OnActionCallBack callBack;
    private ImageView ivPlayNow;
    private ImageView ivTutorial;
    private ImageView ivMusic;
    private ImageView ivHighScore;
    // Minh: them view - start
    private ImageView mAvatarImageView;
    private TextView mPlayerNameTextView;
    private TextView mPlayerLevelTextView;
    private TextView mPlayerStamina;
    // Minh: them view - end
    private ImageView mShopImageView;
    private ImageView mBagImageView;

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
        mPlayerStamina = findViewById(R.id.tv_player_stamina);
        // Minh: them phan hien thi ten va avatar - end.
        mShopImageView = findViewById(R.id.iv_shop,this);
        mBagImageView = findViewById(R.id.iv_bag, this);

         // Minh: sua dung service choi bg music
        if (mBackgroundService != null) {
            musicIsOn = mBackgroundService.isBgMusicUserSetting();
            if (musicIsOn) {
                ivMusic.setImageDrawable(getActivity().getDrawable(R.drawable.ic_sound));
            } else {
                ivMusic.setImageDrawable(getActivity().getDrawable(R.drawable.ic_sound_off));
            }
        }

        mModel.getPlayerInfoMutableLiveData().observe(this, playerInfo -> {
            if (playerInfo != null) {
                mPlayerNameTextView.setText(playerInfo.getDisplayName());
                String levelText = getString(R.string.default_player_level, playerInfo.getLevel());
                mPlayerLevelTextView.setText(levelText);
                String staminaText = getString(R.string.stamina, playerInfo.getStamina(), GameConstant.DEFAULT_STAMINA_PLAYER);
                mPlayerStamina.setText(staminaText);
            }
        });

        // show blur de sync data
        final WaitingLoadingBlurDialog blurDialog = new WaitingLoadingBlurDialog();
        blurDialog.show(getParentFragmentManager(), TAG_DIALOG_BLUR);
        // sync
        mModel.syncPlayerInfo(objects -> {
            // Check info cua player da co hay chua, neu chua thi tao con khong thi sync
            blurDialog.dismiss();
            if (mModel.getPlayerInfoMutableLiveData().getValue() == null) {
                final ChangeUserNameDialog dialog = new ChangeUserNameDialog();
                dialog.setButtonCallBack(objects1 -> {
                    if (objects1 != null && objects1.length > 0) {
                        String userName = (String) objects1[0];
                        if (TextUtils.isEmpty(userName)) {
                            return;
                        }
                        dialog.dismiss();
                        blurDialog.show(getParentFragmentManager(), TAG_DIALOG_BLUR);
                        mModel.createDefaultPlayerInfo(userName, o -> {
                            mModel.syncPlayerInfo(o1 -> {
                                blurDialog.dismiss();
                            }, o1 -> {
                                blurDialog.dismiss();
                                showMessageDialog(o1);
                            });
                        }, o -> {
                            blurDialog.dismiss();
                            showMessageDialog(o);
                        });
                    }
                });
                dialog.show(getParentFragmentManager(), ChangeUserNameDialog.TAG_DIALOG_CHANGE_USER_NAME);
            } else {

            }
        }, objects -> {
            blurDialog.dismiss();
            showMessageDialog(objects);
        });
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.iv_play_now) {
            // show dialog chon dung vat pham truoc khi choi
            UseItemPlayDialog dialog = new UseItemPlayDialog();
            dialog.setOwnedItems(mModel.getOwnedBonusItem());
            dialog.setButtonExecutable(objects -> {
                int stamina = (int) objects[0];
                List<BonusItem> bonusItems = (List<BonusItem>) objects[1];
                mModel.updateItems(bonusItems);
                showPlayFragment();
            });
            dialog.show(getParentFragmentManager(), UseItemPlayDialog.TAG);
            //
        }
        if(v.getId()==R.id.iv_info) {
            showTutorial();
        }
        if(v.getId()==R.id.iv_music){
            //OnOrOffMusic();
            final SettingsDialog dialog = new SettingsDialog(musicIsOn);
            dialog.setListenerOnLogOutButton(view -> {
                final WaitingLoadingBlurDialog blurDialog = new WaitingLoadingBlurDialog();
                blurDialog.show(getParentFragmentManager(), TAG_DIALOG_BLUR);

                mModel.logout(objects -> {
                    // Logout thanh cong dua nguoi dung ra man login
                    dialog.dismiss();
                    callBack.onCallBack(LoginFragment.KEY_SHOW_LOGIN_FRAGMENT);
                }, objects -> {
                    blurDialog.dismiss();
                    if (objects != null && objects.length > 0) {
                        String message = (String) objects[0];
                        SimpleMessageDialog simpleMessageDialog = new SimpleMessageDialog();
                        simpleMessageDialog.setDialogMessage(message);
                        simpleMessageDialog.show(getParentFragmentManager(), TAG_DIALOG_MESSAGE);
                    }
                });
            });
            dialog.setListenerBackgroundMusicSwitch((compoundButton, b) -> {
                onOrOffMusic();
            });

            dialog.show(getParentFragmentManager(), SettingsDialog.TAG_DIALOG_SETTINGS);
        }
        if(v.getId()==R.id.iv_highScore){
            showHighScore();
        }
        // Su kien click vao avatar, navigate den frag trang ca nhan
        if (v.getId() == R.id.iv_person) {
            callBack.onCallBack(PlayerProfileFragment.KEY_SHOW_PLAYER_PROFILE_FRAGMENT);
        }
        // Su kien shop
        if (v.getId() == R.id.iv_shop) {
            callBack.onCallBack(ShopFragment.KEY_SHOP_FRAGMENT, null);
        }
        if (v.getId() == R.id.iv_bag) {
            callBack.onCallBack(BagFragment.KEY_BAG_FRAGMENT, null);
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
}
