package com.example.ditimtrieuphu.roomdatabse;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.ditimtrieuphu.entity.HighScore;
import com.example.ditimtrieuphu.entity.Manager;
import com.example.ditimtrieuphu.entity.Question;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Database(version = 1,entities = {Question.class, HighScore.class, Manager.class}, exportSchema = true)
public abstract class AppDatabase extends RoomDatabase{

    private static final int NUMBER_OF_THREAD = 4;
    private static AppDatabase instance;
    private static final String DATABASE_NAME = "app_database";
    public static Executor executor = Executors.newFixedThreadPool(NUMBER_OF_THREAD);

    public abstract AppDao appDao();

    public static AppDatabase getInstance(Context context) {
        if(instance == null){
            synchronized (AppDatabase.class){
                if(instance==null){
                    instance = Room.databaseBuilder(context, AppDatabase.class, DATABASE_NAME)
                                    .allowMainThreadQueries()
                                    .createFromAsset("Question.db").build();
                }
                else instance = Room.databaseBuilder(context, AppDatabase.class, DATABASE_NAME)
                                    .allowMainThreadQueries()
                                    .build();
            }
        }
        return instance;
    }
}
