package com.nhn.ditimtrieuphu.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.nhn.ditimtrieuphu.entity.Question;

import java.util.List;

@Dao
public interface QuestionDao {
    @Query("SELECT * FROM Question")
    List<Question> getAllQuestions();

    @Query("SELECT * FROM Question WHERE level= :level ORDER BY RANDOM() LIMIT 1")
    List<Question> getQuestionByLevel(int level);
}