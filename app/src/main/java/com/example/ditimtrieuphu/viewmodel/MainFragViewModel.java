package com.example.ditimtrieuphu.viewmodel;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ditimtrieuphu.ContextAccessable;
import com.example.ditimtrieuphu.Executable;
import com.example.ditimtrieuphu.dto.PlayerInfo;
import com.example.ditimtrieuphu.entity.Badge;
import com.example.ditimtrieuphu.entity.BonusItem;
import com.example.ditimtrieuphu.session.UserSessionManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;

public class MainFragViewModel extends ViewModel implements ContextAccessable {
    private UserSessionManager mUserSessionManager;
    private final MutableLiveData<PlayerInfo> mPlayerInfoMutableLiveData;
    private List<BonusItem> itemUsed;
    public final MutableLiveData<Long> totalMoneyLiveData;
    public int totalExp;
    public int heSoChoi = 1;

    public MainFragViewModel() {
        mPlayerInfoMutableLiveData = new MutableLiveData<>();
        itemUsed = new ArrayList<>();
        totalMoneyLiveData = new MutableLiveData<>(0L);
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

    public void updateBadge(Badge badge, Executable success, Executable fail) {
        mUserSessionManager.saveBadgeRelation(badge)
                .addOnCompleteListener(task -> {
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

    public void updateItems(List<BonusItem> bonusItems) {
        itemUsed.clear();
        itemUsed.addAll(bonusItems);
        for (BonusItem item: bonusItems) {
            item.setAmount(item.getAmount() - 1);
            mUserSessionManager.updateBonusItemRelation(item, false);
            if (item.getAmount() == 0) {
                mUserSessionManager.getOwnedItems().remove(item);
            }
        }
    }

    public void updatePlayerStamina(int stamina) {
        mPlayerInfoMutableLiveData.getValue().setStamina(stamina);
        mUserSessionManager.updatePlayerInfo();
    }

    public void updateMoneyAndExpPlayer(long money, int exp) {
        long playerMoney = mPlayerInfoMutableLiveData.getValue().getProperty();
        int playerExp = mPlayerInfoMutableLiveData.getValue().getLevelProgress();
        mPlayerInfoMutableLiveData.getValue().setProperty(playerMoney + money);
        mPlayerInfoMutableLiveData.getValue().setLevelProgress(playerExp + exp);
        mUserSessionManager.updatePlayerInfo();
    }

    public void resetPlaySessionInfo() {
        totalMoneyLiveData.setValue(0L);
        itemUsed.clear();
        totalExp = 0;
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

    public List<BonusItem> getOwnedBonusItem() {
        return mUserSessionManager.getOwnedItems();
    }

    public List<BonusItem> getItemUsed() { return itemUsed; }

    public float getHeSoBonusItem() {
        float heSo = 0f;
        for (BonusItem item: getItemUsed()) {
            heSo += item.getEffect();
        }
        return heSo;
    }

    public float getHeSoBadges() {
        float heSo = 0f;
        for (Badge badge: mUserSessionManager.getOwnedBadges()) {
            if (badge.isEquipped()) {
                heSo += badge.getEffect();
            }
        }
        return heSo;
    }
}
