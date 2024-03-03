package com.nhn.ditimtrieuphu.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.nhn.ditimtrieuphu.entity.HighScore;

import java.util.List;

@Dao
public interface HighScoreDao {
    @Query("SELECT * FROM HighScore")
    List<HighScore> getAllHighScoreUser();

    @Query("SELECT * FROM HighScore WHERE Name = :username")
    List<HighScore> getAllHighScoreUser(String username);

    @Insert
    void addNewScore(HighScore... item);

}
