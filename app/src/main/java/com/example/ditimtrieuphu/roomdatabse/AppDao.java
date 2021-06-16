package com.example.ditimtrieuphu.roomdatabse;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.ditimtrieuphu.entity.HighScore;
import com.example.ditimtrieuphu.entity.Manager;
import com.example.ditimtrieuphu.entity.Question;

import java.util.List;

@Dao
interface AppDao {
    @Query("SELECT * FROM Question")
    List<Question> getAllQuestions();

    @Query("SELECT * FROM question WHERE _id = :id")
    Question getQuestionById(String id);

    @Query("SELECT * FROM Question WHERE level = :level")
    List<Question> getQuestionsByLevel(String level);


    @Query("SELECT * FROM HighScore")
    List<HighScore> getAllHighScore();

    @Query("SELECT * FROM HighScore WHERE Name = :name" )
    HighScore getHighScoreByName (String name);

    @Insert
    void insertHighScore(HighScore highScore);


    @Query("SELECT * FROM manager")
    List<Manager> getAllManager();

    @Query("SELECT * FROM manager WHERE _id = :id" )
    Manager getManagerById (int id);

    @Insert
    void insertManager(Manager manager);

}
