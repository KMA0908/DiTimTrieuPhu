package com.example.ditimtrieuphu.view.fragment;

import android.os.Handler;

import com.example.ditimtrieuphu.OnActionCallBack;
import com.example.ditimtrieuphu.R;
import com.example.ditimtrieuphu.viewmodel.SplashViewModel;

public class M001SplashFragment extends BaseFragment<SplashViewModel> {
    public static final String KEY_SHOW_HOME_FRAGMENT = "KEY_SHOW_HOME_FRAGMENT";
    private OnActionCallBack mCallBack;

    public void setmCallBack(OnActionCallBack mCallBack) {
        this.mCallBack = mCallBack;
    }

    @Override
    protected void initViews() {
        new Handler().postDelayed(this::gotoMainFragment, 2000);
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
        mCallBack.onCallBack(KEY_SHOW_HOME_FRAGMENT);
    }
}
