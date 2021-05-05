package com.example.ditimtrieuphu.view.act;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.ditimtrieuphu.OnActionCallBack;
import com.example.ditimtrieuphu.R;

import com.example.ditimtrieuphu.view.fragment.M001SplashFragment;
import com.example.ditimtrieuphu.view.fragment.M002MainFragment;
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
            case M001SplashFragment.KEY_SHOW_MAIN_FRAGMENT:
                M002MainFragment mainFragment = new M002MainFragment();
                mainFragment.setCallBack(this);
                showFragment(R.id.container_view, mainFragment, false);
                break;
        }
    }
}