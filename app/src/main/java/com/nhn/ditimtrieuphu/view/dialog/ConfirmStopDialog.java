package com.nhn.ditimtrieuphu.view.dialog;

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
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.DialogFragment;

import com.nhn.ditimtrieuphu.R;
import com.nhn.ditimtrieuphu.common.UiUtils;

public class ConfirmStopDialog extends DialogFragment {
    private AppCompatButton mStopButton;
    private AppCompatButton mContinueButton;

    private OnCallBack callBack;

    public void setOnCallBack(OnCallBack event) {
        callBack = event;
    }

    public interface OnCallBack {
        void onClickStop();
        void onClickContinue();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return inflater.inflate(R.layout.dialog_confirm_stop, container);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mStopButton = view.findViewById(R.id.bt_stop);
        mContinueButton = view.findViewById(R.id.bt_continue);

        mStopButton.setOnClickListener(view1 -> {
            callBack.onClickStop();
            dismiss();
        });
        mContinueButton.setOnClickListener(view1 -> {
            callBack.onClickContinue();
            dismiss();
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = UiUtils.getInstance(getContext()).calculateWidthWithMargin(24f);
            getDialog().getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }
}
