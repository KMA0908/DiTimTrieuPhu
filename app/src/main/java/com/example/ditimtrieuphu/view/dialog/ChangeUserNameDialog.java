package com.example.ditimtrieuphu.view.dialog;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.DialogFragment;

import com.example.ditimtrieuphu.Executable;
import com.example.ditimtrieuphu.R;

public class ChangeUserNameDialog extends DialogFragment {
    public static final String TAG_DIALOG_CHANGE_USER_NAME = "TAG_DIALOG_CHANGE_USER_NAME";

    private EditText mUserNameEditText;
    private AppCompatButton mConfirmButton;
    private Executable mButtonCallBack;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getDialog().setCanceledOnTouchOutside(false);
        WindowManager.LayoutParams params = getDialog().getWindow().getAttributes();
        params.height = 200;
        getDialog().getWindow().setAttributes(params);

        return inflater.inflate(R.layout.dialog_change_user_name, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mUserNameEditText = view.findViewById(R.id.et_username);
        mConfirmButton = view.findViewById(R.id.bt_confirm_name);

        mConfirmButton.setOnClickListener(view1 -> {
            mButtonCallBack.execute(mUserNameEditText.getText().toString().trim());
        });
    }

    /**
     * set handle khi click vao nut, tham so tra ve la text cua input user name
     * @param handle
     */
    public void setButtonCallBack(Executable handle) {
        this.mButtonCallBack = handle;
        if (mConfirmButton != null) {
            mConfirmButton.setOnClickListener(view -> {
                handle.execute(mUserNameEditText.getText().toString().trim());
            });
        }
    }
}
