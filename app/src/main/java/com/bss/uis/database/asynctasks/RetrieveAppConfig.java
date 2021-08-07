package com.bss.uis.database.asynctasks;

import android.os.AsyncTask;

import com.bss.uis.database.dao.ApplicationDao;
import com.bss.uis.database.entity.AppConfig;

import java.util.List;

import lombok.Getter;

@Getter
public class RetrieveAppConfig extends AsyncTask<Void, Void, List<AppConfig>> {
    private final ApplicationDao applicationDao;
    List<AppConfig> appConfigList;
    AsynResponse asynResponse = null;
    public interface AsynResponse {
        void processFinish(List<AppConfig> appConfigList);
    }

    public RetrieveAppConfig(ApplicationDao applicationDao,AsynResponse asynResponse) {
        this.applicationDao = applicationDao;
        this.asynResponse = asynResponse;
    }
    @Override
    protected List<AppConfig> doInBackground(Void... voids) {
        return applicationDao.findAll();
    }
    @Override
    protected void onPostExecute(List<AppConfig> appConfigs) {
        if (appConfigs!=null && !appConfigs.isEmpty() ){
            appConfigList = appConfigs;
        }
        asynResponse.processFinish(appConfigList);
    }
}
