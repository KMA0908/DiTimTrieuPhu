package com.example.ditimtrieuphu.session;

import android.content.Context;

import androidx.room.Room;

import com.example.ditimtrieuphu.Executable;
import com.example.ditimtrieuphu.common.GameConstant;
import com.example.ditimtrieuphu.database.AppDatabase;
import com.example.ditimtrieuphu.database.FirebaseDatabaseManager;
import com.example.ditimtrieuphu.dto.BadgeRelation;
import com.example.ditimtrieuphu.dto.PlayerInfo;
import com.example.ditimtrieuphu.entity.Badge;
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

    private FirebaseAuth auth;
    private FirebaseDatabaseManager firebaseDatabaseManager;
    private AppDatabase appDatabase;
    private PlayerInfo playerInfo;
    private List<Badge> ownedBadges;

    private UserSessionManager(Context context) {
        auth = FirebaseAuth.getInstance();
        firebaseDatabaseManager = FirebaseDatabaseManager.getInstance();
        appDatabase = Room.databaseBuilder(context, AppDatabase.class, GameConstant.APP_DATABASE)
                .allowMainThreadQueries()
                .build();
        ownedBadges = new ArrayList<>();
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
                                            BadgeRelation badgeRelation = relation.toObject(BadgeRelation.class);
                                            appDatabase.badgeDao().updateOwnedAndEquippedById(badgeRelation.getBadgeId(), true,
                                                    badgeRelation.isEquipped());
                                        }
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
                } else {
                    if (fail != null) {
                        fail.execute(task.getException().getMessage());
                    }
                }
            });
        }
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
}
