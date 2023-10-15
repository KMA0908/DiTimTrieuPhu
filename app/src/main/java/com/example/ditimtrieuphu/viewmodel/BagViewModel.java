package com.example.ditimtrieuphu.viewmodel;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.example.ditimtrieuphu.ContextAccessable;
import com.example.ditimtrieuphu.entity.BonusItem;
import com.example.ditimtrieuphu.session.UserSessionManager;

import java.util.ArrayList;
import java.util.List;

public class BagViewModel extends ViewModel implements ContextAccessable {
    private UserSessionManager mUserSessionManager;


    @Override
    public void setContext(Context context) {
        mUserSessionManager = UserSessionManager.getInstance(context);
    }

    public List<BonusItem> getAllItem() {
        if (mUserSessionManager == null) {
            return new ArrayList<>();
        }
        return mUserSessionManager.getOwnedItems();
    }
}
