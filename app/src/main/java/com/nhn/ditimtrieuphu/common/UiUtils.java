package com.nhn.ditimtrieuphu.common;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import androidx.fragment.app.FragmentManager;

import com.nhn.ditimtrieuphu.view.dialog.SimpleMessageDialog;
import com.nhn.ditimtrieuphu.view.dialog.WaitingLoadingBlurDialog;

public class UiUtils {
    private static UiUtils instance;

    private WaitingLoadingBlurDialog blurDialog;
    private SimpleMessageDialog messageDialog;
    private Context context;

    private UiUtils(Context context) {
        this.context = context;
    }

    public static UiUtils getInstance(Context context) {
        if (instance == null) {
            instance = new UiUtils(context);
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

    public int calculateWidthWithMargin(float margin) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        int screenWidth = displayMetrics.widthPixels;
        float density = context.getResources().getDisplayMetrics().density;
        float marginPixels = margin * density;

        return (int) (screenWidth - 2 * marginPixels);
    }
}
