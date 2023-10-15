package com.example.ditimtrieuphu.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.ditimtrieuphu.dto.BonusItemRelation;

@Entity(tableName = "bonus_item")
public class BonusItem {
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

    @ColumnInfo(name = "priceMoney")
    private long priceMoney;

    @ColumnInfo(name = "priceRocket")
    private int priceRocket;

    @ColumnInfo(name = "amount")
    private int amount;

    @ColumnInfo(name = "detail")
    private String detail;

    @ColumnInfo(name = "relationId")
    private String relationId;

    public BonusItem() {}

    public BonusItem(@NonNull String id, String name, String effect, String icon, long priceMoney, int priceRocket, int amount) {
        this.id = id;
        this.name = name;
        this.effect = effect;
        this.icon = icon;
        this.priceMoney = priceMoney;
        this.priceRocket = priceRocket;
        this.amount = amount;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEffect() {
        return effect;
    }

    public String getIcon() {
        return icon;
    }

    public long getPriceMoney() {
        return priceMoney;
    }

    public int getPriceRocket() {
        return priceRocket;
    }

    public int getAmount() {
        return amount;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setPriceMoney(long priceMoney) {
        this.priceMoney = priceMoney;
    }

    public void setPriceRocket(int priceRocket) {
        this.priceRocket = priceRocket;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getRelationId() {
        return relationId;
    }

    public void setRelationId(String relationId) {
        this.relationId = relationId;
    }

    public BonusItemRelation toRelationDto(String userId) {
        BonusItemRelation bonusItemRelation = new BonusItemRelation();
        bonusItemRelation.setItemRelationId(relationId);
        bonusItemRelation.setAmount(amount);
        bonusItemRelation.setItemId(id);
        bonusItemRelation.setUserId(userId);
        return bonusItemRelation;
    }
}
