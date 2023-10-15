package com.example.ditimtrieuphu.dto;

public class BonusItemRelation {
    public static final String[] KEYS = {"itemRelationId", "userId", "itemId", "amount"};

    private String itemRelationId;
    private String userId;
    private String itemId;
    private int amount;

    public String getItemRelationId() {
        return itemRelationId;
    }

    public String getUserId() {
        return userId;
    }

    public String getItemId() {
        return itemId;
    }

    public int getAmount() {
        return amount;
    }

    public void setItemRelationId(String itemRelationId) {
        this.itemRelationId = itemRelationId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
