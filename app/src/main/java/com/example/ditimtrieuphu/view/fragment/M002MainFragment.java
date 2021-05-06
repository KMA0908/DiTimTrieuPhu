package com.example.ditimtrieuphu.view.fragment;


import android.view.View;
import android.widget.ImageView;

import com.example.ditimtrieuphu.OnActionCallBack;
import com.example.ditimtrieuphu.R;
import com.example.ditimtrieuphu.viewmodel.MainFragViewModel;

public class M002MainFragment extends BaseFragment<MainFragViewModel> {
    public static final String KEY_SHOW_PLAY_FRAGMENT = "KEY_SHOW_PLAY_FRAGMENT";
    private OnActionCallBack callBack;
    private ImageView ivPlayNow;
    @Override
    protected void initViews() {
         ivPlayNow=findViewById(R.id.iv_play_now,this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.iv_play_now){
            showPlayFragmnet();
        }
    }

    private void showPlayFragmnet() {
        callBack.onCallBack(KEY_SHOW_PLAY_FRAGMENT,null);
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
