package com.bss.uis.context;

import android.app.Application;

import com.bss.uis.model.User;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UISApplicationContext extends Application {
    private static final String TAG = UISApplicationContext.class.getSimpleName();
    public static UISApplicationContext instance = null;
    private User user;
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
