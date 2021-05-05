package com.example.ditimtrieuphu;

import android.app.Application;

public class App extends Application {
    private static App instance;
    private String text;
    private Storage storage;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public static App getInstance() {
        if(instance == null) instance = new App();
        return instance;
    }

    public Storage getStorage(){
        return storage;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        storage=new Storage();
    }
}
