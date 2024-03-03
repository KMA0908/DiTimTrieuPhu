package com.nhn.ditimtrieuphu.view.dialog;

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

import com.nhn.ditimtrieuphu.R;
import com.nhn.ditimtrieuphu.common.CommonUtils;
import com.nhn.ditimtrieuphu.common.UiUtils;

public class DialogProfileUser extends DialogFragment {
    public static final String TAG = "DialogProfileUser";

    private AppCompatButton btClose;
    private TextView tvUserName;
    private TextView tvHighScore;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return inflater.inflate(R.layout.dialog_user_profile, container);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btClose = view.findViewById(R.id.bt_close);
        tvUserName = view.findViewById(R.id.tv_player_name);
        if (CommonUtils.getInstance().isExistPref("username")) {
            String username = CommonUtils.getInstance().getPref("username");
            tvUserName.setText(username);
        }
        btClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
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
