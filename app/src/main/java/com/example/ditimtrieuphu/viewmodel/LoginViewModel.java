package com.example.ditimtrieuphu.viewmodel;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.example.ditimtrieuphu.ContextAccessable;
import com.example.ditimtrieuphu.Executable;
import com.example.ditimtrieuphu.session.UserSessionManager;

/**
 * Minh: them login view model
 */
public class LoginViewModel extends ViewModel implements ContextAccessable {
    private UserSessionManager mUserSessionManager;

    public LoginViewModel() {

    }

    /**
     * Dang ky tai khoan moi voi firebase
     * @param email
     * @param password
     */
    public void createAccount(String email, String password, Executable successExe, Executable failedExe) {
        mUserSessionManager.createAccountWithEmailoAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                if (successExe != null) {
                    successExe.execute();
                }
            } else {
                failedExe.execute(task.getException().getMessage());
            }
        });
    }

    /**
     * Dang nhap tai khoan bang tai khoan va mat khau
     * @param account tai khoan
     * @param password mat khau
     * @param successExe ham thuc hien neu thanh cong
     * @param failedExe ham thuc hien neu that bai
     */
    public void loginWithAccountAndPassword(String account, String password, Executable successExe, Executable failedExe) {
        mUserSessionManager.loginWithAccountAndPassword(account, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                successExe.execute();
            } else {
                failedExe.execute(task.getException().getMessage());
            }
        });
    }

    public boolean isUserSignedIn() {
        return mUserSessionManager.userAvailable();
    }

    @Override
    public void setContext(Context context) {
        mUserSessionManager = UserSessionManager.getInstance(context);
    }

    public void syncGameResources(Executable success, Executable fail) {
        mUserSessionManager.syncGameResources(success, fail);
    }
}
