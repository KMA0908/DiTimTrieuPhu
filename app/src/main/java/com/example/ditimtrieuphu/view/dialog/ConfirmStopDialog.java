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
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.DialogFragment;

import com.example.ditimtrieuphu.Executable;
import com.example.ditimtrieuphu.R;
import com.example.ditimtrieuphu.common.UiUtils;

public class ConfirmStopDialog extends DialogFragment {
    private AppCompatButton mStopButton;
    private AppCompatButton mContinueButton;
    private Executable stopExecutable;
    private Executable continueExecutable;

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
            if (stopExecutable != null) {
                stopExecutable.execute();
            }
            dismiss();
        });
        mContinueButton.setOnClickListener(view1 -> {
            if (stopExecutable != null) {
                continueExecutable.execute();
            }
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

    public void setHandleButton(Executable stop, Executable cntinue) {
        stopExecutable = stop;
        continueExecutable = cntinue;
        if (mContinueButton != null) {
            mStopButton.setOnClickListener(view1 -> {
                if (stopExecutable != null) {
                    stopExecutable.execute();
                }
                dismiss();
            });
            mContinueButton.setOnClickListener(view1 -> {
                if (stopExecutable != null) {
                    continueExecutable.execute();
                }
                dismiss();
            });
        }
    }
}
