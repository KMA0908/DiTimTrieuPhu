package com.nhn.ditimtrieuphu.view.dialog;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.DialogFragment;

import com.nhn.ditimtrieuphu.R;
import com.nhn.ditimtrieuphu.common.CommonUtils;

public class ChangeUserNameDialog extends DialogFragment {
    public static final String TAG_DIALOG_CHANGE_USER_NAME = "TAG_DIALOG_CHANGE_USER_NAME";

    private EditText mUserNameEditText;
    private AppCompatButton mConfirmButton;

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCanceledOnTouchOutside(false);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        return inflater.inflate(R.layout.dialog_change_user_name, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mUserNameEditText = view.findViewById(R.id.et_username);
        mConfirmButton = view.findViewById(R.id.bt_confirm_name);

        mConfirmButton.setOnClickListener(view1 -> {
            CommonUtils.getInstance().savePref("username", mUserNameEditText.getText().toString().trim());
            callBack.onClickConfirm();
            dismiss();
        });
    }
    private OnCallBack callBack;

    public void setOnCallBack(OnCallBack event) {
        callBack = event;
    }

    public interface OnCallBack {
        void onClickConfirm();
    }
}
