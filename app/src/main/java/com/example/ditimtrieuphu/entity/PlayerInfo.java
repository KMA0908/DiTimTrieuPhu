package com.example.ditimtrieuphu.entity;

public class PlayerInfo {
    public static final String KEY_ID = "id";
    public static final String KEY_LEVEL = "level";
    public static final String KEY_STAMINA = "stamina";
    public static final String KEY_LEVEL_PROGRESS = "levelProgress";
    public static final String KEY_PROPERTY = "property";
    public static final String KEY_DISPLAY_NAME = "displayName";
    public static final String KEY_USER_ID = "userId";

    private String id;
    private int level;
    private int stamina;
    private int levelProgress;
    private long property;
    private String displayName;
    private String userId;

    public PlayerInfo() {

    }

    public PlayerInfo(String id, int level, int stamina, int levelProgress, long property, String displayName, String userId) {
        this.id = id;
        this.level = level;
        this.stamina = stamina;
        this.levelProgress = levelProgress;
        this.property = property;
        this.displayName = displayName;
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public int getLevel() {
        return level;
    }

    public int getStamina() {
        return stamina;
    }

    public int getLevelProgress() {
        return levelProgress;
    }

    public long getProperty() {
        return property;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getUserId() {
        return userId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setStamina(int stamina) {
        this.stamina = stamina;
    }

    public void setLevelProgress(int levelProgress) {
        this.levelProgress = levelProgress;
    }

    public void setProperty(long property) {
        this.property = property;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
