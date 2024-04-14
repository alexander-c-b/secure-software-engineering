package edu.floridapoly.securesoftware.spring24.SocialEngineerGame;

import android.app.Application;
import android.content.Context;

public class App extends Application {

    private static Application instance;

    public static Application getInstance() {
        if (instance == null) {
            instance = new App();
        }
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static Context getContext() {
        return getInstance().getApplicationContext();
    }
}
