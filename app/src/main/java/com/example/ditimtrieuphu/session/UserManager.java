package com.example.ditimtrieuphu.session;

import com.example.ditimtrieuphu.common.GameConstant;
import com.example.ditimtrieuphu.database.FirebaseDatabaseManager;
import com.example.ditimtrieuphu.entity.PlayerInfo;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class UserManager {
    private static UserManager sUserManager;

    private FirebaseAuth auth;
    private FirebaseDatabaseManager firebaseDatabaseManager;
    private PlayerInfo playerInfo;

    private UserManager() {
        auth = FirebaseAuth.getInstance();
        firebaseDatabaseManager = FirebaseDatabaseManager.getInstance();
    }

    public static UserManager getInstance() {
        if (sUserManager == null) {
            sUserManager = new UserManager();
        }
        return sUserManager;
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
                        }
                    });
        }

        return Tasks.forException(new RuntimeException("Player đang không đăng nhập."));
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
}
