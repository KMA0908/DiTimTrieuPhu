package com.nhn.ditimtrieuphu.model;

import java.io.Serializable;

public class ItemUser implements Serializable {
    private String name;
    private int image;

    private String price;
    private boolean isActive;

    public ItemUser(String name, int image, String price, boolean isActive) {
        this.name = name;
        this.image = image;
        this.price = price;
        this.isActive = isActive;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
