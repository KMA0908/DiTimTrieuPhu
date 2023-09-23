package com.example.ditimtrieuphu.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ditimtrieuphu.Executable;
import com.example.ditimtrieuphu.session.UserManager;
import com.google.firebase.auth.FirebaseUser;

public class MainFragViewModel extends ViewModel {
    private UserManager mUserManager;
    private MutableLiveData<String> mUserNameLiveData;

    public MainFragViewModel() {
        mUserManager = UserManager.getInstance();
        mUserNameLiveData = new MutableLiveData<>();
    }

    public void updateUserDisplayName(String name, Executable success, Executable fail) {
        mUserManager.updateUserDisplayName(name).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // update lai data name user
                mUserNameLiveData.postValue(mUserManager.getUserDisplayName());
                if (success != null) {
                    success.execute();
                }
            } else {
                if (fail != null) {
                    fail.execute(task.getException().getMessage());
                }
            }
        });
    }

    // Getter
    public LiveData<String> getUserNameLiveData() {
        return mUserNameLiveData;
    }

    public void updateUserName() {
        mUserNameLiveData.postValue(mUserManager.getUserDisplayName());
    }
}
