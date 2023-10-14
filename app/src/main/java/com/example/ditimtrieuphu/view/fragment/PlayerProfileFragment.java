package com.example.ditimtrieuphu.view.fragment;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ditimtrieuphu.OnActionCallBack;
import com.example.ditimtrieuphu.R;
import com.example.ditimtrieuphu.entity.Badge;
import com.example.ditimtrieuphu.dto.PlayerInfo;
import com.example.ditimtrieuphu.view.adapter.EquippedBadgeAdapter;
import com.example.ditimtrieuphu.view.dialog.OwnedBadgeListDialog;
import com.example.ditimtrieuphu.viewmodel.MainFragViewModel;

import java.util.ArrayList;
import java.util.List;

public class PlayerProfileFragment extends BaseFragment<MainFragViewModel>{
    public static final String KEY_SHOW_PLAYER_PROFILE_FRAGMENT = "KEY_SHOW_PLAYER_PROFILE_FRAGMENT";

    private OnActionCallBack mCallBack;
    private List<Badge> mBagdes;
    // view
    private ImageView mBackImageView;
    private TextView mPlayerNameTextView;
    private TextView mPlayerLevelTextView;
    private TextView mPlayerPropertyTextView;
    private TextView mPlayerRocketTextView;
    private RecyclerView mEquippedBadgeRecyclerView;
    private EquippedBadgeAdapter mAdapter;

    @Override
    protected void initViews() {
        mBackImageView = findViewById(R.id.iv_back, this);
        mPlayerNameTextView = findViewById(R.id.tv_player_name);
        mPlayerLevelTextView = findViewById(R.id.tv_player_level);
        mPlayerPropertyTextView = findViewById(R.id.tv_player_money);
        mPlayerRocketTextView = findViewById(R.id.tv_player_rocket);
        mEquippedBadgeRecyclerView = findViewById(R.id.rv_badges);

        mBagdes = new ArrayList<>();
        mAdapter = new EquippedBadgeAdapter(mContext, mBagdes, objects -> {
            OwnedBadgeListDialog dialog = new OwnedBadgeListDialog();
            dialog.setBadges(mBagdes);
            dialog.show(getParentFragmentManager(), OwnedBadgeListDialog.TAG_DIALOG_OWNED_BADGES);
        });
        mEquippedBadgeRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 3, RecyclerView.VERTICAL, false));
        mEquippedBadgeRecyclerView.setAdapter(mAdapter);

        mModel.getPlayerInfoMutableLiveData().observe(this, this::setPlayerDataOnView);
        setPlayerDataOnView(mModel.getPlayerInfoMutableLiveData().getValue());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back: {
                mCallBack.onCallBack(M001SplashFragment.KEY_SHOW_HOME_FRAGMENT);
                break;
            }
        }
    }

    @Override
    protected Class<MainFragViewModel> getClassViewModel() {
        return MainFragViewModel.class;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.m007_player_profile_fragment;
    }

    public void setCallBack(OnActionCallBack mCallBack) {
        this.mCallBack = mCallBack;
    }

    private void setPlayerDataOnView(PlayerInfo playerInfo) {
        if (playerInfo != null) {
            mPlayerNameTextView.setText(playerInfo.getDisplayName());
            String levelText = getString(R.string.profile_player_level, playerInfo.getLevel());
            mPlayerLevelTextView.setText(levelText);
            mPlayerPropertyTextView.setText(String.valueOf(playerInfo.getProperty()));
            mPlayerRocketTextView.setText(String.valueOf(playerInfo.getRocket()));
            // set cac badges so huu neu dang equip
            mBagdes.clear();
            mBagdes.addAll(mModel.getOwnedBadges());
            mAdapter.notifyDataSetChanged();
        }
    }
}
