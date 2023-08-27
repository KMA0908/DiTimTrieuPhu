package com.example.ditimtrieuphu.async;

import com.example.ditimtrieuphu.App;
import com.example.ditimtrieuphu.entity.HighScore;
import com.example.ditimtrieuphu.entity.Question;

import java.util.List;

public class QuestionManager {
    private static QuestionManager instance;

    private QuestionManager(){

    }

    public static QuestionManager getInstance() {
        if(instance==null){
            instance=new QuestionManager();
        }
        return instance;
    }

    public void getQuestionByLevel(int level,OnResultCallBack cb){
        new Thread(){
            @Override
            public void run() {
                List<Question> rs= App.getInstance().getAppDB().questionDao().getQuestionByLevel(level);
                cb.callBack(rs);
            }
        }.start();
    }

    public void addNewScore(OnResultCallBack cb, HighScore... items) {
        new Thread() {
            @Override
            public void run() {
                try {
                    App.getInstance().getAppDB().highScoreDao().addNewScore(items);
                    cb.callBack(true);
                } catch (Exception e) {
                    e.printStackTrace();
                    cb.callBack(false);
                }
            }
        }.start();
    }


    public interface OnResultCallBack{
        void callBack(Object data);
    }
}
