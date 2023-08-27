package com.example.ditimtrieuphu;

import android.app.Application;

import androidx.room.Room;

import com.example.ditimtrieuphu.database.AppDatabase;

public class App extends Application {
    private static App instance;
    private Storage storage;
    private AppDatabase appDB;

    public int getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }

    private int currentLevel = 0;
    private boolean state50 = false;
    private boolean stateAudi = false;

    public boolean isState50() {
        return state50;
    }

    public void setState50(boolean state50) {
        this.state50 = state50;
    }

    public boolean isStateAudi() {
        return stateAudi;
    }

    public void setStateAudi(boolean stateAudi) {
        this.stateAudi = stateAudi;
    }

    public boolean isStateChange() {
        return stateChange;
    }

    public void setStateChange(boolean stateChange) {
        this.stateChange = stateChange;
    }

    public boolean isStateCall() {
        return stateCall;
    }

    public void setStateCall(boolean stateCall) {
        this.stateCall = stateCall;
    }

    private boolean stateChange = false;
    private boolean stateCall = false;

    public AppDatabase getAppDB() {
        return appDB;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
        storage=new Storage();
        appDB = Room.databaseBuilder(this,AppDatabase.class,"Question").createFromAsset("databases/Question").build();
    }

    public static App getInstance() {
        if(instance == null) instance = new App();
        return instance;
    }

    public Storage getStorage(){
        return storage;
    }

}
