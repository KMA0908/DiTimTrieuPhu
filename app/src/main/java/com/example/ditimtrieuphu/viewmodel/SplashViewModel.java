package com.example.ditimtrieuphu.viewmodel;

import android.content.Context;

import androidx.lifecycle.ViewModel;
import androidx.room.Room;

import com.example.ditimtrieuphu.ContextAccessable;
import com.example.ditimtrieuphu.Executable;
import com.example.ditimtrieuphu.common.GameConstant;
import com.example.ditimtrieuphu.database.AppDatabase;
import com.example.ditimtrieuphu.database.FirebaseDatabaseManager;
import com.example.ditimtrieuphu.dto.BadgeRelation;
import com.example.ditimtrieuphu.entity.Badge;
import com.example.ditimtrieuphu.session.UserSessionManager;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.List;

public class SplashViewModel extends ViewModel implements ContextAccessable {
    private UserSessionManager mUserSessionManager;

    public SplashViewModel() {
    }

    @Override
    public void setContext(Context context) {
        mUserSessionManager = UserSessionManager.getInstance(context);
    }

    /**
     * sync du lieu tu firebase ve
     * @param success
     * @param fail
     */
    public void syncGameResources(Executable success, Executable fail) {
        mUserSessionManager.syncGameResources(success, fail);
    }

}