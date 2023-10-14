package com.example.ditimtrieuphu.dto;

public class BadgeRelation {
    public static final String KEY_ID = "id";
    public static final String KEY_USER_ID = "userId";
    public static final String KEY_BADGE_ID = "badgeId";
    public static final String KEY_EQUIPPED = "equipped";

    private String id;
    private String userId;
    private String badgeId;
    private boolean equipped;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBadgeId() {
        return badgeId;
    }

    public void setBadgeId(String badgeId) {
        this.badgeId = badgeId;
    }

    public boolean isEquipped() {
        return equipped;
    }

    public void setEquipped(boolean equipped) {
        this.equipped = equipped;
    }
}
