package com.example.ditimtrieuphu.view.dialog;

import android.app.Dialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.DialogFragment;

import com.example.ditimtrieuphu.Executable;
import com.example.ditimtrieuphu.R;
import com.example.ditimtrieuphu.entity.BonusItem;

public class BonusItemDetailsDialog extends DialogFragment {
    public static final String TAG = "BONUS_ITEM_DETAILS";

    private ImageView mItemImageView;
    private TextView mDetaiilTextView;
    private TextView mItemNameTextView;
    private TextView mItemAmountTextView;
    private AppCompatButton mCancelButton;
    private AppCompatButton mBuyButton;
    private BonusItem mBonusItem;
    private Executable mItemBuyClickExecutable;
    private boolean mIsItemInShop;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return inflater.inflate(R.layout.dialog_bonus_item_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mItemImageView = view.findViewById(R.id.iv_image_item);
        mItemNameTextView = view.findViewById(R.id.tv_item_name);
        mDetaiilTextView = view.findViewById(R.id.tv_detail);
        mCancelButton = view.findViewById(R.id.bt_cancel);
        mBuyButton = view.findViewById(R.id.bt_buy);
        mItemAmountTextView = view.findViewById(R.id.tv_amount);

        if (mBonusItem != null) {
            setItemData();
        }

        mCancelButton.setOnClickListener(view1 -> {
            dismiss();
        });
        mBuyButton.setOnClickListener(view1 -> {
            if (mItemBuyClickExecutable != null) {
                dismiss();
                mItemBuyClickExecutable.execute(mBonusItem);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    public void setBonusItem(BonusItem bonusItem) {
        mBonusItem = bonusItem;
        if (mItemImageView != null) {
            setItemData();
        }
    }

    public void setItemBuyClickExecutable(Executable executable) {
        mItemBuyClickExecutable = executable;
    }

    private void setItemData() {
        int id = getResources().getIdentifier(mBonusItem.getIcon(), "drawable", getContext().getPackageName());
        if (id != 0) {
            Drawable drawable = getContext().getDrawable(id);
            mItemImageView.setImageDrawable(drawable);
        }
        mItemNameTextView.setText(mBonusItem.getName());
        mDetaiilTextView.setText(mBonusItem.getDetail());
        String amount = "Số lượng: " + mBonusItem.getAmount();
        mItemAmountTextView.setText(amount);
        if (mIsItemInShop) {
            mCancelButton.setVisibility(View.VISIBLE);
            mBuyButton.setVisibility(View.VISIBLE);
            mItemAmountTextView.setVisibility(View.GONE);
        } else {
            mCancelButton.setVisibility(View.GONE);
            mBuyButton.setVisibility(View.GONE);
            mItemAmountTextView.setVisibility(View.VISIBLE);
        }
    }

    public void setIsItemInShop(boolean mIsItemInShop) {
        this.mIsItemInShop = mIsItemInShop;
    }
}
