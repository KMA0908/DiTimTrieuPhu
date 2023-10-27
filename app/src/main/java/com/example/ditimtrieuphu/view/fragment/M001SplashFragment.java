package com.example.ditimtrieuphu.view.fragment;

import static com.example.ditimtrieuphu.view.fragment.LoginFragment.KEY_SHOW_LOGIN_FRAGMENT;

import android.os.Handler;

import com.example.ditimtrieuphu.OnActionCallBack;
import com.example.ditimtrieuphu.R;
import com.example.ditimtrieuphu.common.UiUtils;
import com.example.ditimtrieuphu.viewmodel.SplashViewModel;

public class M001SplashFragment extends BaseFragment<SplashViewModel> {
    public static final String KEY_SHOW_HOME_FRAGMENT = "KEY_SHOW_HOME_FRAGMENT";
    private OnActionCallBack mCallBack;

    public void setmCallBack(OnActionCallBack mCallBack) {
        this.mCallBack = mCallBack;
    }

    @Override
    protected void initViews() {
        // fetch data resource tu firebase
        mModel.syncGameResources(objects -> gotoMainFragment(), objects -> {
            UiUtils.getInstance(getContext()).showMessage(getParentFragmentManager(), (String) objects[0]);
        });
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
        mCallBack.onCallBack(KEY_SHOW_LOGIN_FRAGMENT);
    }
}
