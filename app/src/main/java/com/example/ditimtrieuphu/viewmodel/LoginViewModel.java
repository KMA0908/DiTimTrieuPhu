package com.example.ditimtrieuphu.viewmodel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.example.ditimtrieuphu.Executable;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.function.Function;

/**
 * Minh: them login view model
 */
public class LoginViewModel extends ViewModel {
    private FirebaseAuth mAuth;

    public LoginViewModel() {
        mAuth = FirebaseAuth.getInstance();
    }

    /**
     * Dang ky tai khoan moi voi firebase
     * @param email
     * @param password
     */
    public void createAccount(String email, String password, Executable successExe, Executable failedExe) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            successExe.execute();
                        } else {
                            failedExe.execute(task.getException().getMessage());
                            task.getException().printStackTrace();
                        }
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
        mAuth.signInWithEmailAndPassword(account, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            successExe.execute();
                        } else {
                            failedExe.execute(task.getException().getMessage());
                        }
                    }
                });
    }
}
