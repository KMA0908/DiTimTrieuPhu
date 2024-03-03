package com.nhn.ditimtrieuphu.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.nhn.ditimtrieuphu.dao.HighScoreDao;
import com.nhn.ditimtrieuphu.dao.ItemDao;
import com.nhn.ditimtrieuphu.dao.QuestionDao;
import com.nhn.ditimtrieuphu.entity.HighScore;
import com.nhn.ditimtrieuphu.entity.Item;
import com.nhn.ditimtrieuphu.entity.Question;

@Database(entities = {Question.class, HighScore.class, Item.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract QuestionDao questionDao();
    public abstract HighScoreDao highScoreDao();
    public abstract ItemDao itemDao();
}
