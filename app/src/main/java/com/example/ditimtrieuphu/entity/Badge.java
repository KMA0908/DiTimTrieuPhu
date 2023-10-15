package com.example.ditimtrieuphu.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.ditimtrieuphu.dto.BadgeRelation;

@Entity(tableName = "badge")
public class Badge {
    @PrimaryKey
    @ColumnInfo(name = "id")
    @NonNull
    private String id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "effect")
    private String effect;

    @ColumnInfo(name = "icon")
    private String icon;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "priceMoney")
    private long priceMoney;

    @ColumnInfo(name = "priceRocket")
    private int priceRocket;

    @ColumnInfo(name = "owned")
    private boolean owned;

    @ColumnInfo(name = "equipped")
    private boolean equipped;

    @ColumnInfo(name = "relationId")
    private String relationId;

    public Badge() {}

    public Badge(@NonNull String id, String name, String effect, String icon, String description, long priceMoney, int priceRocket, boolean owned, boolean equipped, String relationId) {
        this.id = id;
        this.name = name;
        this.effect = effect;
        this.icon = icon;
        this.description = description;
        this.priceMoney = priceMoney;
        this.priceRocket = priceRocket;
        this.owned = owned;
        this.equipped = equipped;
        this.relationId = relationId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getPriceMoney() {
        return priceMoney;
    }

    public void setPriceMoney(long priceMoney) {
        this.priceMoney = priceMoney;
    }

    public int getPriceRocket() {
        return priceRocket;
    }

    public void setPriceRocket(int priceRocket) {
        this.priceRocket = priceRocket;
    }

    public boolean isOwned() {
        return owned;
    }

    public void setOwned(boolean owned) {
        this.owned = owned;
    }

    public boolean isEquipped() {
        return equipped;
    }

    public void setEquipped(boolean equipped) {
        this.equipped = equipped;
    }

    public String getRelationId() {
        return relationId;
    }

    public void setRelationId(String relationId) {
        this.relationId = relationId;
    }

    public BadgeRelation toBadgeRelationDto(String userId) {
        BadgeRelation badgeRelation = new BadgeRelation();
        badgeRelation.setBadgeId(id);
        badgeRelation.setBadgeRelationId(relationId);
        badgeRelation.setEquipped(equipped);
        badgeRelation.setUserId(userId);
        return badgeRelation;
    }
}
