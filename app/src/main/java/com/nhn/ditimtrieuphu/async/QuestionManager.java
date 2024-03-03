package com.nhn.ditimtrieuphu.async;

import com.nhn.ditimtrieuphu.App;
import com.nhn.ditimtrieuphu.entity.HighScore;
import com.nhn.ditimtrieuphu.entity.Item;
import com.nhn.ditimtrieuphu.entity.Question;

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

    public void addNewItemUser(OnResultCallBack cb, Item... items) {
        new Thread() {
            @Override
            public void run() {
                try {
                    App.getInstance().getAppDB().itemDao().addNewItemUser(items);
                    cb.callBack(true);
                } catch (Exception e) {
                    e.printStackTrace();
                    cb.callBack(false);
                }
            }
        }.start();
    }


    public void getHighScoreUser(String username, OnResultCallBack cb){
        new Thread(){
            @Override
            public void run() {
                List<HighScore> rs= App.getInstance().getAppDB().highScoreDao().getAllHighScoreUser(username);
                cb.callBack(rs);
            }
        }.start();
    }
    public void getItemUser(String username, OnResultCallBack cb){
        new Thread(){
            @Override
            public void run() {
                List<Item> rs= App.getInstance().getAppDB().itemDao().getItemUserActive(username);
                cb.callBack(rs);
            }
        }.start();
    }

    public void getAllHighScore(OnResultCallBack cb){
        new Thread(){
            @Override
            public void run() {
                List<HighScore> rs= App.getInstance().getAppDB().highScoreDao().getAllHighScoreUser();
                cb.callBack(rs);
            }
        }.start();
    }

    public interface OnResultCallBack{
        void callBack(Object data);
    }
}
