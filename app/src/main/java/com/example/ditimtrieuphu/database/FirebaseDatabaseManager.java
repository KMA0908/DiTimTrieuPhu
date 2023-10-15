package com.example.ditimtrieuphu.database;

import com.example.ditimtrieuphu.common.CommonUtils;
import com.example.ditimtrieuphu.dto.BadgeRelation;
import com.example.ditimtrieuphu.dto.BonusItemRelation;
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
    public static final String BONUS_ITEM_COLLECTION = "bonus_item";
    public static final String BONUS_ITEM_RELATION_COLLECTION = "bonus_items_relation";

    private static FirebaseDatabaseManager sInstance;
    private FirebaseFirestore database;
    private CommonUtils commonUtils;

    private FirebaseDatabaseManager() {
        database = FirebaseFirestore.getInstance();
        commonUtils = CommonUtils.getInstance();
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

    // ======= user info collection =========
    public Task<QuerySnapshot> getPlayerInfoByUserId(String userid) {
        CollectionReference collection = database.collection(USER_INFO_COLLECTION);
        return collection.whereEqualTo(PlayerInfo.KEY_USER_ID, userid)
                .get();
    }

    public Task<Void> updatePlayerInfo(PlayerInfo playerInfo) {
        CollectionReference collection = database.collection(USER_INFO_COLLECTION);
        return collection.document(playerInfo.getId())
                .set(playerInfo);
    }

    // ========= Badge collection ===========
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
    // ======== Badge relation collection ==========
    public Task<QuerySnapshot> getBadgeRelationByUserId(String userId) {
        CollectionReference reference = database.collection(BADGE_RELATION_COLLECTION);
        return reference.whereEqualTo("userId", userId).get();
    }

    public Task<Void> saveBadgeRelation(BadgeRelation badgeRelation) {
        CollectionReference reference = database.collection(BADGE_RELATION_COLLECTION);
        return reference.document(badgeRelation.getBadgeRelationId())
                .set(commonUtils.convertToDto(BadgeRelation.KEYS, badgeRelation));
    }

    // ========= Bonus item collection ==============
    public Task<QuerySnapshot> getAllBonusItems() {
        CollectionReference reference = database.collection(BONUS_ITEM_COLLECTION);
        return reference.get();
    }

    // ======== Bonus item relation collection ==========
    public Task<QuerySnapshot> getBonusItemRelationByUserId(String userId) {
        CollectionReference reference = database.collection(BONUS_ITEM_RELATION_COLLECTION);
        return reference.whereEqualTo("userId", userId).get();
    }

    // Note neu so luong item nay so huu la 0 thi save moi
    public Task<DocumentReference> saveBonusItemRelation(BonusItemRelation bonusItemRelation) {
        CollectionReference reference = database.collection(BONUS_ITEM_RELATION_COLLECTION);
        return reference.add(commonUtils.convertToDto(BonusItemRelation.KEYS, bonusItemRelation));
    }

    // neu so luong khac 0 thi dung update
    public Task<Void> updateBonusItemRelation(BonusItemRelation bonusItemRelation) {
        CollectionReference reference = database.collection(BONUS_ITEM_RELATION_COLLECTION);
        return reference.document(bonusItemRelation.getItemRelationId())
                .set(commonUtils.convertToDto(BonusItemRelation.KEYS, bonusItemRelation));
    }

    public Task<Void> deleteBonusItemRelation(BonusItemRelation bonusItemRelation) {
        CollectionReference reference = database.collection(BONUS_ITEM_RELATION_COLLECTION);
        return reference.document(bonusItemRelation.getItemRelationId())
                .delete();
    }
}
