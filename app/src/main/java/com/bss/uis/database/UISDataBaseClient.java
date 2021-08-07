package com.bss.uis.database;

import android.content.Context;

import androidx.room.Room;

public class UISDataBaseClient {
    private final Context context;
    private static UISDataBaseClient uisDataBaseClient;

    //our app database object
    private final UISDatabase uisDatabase;

    private UISDataBaseClient(Context mCtx) {
        this.context = mCtx;
        uisDatabase = Room.databaseBuilder(mCtx, UISDatabase.class, "UISDB").build();
    }

    public static synchronized UISDataBaseClient getInstance(Context mCtx) {
        if (uisDataBaseClient == null) {
            uisDataBaseClient = new UISDataBaseClient(mCtx);
        }
        return uisDataBaseClient;
    }

    public UISDatabase getUisDatabase() {
        return uisDatabase;
    }
}
