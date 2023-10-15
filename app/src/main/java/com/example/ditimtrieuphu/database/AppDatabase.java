package com.example.ditimtrieuphu.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.ditimtrieuphu.dao.BadgeDao;
import com.example.ditimtrieuphu.dao.BonusItemDao;
import com.example.ditimtrieuphu.dao.HighScoreDao;
import com.example.ditimtrieuphu.dao.QuestionDao;
import com.example.ditimtrieuphu.entity.Badge;
import com.example.ditimtrieuphu.entity.BonusItem;
import com.example.ditimtrieuphu.entity.HighScore;
import com.example.ditimtrieuphu.entity.Question;

@Database(entities = {Question.class, HighScore.class, Badge.class, BonusItem.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract QuestionDao questionDao();
    public abstract HighScoreDao highScoreDao();
    public abstract BadgeDao badgeDao();
    public abstract BonusItemDao bonusItemDao();
}
