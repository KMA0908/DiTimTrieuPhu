package com.example.ditimtrieuphu.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.ditimtrieuphu.entity.BonusItem;

import java.util.List;

@Dao
public interface BonusItemDao {
    @Insert
    void add(BonusItem bonusItem);

    @Update
    void update(BonusItem bonusItem);

    @Query("SELECT * FROM bonus_item WHERE id = :id")
    BonusItem getById(String id);

    @Query("UPDATE bonus_item SET amount = :amount, relationId = :relationId WHERE id = :id")
    void updateAmountAndRelationOwned(String id, int amount, String relationId);

    @Query("SELECT * FROM bonus_item")
    List<BonusItem> getAll();

    @Query("SELECT * FROM bonus_item WHERE amount > 0")
    List<BonusItem> getOwnedItems();
}
