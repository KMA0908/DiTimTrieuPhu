package com.example.ditimtrieuphu.view.dialog;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ditimtrieuphu.Executable;
import com.example.ditimtrieuphu.R;
import com.example.ditimtrieuphu.common.UiUtils;
import com.example.ditimtrieuphu.entity.Badge;
import com.example.ditimtrieuphu.view.adapter.BadgeAdapter;

import java.util.ArrayList;
import java.util.List;

public class OwnedBadgeListDialog extends DialogFragment {
    public static final String TAG_DIALOG_OWNED_BADGES = "TAG_DIALOG_OWNED_BADGES";
    private RecyclerView mOwnedBadgeRecyclerView;
    private BadgeAdapter mAdapter;
    private List<Badge> mBadges;
    private Executable mOnEquipBadgeChangeExecutable;
    private UiUtils mUiUtils;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        return inflater.inflate(R.layout.dialog_list_badge, container);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mBadges == null) {
            mBadges = new ArrayList<>();
        }
        mUiUtils = UiUtils.getInstance(getContext());
        mOwnedBadgeRecyclerView = view.findViewById(R.id.rv_list_owned_badge);
        mAdapter = new BadgeAdapter(getContext(), mBadges, objects -> onEquipBadgeChange(objects), objects -> onEquippedFail());
        mOwnedBadgeRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mOwnedBadgeRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(600, 800);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }

    public void setBadges(List<Badge> badges) {
        if (mBadges == null) {
            mBadges = new ArrayList<>();
        }
        mBadges.clear();
        mBadges.addAll(badges);
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }
    }

    public void setCallbackOnEquipBadge(Executable callback) {
        mOnEquipBadgeChangeExecutable = callback;
    }

    private void onEquipBadgeChange(Object...objects) {
        if (objects != null && objects.length > 0) {
            int index = (Integer) objects[0];
            if (mOnEquipBadgeChangeExecutable != null) {
                mOnEquipBadgeChangeExecutable.execute(mBadges.get(index));
            }
        }
    }

    private void onEquippedFail() {
        mUiUtils.showMessage(getParentFragmentManager(), "Số lượng huy hiệu trang bị đã full.");
    }
}
