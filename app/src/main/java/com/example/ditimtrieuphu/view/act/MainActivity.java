package com.example.ditimtrieuphu.view.act;

import static com.example.ditimtrieuphu.view.fragment.HighScoreFragment.KEY_SHOW_HIGH_SCORE_FRAGMENT;

import com.example.ditimtrieuphu.OnActionCallBack;
import com.example.ditimtrieuphu.R;
import com.example.ditimtrieuphu.view.fragment.HighScoreFragment;
import com.example.ditimtrieuphu.view.fragment.M001SplashFragment;
import com.example.ditimtrieuphu.view.fragment.M002MainFragment;
import com.example.ditimtrieuphu.view.fragment.M003PlayFragment;
import com.example.ditimtrieuphu.view.fragment.M004QuestionFragment;
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
        }
    }
}