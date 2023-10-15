package com.example.ditimtrieuphu.common;

import androidx.fragment.app.FragmentManager;

import com.example.ditimtrieuphu.view.dialog.SimpleMessageDialog;
import com.example.ditimtrieuphu.view.dialog.WaitingLoadingBlurDialog;

public class UiUtils {
    private static UiUtils instance;

    private WaitingLoadingBlurDialog blurDialog;
    private SimpleMessageDialog messageDialog;

    private UiUtils() {}

    public static UiUtils getInstance() {
        if (instance == null) {
            instance = new UiUtils();
        }
        return instance;
    }

    public void showBlur(FragmentManager fragmentManager) {
        if (blurDialog == null) {
            blurDialog = new WaitingLoadingBlurDialog();
        }
        blurDialog.show(fragmentManager, WaitingLoadingBlurDialog.TAG_DIALOG_BLUR);
    }

    public void dismissBlur() {
        if (blurDialog != null) {
            blurDialog.dismiss();
        }
    }

    public void showMessage(FragmentManager fragmentManager, String message) {
        if (messageDialog == null) {
            messageDialog = new SimpleMessageDialog();
        }
        messageDialog.setDialogMessage(message);
        messageDialog.show(fragmentManager, SimpleMessageDialog.TAG_DIALOG_MESSAGE);
    }
}
