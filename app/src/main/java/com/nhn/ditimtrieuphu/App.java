package com.nhn.ditimtrieuphu;

import android.app.Application;

import androidx.room.Room;

import com.google.android.gms.ads.MobileAds;
import com.nhn.ditimtrieuphu.database.AppDatabase;

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
        appDB = Room.databaseBuilder(this,AppDatabase.class,"Question.db")
                .allowMainThreadQueries()
                .createFromAsset("databases/Question.db").build();

        MobileAds.initialize(this);
    }

    public static App getInstance() {
        if(instance == null) instance = new App();
        return instance;
    }

    public Storage getStorage(){
        return storage;
    }

}
