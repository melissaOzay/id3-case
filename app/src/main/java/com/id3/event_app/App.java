package com.id3.event_app;

import android.app.Application;

import com.id3.event_app.data.mock.MockDataSource;

import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class App extends Application {

    private static App instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        MockDataSource.getInstance(this);
    }

    public static App getInstance() {
        return instance;
    }
}
