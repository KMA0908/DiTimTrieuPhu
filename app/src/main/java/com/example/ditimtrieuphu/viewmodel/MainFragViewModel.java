package com.example.ditimtrieuphu.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ditimtrieuphu.Executable;
import com.example.ditimtrieuphu.entity.PlayerInfo;
import com.example.ditimtrieuphu.session.UserManager;

public class MainFragViewModel extends ViewModel {
    private UserManager mUserManager;
    private MutableLiveData<PlayerInfo> mPlayerInfoMutableLiveData;

    public MainFragViewModel() {
        mUserManager = UserManager.getInstance();
        mPlayerInfoMutableLiveData = new MutableLiveData<>();
    }

    public void logout(Executable success, Executable fail) {
        mUserManager.logOut().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
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

    public void syncPlayerInfo(Executable success, Executable fail) {
        mUserManager.syncPlayerInfo().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                mPlayerInfoMutableLiveData.setValue(mUserManager.getPlayerInfo());
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

    public void createDefaultPlayerInfo(String playerName, Executable success, Executable fail) {
        // Minh: neu thanh cong thi tao thong mac dinh cua player
        mUserManager.createDefaultPlayerInfo(playerName).addOnCompleteListener(t -> {
            if (t.isSuccessful()) {
                success.execute();
            } else {
                fail.execute(t.getException().getMessage());
            }
        });
    }

    // Getter
    public MutableLiveData<PlayerInfo> getPlayerInfoMutableLiveData() {
        return mPlayerInfoMutableLiveData;
    }
}
