package com.example.ditimtrieuphu.roomdatabse;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.example.ditimtrieuphu.entity.HighScore;
import com.example.ditimtrieuphu.entity.Manager;
import com.example.ditimtrieuphu.entity.Question;

import java.util.List;

public class Repository {
    private AppDao appDao;
    public Repository(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        appDao = db.appDao();
    }

    public List<Question> getAllQuestions(){
        return appDao.getAllQuestions();
    };

    public Question getQuestionById(String id) { return appDao.getQuestionById(id); };

    public List<Question> getQuestionsByLevel(String level){ return appDao.getQuestionsByLevel(level); };

    public List<HighScore> getAllHighScore(){
        return appDao.getAllHighScore();
    };

    public  HighScore getHighScoreByName (String name){ return appDao.getHighScoreByName(name); };

    public  void insertHighScore(HighScore highScore){ AppDatabase.executor.execute(()->appDao.insertHighScore(highScore)); };

    public List<Manager> getAllManager(){
        return appDao.getAllManager();
    };

    public Manager getManagerById (int id){return appDao.getManagerById(id);    };

    public void insertManager(Manager manager){ AppDatabase.executor.execute(()->appDao.insertManager(manager));};


}
