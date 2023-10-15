package com.example.ditimtrieuphu.database;

import com.example.ditimtrieuphu.dto.PlayerInfo;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class FirebaseDatabaseManager {
    public static final String USER_INFO_COLLECTION = "user_info";
    public static final String BADGE_COLLECTION = "badge";
    public static final String BADGE_RELATION_COLLECTION = "badge_relation";

    private static FirebaseDatabaseManager sInstance;
    private FirebaseFirestore database;

    private FirebaseDatabaseManager() {
        database = FirebaseFirestore.getInstance();
    }

    public static FirebaseDatabaseManager getInstance() {
        if (sInstance == null) {
            sInstance = new FirebaseDatabaseManager();
        }
        return sInstance;
    }

    // Player info collection
    public Task<DocumentReference> savePlayerInfo(PlayerInfo playerInfo) {
        Map<String, Object> info = new HashMap<>();
        info.put(PlayerInfo.KEY_LEVEL, playerInfo.getLevel());
        info.put(PlayerInfo.KEY_STAMINA, playerInfo.getStamina());
        info.put(PlayerInfo.KEY_LEVEL_PROGRESS, playerInfo.getLevelProgress());
        info.put(PlayerInfo.KEY_PROPERTY, playerInfo.getProperty());
        info.put(PlayerInfo.KEY_ROCKET, playerInfo.getRocket());
        info.put(PlayerInfo.KEY_DISPLAY_NAME, playerInfo.getDisplayName());
        info.put(PlayerInfo.KEY_USER_ID, playerInfo.getUserId());

        return database.collection(USER_INFO_COLLECTION)
                .add(info);
    }

    public Task<QuerySnapshot> getPlayerInfoByUserId(String userid) {
        CollectionReference collection = database.collection(USER_INFO_COLLECTION);
        return collection.whereEqualTo(PlayerInfo.KEY_USER_ID, userid)
                .get();
    }

    // Badge collection
    public Task<QuerySnapshot> getAllBadges() {
        CollectionReference reference = database.collection(BADGE_COLLECTION);
        return reference.get();
    }

    public Task<QuerySnapshot> getAllUserInfoSortedByProperty() {
        CollectionReference collection = database.collection(USER_INFO_COLLECTION);

        Query query = collection.orderBy("property");

        return query.get();
    }

    // Badge relation collection
    public Task<QuerySnapshot> getBadgeRelationByUserId(String userId) {
        CollectionReference reference = database.collection(BADGE_RELATION_COLLECTION);
        return reference.whereEqualTo("userId", userId).get();
    }
}
