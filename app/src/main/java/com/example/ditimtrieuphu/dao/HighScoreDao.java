package com.example.ditimtrieuphu.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.ditimtrieuphu.entity.HighScore;

import java.util.List;

@Dao
public interface HighScoreDao {
    @Query("SELECT * FROM HighScore")
    List<HighScore> getAllHighScore();

    @Insert
    void addNewScore(HighScore... item);

}
