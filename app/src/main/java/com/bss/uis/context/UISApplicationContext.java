package com.bss.uis.context;

import android.app.Application;
import android.content.Context;

import com.bss.uis.database.entity.UserRightData;
import com.bss.uis.model.AuthResponse;
import com.bss.uis.model.User;

import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UISApplicationContext extends Application {
    private static final String TAG = UISApplicationContext.class.getSimpleName();
    public static UISApplicationContext instance = null;
    public Context context = null;
    private AuthResponse authResponse;
    private User user;
    private int userCurrentRole;
    private List<UserRightData> userRightDataList;
    private Map<String,String> appConfigMap;
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
