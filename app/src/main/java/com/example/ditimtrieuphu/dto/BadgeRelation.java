package com.example.ditimtrieuphu.dto;

public class BadgeRelation {
    public static final String[] KEYS = {"badgeRelationId", "userId", "badgeId", "equipped"};

    private String badgeRelationId;
    private String userId;
    private String badgeId;
    private boolean equipped;

    public String getBadgeRelationId() {
        return badgeRelationId;
    }

    public void setBadgeRelationId(String badgeRelationId) {
        this.badgeRelationId = badgeRelationId;
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
