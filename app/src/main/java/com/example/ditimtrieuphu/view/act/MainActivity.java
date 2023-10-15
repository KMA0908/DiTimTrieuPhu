package com.example.ditimtrieuphu.view.act;

import static com.example.ditimtrieuphu.view.fragment.HighScoreFragment.KEY_SHOW_HIGH_SCORE_FRAGMENT;

import androidx.fragment.app.FragmentManager;

import com.example.ditimtrieuphu.OnActionCallBack;
import com.example.ditimtrieuphu.R;
import com.example.ditimtrieuphu.view.dialog.WaitingLoadingBlurDialog;
import com.example.ditimtrieuphu.view.fragment.HighScoreFragment;
import com.example.ditimtrieuphu.view.fragment.LoginFragment;
import com.example.ditimtrieuphu.view.fragment.M001SplashFragment;
import com.example.ditimtrieuphu.view.fragment.M002MainFragment;
import com.example.ditimtrieuphu.view.fragment.M003PlayFragment;
import com.example.ditimtrieuphu.view.fragment.M004QuestionFragment;
import com.example.ditimtrieuphu.view.fragment.PlayerProfileFragment;
import com.example.ditimtrieuphu.view.fragment.ShopFragment;
import com.example.ditimtrieuphu.view.fragment.SignUpFragment;
import com.example.ditimtrieuphu.viewmodel.MainViewModel;


public class MainActivity extends BaseAct<MainViewModel> implements OnActionCallBack {
    @Override
    protected Class<MainViewModel> getClassViewModel() {
        return MainViewModel.class;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews() {
        M001SplashFragment splashFragment = new M001SplashFragment();
        splashFragment.setmCallBack(this);
        showFragment(R.id.container_view, splashFragment, false);
    }

    @Override
    public void onCallBack(String key, Object... obj) {
        switch (key) {
            case M001SplashFragment.KEY_SHOW_HOME_FRAGMENT:
            case M003PlayFragment.KEY_SHOW_MAIN_FRAGMENT:
                // Minh - start:
                dismissBlurDialog();
                // Minh - end.
                M002MainFragment mainFragment = new M002MainFragment();
                mainFragment.setCallBack(this);
                mainFragment.setBackgroundService(mService);
                showFragment(R.id.container_view, mainFragment, false);
                break;
            case M002MainFragment.KEY_SHOW_PLAY_FRAGMENT:
            case M003PlayFragment.KEY_SHOW_QUESTION_FRAGMENT:
                M004QuestionFragment questionFragment = new M004QuestionFragment();
                questionFragment.setCallBack(this);
                showFragment(R.id.container_view, questionFragment, false);
                break;
            case M004QuestionFragment.KEY_SHOW_QUESTION_DETAIL:
                M003PlayFragment playFragment = new M003PlayFragment();
                playFragment.setCallBack(this);
                showFragment(R.id.container_view, playFragment, false);
                break;
            // Minh: them dieu huong den high score frag
            case KEY_SHOW_HIGH_SCORE_FRAGMENT: {
                HighScoreFragment highScoreFragment = new HighScoreFragment();
                highScoreFragment.setCallBack(this);
                showFragment(R.id.container_view, highScoreFragment, false);
                break;
            }
            // Minh: them case hien thi login fragment
            case LoginFragment.KEY_SHOW_LOGIN_FRAGMENT: {
                // Minh - start:
                dismissBlurDialog();
                // Minh - end.
                LoginFragment loginFragment = new LoginFragment();
                loginFragment.setCallBack(this);
                showFragment(R.id.container_view, loginFragment, false);
                break;
            }
            case SignUpFragment.KEY_SHOW_SIGNUP_FRAGMENT: {
                SignUpFragment signUpFragment = new SignUpFragment();
                signUpFragment.setCallBack(this);
                showFragment(R.id.container_view, signUpFragment, false);
                break;
            }
            case PlayerProfileFragment.KEY_SHOW_PLAYER_PROFILE_FRAGMENT: {
                PlayerProfileFragment fragment = new PlayerProfileFragment();
                fragment.setCallBack(this);
                showFragment(R.id.container_view, fragment, true);
                break;
            }
            case ShopFragment.KEY_SHOP_FRAGMENT: {
                ShopFragment shopFragment = new ShopFragment();
                shopFragment.setmCallBack(this);
                showFragment(R.id.container_view, shopFragment, true);
                break;
            }
        }
    }

    /**
     * Minh: Dong dialog blur do loading lai neu no dang duoc hien
     */
    private void dismissBlurDialog() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        WaitingLoadingBlurDialog blurDialog = (WaitingLoadingBlurDialog)
                fragmentManager.findFragmentByTag(WaitingLoadingBlurDialog.TAG_DIALOG_BLUR);
        if (blurDialog != null) {
            blurDialog.dismiss();
        }
    }
}