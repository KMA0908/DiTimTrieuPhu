package com.example.ditimtrieuphu.view.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ditimtrieuphu.OnActionCallBack;
import com.example.ditimtrieuphu.R;
import com.example.ditimtrieuphu.common.GameConstant;
import com.example.ditimtrieuphu.common.UiUtils;
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
    private List<Badge> mEquippedBagdes;
    private UiUtils mUiUtils;
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
        mUiUtils = UiUtils.getInstance(getContext());

        mBackImageView = findViewById(R.id.iv_back, this);
        mPlayerNameTextView = findViewById(R.id.tv_player_name);
        mPlayerLevelTextView = findViewById(R.id.tv_player_level);
        mPlayerPropertyTextView = findViewById(R.id.tv_player_money);
        mPlayerRocketTextView = findViewById(R.id.tv_player_rocket);
        mEquippedBadgeRecyclerView = findViewById(R.id.rv_badges);

        mBagdes = new ArrayList<>();
        mEquippedBagdes = new ArrayList<>();
        mAdapter = new EquippedBadgeAdapter(mContext, mEquippedBagdes, objects -> {
            OwnedBadgeListDialog dialog = new OwnedBadgeListDialog();
            dialog.setBadges(mBagdes);
            dialog.setCallbackOnEquipBadge(objects1 -> {
                if (objects1 != null) {
                    Badge changedBadge = (Badge) objects1[0];
                    mUiUtils.showBlur(getParentFragmentManager());
                    mModel.updateBadge(changedBadge, objects2 -> {
                        // Update firebase xong cap nhat giao dien
                        notifyEquippedBadgesData();
                        mUiUtils.dismissBlur();
                    }, objects2 -> {
                        mUiUtils.dismissBlur();
                        if (objects2 != null) {
                            mUiUtils.showMessage(getParentFragmentManager(), (String) objects2[0]);
                        }
                    });
                }
            });
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
            notifyEquippedBadgesData();
        }
    }

    private void notifyEquippedBadgesData() {
        if (mBagdes == null || mEquippedBagdes == null) {
            return;
        }
        mEquippedBagdes.clear();
        for (Badge badge: mBagdes) {
            if (badge.isEquipped()) {
                mEquippedBagdes.add(badge);
            }
        }
        mAdapter.notifyDataSetChanged();
    }
}
