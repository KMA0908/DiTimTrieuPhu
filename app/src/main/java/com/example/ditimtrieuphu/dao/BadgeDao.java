package com.example.ditimtrieuphu.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.ditimtrieuphu.entity.Badge;

import java.util.List;

@Dao
public interface BadgeDao {
    @Insert
    void addBadge(Badge badge);

    @Update
    void updateBadge(Badge badge);

    @Query("SELECT * FROM badge WHERE id = :id")
    Badge getById(String id);

    @Query("UPDATE badge SET owned = :owned, equipped = :equipped WHERE id = :id")
    void updateOwnedAndEquippedById(String id, boolean owned, boolean equipped);

    @Query("SELECT * FROM badge WHERE owned = 1")
    List<Badge> getOwnedBadges();
}
