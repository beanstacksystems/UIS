package com.bss.uis.database.dao;

import com.bss.uis.context.UISApplicationContext;
import com.bss.uis.database.UISDataBaseClient;
import com.bss.uis.database.UISDatabase;
import com.bss.uis.database.asynctasks.InsertAppConfig;
import com.bss.uis.database.asynctasks.RetrieveAppConfig;
import com.bss.uis.database.entity.AppConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ApplicationRepository {
    private ApplicationDao applicationDao;
    public ApplicationRepository(UISApplicationContext context) {
        UISDatabase database = UISDataBaseClient.getInstance(context.getContext()).getUisDatabase();
        applicationDao = database.applicationDao();
    }
    public List<AppConfig> retrieve(){
        final List<AppConfig> appConfigLists = new ArrayList<>();
        RetrieveAppConfig retrieveAppConfig = new RetrieveAppConfig(applicationDao,new RetrieveAppConfig.AsynResponse() {
            @Override
            public void processFinish(List<AppConfig> appConfigList) {
//                appConfigLists.addAll(appConfigList);
            }
        });
        try {
            return retrieveAppConfig.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
    public void insert(AppConfig appConfig) {
        new InsertAppConfig(applicationDao).execute(appConfig);
    }

}

