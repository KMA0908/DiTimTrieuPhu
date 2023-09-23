package com.example.ditimtrieuphu.session;

import androidx.annotation.NonNull;

import com.example.ditimtrieuphu.Executable;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class UserManager {
    private static UserManager sUserManager;

    private FirebaseAuth mAuth;

    private UserManager() {
        mAuth = FirebaseAuth.getInstance();
    }

    public static UserManager getInstance() {
        if (sUserManager == null) {
            sUserManager = new UserManager();
        }
        return sUserManager;
    }

    public Task<AuthResult> createAccountWithEmailoAndPassword(String email, String password) {
        return mAuth.createUserWithEmailAndPassword(email, password);
    }

    public Task<AuthResult> loginWithAccountAndPassword(String account, String password) {
        return mAuth.signInWithEmailAndPassword(account, password);
    }

    private FirebaseUser getCurrentUser() {
        return mAuth.getCurrentUser();
    }

    /**
     * Cap nhat ten hien thi cua user
     * @param newName
     * @return
     */
    public Task<Void> updateUserDisplayName(String newName) {
        final FirebaseUser user = getCurrentUser();
        UserProfileChangeRequest request = new UserProfileChangeRequest.Builder()
                .setDisplayName(newName)
                .build();
        return user.updateProfile(request);
    }

    public boolean userAvailable() {
        return getCurrentUser() != null;
    }

    public String getUserDisplayName() {
        return getCurrentUser().getDisplayName();
    }
}
