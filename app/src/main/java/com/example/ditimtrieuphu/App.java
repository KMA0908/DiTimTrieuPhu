package com.example.ditimtrieuphu;

import android.app.Application;

import androidx.room.Room;

import com.example.ditimtrieuphu.database.AppDatabase;

public class App extends Application {
    private static App instance;
    private Storage storage;
    private AppDatabase appDB;

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
