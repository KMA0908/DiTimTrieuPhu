package com.example.ditimtrieuphu.viewmodel;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.example.ditimtrieuphu.ContextAccessable;
import com.example.ditimtrieuphu.Executable;
import com.example.ditimtrieuphu.dto.BonusItemRelation;
import com.example.ditimtrieuphu.entity.BonusItem;
import com.example.ditimtrieuphu.session.UserSessionManager;

import java.util.ArrayList;
import java.util.List;

public class ShopViewModel extends ViewModel implements ContextAccessable {
    private UserSessionManager mUserSessionManager;

    @Override
    public void setContext(Context context) {
        mUserSessionManager = UserSessionManager.getInstance(context);
    }

    public List<BonusItem> getAllItem() {
        if (mUserSessionManager == null) {
            return new ArrayList<>();
        }
        return mUserSessionManager.getAllItems();
    }

    public void buyItem(BonusItem bonusItem, Executable success, Executable fail) {
        if (mUserSessionManager.getPlayerInfo().getProperty() < bonusItem.getPriceMoney()) {
            fail.execute("Bạn không đủ tiền mua vật phẩm này");
            return;
        }
        bonusItem.setAmount(bonusItem.getAmount()+1);
        if (TextUtils.isEmpty(bonusItem.getRelationId())) {
            mUserSessionManager.saveBonusItemRelation(bonusItem)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            addOwnedItem(bonusItem);
                            success.execute();
                        } else {
                            fail.execute();
                        }
                    });
        } else {
            mUserSessionManager.updateBonusItemRelation(bonusItem)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            addOwnedItem(bonusItem);
                            success.execute();
                        } else {
                            fail.execute(task.getException().getMessage());
                        }
                    });
        }
    }

    private void addOwnedItem(BonusItem bonusItem) {
        if (!mUserSessionManager.getOwnedItems().contains(bonusItem)) {
            mUserSessionManager.getOwnedItems().add(bonusItem);
        }
    }
}
