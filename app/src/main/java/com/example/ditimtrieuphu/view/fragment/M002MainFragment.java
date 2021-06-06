package com.example.ditimtrieuphu.view.fragment;


import android.view.View;
import android.widget.ImageView;

import com.example.ditimtrieuphu.OnActionCallBack;
import com.example.ditimtrieuphu.R;
import com.example.ditimtrieuphu.view.dialog.CustomDialogInfo;
import com.example.ditimtrieuphu.viewmodel.MainFragViewModel;

public class M002MainFragment extends BaseFragment<MainFragViewModel> {
    public static final String KEY_SHOW_PLAY_FRAGMENT = "KEY_SHOW_PLAY_FRAGMENT";
    private OnActionCallBack callBack;
    private ImageView ivPlayNow;
    private ImageView ivTutorial;
    private ImageView ivMusic;
    private ImageView ivAchievement;
    private boolean musicIsOn = true;

    @Override
    protected void initViews() {
         ivPlayNow=findViewById(R.id.iv_play_now,this);
         ivTutorial=findViewById(R.id.iv_info, this);
         ivMusic=findViewById(R.id.iv_music, this);
         ivAchievement=findViewById(R.id.archivement, this);
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
    }

    private void showPlayFragment() {
        callBack.onCallBack(KEY_SHOW_PLAY_FRAGMENT,null);
    }

    private void showTutorial() {
        CustomDialogInfo customDialogInfo = new CustomDialogInfo();
        customDialogInfo.show(getActivity().getFragmentManager(), "tutorial_Fragment");
    }

    private void OnOrOffMusic() {
        if(musicIsOn == true) {
            ivMusic.setImageDrawable(getActivity().getDrawable(R.drawable.ic_sound_off));
            musicIsOn = false;
            //Code music is off in here
        }
        else {
            ivMusic.setImageDrawable(getActivity().getDrawable(R.drawable.ic_sound));
            musicIsOn = true;
            //Code music is on in here
        }
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
