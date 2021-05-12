package com.bss.uis.database.asynctasks;

import android.os.AsyncTask;

import com.bss.uis.database.dao.ApplicationDao;
import com.bss.uis.database.entity.AppConfig;

public class InsertAppConfig extends AsyncTask<AppConfig, Void, Void> {
    private ApplicationDao applicationDao;

    public InsertAppConfig(ApplicationDao applicationDao) {
        this.applicationDao = applicationDao;
    }

    @Override
    protected Void doInBackground(AppConfig... appConfigs) {
        applicationDao.insertAppConfig(appConfigs[0]);
        return null;
    }
}
