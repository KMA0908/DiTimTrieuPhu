package com.nhn.ditimtrieuphu.view.dialog;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.DialogFragment;

import com.nhn.ditimtrieuphu.R;

public class SettingsDialog extends DialogFragment {
    public static final String TAG_DIALOG_SETTINGS = "TAG_DIALOG_SETTINGS";

    private Switch mBackgroundMusicASwitch;
    private AppCompatButton mLogOutButton;
    private CompoundButton.OnCheckedChangeListener mBgMusicListener;
    private boolean mBgMusicOnOff;

    public SettingsDialog(boolean bgMusic) {
        super();
        this.mBgMusicOnOff = bgMusic;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        return inflater.inflate(R.layout.dialog_settings, container);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mBackgroundMusicASwitch = view.findViewById(R.id.sw_bg_music);
        mLogOutButton = view.findViewById(R.id.bt_logout);

        mBackgroundMusicASwitch.setChecked(mBgMusicOnOff);

        if (mBgMusicListener != null) {
            mBackgroundMusicASwitch.setOnCheckedChangeListener(mBgMusicListener);
        }
        mLogOutButton.setOnClickListener(new View.OnClickListener() {
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
            int width = getResources().getDimensionPixelSize(R.dimen.settings_dialog_width);
            dialog.getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }

    public void setListenerBackgroundMusicSwitch(CompoundButton.OnCheckedChangeListener listener) {
        mBgMusicListener = listener;
        if (mBackgroundMusicASwitch != null) {
            mBackgroundMusicASwitch.setOnCheckedChangeListener(listener);
        }
    }
}
