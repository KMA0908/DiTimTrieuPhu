package com.example.ditimtrieuphu.viewmodel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ditimtrieuphu.ContextAccessable;
import com.example.ditimtrieuphu.Executable;
import com.example.ditimtrieuphu.dto.PlayerInfo;
import com.example.ditimtrieuphu.entity.Badge;
import com.example.ditimtrieuphu.session.UserSessionManager;

import java.util.List;

public class MainFragViewModel extends ViewModel implements ContextAccessable {
    private UserSessionManager mUserSessionManager;
    private final MutableLiveData<PlayerInfo> mPlayerInfoMutableLiveData;

    public MainFragViewModel() {
        mPlayerInfoMutableLiveData = new MutableLiveData<>();
    }

    public void logout(Executable success, Executable fail) {
        mUserSessionManager.logOut().addOnCompleteListener(task -> {
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
        mUserSessionManager.syncPlayerInfo().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                mPlayerInfoMutableLiveData.setValue(mUserSessionManager.getPlayerInfo());
                mUserSessionManager.syncOwnedBadges();
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
        mUserSessionManager.createDefaultPlayerInfo(playerName).addOnCompleteListener(t -> {
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

    @Override
    public void setContext(Context context) {
        mUserSessionManager = UserSessionManager.getInstance(context);
    }

    public List<Badge> getOwnedBadges() {
        return mUserSessionManager.getOwnedBadges();
    }
}
