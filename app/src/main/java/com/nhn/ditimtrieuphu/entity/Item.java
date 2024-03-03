package com.nhn.ditimtrieuphu.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Item")
public class Item {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="_id")
    @NonNull
    private int id;

    @ColumnInfo(name = "username")
    @NonNull
    private String username;

    @ColumnInfo(name = "index")
    @NonNull
    private int indexItem;

    public Item(@NonNull String username, int indexItem) {
        this.username = username;
        this.indexItem = indexItem;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getUsername() {
        return username;
    }

    public void setUsername(@NonNull String username) {
        this.username = username;
    }

    public int getIndexItem() {
        return indexItem;
    }

    public void setIndexItem(int indexItem) {
        this.indexItem = indexItem;
    }
}
