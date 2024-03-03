package com.nhn.ditimtrieuphu.view.dialog;

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

public class SimpleMessageDialog extends DialogFragment {
    public static final String TAG_DIALOG_MESSAGE = "TAG_DIALOG_MESSAGE";

    private TextView mContentTextView;
    private AppCompatButton mCloseButton;
    private String mContentMsg;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        return inflater.inflate(R.layout.dialog_simple_message, container);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mContentTextView = view.findViewById(R.id.tv_dialog_content);
        mCloseButton = view.findViewById(R.id.bt_close_dialog);

        if (mContentMsg != null) {
            mContentTextView.setText(mContentMsg);
        }
        mCloseButton.setOnClickListener(v -> {
            SimpleMessageDialog.this.dismiss();
        });
    }

    /**
     * Set noi dung message de hien thi len dialog
     * @param message
     */
    public void setDialogMessage(String message) {
        mContentMsg = message;
        if (mContentTextView != null) {
            mContentTextView.setText(mContentMsg);
        }
    }
}
