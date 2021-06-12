package com.example.ditimtrieuphu.view.fragment;


import android.view.View;

import com.example.ditimtrieuphu.OnActionCallBack;
import com.example.ditimtrieuphu.R;
import com.example.ditimtrieuphu.viewmodel.MainFragViewModel;

public class M003PlayFragment extends BaseFragment<MainFragViewModel> {
    private OnActionCallBack callBack;
    @Override
    protected void initViews() {

    }

    @Override
    protected Class<MainFragViewModel> getClassViewModel() {
        return MainFragViewModel.class;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.m003_play_fragment;
    }

    public void setCallBack(OnActionCallBack callBack) {
        this.callBack = callBack;
    }

}
