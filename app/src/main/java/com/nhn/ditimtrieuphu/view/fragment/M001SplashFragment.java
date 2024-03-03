package com.nhn.ditimtrieuphu.view.fragment;


import android.os.Handler;

import com.nhn.ditimtrieuphu.OnActionCallBack;
import com.nhn.ditimtrieuphu.R;
import com.nhn.ditimtrieuphu.viewmodel.SplashViewModel;

public class M001SplashFragment extends BaseFragment<SplashViewModel> {
    public static final String KEY_SHOW_HOME_FRAGMENT = "KEY_SHOW_HOME_FRAGMENT";
    private OnActionCallBack mCallBack;

    @Override
    protected void initViews() {
        // fetch data resource tu firebase
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                gotoMainFragment();
            }
        }, 2000);
    }

    public void setCallBack(OnActionCallBack callBack) {
        this.mCallBack = callBack;
    }

    @Override
    protected Class<SplashViewModel> getClassViewModel() {
        return SplashViewModel.class;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.m001_splash_fragment;
    }

    private void gotoMainFragment() {
        // Minh: sua navigate den login screen thay vi main nhu truoc
        mCallBack.onCallBack(KEY_SHOW_HOME_FRAGMENT);
    }
}
