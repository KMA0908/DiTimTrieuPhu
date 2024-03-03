package com.nhn.ditimtrieuphu.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.nhn.ditimtrieuphu.entity.Item;

import java.util.List;

@Dao
public interface ItemDao {
    @Query("SELECT * FROM Item WHERE username = :username")
    List<Item> getItemUserActive(String username);
    @Insert
    void addNewItemUser(Item... item);
}
