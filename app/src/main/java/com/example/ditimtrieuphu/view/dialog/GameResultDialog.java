package com.example.ditimtrieuphu.view.dialog;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.DialogFragment;

import com.example.ditimtrieuphu.Executable;
import com.example.ditimtrieuphu.R;
import com.example.ditimtrieuphu.common.UiUtils;

public class GameResultDialog extends DialogFragment {
    public static final String TAG = "GameResultDialog";

    private AppCompatButton mConfirmButton;
    private TextView mMoneyTextView;
    private TextView mExpTextView;
    private Executable mConfirmExecutable;
    private long money;
    private int exp;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return inflater.inflate(R.layout.dialog_result_gameplay, container);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mConfirmButton = view.findViewById(R.id.bt_confirm);
        mMoneyTextView = view.findViewById(R.id.tv_money_result);
        mExpTextView = view.findViewById(R.id.tv_exp_result);

        mConfirmButton.setOnClickListener(view1 -> {
            if (mConfirmExecutable != null) {
                mConfirmExecutable.execute(money, exp);
            }
            dismiss();
        });
        String moneyString = String.valueOf(money);
        String expString = String.valueOf(exp);
        mMoneyTextView.setText(moneyString);
        mExpTextView.setText(expString);
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

    public void setmConfirmExecutable(Executable executable) {
        mConfirmExecutable = executable;
        if (mConfirmButton != null) {
            mConfirmButton.setOnClickListener(view1 -> {
                if (mConfirmExecutable != null) {
                    long money = Long.parseLong(mMoneyTextView.getText().toString());
                    int exp = Integer.parseInt(mExpTextView.getText().toString());
                    mConfirmExecutable.execute(money, exp);
                }
                dismiss();
            });
        }
    }

    public void setDataResult(long money, int exp) {
        this.money = money;
        this.exp = exp;

        if (mMoneyTextView != null) {
            String moneyString = String.valueOf(money);
            String expString = String.valueOf(exp);
            mMoneyTextView.setText(moneyString);
            mExpTextView.setText(expString);
        }
    }
}
