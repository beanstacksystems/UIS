package com.bss.uis.model;

import android.app.Application;


public class UISApplicationContext extends Application {
    private static final String TAG = UISApplicationContext.class.getSimpleName();
    public static UISApplicationContext instance = null;
    public static UISApplicationContext getInstance() {
        if (null == instance) {
            instance = new UISApplicationContext();
        }
        return instance;
    }
    @Override
    public void onCreate() {
        super.onCreate();
    }
}
