package com.example.ditimtrieuphu.session;

import android.content.Context;
import android.util.Log;

import androidx.room.Room;

import com.example.ditimtrieuphu.App;
import com.example.ditimtrieuphu.Executable;
import com.example.ditimtrieuphu.common.GameConstant;
import com.example.ditimtrieuphu.database.AppDatabase;
import com.example.ditimtrieuphu.database.FirebaseDatabaseManager;
import com.example.ditimtrieuphu.dto.BadgeRelation;
import com.example.ditimtrieuphu.dto.BonusItemRelation;
import com.example.ditimtrieuphu.dto.PlayerInfo;
import com.example.ditimtrieuphu.entity.Badge;
import com.example.ditimtrieuphu.entity.BonusItem;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class UserSessionManager {
    private static UserSessionManager sUserSessionManager;
    private static final String MESSAGE_ERROR = "MESSAGE_ERROR";

    private FirebaseAuth auth;
    private FirebaseDatabaseManager firebaseDatabaseManager;
    private AppDatabase appDatabase;
    private PlayerInfo playerInfo;
    private List<Badge> ownedBadges;
    private List<BonusItem> ownedItems;
    private List<BonusItem> allItems;

    private UserSessionManager(Context context) {
        auth = FirebaseAuth.getInstance();
        firebaseDatabaseManager = FirebaseDatabaseManager.getInstance();
        appDatabase = App.getInstance().getAppDB();
        ownedBadges = new ArrayList<>();
        ownedItems = new ArrayList<>();
        allItems = new ArrayList<>();
    }

    public static UserSessionManager getInstance(Context context) {
        if (sUserSessionManager == null) {
            sUserSessionManager = new UserSessionManager(context);
        }
        return sUserSessionManager;
    }

    /**
     * Tao account
     * @param email
     * @param password
     * @return
     */
    public Task<AuthResult> createAccountWithEmailoAndPassword(String email, String password) {
        return auth.createUserWithEmailAndPassword(email, password);
    }

    public Task<AuthResult> loginWithAccountAndPassword(String account, String password) {
        return auth.signInWithEmailAndPassword(account, password);
    }

    public Task<DocumentReference> createDefaultPlayerInfo(String playerName) {
        //TODO ve sau can check ten da duoc dung hay chua, tam thoi hien tai cho tao ten trung nhau.
        if (userAvailable()) {
            PlayerInfo playerInfo = new PlayerInfo();
            playerInfo.setLevel(GameConstant.DEFAULT_LEVEL_PLAYER);
            playerInfo.setStamina(GameConstant.DEFAULT_STAMINA_PLAYER);
            playerInfo.setLevelProgress(GameConstant.DEFAULT_LEVEL_PROGRESS);
            playerInfo.setProperty(GameConstant.DEFAULT_PROPERTY_PLAYER);
            playerInfo.setRocket(GameConstant.DEFAULT_ROCKET_PLAYER);
            playerInfo.setDisplayName(playerName);
            playerInfo.setUserId(getCurrentUser().getUid());
            return firebaseDatabaseManager
                    .savePlayerInfo(playerInfo);
        }

        return Tasks.forException(new RuntimeException("Người chơi đang không đăng nhập"));
    }

    public Task<Object> logOut() {
        auth.signOut();
        if (getCurrentUser() == null) {
            return Tasks.forResult("Success");
        }
        return Tasks.forException(new RuntimeException("Logout lỗi"));
    }

    public Task<QuerySnapshot> syncPlayerInfo() {
        if (userAvailable()) {
            return firebaseDatabaseManager.getPlayerInfoByUserId(getCurrentUser().getUid())
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        List<PlayerInfo> playerInfos = queryDocumentSnapshots.toObjects(PlayerInfo.class);
                        if (playerInfos.size() > 0) {
                            playerInfo = playerInfos.get(0);
                            playerInfo.setId(queryDocumentSnapshots.getDocuments().get(0).getId());
                            playerInfo.setUserId(getCurrentUser().getUid());
                        }
                    });
        }

        return Tasks.forException(new RuntimeException("Player đang không đăng nhập."));
    }

    public void syncOwnedBadges() {
        ownedBadges.clear();
        ownedBadges.addAll(appDatabase.badgeDao().getOwnedBadges());
    }

    public void syncItems() {
        allItems.clear();
        ownedItems.clear();
        allItems.addAll(appDatabase.bonusItemDao().getAll());
        for (BonusItem bonusItem: allItems) {
            if (bonusItem.getAmount() > 0) {
                ownedItems.add(bonusItem);
            }
            Log.d("MinhNTn", "syncItems: " + bonusItem.getRelationId());
        }
    }

    public void syncGameResources(Executable success, Executable fail) {
        if (appDatabase != null) {
            // sync data cua badge
            firebaseDatabaseManager.getAllBadges().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    List<DocumentSnapshot> snapshots = task.getResult().getDocuments();
                    for (DocumentSnapshot d: snapshots) {
                        Badge badge = d.toObject(Badge.class);
                        badge.setId(d.getId());
                        if (appDatabase.badgeDao().getById(badge.getId()) == null) {
                            appDatabase.badgeDao().addBadge(badge);
                        } else {
                            appDatabase.badgeDao().updateBadge(badge);
                        }
                    }
                    // sync relation cua bagde voi user hien tai
                    if (userAvailable()) {
                        firebaseDatabaseManager
                                .getBadgeRelationByUserId(getCurrentUserId())
                                .addOnCompleteListener(t -> {
                                    if (t.isSuccessful()) {
                                        List<DocumentSnapshot> relations = t.getResult().getDocuments();
                                        for (DocumentSnapshot relation: relations) {
                                            Log.d("MinhNTn", "syncGameResources: "+ relation.getData());
                                            BadgeRelation badgeRelation = relation.toObject(BadgeRelation.class);
                                            appDatabase.badgeDao().updateOwnedAndEquippedById(badgeRelation.getBadgeId(), true,
                                                    badgeRelation.isEquipped(), relation.getId());
                                        }
                                        syncBonusItem(success, fail);
                                    } else {
                                        if (fail != null) {
                                            fail.execute(task.getException().getMessage());
                                        }
                                    }
                                });
                    } else {
                        syncBonusItem(success, fail);
                    }
                } else {
                    if (fail != null) {
                        fail.execute(task.getException().getMessage());
                    }
                }
            });
        }
    }

    public void syncBonusItem(Executable success, Executable fail) {
        // sync data cua bonus item
        firebaseDatabaseManager.getAllBonusItems().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<DocumentSnapshot> snapshots = task.getResult().getDocuments();
                for (DocumentSnapshot d: snapshots) {
                    BonusItem bonusItem = d.toObject(BonusItem.class);
                    bonusItem.setId(d.getId());
                    if (appDatabase.bonusItemDao().getById(bonusItem.getId()) == null) {
                        appDatabase.bonusItemDao().add(bonusItem);
                    } else {
                        appDatabase.bonusItemDao().update(bonusItem);
                    }
                }
                // sync relation cua item voi user hien tai
                if (userAvailable()) {
                    firebaseDatabaseManager
                            .getBonusItemRelationByUserId(getCurrentUserId())
                            .addOnCompleteListener(t -> {
                                if (t.isSuccessful()) {
                                    List<DocumentSnapshot> relations = t.getResult().getDocuments();
                                    for (DocumentSnapshot relation: relations) {
                                        BonusItemRelation bonusItemRelation = relation.toObject(BonusItemRelation.class);
                                        appDatabase.bonusItemDao().updateAmountAndRelationOwned(bonusItemRelation.getItemId(), bonusItemRelation.getAmount(), bonusItemRelation.getItemRelationId());
                                    }
                                    syncItems();
                                    syncOwnedBadges();
                                    success.execute();
                                } else {
                                    if (fail != null) {
                                        fail.execute(task.getException().getMessage());
                                    }
                                }
                            });
                } else {
                    success.execute();
                }
            }
        });
    }

    public Task<Void> saveBadgeRelation(Badge badge) {
        if (userAvailable()) {
            return firebaseDatabaseManager.saveBadgeRelation(badge.toBadgeRelationDto(getCurrentUserId()))
                    .addOnSuccessListener(unused -> {
                        appDatabase.badgeDao().updateBadge(badge);
                    });
        }
        return Tasks.forException(new RuntimeException(MESSAGE_ERROR));
    }

    public Task<DocumentReference> saveBonusItemRelation(BonusItem bonusItem) {
        return firebaseDatabaseManager.saveBonusItemRelation(bonusItem.toRelationDto(getCurrentUserId()))
                .addOnCompleteListener(task -> {
                    bonusItem.setRelationId(task.getResult().getId());
                    appDatabase.bonusItemDao().update(bonusItem);
                    // Cap nhat lai relation id
                    firebaseDatabaseManager.updateBonusItemRelation(bonusItem.toRelationDto(getCurrentUserId()));
                    payForItem(bonusItem.getPriceMoney());
                });
    }

    public Task<Void> updateBonusItemRelation(BonusItem bonusItem, boolean increase) {
        // Neu so luong update bang 0 thi xoa
        if (bonusItem.getAmount() == 0) {
            return firebaseDatabaseManager.deleteBonusItemRelation(bonusItem.toRelationDto(getCurrentUserId()))
                    .addOnCompleteListener(task -> {
                        appDatabase.bonusItemDao().update(bonusItem);
                    });
        }
        return firebaseDatabaseManager.updateBonusItemRelation(bonusItem.toRelationDto(getCurrentUserId()))
                .addOnCompleteListener(task -> {
                    appDatabase.bonusItemDao().update(bonusItem);
                    if (increase) {
                        payForItem(bonusItem.getPriceMoney());
                    }
                });
    }

    public void payForItem(long price) {
        playerInfo.setProperty(playerInfo.getProperty() - price);
        firebaseDatabaseManager.updatePlayerInfo(playerInfo);
    }

    public Task<Void> updatePlayerInfo() {
        return firebaseDatabaseManager.updatePlayerInfo(playerInfo);
    }

    // Getter
    private FirebaseUser getCurrentUser() {
        return auth.getCurrentUser();
    }

    public PlayerInfo getPlayerInfo() {
        return playerInfo;
    }

    public boolean userAvailable() {
        return getCurrentUser() != null;
    }

    public String getCurrentUserId() {
        return auth.getUid();
    }

    public List<Badge> getOwnedBadges() {
        return ownedBadges;
    }

    public List<BonusItem> getOwnedItems() {
        return ownedItems;
    }

    public List<BonusItem> getAllItems() {
        return allItems;
    }
}
